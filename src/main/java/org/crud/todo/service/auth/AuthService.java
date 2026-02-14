package org.crud.todo.service.auth;

import jakarta.transaction.Transactional;
import org.crud.todo.dto.auth.LoginRequest;
import org.crud.todo.dto.auth.RegisterRequest;
import org.crud.todo.helper.ServiceReturnHandler;
import org.crud.todo.model.User;
import org.crud.todo.repository.user.UserRepository;
import org.crud.todo.security.PasswordService;
import org.crud.todo.security.service.CustomUserPrincipal;
import org.crud.todo.security.service.JwtService;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordService passwordService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.jwtService = jwtService;
    }

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalStateException("Username already exists");
        }

        String hashedPassword = passwordService.hashPassword(request.getPassword());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(hashedPassword);
        user.setIs_active(true);
        userRepository.save(user);
    }

    public class LoginResult {
        private String accessToken;
        private String refreshToken;

        public LoginResult(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }
        public String getRefreshToken() {
            return refreshToken;
        }
    }

    public ServiceReturnHandler<LoginResult> login(LoginRequest request) {
        try {
        if (!userRepository.existsByUsername(request.getUsername())) {
            return ServiceReturnHandler.returnError("User not exists", HttpStatus.BAD_REQUEST.value());
        }

        User whereCondition = new User();
        whereCondition.setUsername(request.getUsername());
        User user = userRepository.findOne(Example.of(whereCondition))
                .orElse(null);

        if (user == null) {
            return ServiceReturnHandler.returnError("User not exists", HttpStatus.BAD_REQUEST.value());
        }

        String userHashedPassword = user.getPassword();
        boolean isPasswordValid = passwordService.matchPassword(request.getPassword(), userHashedPassword);

        if (!isPasswordValid) return ServiceReturnHandler.returnError("Invalid credentials", HttpStatus.UNAUTHORIZED.value());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);


        LoginResult result = new LoginResult(accessToken, refreshToken);
        return ServiceReturnHandler.returnSuccess("Login successful", 200, result);
        } catch (Exception e) {
            return ServiceReturnHandler.returnError("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public ServiceReturnHandler verifyUser(Authentication userAuthentication) {
        CustomUserPrincipal principal = (CustomUserPrincipal) userAuthentication.getPrincipal();
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", principal.getId());
        userData.put("username", principal.getUsername());
        return ServiceReturnHandler.returnSuccess("user", HttpStatus.OK.value(), userData);
    }
}
