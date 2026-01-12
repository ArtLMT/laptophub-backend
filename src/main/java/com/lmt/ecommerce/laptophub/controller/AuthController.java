package com.lmt.ecommerce.laptophub.controller;

import com.lmt.ecommerce.laptophub.dto.request.UserLoginRequest;
import com.lmt.ecommerce.laptophub.dto.request.UserRegisterRequest;
import com.lmt.ecommerce.laptophub.dto.response.JwtResponse;
import com.lmt.ecommerce.laptophub.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);

        ResponseCookie jwtCookie = ResponseCookie.from("accessToken", jwtResponse.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(15 * 60)
                .sameSite("Strict")
                .build();

        // 3. Đóng gói Refresh Token vào Cookie
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", jwtResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/api/auth")
                .maxAge(7 * 24 * 60 * 60) // 7 ngày
                .sameSite("Strict")
                .build();

        // 4. Trả về Response kèm Header Set-Cookie
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(jwtResponse); // Vẫn trả body để frontend lấy email/username hiển thị
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest request) {
        // 1. Gọi service (nó sẽ lưu user và trả về token)
        JwtResponse jwtResponse = authService.register(request);

        // 2. Tạo Cookie Access Token
        ResponseCookie jwtCookie = ResponseCookie.from("accessToken", jwtResponse.getAccessToken())
                .httpOnly(true)
                .secure(true) // Set true nếu chạy HTTPS
                .path("/")
                .maxAge(15 * 60) // Config giống login
                .sameSite("Strict")
                .build();

        // 3. Tạo Cookie Refresh Token
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", jwtResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/api/auth")
                .maxAge(7 * 24 * 60 * 60) // Config giống login
                .sameSite("Strict")
                .build();

        // 4. Trả về Response kèm Cookie
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(jwtResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            // Lấy value của cookie có tên "refreshToken".
            // required = false để nếu không có cookie thì code không bị lỗi 500 (vẫn cho logout thành công)
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        if (refreshToken != null && !refreshToken.isEmpty()) {
            authService.logout(refreshToken);
        }

        // 2. Xử lý phía Client: Xóa Cookie bằng cách set MaxAge = 0
        ResponseCookie jwtCookie = ResponseCookie.from("accessToken", "").path("/").maxAge(0).build();
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "").path("/api/auth/refresh").maxAge(0).build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body("You've been signed out!");
    }
}