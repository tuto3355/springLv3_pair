package com.nakta.springlv1.domain.comment.repository;

import com.nakta.springlv1.domain.comment.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    
}
