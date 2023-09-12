package com.nakta.springlv1.domain.board.entity;

import com.nakta.springlv1.domain.board.dto.BoardRequestDto;
import com.nakta.springlv1.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor

public class Board extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "category", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BoardCategory category;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "username", length = 500)
    private String username;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardLike> boardLikeList = new ArrayList<>();

    public Board(BoardRequestDto requestDto, User user) {
        this.category = requestDto.getBoardCategory();
        this.title = requestDto.getTitle();
        this.username = user.getUsername();
        this.content = requestDto.getContent();
        this.user = user;
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
