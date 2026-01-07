package com.lmt.ecommerce.laptophub.mapper;

import com.lmt.ecommerce.laptophub.dto.request.OrderRequest;
import com.lmt.ecommerce.laptophub.dto.response.OrderResponse;
import com.lmt.ecommerce.laptophub.entity.Order;
import com.lmt.ecommerce.laptophub.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    // 1. Map Order -> OrderResponse
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "status", source = "orderStatus")
    OrderResponse toOrderResponse(Order order);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "skuCode", source = "product.skuCode")
    OrderResponse.OrderItemDetail toOrderItemDetail(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Order toOrder(OrderRequest request);
}
