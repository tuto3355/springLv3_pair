package com.nakta.springlv1.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ID_NOT_MATCH(HttpStatus.BAD_REQUEST, "작성자가 일치하지 않습니다"),
    ID_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "선택한 게시글은 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "선택한 댓글은 존재하지 않습니다."),
    Reply_NOT_FOUND(HttpStatus.NOT_FOUND, "선택한 대댓글은 존재하지 않습니다."),
    COMMENT_BOARD_NOT_MATCH(HttpStatus.BAD_REQUEST, "댓글이 게시글에 포함되지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    DUPLICATED_ID(HttpStatus.BAD_REQUEST, "중복된 아이디입니다."),
    ADMINTOKEN_NOT_MATCH(HttpStatus.BAD_REQUEST, "관리자 패스워드가 일치하지 않습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "잘못된 패스워드입니다.");


//    NO_AUTHORITY(HttpStatus.BAD_REQUEST, "권한이 없습니다."),
//    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "관리자 암호가 틀려 등록이 불가능합니다."),

    private final HttpStatus httpStatus;
    private final String message;
}
