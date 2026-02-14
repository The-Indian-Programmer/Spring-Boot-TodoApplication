package org.crud.todo.exception;

import org.crud.todo.dto.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        false,
                        errorMessage,
                        HttpStatus.BAD_REQUEST.value()
                ));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleMissingBody(
            HttpMessageNotReadableException ex) {
                ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "request body is required", HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalState(
            IllegalStateException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
    }


    // ✅ 2️⃣ Handles method-level validation (@PathVariable, @RequestParam)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleHandlerMethodValidation(HandlerMethodValidationException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getAllValidationResults().forEach(result -> result.getResolvableErrors().forEach(error -> errors.put(
                                result.getMethodParameter().getParameterName(),
                                error.getDefaultMessage()
                        )
                )
        );

        return ResponseEntity.badRequest().body(
                new ApiResponse<>(
                        false,
                        "Validation failed",
                        400,
                        errors
                )
        );
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneric(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false,"Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
