package com.lmt.ecommerce.laptophub.exception;

public enum ErrorCode {
    // Auth
    INVALID_CREDENTIALS,
    REFRESH_TOKEN_EXPIRED,
    REFRESH_TOKEN_REVOKED,
    REFRESH_TOKEN_NOT_FOUND,
    UNAUTHENTICATED,

    // User
    USERNAME_ALREADY_EXISTS,
    EMAIL_ALREADY_EXISTS,


    // Product
    PRODUCT_NOT_FOUND,
    PRODUCT_NOT_AVAILABLE,

    // Order
    ORDER_NOT_FOUND,
    ORDER_ALREADY_COMPLETED,
    ORDER_ALREADY_CANCELLED


}
