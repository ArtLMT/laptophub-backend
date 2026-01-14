package com.lmt.ecommerce.laptophub.mapper;

import com.lmt.ecommerce.laptophub.dto.request.OrderRequest;
import com.lmt.ecommerce.laptophub.dto.response.OrderResponse;
import com.lmt.ecommerce.laptophub.entity.Order;
import com.lmt.ecommerce.laptophub.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "status", source = "orderStatus")
    OrderResponse toOrderResponse(Order order);


    @Mapping(target = "productId", source = "productVariant.product.id")
    @Mapping(target = "productName", source = "productVariant.product.name")
    @Mapping(target = "skuCode", source = "productVariant.skuCode") // SKU nằm ở Variant
        // Nếu OrderItemDetail có field price, nhớ lấy từ variant:
        // @Mapping(target = "price", source = "productVariant.price")
    OrderResponse.OrderItemDetail toOrderItemDetail(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Order toOrder(OrderRequest request);
}