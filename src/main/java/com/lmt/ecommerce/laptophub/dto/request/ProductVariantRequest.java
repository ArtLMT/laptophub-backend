package com.lmt.ecommerce.laptophub.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantRequest<T> {

    private String skuCode;

    @NotNull(message = "Price is required")
    @Min(0)
    private BigDecimal price;

    @Valid
    @NotNull(message = "Variant attributes are required")
    private T variantAttributes;
}