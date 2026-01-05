package com.lmt.ecommerce.laptophub.service;

import com.lmt.ecommerce.laptophub.dto.request.CartItemRequest;
import com.lmt.ecommerce.laptophub.dto.response.CartItemResponse;

public interface CartService {
    CartItemResponse addCartItem(Long userId, CartItemRequest request);
    CartItemResponse getCartItem(Long id);
    CartItemResponse updateCartItem(Long id, CartItemRequest request);
    void deleteCartItem(Long id);
}
