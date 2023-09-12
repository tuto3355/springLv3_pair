package com.nakta.springlv1.domain.board.entity;

import com.nakta.springlv1.domain.board.dto.ReplyRequestDto;
import com.nakta.springlv1.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reply")
@Getter
@NoArgsConstructor
public class Reply extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "username", length = 500)
    private String username;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Reply(ReplyRequestDto requestDto, User user, Comment comment) {
        this.contents = requestDto.getContents();
        this.username = user.getUsername();
        this.user = user;
        this.comment = comment;
    }

    public void update(ReplyRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
