package com.hw.config;

import com.hw.shared.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.hw.shared.AppConstant.HTTP_HEADER_ERROR_ID;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DomainExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            UncategorizedMongoDbException.class,
            DuplicateKeyException.class

    })
    protected ResponseEntity<?> handle400Exception(RuntimeException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(ex);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HTTP_HEADER_ERROR_ID, errorMessage.getErrorId());
        return handleExceptionInternal(ex, errorMessage, httpHeaders, HttpStatus.BAD_REQUEST, request);
    }

//    @ExceptionHandler(value = {
//    })
//    protected ResponseEntity<Object> handle500Exception(RuntimeException ex, WebRequest request) {
//        ErrorMessage errorMessage = new ErrorMessage(ex);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set(HTTP_HEADER_ERROR_ID, errorMessage.getErrorId());
//        return handleExceptionInternal(ex, errorMessage, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR, request);
//    }
}
