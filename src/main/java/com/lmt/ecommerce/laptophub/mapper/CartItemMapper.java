package com.lmt.ecommerce.laptophub.mapper;

import com.lmt.ecommerce.laptophub.dto.response.CartItemResponse;
import com.lmt.ecommerce.laptophub.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "skuCode", source = "product.skuCode")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "subTotal", source = ".", qualifiedByName = "calculateSubTotal")
    CartItemResponse toCartItemResponse(CartItem cartItem);

    @Named("calculateSubTotal")
    default BigDecimal calculateSubTotal(CartItem cartItem) {
        if (cartItem.getProduct() == null || cartItem.getProduct().getPrice() == null) {
            return BigDecimal.ZERO;
        }
        return cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
    }
}