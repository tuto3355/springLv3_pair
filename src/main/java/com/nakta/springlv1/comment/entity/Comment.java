package com.nakta.springlv1.comment.entity;

import com.nakta.springlv1.board.entity.Board;
import com.nakta.springlv1.comment.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor //지우기
public class Comment extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String contents;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Board board;

    public Comment(CommentRequestDto requestDto,Board board){
        this.contents = requestDto.getContents();
        this.board=board;
    }
    public Comment(Board board){
        this.board=board;
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
