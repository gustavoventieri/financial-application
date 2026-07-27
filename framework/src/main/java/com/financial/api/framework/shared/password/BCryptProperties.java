package com.financial.api.framework.shared.password;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security.bcrypt")
public record BCryptProperties(
        int strength
) {
}