package com.lmt.ecommerce.laptophub.mapper;

import com.lmt.ecommerce.laptophub.common.ProductType;
import com.lmt.ecommerce.laptophub.dto.request.ProductRequest;
import com.lmt.ecommerce.laptophub.dto.response.LaptopResponse;
import com.lmt.ecommerce.laptophub.dto.response.PhoneResponse;
import com.lmt.ecommerce.laptophub.dto.response.ProductResponse;
import com.lmt.ecommerce.laptophub.dto.response.ProductVariantResponse;
import com.lmt.ecommerce.laptophub.entity.Product;
import com.lmt.ecommerce.laptophub.entity.ProductVariant;
import com.lmt.ecommerce.laptophub.strategy.attribute.AttributeMappingStrategy;
import com.lmt.ecommerce.laptophub.strategy.attribute.AttributeMappingStrategyFactory;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    protected AttributeMappingStrategyFactory strategyFactory;

    @Autowired
    public void setStrategyFactory(AttributeMappingStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public Map<String, Object> mapVariantAttributesToMap(Object dto, ProductType type) {
        if (dto == null) return null;
        AttributeMappingStrategy strategy = strategyFactory.getStrategy(type);
        return strategy.mapVariantToEntity(dto);
    }

    // ================= TO ENTITY (CREATE) =================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commonAttributes", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    public abstract Product toProduct(ProductRequest request);

    @AfterMapping
    protected void mapRequestToEntity(ProductRequest request, @MappingTarget Product product) {
        AttributeMappingStrategy strategy = strategyFactory.getStrategy(request.getType());

        // 1. Map Common Attributes (Product) -> Update vẫn cần chạy cái này -> OK
        product.setCommonAttributes(strategy.mapCommonToEntity(request.getCommonAttributes()));

        // 2. Map Variants List
        if (product.getId() == null && request.getVariants() != null) {
            List<ProductVariant> entityVariants = new ArrayList<>();
            for (var variantReq : request.getVariants()) {
                ProductVariant variantEntity = new ProductVariant();

                variantEntity.setPrice(variantReq.getPrice());
                variantEntity.setSkuCode(variantReq.getSkuCode());
                variantEntity.setProduct(product);

                variantEntity.setVariantAttributes(
                        strategy.mapVariantToEntity(variantReq.getVariantAttributes())
                );

                entityVariants.add(variantEntity);
            }
            product.setVariants(entityVariants);
        }
    }


    @Mapping(target = "commonAttributes", ignore = true)
    @Mapping(target = "variants", ignore = true)
    public abstract ProductResponse toResponse(Product product);

    @AfterMapping
    protected void mapEntityToResponse(Product product, @MappingTarget ProductResponse response) {
        AttributeMappingStrategy strategy = strategyFactory.getStrategy(product.getType());

        // 1. Map Common Attributes
        if (product.getCommonAttributes() != null) {
            Object commonDto = strategy.mapCommonToResponse(product.getCommonAttributes());
            response.setCommonAttributes(commonDto);
        }

        // 2. Map Variants List
        if (product.getVariants() != null) {
            List<ProductVariantResponse> variantResponses = new ArrayList<>();

            for (ProductVariant variantEntity : product.getVariants()) {
                Object variantAttrDto = strategy.mapVariantToResponse(variantEntity.getVariantAttributes());

                ProductVariantResponse<Object> variantResp = new ProductVariantResponse(
                        variantEntity.getSkuCode(),
                        variantEntity.getPrice(),
                        variantAttrDto
                );
                variantResponses.add(variantResp);
            }
            response.setVariants(variantResponses);
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commonAttributes", ignore = true) // <--- QUAN TRỌNG: Update cũng phải ignore
    @Mapping(target = "variants", ignore = true)         // <--- QUAN TRỌNG: Logic update variant rất phức tạp, nên tự xử lý
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    public abstract void updateProduct(@MappingTarget Product product, ProductRequest request);

    @ObjectFactory
    public ProductResponse createProductResponse(Product product) {
        return switch (product.getType()) {
            case LAPTOP -> new LaptopResponse();
            case PHONE -> new PhoneResponse();
            default -> throw new IllegalArgumentException("Unknown type: " + product.getType());
        };
    }


}