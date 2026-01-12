package com.lmt.ecommerce.laptophub.repository;

import com.lmt.ecommerce.laptophub.entity.RefreshToken;
import com.lmt.ecommerce.laptophub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository  extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
    List<RefreshToken> findAllByUserIdAndRevokedFalse(Long userId);
    void deleteByUserId(Long userId);
}
