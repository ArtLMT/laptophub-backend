package com.lmt.ecommerce.laptophub.controller;

import com.lmt.ecommerce.laptophub.dto.request.ProductRequest;
import com.lmt.ecommerce.laptophub.dto.request.ProductVariantRequest;
import com.lmt.ecommerce.laptophub.dto.response.ProductResponse;
import com.lmt.ecommerce.laptophub.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Trả về 201 Created
    public ProductResponse createProduct(@RequestBody @Valid ProductRequest request) {
        return productService.addProduct(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Trả về 204 No Content
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @DeleteMapping("/{productId}/variants/{variantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductVariant(@PathVariable Long productId,
                                     @PathVariable Long variantId) {
        productService.deleteProductVariant(productId, variantId);
    }

    @PostMapping("/{productId}/variants")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addVariant(@PathVariable Long productId,
                                      @RequestBody @Valid ProductVariantRequest<?> request) {
        return productService.addProductVariant(productId, request);
    }
}