package com.lmt.ecommerce.laptophub.service;

import java.util.Map;

/**
 * Service responsible for SKU (Stock Keeping Unit) code generation, validation, and manipulation.
 * Handles all SKU-related business logic in a centralized, testable manner.
 */
public interface SkuService {

    /**
     * Generates a smart SKU code from product name and variant attributes.
     *
     * @param productName the product name
     * @param variantAttributes the variant attributes (ram, storage, color, etc.)
     * @return generated SKU code
     */
    String generateSmartSkuCode(String productName, Map<String, Object> variantAttributes);

    /**
     * Sanitizes a provided SKU code by trimming whitespace.
     *
     * @param skuCode the SKU code to sanitize
     * @return sanitized SKU code, or null if input is empty
     */
    String sanitizeSkuCode(String skuCode);

    /**
     * Resolves the final SKU code - uses provided code if available, otherwise generates one.
     *
     * @param providedSkuCode the SKU code provided by caller
     * @param productName the product name for generation
     * @param variantAttributes the variant attributes for generation
     * @return resolved SKU code
     */
    String resolveSkuCode(String providedSkuCode, String productName, Map<String, Object> variantAttributes);
}

