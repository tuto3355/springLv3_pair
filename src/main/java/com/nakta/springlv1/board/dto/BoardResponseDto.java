package com.nakta.springlv1.board.dto;

import com.nakta.springlv1.board.entity.Board;
import com.nakta.springlv1.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<Comment> commentList;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.commentList = board.getCommentList();
    }
}
