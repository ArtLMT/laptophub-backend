package com.lmt.ecommerce.laptophub.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequest {
    @NotNull(message = "Product ID can't be null")
    private Long productId;

    private Integer quantity;

}
