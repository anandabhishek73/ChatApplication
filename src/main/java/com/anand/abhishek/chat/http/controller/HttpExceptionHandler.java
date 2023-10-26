package com.anand.abhishek.chat.http.controller;

import com.anand.abhishek.chat.http.model.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * All controller exceptions will land here for advice on response propagation
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(value = {WebClientRequestException.class, WebClientException.class})
//    public ResponseEntity<ApiError> handleWebClientException(WebClientException e, WebRequest request) {
//        log.warn("{}: ", e.getClass().getName(), e);
//        ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE, e);
//        return new ResponseEntity<>(apiError, HttpStatus.SERVICE_UNAVAILABLE);
//    }
//
//    @ExceptionHandler(value = {WebClientResponseException.class})
//    public ResponseEntity<ApiError> handleWebClientResponseException(WebClientResponseException e, WebRequest request) {
//        log.warn("{}: ", e.getClass().getName(), e);
//
//        HttpStatus mappedStatus = BaseHttpClient.getMappedStatus(e.getStatusCode());
//        ApiError apiError = new ApiError(mappedStatus, e);
//        return new ResponseEntity<>(apiError, mappedStatus);
//    }
//
//    @ExceptionHandler(value = {HttpException.class})
//    public ResponseEntity<ApiError> handleHttpException(HttpException e, WebRequest request) {
//        log.warn("{}: ", e.getClass().getName(), e);
//
//        HttpStatus mappedStatus = BaseHttpClient.getMappedStatus(e.getHttpStatusCode());
//        ApiError apiError = new ApiError(mappedStatus, e, e.getResponseBody());
//        return new ResponseEntity<>(apiError, mappedStatus);
//    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.warn("handleMethodArgumentNotValid for API : {}  | \nHeaders {} | \nParams : {}",
                request.getDescription(false),
                request.getParameterNames(),
                request.getParameterMap());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.warn("handleHttpMessageNotReadable for API : {}  | \nHeaders {} | \nParams : {}",
                request.getDescription(false),
                request.getParameterNames(),
                request.getParameterMap());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String message = String.format("API not supported: %s %s", ex.getHttpMethod(), ex.getRequestURL());
        log.warn(message);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND, message, ex);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String message = String.format("API supported media types are : %s", ex.getSupportedMediaTypes());
        log.warn(message);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND, message, ex);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.warn("Request Internal Exception: ", ex);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(status, ex, body);
        return new ResponseEntity<>(apiErrorResponse, headers, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAll(Exception e, WebRequest request) {

        log.error("Request Unhandled Exception : ", e);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {

        log.error("handleArgumentTypeMismatchException Exception : ", ex);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Failed to convert from type: " + ex.getParameter().getParameterName()
                + " for value: " + ex.getValue(), ex);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }
}