package com.nakta.springlv1.comment.entity;

import com.nakta.springlv1.board.entity.Board;
import com.nakta.springlv1.comment.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
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

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Board board;

    public Comment(CommentRequestDto requestDto,Board board){
        this.contents = requestDto.getContents();
        this.board = board;
        this.board.setId(requestDto.getPostId());
    }

    public void setBoard(Board board) {
        this.board = board;
        board.getCommentList().add(this);
    }




    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
