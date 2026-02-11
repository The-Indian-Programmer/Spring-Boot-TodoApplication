package org.crud.todo.helper;

public class ServiceReturnHandler<T> {

    private final boolean status;
    private final String message;
    private final int statusCode;
    private final T data;

    public ServiceReturnHandler(boolean status, String message, int statusCode, T data) {
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public ServiceReturnHandler(boolean status, String message, int statusCode) {
        this(status, message, statusCode, null);
    }

    public static <T> ServiceReturnHandler<T> returnSuccess(String message, int statusCode, T data) {
        return new ServiceReturnHandler<>(true, message, statusCode, data);
    }

    public static <T> ServiceReturnHandler<T> returnSuccess(String message, int statusCode) {
        return new ServiceReturnHandler<>(true, message, statusCode, null);
    }

    public static <T> ServiceReturnHandler<T> returnError(String message, int statusCode) {
        return new ServiceReturnHandler<>(false, message, statusCode, null);
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
