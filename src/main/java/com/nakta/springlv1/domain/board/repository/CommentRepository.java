package com.nakta.springlv1.domain.board.repository;

import com.nakta.springlv1.domain.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardIdOrderByCreatedAtDesc(Long boardId);
}
