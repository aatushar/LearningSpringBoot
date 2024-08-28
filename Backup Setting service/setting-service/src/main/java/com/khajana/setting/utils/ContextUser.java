package com.khajana.setting.utils;

import com.khajana.setting.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class ContextUser {

    public static CustomUserDetails getLoginUserData() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            return null;

        CustomUserDetails userData = (CustomUserDetails) auth.getPrincipal();
        if (userData != null) {
            return userData;
        } else {
            return null;
        }
    }

}
