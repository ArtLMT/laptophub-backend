package com.lmt.ecommerce.laptophub.service;

import com.lmt.ecommerce.laptophub.dto.request.UserLoginRequest;
import com.lmt.ecommerce.laptophub.dto.request.UserRegisterRequest;
import com.lmt.ecommerce.laptophub.dto.response.JwtResponse;

public interface AuthService {
    JwtResponse login(UserLoginRequest request);
    JwtResponse refreshAccessToken(String refreshTokenRequest);
    JwtResponse register(UserRegisterRequest request);
    void logout(String refreshToken);
}
