package com.lmt.ecommerce.laptophub.repository;

import com.lmt.ecommerce.laptophub.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySkuCode(String skuCode);
    Optional<Product> findBySkuCode(String skuCode);
}
