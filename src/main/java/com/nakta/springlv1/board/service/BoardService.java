package com.nakta.springlv1.board.service;

import com.nakta.springlv1.board.dto.BoardRequestDto;
import com.nakta.springlv1.board.dto.BoardResponseDto;
import com.nakta.springlv1.comment.entity.Comment;
import com.nakta.springlv1.comment.repository.CommentRepository;
import com.nakta.springlv1.error.exception.CustomException;
import com.nakta.springlv1.user.dto.StringResponseDto;
import com.nakta.springlv1.board.entity.Board;
import com.nakta.springlv1.user.entity.User;
import com.nakta.springlv1.user.jwt.JwtUtil;
import com.nakta.springlv1.board.repository.BoardRepository;
import com.nakta.springlv1.user.jwt.UserRoleEnum;
import com.nakta.springlv1.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest req) {

        //토큰 검증
        String tokenValue = validateToken(req);
        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

        User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() -> {
            throw new IllegalArgumentException("아이디가 없음");
        });

        Board board = new Board(requestDto, user); //username을 따로 받기 위한 생성자 생성
        Board newboard = boardRepository.save(board);
        return new BoardResponseDto(newboard);
    }

    public List<BoardResponseDto> getAllBoard() {
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }
    public BoardResponseDto getOneBoard(Long id) {
        Board board = findById(id);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto modifyBoard(Long id, BoardRequestDto requestDto, HttpServletRequest req) {

        //토큰 검증
        String tokenValue = validateToken(req);

        //작성자 일치 확인
        Board board = findById(id);
        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        String authority = info.get("auth", String.class);

        if (authority.equals("ADMIN")||info.getSubject().equals(board.getUsername())) {
            board.update(requestDto);
            return new BoardResponseDto(board);
        } else {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다");
        }
    }

    public StringResponseDto deleteBoard(Long id, HttpServletRequest req) {

        //토큰 검증
        String tokenValue = validateToken(req);
        //작성자 일치 확인
        Board board = findById(id);
        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        String authority = info.get("auth", String.class);

        if (authority.equals("ADMIN")||info.getSubject().equals(board.getUsername())) {
            boardRepository.deleteById(id);
            return new StringResponseDto("삭제를 성공하였음");
        } else {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다");
        }

    }

    private Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }

    private String validateToken(HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);
        tokenValue = jwtUtil.substringToken(tokenValue);
        if (!jwtUtil.validateToken(tokenValue)) {
            throw new IllegalArgumentException("토큰이 유효하지 않음");
        }
        return tokenValue;
    }
}