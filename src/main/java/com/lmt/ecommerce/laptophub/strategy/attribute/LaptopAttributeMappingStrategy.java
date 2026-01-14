package com.lmt.ecommerce.laptophub.strategy.attribute;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmt.ecommerce.laptophub.attribute.laptop.LaptopCommonAttribute;
import com.lmt.ecommerce.laptophub.attribute.laptop.LaptopVariantAttribute;
import com.lmt.ecommerce.laptophub.common.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LaptopAttributeMappingStrategy
        implements AttributeMappingStrategy {

    private final ObjectMapper objectMapper;

    private static final TypeReference<Map<String, Object>> ATTRIBUTE_MAP_TYPE =
            new TypeReference<Map<String, Object>>() {};

    @Override
    public ProductType getSupportedType() {
        return ProductType.LAPTOP;
    }

    // 1. DTO chung -> Map lưu DB
    @Override
    public Map<String, Object> mapCommonToEntity(Object dto) {
        return objectMapper.convertValue(dto, ATTRIBUTE_MAP_TYPE);
    }

    // 2. Map từ DB -> DTO chung trả về Client
    @Override
    public LaptopCommonAttribute mapCommonToResponse(Map<String, Object> map) {
        return objectMapper.convertValue(map, LaptopCommonAttribute.class);
    }

    // 3. DTO Variant -> Map lưu DB
    @Override
    public Map<String, Object> mapVariantToEntity(Object dto) {
        return objectMapper.convertValue(dto, ATTRIBUTE_MAP_TYPE);
    }

    // 4. Map từ DB -> DTO variant
    @Override
    public LaptopVariantAttribute mapVariantToResponse(Map<String, Object> map) {
        return objectMapper.convertValue(map, LaptopVariantAttribute.class);
    }
}