//package com.lmt.ecommerce.laptophub.controller;
//
//import com.lmt.ecommerce.laptophub.dto.request.CartItemRequest;
//import com.lmt.ecommerce.laptophub.dto.response.CartItemResponse;
//import com.lmt.ecommerce.laptophub.service.CartService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/carts")
//@RequiredArgsConstructor
//public class CartController {
//    private final CartService cartService;
//
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public CartItemResponse getCartItemsById(@PathVariable Long id) {
//        return cartService.getCartItem(id);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public CartItemResponse addCartItem(@RequestBody CartItemRequest request) {
//        return cartService.addCartItem(request);
//    }
//
//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public CartItemResponse updateCartItem(@PathVariable Long id, @RequestBody CartItemRequest request) {
//        return cartService.updateCartItem(id, request);
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteCartItem(@PathVariable Long id) {
//        cartService.deleteCartItem(id);
//    }
//}
