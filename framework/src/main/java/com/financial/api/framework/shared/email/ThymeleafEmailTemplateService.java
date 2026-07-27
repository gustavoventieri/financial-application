package com.financial.api.framework.shared.email;

import com.financial.api.shared.email.EmailTemplateUseCase;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class ThymeleafEmailTemplateService
        implements EmailTemplateUseCase {


    private final TemplateEngine templateEngine;

    public ThymeleafEmailTemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }


    @Override
    public String buildVerificationEmail(String otpCode) {

        Context context = new Context();

        context.setVariable(
                "otpCode",
                otpCode
        );

        return templateEngine.process(
                "verification-email",
                context
        );
    }
}