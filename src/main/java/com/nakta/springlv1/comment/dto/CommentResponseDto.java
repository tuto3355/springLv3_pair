package com.nakta.springlv1.comment.dto;

import com.nakta.springlv1.comment.entity.Comment;
import com.nakta.springlv1.user.entity.User;

import java.time.LocalDateTime;

public class CommentResponseDto {
    Long id;
    String contents;
    private LocalDateTime modifiedAt; // 게시글 수정 날짜
    private LocalDateTime createAt; // 게시글 생성 날짜
    private String username;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.modifiedAt = comment.getModifiedAt();
        this.createAt = comment.getCreatedAt();
        this.username = comment.getBoard().getUsername();
    }
}

