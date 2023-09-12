package com.nakta.springlv1.domain.board.repository;

import com.nakta.springlv1.domain.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    
}
