package com.lmt.ecommerce.laptophub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequest {
    @NotBlank(message = "Name can't be empty")
    private String name;

    @NotBlank(message = "SKU Code can't be empty")
    private String skuCode;

    @NotBlank(message = "Price can't be empty")
    private BigDecimal price;

    private boolean isFlashSale;
}
