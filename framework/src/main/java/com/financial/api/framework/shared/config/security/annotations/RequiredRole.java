package com.financial.api.framework.shared.config.security.annotations;

import com.financial.api.shared.enumerated.Roles;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@roleAuthorization.check(authentication, #root.method.getAnnotation(T(com.financial.api.framework.shared.config.security.annotations.RequiredRole)).value())")
public @interface RequiredRole {

    Roles[] value();
}