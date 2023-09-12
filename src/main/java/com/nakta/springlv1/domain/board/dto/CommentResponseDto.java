package com.nakta.springlv1.domain.board.dto;

import com.nakta.springlv1.domain.board.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponseDto {
    private Long id;
    private Long postId;
    private String contents;
    private int likes;
    private LocalDateTime modifiedAt; // 게시글 수정 날짜
    private LocalDateTime createdAt; // 게시글 생성 날짜
    private String username;
    private List<ReplyResponseDto> replyList;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getBoard().getId();
        this.username = comment.getUsername();
        this.contents = comment.getContents();
        this.likes = comment.getCommentLikeList().size();
        this.modifiedAt = comment.getModifiedAt();
        this.createdAt = comment.getCreatedAt();
        this.replyList = comment.getReplyList().stream().map(ReplyResponseDto::new).toList();
    }
}

