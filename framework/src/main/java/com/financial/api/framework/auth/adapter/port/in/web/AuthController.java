package com.financial.api.framework.auth.adapter.port.in.web;


import com.financial.api.auth.application.dto.response.AuthResponse;
import com.financial.api.auth.application.port.in.sign.*;
import com.financial.api.framework.auth.adapter.dto.EmailVerificationRequestValidator;
import com.financial.api.framework.auth.adapter.dto.SignInRequestValidator;
import com.financial.api.framework.auth.adapter.dto.SignUpRequestValidator;
import com.financial.api.framework.shared.dto.response.ControllerResponseDTO;
import com.financial.api.framework.shared.handler.dto.ErrorResponse;
import com.financial.api.shared.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Controller")
public class AuthController {

    private final SignInUseCase signInUseCase;
    private final SignUpUseCase signUpUseCase;
    private final VerifyEmailVerificationTokenUseCase verifyEmailVerificationTokenUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final RevokeRefreshTokenUseCase revokeRefreshTokenUseCase;
    private final SignOutUseCase signOutUseCase;

    public AuthController(
            SignInUseCase signInUseCase,
            SignUpUseCase signUpUseCase,
            VerifyEmailVerificationTokenUseCase verifyEmailVerificationTokenUseCase,
            RefreshTokenUseCase refreshTokenUseCase,
            RevokeRefreshTokenUseCase revokeRefreshTokenUseCase,
            SignOutUseCase signOutUseCase

    ){

        this.signInUseCase = signInUseCase;
        this.signUpUseCase = signUpUseCase;
        this.verifyEmailVerificationTokenUseCase = verifyEmailVerificationTokenUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.revokeRefreshTokenUseCase = revokeRefreshTokenUseCase;
        this.signOutUseCase = signOutUseCase;
    }


    @Operation(
            summary = "Sign in",
            description = "Authenticate a user and return access and refresh tokens."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authenticated successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Email not verified",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                    )),

    })
    @PostMapping("/sign-in")
    public ResponseEntity<ControllerResponseDTO<String>> signIn(
            HttpServletResponse servletResponse,
            @Valid @RequestBody SignInRequestValidator request,
            HttpServletRequest httpRequest
    ) {
        String ip = getClientIp(httpRequest);
        String device = getDevice(httpRequest);

        AuthResponse response = signInUseCase.execute(request.email(), request.password(), ip, device);

        addAuthCookies(servletResponse, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ControllerResponseDTO<>("Login successful", null));
    }


    @PostMapping("/sign-up")
    @Operation(
            summary = "Sign Up",
            description = "Create a user and send a OTP code through email."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User Created and OTP Code Sent"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Email already registered",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),

    })
    public ResponseEntity<ControllerResponseDTO<String>> signup(
            @Valid @RequestBody SignUpRequestValidator request
    ) {

        String message = signUpUseCase.execute(request.name(), request.email(), request.password());

        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponseDTO<>(message, null));
    }


    @Operation(
            summary = "Verify email",
            description = "Verify a user's email using the verification code and return access and refresh tokens."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Email verified successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Verification code expired or already used",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid verification code",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PostMapping("/verify-email")
    public ResponseEntity<ControllerResponseDTO<String>> verifyEmail(
            HttpServletResponse servletResponse,
            @Valid @RequestBody EmailVerificationRequestValidator request,
            HttpServletRequest httpRequest
    ) {

        String ip = getClientIp(httpRequest);
        String device = getDevice(httpRequest);

        AuthResponse response = verifyEmailVerificationTokenUseCase.execute(
                request.codeOtp(),
                request.email(),
                ip,
                device
        );

        addAuthCookies(servletResponse, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ControllerResponseDTO<>("Email verified successfully", null));
    }

    @PostMapping("/sessions/refresh")
    @Operation(
            summary = "Refresh access token",
            description = "Generate a new access token using the refresh token stored in the authentication cookie."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Access token refreshed successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid or expired refresh token",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<ControllerResponseDTO<String>> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String refreshToken = getRefreshToken(request);

        try {

            AuthResponse authResponse =
                    refreshTokenUseCase.execute(refreshToken);

            addAuthCookies(response, authResponse);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ControllerResponseDTO<>(
                            "Token refreshed successfully",
                            null
                    ));

        } catch (BusinessException ex) {

            clearAuthCookies(response);

            throw ex;
        }
    }

    @PostMapping("/sessions/sign-out")
    @Operation(
            summary = "Logout",
            description = "Revoke the current session and clear authentication cookies."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Logged out successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid refresh token",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<ControllerResponseDTO<String>> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String refreshToken = getRefreshToken(request);

        try {

            signOutUseCase.execute(refreshToken);

        } finally {

            clearAuthCookies(response);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new ControllerResponseDTO<>(
                                "Logout successful",
                                null
                        )
                );
    }

    @DeleteMapping("/sessions/revoke/{sessionId}")
    @Operation(
            summary = "Revoke session",
            description = "Revoke a specific authenticated session."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Session revoked successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Session not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    public ResponseEntity<ControllerResponseDTO<String>> revokeSession(
            @PathVariable String sessionId,
            Authentication authentication
    ) {

        String userId = authentication.getName();

        revokeRefreshTokenUseCase.execute(
                sessionId,
                userId
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new ControllerResponseDTO<>(
                                "Session revoked successfully",
                                null
                        )
                );
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

    public static void addAuthCookies(
            HttpServletResponse response,
            AuthResponse authResponse
    ) {

        ResponseCookie accessTokenCookie = ResponseCookie
                .from("access_token", authResponse.accessToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie
                .from("refresh_token", authResponse.refreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofDays(30))
                .build();

        response.addHeader(
                "Set-Cookie",
                accessTokenCookie.toString()
        );

        response.addHeader(
                "Set-Cookie",
                refreshTokenCookie.toString()
        );
    }

    public static void clearAuthCookies(HttpServletResponse response) {

        ResponseCookie accessTokenCookie = ResponseCookie
                .from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie
                .from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();

        response.addHeader(
                "Set-Cookie",
                accessTokenCookie.toString()
        );

        response.addHeader(
                "Set-Cookie",
                refreshTokenCookie.toString()
        );
    }

    private String getRefreshToken(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new BusinessException("Refresh token not found");
        }

        for (Cookie cookie : cookies) {

            if ("refresh_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }



        throw new BusinessException("Refresh token not found");
    }


}
