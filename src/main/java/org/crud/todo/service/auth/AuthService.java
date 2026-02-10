package org.crud.todo.service.auth;

import jakarta.transaction.Transactional;
import org.crud.todo.dto.auth.RegisterRequest;
import org.crud.todo.model.User;
import org.crud.todo.repository.user.UserRepository;
import org.crud.todo.security.PasswordService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public AuthService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
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
}
