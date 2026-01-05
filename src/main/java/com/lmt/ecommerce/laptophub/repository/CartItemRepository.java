package com.lmt.ecommerce.laptophub.repository;

import com.lmt.ecommerce.laptophub.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT c FROM CartItem c JOIN FETCH c.product WHERE c.user.id = :userId" ) // Lấy luôn Product
    List<CartItem> findAllByUserId(Long userId);

    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
    void deleteAllByUserId(Long userId);
}
