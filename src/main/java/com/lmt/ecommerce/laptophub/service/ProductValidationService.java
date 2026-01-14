package com.lmt.ecommerce.laptophub.service;

import com.lmt.ecommerce.laptophub.entity.Product;
import com.lmt.ecommerce.laptophub.entity.ProductVariant;
import com.lmt.ecommerce.laptophub.dto.request.ProductRequest;

import java.util.Set;

/**
 * Service for validating product and variant operations.
 * Centralizes all validation logic to keep the main service clean.
 */
public interface ProductValidationService {

    /**
     * Validates that product type has not changed.
     *
     * @param existingProduct the existing product
     * @param updateRequest the update request
     * @throws IllegalArgumentException if type has changed
     */
    void validateProductTypeNotChanged(Product existingProduct, ProductRequest updateRequest);

    /**
     * Validates that variant SKU is unique within the request and system.
     *
     * @param skuCode the SKU code to validate
     * @param processedSkuCodes set of already processed SKUs in this request
     * @param skuExistenceChecker function to check if SKU exists in database
     * @throws IllegalArgumentException if SKU is duplicate in request
     * @throws IllegalArgumentException if SKU already exists in system
     */
    void validateSkuCodeUniqueness(String skuCode, Set<String> processedSkuCodes, SkuExistenceChecker skuExistenceChecker);

    /**
     * Validates that variant belongs to the specified product.
     *
     * @param variant the variant to check
     * @param productId the expected product ID
     * @throws IllegalArgumentException if variant doesn't belong to product
     */
    void validateVariantBelongsToProduct(ProductVariant variant, Long productId);

    /**
     * Functional interface for checking SKU existence in database.
     */
    @FunctionalInterface
    interface SkuExistenceChecker {
        boolean existsBySkuCode(String skuCode);
    }
}

