package com.nakta.springlv1.domain.board.dto;

import com.nakta.springlv1.domain.board.entity.BoardCategory;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    private Long id;
    private BoardCategory boardCategory;
    private String title;
    private String username;
    private String content;
}
