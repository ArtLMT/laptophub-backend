package com.lmt.ecommerce.laptophub.mapper;

import com.lmt.ecommerce.laptophub.dto.response.OrderResponse;
import com.lmt.ecommerce.laptophub.entity.Order;
import com.lmt.ecommerce.laptophub.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "items", source = "orderItems")
    @Mapping(target = "status", source = "orderStatus")
    @Mapping(target = "totalPrice", source = "totalAmount")
    OrderResponse toOrderResponse(Order order);

    // Hàm này chuyển đổi từng thằng OrderItem
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "skuCode", source = "product.skuCode")
    @Mapping(target = "priceAtPurchase", source = "priceAtPurchase")
    OrderResponse.OrderItemDetail toOrderItemDetail(OrderItem orderItem);
}
