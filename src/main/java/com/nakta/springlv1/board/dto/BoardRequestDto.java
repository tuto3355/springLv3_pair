package com.nakta.springlv1.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private Long id;
    private String title;
    private String username;
    private String content;
}
