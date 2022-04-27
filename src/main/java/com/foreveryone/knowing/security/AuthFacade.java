package com.foreveryone.knowing.security;

import com.foreveryone.knowing.entity.auth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    public Integer getCurrentUserId() {
        return getCurrentUser().getId();
    }

}
