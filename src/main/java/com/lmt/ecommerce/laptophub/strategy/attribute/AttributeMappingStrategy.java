package com.lmt.ecommerce.laptophub.strategy.attribute;

import com.lmt.ecommerce.laptophub.common.ProductType;
import java.util.Map;

public interface AttributeMappingStrategy {

    ProductType getSupportedType();

    Map<String, Object> mapCommonToEntity(Object dto);

    Object mapCommonToResponse(Map<String, Object> map);

    Map<String, Object> mapVariantToEntity(Object dto);

    Object mapVariantToResponse(Map<String, Object> map);
}