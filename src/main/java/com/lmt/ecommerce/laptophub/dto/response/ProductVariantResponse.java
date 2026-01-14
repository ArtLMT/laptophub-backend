package com.lmt.ecommerce.laptophub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantResponse<T> {
    private String skuCode;
    private BigDecimal price;

    private T variantAttributes;
}