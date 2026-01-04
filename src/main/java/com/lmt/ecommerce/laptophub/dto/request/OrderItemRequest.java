package com.lmt.ecommerce.laptophub.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {

    @NotNull(message = "Order ID can't be null")
    private Long orderId;

    @NotNull(message = "Product ID can't be null")
    private Long productId;

    private Integer quantity;
}
