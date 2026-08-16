package com.financial.api.framework.shared.config.security.annotations;

import com.financial.api.shared.enumerated.Roles;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredRole {

    Roles[] value();
}