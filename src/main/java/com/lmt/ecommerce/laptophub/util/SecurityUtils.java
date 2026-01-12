package com.lmt.ecommerce.laptophub.util;

import com.lmt.ecommerce.laptophub.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    // Private constructor để ngăn việc new SecurityUtils()
    private SecurityUtils() {}

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal() instanceof String) {
            throw new RuntimeException("User not authenticated or is anonymous");
        }

        return (User) authentication.getPrincipal();
    }
}