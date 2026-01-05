package com.lmt.ecommerce.laptophub.service.impl;

import com.lmt.ecommerce.laptophub.dto.request.ProductRequest;
import com.lmt.ecommerce.laptophub.dto.response.ProductResponse;
import com.lmt.ecommerce.laptophub.entity.Product;
import com.lmt.ecommerce.laptophub.mapper.ProductMapper;
import com.lmt.ecommerce.laptophub.repository.ProductRepository;
import com.lmt.ecommerce.laptophub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponse addProduct(ProductRequest request) {
        Product product = mapper.toProduct(request);

        if (product.getSkuCode() == null || product.getSkuCode().isEmpty()) {
            product.setSkuCode(generateSkuCode(request.getName()));
        }

        // Lưu xuống DB
        Product savedProduct = productRepository.save(product);
        return mapper.toProductResponse(savedProduct);
    }

    @Override
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        return mapper.toProductResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot update. Product not found with id: " + id));

        mapper.updateProduct(productToUpdate, request);

        Product updatedProduct = productRepository.save(productToUpdate);

        return mapper.toProductResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Product not found with id: " + id);
        }

        productRepository.deleteById(id);
    }

    // Hàm phụ trợ sinh SKU
    private String generateSkuCode(String name) {
        // Logic đơn giản: Lấy 3 chữ cái đầu + random
        String prefix = (name != null && name.length() >= 3) ? name.substring(0, 3).toUpperCase() : "PRO";
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
