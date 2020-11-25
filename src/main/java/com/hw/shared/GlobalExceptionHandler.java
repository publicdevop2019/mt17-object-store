package com.hw.shared;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.hw.shared.AppConstant.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            TransactionSystemException.class,
            IllegalArgumentException.class,
            JwtTokenExtractException.class,
            MaxPageSizeExceedException.class,
            EntityNotExistException.class,
            EntityOutdatedException.class
    })
    protected ResponseEntity<Object> handle400Exception(RuntimeException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(ex);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HTTP_HEADER_ERROR_ID, errorMessage.getErrorId());
        return handleExceptionInternal(ex, errorMessage, httpHeaders, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {
            RuntimeException.class,
            JwtTokenRetrievalException.class,
    })
    protected ResponseEntity<Object> handle500Exception(RuntimeException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(ex);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HTTP_HEADER_ERROR_ID, errorMessage.getErrorId());
        return handleExceptionInternal(ex, errorMessage, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {
            DataIntegrityViolationException.class,
    })
    protected ResponseEntity<Object> handle200Exception(RuntimeException ex, WebRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HTTP_HEADER_SUPPRESS, HTTP_HEADER_SUPPRESS_REASON_CHANGE_ID_EXIST);
        return handleExceptionInternal(ex, null, httpHeaders, HttpStatus.OK, request);
    }
}
