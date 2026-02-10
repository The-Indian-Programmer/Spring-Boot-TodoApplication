package org.crud.todo.dto.common;

public class ApiResponse {

    private boolean status;
    private String message;

    public ApiResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
