package com.financial.api.framework.auth.adapter.in.web;


import com.financial.api.auth.application.dto.response.AuthResponse;
import com.financial.api.auth.application.port.in.sign.SignInUseCase;
import com.financial.api.framework.auth.adapter.dto.SignInRequestValidator;
import com.financial.api.framework.shared.handler.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Controller")
public class AuthController {

    private final SignInUseCase signInUseCase;

    public AuthController(SignInUseCase signInUseCase){
        this.signInUseCase = signInUseCase;
    }


    @Operation(
            summary = "Sign in",
            description = "Authenticate a user and return access and refresh tokens."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authenticated successfully"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @ApiResponse(
                    responseCode = "403",
                    description = "Email not verified",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(
            @Valid @RequestBody SignInRequestValidator request,
            HttpServletRequest httpRequest
    ) {
        String ip = getClientIp(httpRequest);
        String device = getDevice(httpRequest);

        AuthResponse response = signInUseCase.execute(request.email(), request.password(),ip, device);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // Helpers
    private String getDevice(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    private String getClientIp(HttpServletRequest request) {

        String forwarded = request.getHeader("X-Forwarded-For");

        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }

}
