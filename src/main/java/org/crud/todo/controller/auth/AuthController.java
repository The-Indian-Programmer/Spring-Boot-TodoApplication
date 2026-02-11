package org.crud.todo.controller.auth;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.crud.todo.dto.auth.LoginRequest;
import org.crud.todo.dto.auth.RegisterRequest;
import org.crud.todo.dto.common.ApiResponse;
import org.crud.todo.helper.ServiceReturnHandler;
import org.crud.todo.service.auth.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "User registered successfully", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        ServiceReturnHandler<AuthService.LoginResult> result = authService.login(request);
        if (!result.isStatus()) return ResponseEntity.status(result.getStatusCode()).body(new ApiResponse<>(false, result.getMessage(), result.getStatusCode()));

        ResponseCookie cookie = ResponseCookie.from("refreshToken", result.getData().getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(7 * 24 * 3600)
                .build();

        Map<String, String> returnData = new HashMap<>();
        returnData.put("accessToken", result.getData().getAccessToken());
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ApiResponse<>(true, "Login successful", 200, returnData));
    }
}
