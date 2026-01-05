package com.lmt.ecommerce.laptophub.dto.response;

import com.lmt.ecommerce.laptophub.entity.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    // OrderItemDetail chỉ chứa chi tiết OrderItem
    private List<OrderItemDetail> items;
    // Làm kiểu này thay vì trả về OrderItem để tránh lập vô hạn
    // Vì thằng này luôn đi kèm với cha nó, kh có vụ đứng lẻ loi 1 mình nên để làm Inner class như này giúp dễ đọc cấu trúc hơn, nhìn phát biết ngay 1 Order có 1 đống orderItem
    @Data
    public static class OrderItemDetail {
        private Long productId;
        private String productName;
        private String skuCode;
        private Integer quantity;
        private BigDecimal priceAtPurchase;
    }
    // Nhưng mà nếu sau này cần làm mấy tính năng mà lấy được OrderItem kh kèm theo Order thì phải tách ra, hoặc OrderItemDetail có 1 nùi thông tin

    private OrderStatus status;
    private BigDecimal totalPrice;
}
