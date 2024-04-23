package com.socialmedia.main.application.util;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static String getUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
