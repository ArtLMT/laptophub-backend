package com.lmt.ecommerce.laptophub.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
    private Long id;
    private Long productId;
    private Integer quantity;

    private String productName;
    private String skuCode;
    private BigDecimal price;
    private BigDecimal subTotal;
}
