//package com.lmt.ecommerce.laptophub.service.impl;
//
//import com.lmt.ecommerce.laptophub.dto.request.OrderRequest;
//import com.lmt.ecommerce.laptophub.dto.response.OrderResponse;
//import com.lmt.ecommerce.laptophub.entity.*;
//import com.lmt.ecommerce.laptophub.mapper.OrderMapper;
//import com.lmt.ecommerce.laptophub.repository.CartItemRepository;
//import com.lmt.ecommerce.laptophub.repository.InventoryRepository;
//import com.lmt.ecommerce.laptophub.repository.OrderRepository;
//import com.lmt.ecommerce.laptophub.repository.ProductRepository;
//import com.lmt.ecommerce.laptophub.service.OrderService;
//import com.lmt.ecommerce.laptophub.util.SecurityUtils;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class OrderServiceImpl implements OrderService {
//
//    private final OrderRepository orderRepository;
//    private final CartItemRepository cartItemRepository;
//    private final InventoryRepository inventoryRepository;
//    private final OrderMapper orderMapper;
//
//    @Override
//    @Transactional
//    public OrderResponse createOrder(OrderRequest request) {
//        // 1. Lấy User
//        User user = SecurityUtils.getCurrentUser();
//
//        List<CartItem> cartItems = cartItemRepository.findAllByUserId(user.getId());
//        if (cartItems.isEmpty()) {
//            throw new RuntimeException("Cart is empty");
//        }
//
//        Order order = new Order();
//        order.setUser(user);
//        order.setOrderStatus(OrderStatus.PENDING);
//        order.setTotalAmount(BigDecimal.ZERO);
//
//        order.setShippingAddress(request.getShippingAddress());
//        order.setPhoneNumber(request.getPhoneNumber());
//        order.setPaymentMethod(request.getPaymentMethod());
//        order.setNote(request.getNote());
//
//        List<OrderItem> orderItems = new ArrayList<>();
//        BigDecimal totalAmount = BigDecimal.ZERO;
//
//        for (CartItem cartItem : cartItems) {
//            Product product = cartItem.getProduct();
//            int buyQty = cartItem.getQuantity();
//
//
//            Inventory inventory = inventoryRepository.findByProduct(product)
//                    .orElseThrow(() -> new RuntimeException("No product found in Inventory"));
//
//            if (inventory.getQuantity() < buyQty) {
//                throw new RuntimeException("Quantity not enough in stock");
//            }
//
//            inventory.setQuantity(inventory.getQuantity() - buyQty);
//            inventoryRepository.save(inventory);
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            orderItem.setProduct(product);
//            orderItem.setQuantity(buyQty);
//
//            orderItem.setPriceAtPurchase(product.getPrice());
//
//            orderItems.add(orderItem);
//
//            BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(buyQty));
//            totalAmount = totalAmount.add(itemTotal);
//        }
//
//        order.setOrderItems(orderItems);
//        order.setTotalAmount(totalAmount);
//
//        Order savedOrder = orderRepository.save(order);
//        cartItemRepository.deleteAll(cartItems);
//
//        return orderMapper.toOrderResponse(savedOrder);
//    }
//    @Override
//    public List<OrderResponse> getAllOrdersByUserId(Long userId) {
//        // Lưu ý: Dù tham số truyền vào là userId, nhưng để an toàn
//        // ta nên lấy ID từ SecurityContext để đảm bảo user chỉ xem được đơn của mình.
//        User currentUser = SecurityUtils.getCurrentUser();
//
//        List<Order> orders = orderRepository.findAllByUserId(currentUser.getId());
//
//        return orders.stream()
//                .map(orderMapper::toOrderResponse)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<OrderResponse.OrderItemDetail> getAllOrderItemsByOrderId(Long orderId) {
//        // Reuse hàm getOrder để tận dụng logic kiểm tra quyền sở hữu
//        OrderResponse orderResponse = getOrder(orderId);
//        return orderResponse.getItems();
//    }
//
//    @Override
//    public OrderResponse getOrder(Long id) {
//        User currentUser = SecurityUtils.getCurrentUser();
//
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
//
//        // SECURITY CHECK: Không cho xem đơn hàng của người khác
//        if (!order.getUser().getId().equals(currentUser.getId())) {
//            throw new RuntimeException("User not authorized to view this order.");
//        }
//
//        return orderMapper.toOrderResponse(order);
//    }
//
//    @Override
//    @Transactional
//    public void deleteOrder(Long id) {
//        User currentUser = SecurityUtils.getCurrentUser();
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        if (!order.getUser().getId().equals(currentUser.getId())) {
//            throw new RuntimeException("You are not authorized to delete this order.");
//        }
//
//        orderRepository.delete(order);
//    }
//}