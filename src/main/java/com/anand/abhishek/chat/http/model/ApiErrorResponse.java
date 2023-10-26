package com.anand.abhishek.chat.http.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiErrorResponse extends ApiResponse{

    private final HttpStatus httpStatus;
    private final String message;
    private final Object metadata;
    private final List<String> errors;
    private final List<String> errorTypes;

    public ApiErrorResponse(HttpStatus httpStatus, String message, Exception error, Object metadata) {
        super(httpStatus.isError() ? "failure": "success");
        this.httpStatus = httpStatus;
        this.message = message;
        this.metadata = metadata;
        this.errors = new ArrayList<>();
        this.errorTypes = new ArrayList<>();
        Throwable rootCause = error;
        do {
            errors.add(rootCause.getLocalizedMessage());
            errorTypes.add(rootCause.getClass().getName());
            rootCause = rootCause.getCause();
        } while (rootCause != null && rootCause.getCause() != rootCause);
    }

    public ApiErrorResponse(HttpStatus httpStatus, String message, Exception error) {
        this(httpStatus, message, error, null);
    }

    public ApiErrorResponse(HttpStatus httpStatus, Exception error, Object metadata) {
        this(httpStatus, error.getMessage(),
                error, metadata);
    }

    public ApiErrorResponse(HttpStatus httpStatus, Exception error) {
        this(httpStatus, error, null);
    }
}
