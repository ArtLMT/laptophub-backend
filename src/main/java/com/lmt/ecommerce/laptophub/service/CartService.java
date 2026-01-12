package com.lmt.ecommerce.laptophub.service;

import com.lmt.ecommerce.laptophub.dto.request.CartItemRequest;
import com.lmt.ecommerce.laptophub.dto.response.CartItemResponse;

import java.util.List;
import java.util.Optional;

public interface CartService {
    CartItemResponse addCartItem(CartItemRequest request);
    CartItemResponse getCartItem(Long id);
    CartItemResponse updateCartItem(Long id, CartItemRequest request);
    void deleteCartItem(Long id);
    List<CartItemResponse> getCartItemByUserId(Long userId);
}
