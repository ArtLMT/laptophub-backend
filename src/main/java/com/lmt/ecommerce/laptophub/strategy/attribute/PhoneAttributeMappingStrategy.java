package com.lmt.ecommerce.laptophub.strategy.attribute;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmt.ecommerce.laptophub.attribute.phone.PhoneCommonAttribute;
import com.lmt.ecommerce.laptophub.attribute.phone.PhoneVariantAttribute;
import com.lmt.ecommerce.laptophub.common.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PhoneAttributeMappingStrategy implements AttributeMappingStrategy {

    private final ObjectMapper objectMapper;

    private static final TypeReference<Map<String, Object>> ATTRIBUTE_MAP_TYPE =
            new TypeReference<Map<String, Object>>() {};

    @Override
    public ProductType getSupportedType() {
        return ProductType.PHONE;
    }

    @Override
    public Map<String, Object> mapCommonToEntity(Object dto) {
        return objectMapper.convertValue(dto, ATTRIBUTE_MAP_TYPE);
    }

    @Override
    public PhoneCommonAttribute mapCommonToResponse(Map<String, Object> map) {
        return objectMapper.convertValue(map, PhoneCommonAttribute.class);
    }

    // --- VARIANT ---
    @Override
    public Map<String, Object> mapVariantToEntity(Object dto) {
        return objectMapper.convertValue(dto, ATTRIBUTE_MAP_TYPE);
    }

    @Override
    public PhoneVariantAttribute mapVariantToResponse(Map<String, Object> map) {
        return objectMapper.convertValue(map, PhoneVariantAttribute.class);
    }
}