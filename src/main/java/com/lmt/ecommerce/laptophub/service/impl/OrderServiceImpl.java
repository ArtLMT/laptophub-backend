package com.lmt.ecommerce.laptophub.service.impl;

import com.lmt.ecommerce.laptophub.dto.request.OrderRequest;
import com.lmt.ecommerce.laptophub.dto.response.OrderResponse;
import com.lmt.ecommerce.laptophub.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        return null;
    }

    @Override
    public OrderResponse getOrder(Long id) {
        return null;
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest request) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public List<OrderResponse> getAllOrdersByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<OrderResponse.OrderItemDetail> getAllOrderItemsByOrderId(Long orderId) {
        return List.of();
    }
}
