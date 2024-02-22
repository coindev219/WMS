package com.warehouse.helper;

import com.warehouse.administration.user.User;
import com.warehouse.config.OAuthUserDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class WarehouseHelper {

    public static User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuthUserDetails userDetails = (OAuthUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    } 
}
