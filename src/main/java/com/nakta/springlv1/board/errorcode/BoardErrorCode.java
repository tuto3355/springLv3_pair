package com.nakta.springlv1.board.errorcode;

import com.nakta.springlv1.error.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode implements ErrorCode { //enum 클래스 이해 필요

    ID_NOT_MATCH(HttpStatus.BAD_REQUEST, "작성자가 일치하지 않습니다");
//    ID_NOT_MATCH(HttpStatus.NOT_FOUND, "아이디를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
