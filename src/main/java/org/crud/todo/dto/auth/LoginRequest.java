package org.crud.todo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {


    @NotBlank
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]+$",
            message = "Username can contain letters, numbers, dot, underscore and hyphen only"
    )
    private String user_name;

    @NotBlank(message = "Password is required")
    private String password;

    public String getUsername() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.user_name = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
