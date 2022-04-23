package com.snl.blogbooster.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    BadRequest(HttpStatus.BAD_REQUEST, "BadRequest"),
    Unauthorized(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    NoContent(HttpStatus.NO_CONTENT, "NoContent"),
    NotFound(HttpStatus.NOT_FOUND, "NotFound"),
    NotAcceptable(HttpStatus.NOT_ACCEPTABLE, "NotAcceptable"),
    Conflict(HttpStatus.CONFLICT, "Conflict"),
    TooManyRequest(HttpStatus.TOO_MANY_REQUESTS, "TooManyRequest"),
    NotMatchRequest(HttpStatus.BAD_REQUEST, "NotMatchRequest"),
    InternalServerError(HttpStatus.INTERNAL_SERVER_ERROR, "InternalServerError"),
    InvalidBodyError(HttpStatus.BAD_REQUEST, "InvalidBodyError"),
    InvalidParameterError(HttpStatus.BAD_REQUEST, "InvalidParameterError");

    private final HttpStatus status;
    private final String code;

    ErrorCode(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }
}
