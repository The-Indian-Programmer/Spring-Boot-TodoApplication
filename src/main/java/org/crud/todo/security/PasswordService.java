package org.crud.todo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PasswordService {

    private final BCryptPasswordEncoder passwordEncoder;

    public  PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder(10);

    }

    public  String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matchPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }
}
