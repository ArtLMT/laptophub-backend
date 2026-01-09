package com.lmt.ecommerce.laptophub.service;

import com.lmt.ecommerce.laptophub.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token) ;
    void deleteByUserId(Long userId);
}
