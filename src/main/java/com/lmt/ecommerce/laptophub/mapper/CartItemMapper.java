package com.lmt.ecommerce.laptophub.mapper;

import com.lmt.ecommerce.laptophub.dto.response.CartItemResponse;
import com.lmt.ecommerce.laptophub.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    // Lấy thông tin từ "Ông nội" (Product)
    @Mapping(target = "productId", source = "productVariant.product.id")
    @Mapping(target = "productName", source = "productVariant.product.name")

    // Lấy thông tin từ "Cha" (ProductVariant)
    @Mapping(target = "skuCode", source = "productVariant.skuCode")
    @Mapping(target = "price", source = "productVariant.price")

    @Mapping(target = "subTotal", source = ".", qualifiedByName = "calculateSubTotal")
    CartItemResponse toCartItemResponse(CartItem cartItem);

    @Named("calculateSubTotal")
    default BigDecimal calculateSubTotal(CartItem cartItem) {
        if (cartItem.getProductVariant() == null || cartItem.getProductVariant().getPrice() == null) {
            return BigDecimal.ZERO;
        }
        return cartItem.getProductVariant().getPrice()
                .multiply(new BigDecimal(cartItem.getQuantity()));
    }
}