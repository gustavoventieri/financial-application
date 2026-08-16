package com.financial.api.framework.shared.config.security.annotations;

import com.financial.api.shared.enumerated.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("roleAuthorization")
public class RoleAuthorization {

    public boolean check(
            Authentication authentication,
            Roles[] requiredRoles
    ) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        return Arrays.stream(requiredRoles)
                .anyMatch(requiredRole ->
                        authentication.getAuthorities()
                                .stream()
                                .anyMatch(authority ->
                                        authority.getAuthority()
                                                .equals("ROLE_" + requiredRole.name())
                                )
                );
    }
}