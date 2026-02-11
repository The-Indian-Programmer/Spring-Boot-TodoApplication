package  org.crud.todo.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean status;
    private String message;
    private int statusCode;
    private T data;

    public ApiResponse(boolean status, String message, int statusCode, T data) {
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public ApiResponse(boolean status, String message, int statusCode) {
        this(status, message, statusCode, null);
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }
}