package com.snl.blogbooster.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Builder
@Getter
public class HttpErrorResponse {

    private ErrorResponse errorResponse;

    public static HttpErrorResponse of(RestException e){
        return HttpErrorResponse.builder()
                .errorResponse(ErrorResponse.builder()
                        .status(e.getErrorCode().getStatus().value())
                        .code(e.getErrorCode().getCode())
                        .message(e.getMessage())
                        .build())
                .build();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ErrorResponse{

        private int status;
        private String code;
        private String message;
    }
}
