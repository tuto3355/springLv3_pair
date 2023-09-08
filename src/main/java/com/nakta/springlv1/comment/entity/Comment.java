package com.nakta.springlv1.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nakta.springlv1.board.entity.Board;
import com.nakta.springlv1.comment.dto.CommentRequestDto;
import com.nakta.springlv1.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "comments")
@Getter

@NoArgsConstructor //지우기
public class Comment extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "username", length = 500)
    private String username;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Board board;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Comment(CommentRequestDto requestDto,Board board,String subject){
        this.contents = requestDto.getContents();
        this.board = board;
        this.username = subject;

    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
