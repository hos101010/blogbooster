package com.snl.blogbooster.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<HttpErrorResponse> exceptionHandler(RestException e) {
        log.debug("RestException");
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(HttpErrorResponse.of(e));
    }

    // 400 BAD_REQUEST
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<HttpErrorResponse> ConstraintViolationException(Exception ex) {
        log.debug("ConstraintViolationException");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(HttpErrorResponse.of(new RestException(ErrorCode.NotMatchRequest, ex.getMessage())));
    }

    // 406 NOT_ACCEPTABLE
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<HttpErrorResponse> HttpMediaTypeNotAcceptableException(Exception ex) {
        log.debug("HttpMediaTypeNotAcceptableException");
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(HttpErrorResponse.of(new RestException(ErrorCode.NotAcceptable, ex.getMessage())));
    }

    // 405 METHOD_NOT_ALLOWED
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpErrorResponse> HttpRequestMethodNotSupportedException(Exception ex) {
        log.debug("HttpRequestMethodNotSupportedException");
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(HttpErrorResponse.of(new RestException(ErrorCode.InternalServerError, ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> exceptionHandler(Exception ex) {
        log.debug("Exception.class : {}, {}", ex.getMessage(), ex.getStackTrace());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpErrorResponse.of(new RestException(ErrorCode.InternalServerError, ex.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpErrorResponse> exceptionHandler(MethodArgumentNotValidException ex) {
        log.debug("Exception.MethodArgumentNotValidException : {}", MethodArgumentNotValidException.class.toString());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(HttpErrorResponse.of(new RestException(ErrorCode.InvalidBodyError, ex.getMessage())));
    }
}
