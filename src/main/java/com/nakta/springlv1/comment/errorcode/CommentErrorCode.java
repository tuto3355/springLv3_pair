package com.nakta.springlv1.comment.errorcode;

import com.nakta.springlv1.error.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode { //enum 클래스 이해 필요


    ID_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"선택한 게시글은 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"선택한 댓글은 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST,"토큰이 유효하지 않습니다."),
    NO_AUTHORITY(HttpStatus.BAD_REQUEST,"권한이 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;

}
