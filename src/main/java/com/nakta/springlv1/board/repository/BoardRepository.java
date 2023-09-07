package com.nakta.springlv1.board.repository;

import com.nakta.springlv1.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    public List<Board> findAllByOrderByModifiedAtDesc();


}