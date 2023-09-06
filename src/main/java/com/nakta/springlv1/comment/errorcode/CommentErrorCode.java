package com.nakta.springlv1.comment.errorcode;

import com.nakta.springlv1.error.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode { //enum 클래스 이해 필요

    DUPLICATED_ID(HttpStatus.BAD_REQUEST, "중복된 아이디입니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "잘못된 패스워드입니다."),
    ID_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
