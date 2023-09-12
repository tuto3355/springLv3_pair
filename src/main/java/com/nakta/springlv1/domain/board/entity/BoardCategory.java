package com.nakta.springlv1.domain.board.entity;

import lombok.Getter;

@Getter
public enum BoardCategory {
    A("축구"),
    B("농구"),
    C("야구"),
    D("피구"),
    E("구구");

    private final String title;

    BoardCategory(String title) {
        this.title = title;
    }

}
