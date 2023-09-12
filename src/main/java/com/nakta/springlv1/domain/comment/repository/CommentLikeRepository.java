package com.nakta.springlv1.domain.comment.repository;

import com.nakta.springlv1.domain.comment.entity.Comment;
import com.nakta.springlv1.domain.comment.entity.CommentLike;
import com.nakta.springlv1.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);

    void deleteByUserAndComment(User user, Comment comment);
}
