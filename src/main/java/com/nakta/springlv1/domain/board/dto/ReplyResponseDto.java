package com.nakta.springlv1.domain.board.dto;

import com.nakta.springlv1.domain.board.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponseDto {
    private Long id;
    private Long commentId;
    private String contents;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private String username;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.commentId = reply.getComment().getId();
        this.username = reply.getUsername();
        this.contents = reply.getContents();
        this.modifiedAt = reply.getModifiedAt();
        this.createdAt = reply.getCreatedAt();
    }
}
