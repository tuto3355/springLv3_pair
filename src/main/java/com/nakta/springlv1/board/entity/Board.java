package com.nakta.springlv1.board.entity;

import com.nakta.springlv1.board.dto.BoardRequestDto;
import com.nakta.springlv1.comment.entity.Comment;
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

public class Board extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "title", nullable = false, length = 500)
    private String title;
    @Column(name = "username", length = 500)
    private String username;
    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @OneToMany(mappedBy = "board",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();
    public List<Comment> getCommentList() {
        return commentList;
    }

    public Board(BoardRequestDto requestDto, String subject) {
        this.title = requestDto.getTitle();
        this.username = subject;
        this.content = requestDto.getContent();
    }

    public void update(BoardRequestDto requestDto, String subject) {
        this.title = requestDto.getTitle();
        this.username = subject;
        this.content = requestDto.getContent();
    }
}
