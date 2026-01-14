package com.lmt.ecommerce.laptophub.strategy.attribute;

import com.lmt.ecommerce.laptophub.common.ProductType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AttributeMappingStrategyFactory {

    private final Map<ProductType, AttributeMappingStrategy> strategies;

    public AttributeMappingStrategyFactory(List<AttributeMappingStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        AttributeMappingStrategy::getSupportedType,
                        Function.identity()
                ));
    }

    @SuppressWarnings("unchecked")
    public AttributeMappingStrategy getStrategy(ProductType type) {
        AttributeMappingStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("No AttributeMappingStrategy for type: " + type);
        }
        return (AttributeMappingStrategy) strategy;
    }
}

