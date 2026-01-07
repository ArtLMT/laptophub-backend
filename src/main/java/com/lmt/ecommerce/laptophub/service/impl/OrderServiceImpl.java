package com.lmt.ecommerce.laptophub.service.impl;

import com.lmt.ecommerce.laptophub.dto.request.OrderRequest;
import com.lmt.ecommerce.laptophub.dto.response.OrderResponse;
import com.lmt.ecommerce.laptophub.entity.*;
import com.lmt.ecommerce.laptophub.mapper.OrderMapper;
import com.lmt.ecommerce.laptophub.repository.CartItemRepository;
import com.lmt.ecommerce.laptophub.repository.OrderRepository;
import com.lmt.ecommerce.laptophub.repository.UserRepository;
import com.lmt.ecommerce.laptophub.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest request) {
        // 1. Lấy User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Lấy toàn bộ sản phẩm trong giỏ hàng của User
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty, cannot place order");
        }

        // 3. Tạo Order (Entity)
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.ZERO); // Tính sau

        // 4. Chuyển đổi CartItem -> OrderItem
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getPrice());
            orderItems.add(orderItem);

            // Tính tổng tiền: price * quantity
            BigDecimal itemTotal = cartItem.getProduct().getPrice()
                    .multiply(new BigDecimal(cartItem.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems);

        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getAllOrdersByUserId(Long userId) {
        return null;
    }

    @Override
    public List<OrderResponse.OrderItemDetail> getAllOrderItemsByOrderId(Long orderId) {
        return null;
    }

    @Override
    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public void deleteOrder(Long id) {

        orderRepository.deleteById(id);
    }

}