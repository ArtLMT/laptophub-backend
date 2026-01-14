package com.lmt.ecommerce.laptophub.repository;

import com.lmt.ecommerce.laptophub.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    Optional<ProductVariant> findBySkuCode(String skuCode);
    boolean existsBySkuCode(@Param("skuCode") String skuCode);

    @Query(value = "SELECT * FROM product_variants WHERE sku_code = :skuCode", nativeQuery = true)
    Optional<ProductVariant> findBySkuCodeIncludeDeleted(@Param("skuCode") String skuCode);

}
