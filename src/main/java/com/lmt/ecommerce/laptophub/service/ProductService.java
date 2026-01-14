package com.lmt.ecommerce.laptophub.service;

import com.lmt.ecommerce.laptophub.dto.request.ProductRequest;
import com.lmt.ecommerce.laptophub.dto.request.ProductVariantRequest;
import com.lmt.ecommerce.laptophub.dto.response.ProductResponse;

public interface ProductService{
    ProductResponse addProduct(ProductRequest request);
    ProductResponse getProduct(Long id);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
    void deleteProductVariant(Long productId, Long variantId);
    // Add a single variant to existing product
    ProductResponse addProductVariant(Long productId, ProductVariantRequest<?> request);
}
