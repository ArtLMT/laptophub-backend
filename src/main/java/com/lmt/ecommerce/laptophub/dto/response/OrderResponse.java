package com.lmt.ecommerce.laptophub.dto.response;

import com.lmt.ecommerce.laptophub.entity.OrderStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private BigDecimal totalPrice;
}
