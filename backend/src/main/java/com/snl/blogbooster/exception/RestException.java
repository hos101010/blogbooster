package com.snl.blogbooster.exception;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException{

    private ErrorCode errorCode;

    public RestException(ErrorCode errorCode)
    {
        this.errorCode = errorCode;
    }

    public RestException(ErrorCode errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }

}
