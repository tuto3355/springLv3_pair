package com.nakta.springlv1.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyRequestDto {
    private Long postId;
    private Long commentId;
    private String contents;
}