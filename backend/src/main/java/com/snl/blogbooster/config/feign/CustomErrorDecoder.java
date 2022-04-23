package com.snl.blogbooster.config.feign;

import com.snl.blogbooster.exception.ErrorCode;
import com.snl.blogbooster.exception.RestException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        if(400 <= response.status() && response.status() <500)
        {
            return new RestException(ErrorCode.BadRequest);
        }
        else if(500 <= response.status() && response.status() <600)
        {
            return new RestException(ErrorCode.InternalServerError);
        }

        return new RestException(ErrorCode.InternalServerError);
    }
}

