package com.lmt.ecommerce.laptophub.service.impl;

import com.lmt.ecommerce.laptophub.dto.request.UserLoginRequest;
import com.lmt.ecommerce.laptophub.dto.request.UserRegisterRequest;
import com.lmt.ecommerce.laptophub.dto.response.JwtResponse;
import com.lmt.ecommerce.laptophub.entity.RefreshToken;
import com.lmt.ecommerce.laptophub.entity.User;
import com.lmt.ecommerce.laptophub.exception.ErrorCode;
import com.lmt.ecommerce.laptophub.repository.UserRepository;
import com.lmt.ecommerce.laptophub.security.JwtUtils;
import com.lmt.ecommerce.laptophub.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse login(UserLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtUtils.generateJwtToken(user.getEmail());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new JwtResponse(accessToken, refreshToken.getToken(), user.getEmail());
    }

    @Override
    public JwtResponse refreshAccessToken(String refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newAccessToken = jwtUtils.generateJwtToken(user.getEmail());
                    return new JwtResponse(newAccessToken, refreshTokenRequest, user.getEmail());
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    @Override
    public JwtResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already used");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        String accessToken = jwtUtils.generateJwtToken(savedUser.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUser.getId());

        return new JwtResponse(accessToken, refreshToken.getToken(), savedUser.getEmail());
    }

    @Override
    public void logout(String refreshToken) {
//        refreshTokenService.deleteByUserId(userId);
        refreshTokenService.revokeRefreshToken(refreshToken);
    }

}
