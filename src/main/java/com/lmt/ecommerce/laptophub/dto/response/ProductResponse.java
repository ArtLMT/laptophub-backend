package com.lmt.ecommerce.laptophub.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String skuCode;
    private BigDecimal price;
    private boolean isFlashSale;
}
