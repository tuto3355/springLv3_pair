package com.nakta.springlv1.error.response;

import com.nakta.springlv1.error.errorcode.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    private final String errorName;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.errorName = errorCode.name();
        this.message = errorCode.getMessage();
    }
}
