package com.lmt.ecommerce.laptophub.service.impl;

import com.lmt.ecommerce.laptophub.dto.request.ProductRequest;
import com.lmt.ecommerce.laptophub.entity.Product;
import com.lmt.ecommerce.laptophub.entity.ProductVariant;
import com.lmt.ecommerce.laptophub.service.ProductValidationService;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Implementation of product validation service.
 * Handles all validation logic for product and variant operations.
 */
@Service
public class ProductValidationServiceImpl implements ProductValidationService {

    @Override
    public void validateProductTypeNotChanged(Product existingProduct, ProductRequest updateRequest) {
        if (existingProduct.getType() != updateRequest.getType()) {
            throw new IllegalArgumentException("Product type cannot be changed after creation");
        }
    }

    @Override
    public void validateSkuCodeUniqueness(String skuCode, Set<String> processedSkuCodes, SkuExistenceChecker skuExistenceChecker) {
        String upperCaseSkuCode = skuCode.toUpperCase();

        if (!processedSkuCodes.add(upperCaseSkuCode)) {
            throw new IllegalArgumentException("Variant with SKU '" + skuCode + "' already exists in this request");
        }

        if (skuExistenceChecker.existsBySkuCode(skuCode)) {
            throw new IllegalArgumentException("Variant with SKU '" + skuCode + "' already exists in the system");
        }
    }

    @Override
    public void validateVariantBelongsToProduct(ProductVariant variant, Long productId) {
        if (!variant.getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("Variant does not belong to product with id: " + productId);
        }
    }
}

