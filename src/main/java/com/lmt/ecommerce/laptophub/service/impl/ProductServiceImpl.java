package com.lmt.ecommerce.laptophub.service.impl;

import com.lmt.ecommerce.laptophub.dto.request.ProductRequest;
import com.lmt.ecommerce.laptophub.dto.request.ProductVariantRequest;
import com.lmt.ecommerce.laptophub.dto.response.ProductResponse;
import com.lmt.ecommerce.laptophub.entity.Product;
import com.lmt.ecommerce.laptophub.entity.ProductVariant;
import com.lmt.ecommerce.laptophub.mapper.ProductMapper;
import com.lmt.ecommerce.laptophub.repository.ProductRepository;
import com.lmt.ecommerce.laptophub.repository.ProductVariantRepository;
import com.lmt.ecommerce.laptophub.service.ProductService;
import com.lmt.ecommerce.laptophub.service.ProductValidationService;
import com.lmt.ecommerce.laptophub.service.SkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final SkuService skuService;
    private final ProductValidationService validationService;

    @Override
    @Transactional
    public ProductResponse addProduct(ProductRequest request) {
        Product product = mapper.toProduct(request);

        if (!product.getVariants().isEmpty()) {
            processAndValidateVariantsForNewProduct(product);
        }

        Product savedProduct = productRepository.save(product);

        return mapper.toResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        return mapper.toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product existingProduct = productRepository.findByIdWithVariants(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        validationService.validateProductTypeNotChanged(existingProduct, request);
        mapper.updateProduct(existingProduct, request);

        if (request.getVariants() != null && !request.getVariants().isEmpty()) {
            mergeProductVariants(existingProduct, request);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return mapper.toResponse(updatedProduct);
    }


    private void processAndValidateVariantsForNewProduct(Product product) {
        List<ProductVariant> variantsToProcess = new ArrayList<>(product.getVariants());
        product.getVariants().clear();
        Set<String> processedSkuCodes = new HashSet<>();

        variantsToProcess.forEach(variant -> {
            String resolvedSkuCode = skuService.resolveSkuCode(variant.getSkuCode(), product.getName(), variant.getVariantAttributes());
            validationService.validateSkuCodeUniqueness(resolvedSkuCode, processedSkuCodes, productVariantRepository::existsBySkuCode);

            variant.setSkuCode(resolvedSkuCode);
            variant.setProduct(product);
            product.addVariant(variant);
        });
    }

    private void mergeProductVariants(Product product, ProductRequest request) {
        request.getVariants().forEach(variantRequest -> processAndMergeVariant(product, variantRequest));
    }

    private void processAndMergeVariant(Product product, ProductVariantRequest<?> variantRequest) {
        String sanitizedSkuCode = skuService.sanitizeSkuCode(variantRequest.getSkuCode());
        Map<String, Object> variantAttributes = mapper.mapVariantAttributesToMap(
                variantRequest.getVariantAttributes(),
                product.getType()
        );

        Optional<ProductVariant> existingVariant = findVariantInProductBySkuCode(product, sanitizedSkuCode);

        if (existingVariant.isPresent()) {
            updateExistingVariant(existingVariant.get(), variantRequest, variantAttributes);
        } else {
            addNewVariantToProduct(product, sanitizedSkuCode, variantRequest, variantAttributes);
        }
    }


    private Optional<ProductVariant> findVariantInProductBySkuCode(Product product, String skuCode) {
        if (skuCode == null) {
            return Optional.empty();
        }
        return product.getVariants().stream()
                .filter(variant -> variant.getSkuCode().equals(skuCode))
                .findFirst();
    }

    private void updateExistingVariant(ProductVariant variant, ProductVariantRequest<?> request, Map<String, Object> attributes) {
        variant.setPrice(request.getPrice());
        variant.setVariantAttributes(attributes);
    }

    private void addNewVariantToProduct(Product product, String providedSkuCode, ProductVariantRequest<?> request, Map<String, Object> attributes) {
//        if (providedSkuCode != null) {
//            throw new IllegalArgumentException("Variant with SKU '" + providedSkuCode + "' does not exist in this product");
//        }

        String generatedSkuCode = skuService.generateSmartSkuCode(product.getName(), attributes);

        if (productVariantRepository.existsBySkuCode(generatedSkuCode)) {
            throw new IllegalStateException("Variant with generated SKU '" + generatedSkuCode + "' already exists in the system");
        }

        ProductVariant newVariant = new ProductVariant();
        newVariant.setProduct(product);
        newVariant.setSkuCode(generatedSkuCode);
        newVariant.setPrice(request.getPrice());
        newVariant.setVariantAttributes(attributes);

        product.addVariant(newVariant);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteProductVariant(Long productId, Long variantId) {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new IllegalArgumentException("Variant not found with id: " + variantId));

        validationService.validateVariantBelongsToProduct(variant, productId);
        productVariantRepository.delete(variant);
    }


    @Override
    @Transactional
    public ProductResponse addProductVariant(Long productId, ProductVariantRequest<?> request) {
        Product product = productRepository.findByIdWithVariants(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        Map<String, Object> variantAttributes = mapper.mapVariantAttributesToMap(
                request.getVariantAttributes(),
                product.getType()
        );

        String resolvedSkuCode = skuService.resolveSkuCode(request.getSkuCode(), product.getName(), variantAttributes);
        handleVariantAddition(product, resolvedSkuCode, request, variantAttributes);

        Product savedProduct = productRepository.save(product);
        return mapper.toResponse(savedProduct);
    }


    private void handleVariantAddition(Product product, String skuCode, ProductVariantRequest<?> request, Map<String, Object> attributes) {
        Optional<ProductVariant> existingDeletedVariant = productVariantRepository.findBySkuCodeIncludeDeleted(skuCode);

        if (existingDeletedVariant.isPresent()) {
            reviveAndReassignVariant(product, existingDeletedVariant.get(), request, attributes);
        } else {
            validateAndAddNewVariant(product, skuCode, request, attributes);
        }
    }

    private void reviveAndReassignVariant(Product product, ProductVariant deletedVariant, ProductVariantRequest<?> request, Map<String, Object> attributes) {
        deletedVariant.setDeleted(false);
        deletedVariant.setPrice(request.getPrice());
        deletedVariant.setVariantAttributes(attributes);
        deletedVariant.setProduct(product);

        boolean isAlreadyLinked = product.getVariants().stream()
                .anyMatch(v -> Objects.equals(v.getId(), deletedVariant.getId()));

        if (!isAlreadyLinked) {
            product.addVariant(deletedVariant);
        }
    }

    private void validateAndAddNewVariant(Product product, String skuCode, ProductVariantRequest<?> request, Map<String, Object> attributes) {
        if (hasSkuConflictInProduct(product, skuCode) || productVariantRepository.existsBySkuCode(skuCode)) {
            throw new IllegalStateException("Variant with SKU '" + skuCode + "' already exists");
        }

        ProductVariant newVariant = createNewVariant(product, skuCode, request, attributes);
        product.addVariant(newVariant);
    }

    private boolean hasSkuConflictInProduct(Product product, String skuCode) {
        return product.getVariants().stream()
                .anyMatch(variant -> variant.getSkuCode().equalsIgnoreCase(skuCode));
    }

    private ProductVariant createNewVariant(Product product, String skuCode, ProductVariantRequest<?> request, Map<String, Object> attributes) {
        ProductVariant newVariant = new ProductVariant();
        newVariant.setProduct(product);
        newVariant.setSkuCode(skuCode);
        newVariant.setPrice(request.getPrice());
        newVariant.setVariantAttributes(attributes);
        return newVariant;
    }
}

