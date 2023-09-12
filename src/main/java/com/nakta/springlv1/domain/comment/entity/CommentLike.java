package com.nakta.springlv1.domain.comment.entity;

import com.nakta.springlv1.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "commentLike")
@Getter
@NoArgsConstructor
public class CommentLike {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
