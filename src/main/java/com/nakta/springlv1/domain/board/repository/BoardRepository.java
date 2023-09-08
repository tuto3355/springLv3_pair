package com.nakta.springlv1.domain.board.repository;

import com.nakta.springlv1.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    public List<Board> findAllByOrderByModifiedAtDesc();


}