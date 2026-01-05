package com.lmt.ecommerce.laptophub.service.impl;

import com.lmt.ecommerce.laptophub.dto.request.CartItemRequest;
import com.lmt.ecommerce.laptophub.dto.response.CartItemResponse;
import com.lmt.ecommerce.laptophub.entity.CartItem;
import com.lmt.ecommerce.laptophub.entity.Product;
import com.lmt.ecommerce.laptophub.entity.User;
import com.lmt.ecommerce.laptophub.mapper.CartItemMapper;
import com.lmt.ecommerce.laptophub.repository.CartItemRepository;
import com.lmt.ecommerce.laptophub.repository.ProductRepository;
import com.lmt.ecommerce.laptophub.repository.UserRepository;
import com.lmt.ecommerce.laptophub.service.CartService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartItemRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemMapper mapper;

    @Override
    @Transactional
    public CartItemResponse addCartItem(Long userId, CartItemRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));

        Optional<CartItem> existedItem = cartRepository.findByUserIdAndProductId(userId, request.getProductId());

        CartItem cartItem;
        if (existedItem.isPresent()) {
            cartItem = existedItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = new CartItem(user, product, request.getQuantity());
            cartRepository.save(cartItem);
        }

        return mapper.toCartItemResponse(cartItem);
    }

    @Override
    public CartItemResponse getCartItem(Long id) {
        CartItem cartItem = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));

        return mapper.toCartItemResponse(cartItem);
    }

    @Override
    @Transactional
    public CartItemResponse updateCartItem(Long id, CartItemRequest request) {
        CartItem cartItemToUpdate = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot update. CartItem not found with id: " + id));

        cartItemToUpdate.setQuantity(request.getQuantity());

        cartRepository.save(cartItemToUpdate);

        return mapper.toCartItemResponse(cartItemToUpdate);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartRepository.deleteById(id);
    }
}
