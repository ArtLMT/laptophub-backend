package com.lmt.ecommerce.laptophub.service;

import com.lmt.ecommerce.laptophub.dto.request.OrderRequest;
import com.lmt.ecommerce.laptophub.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse getOrder(Long id);
    OrderResponse updateOrder(Long id, OrderRequest request);
    void deleteOrder(Long id);

    List<OrderResponse> getAllOrdersByUserId(Long userId);
    List<OrderResponse.OrderItemDetail> getAllOrderItemsByOrderId(Long orderId);
}
