package com.nakta.springlv1.comment.service;

import com.nakta.springlv1.board.dto.BoardResponseDto;
import com.nakta.springlv1.board.entity.Board;
import com.nakta.springlv1.board.repository.BoardRepository;
import com.nakta.springlv1.comment.dto.CommentRequestDto;
import com.nakta.springlv1.comment.dto.CommentResponseDto;
import com.nakta.springlv1.comment.entity.Comment;
import com.nakta.springlv1.comment.repository.CommentRepository;
import com.nakta.springlv1.error.exception.CustomException;
import com.nakta.springlv1.user.dto.StringResponseDto;
import com.nakta.springlv1.user.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class CommentService {
    private final JwtUtil jwtUtil;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    public CommentResponseDto createComment(CommentRequestDto requestDto, HttpServletRequest req) {

        //토큰 검증
        validateToken(req);

        Board board = findBoard(requestDto.getPostId()); // DB에 해당 username 있는지 확인
        Comment comment = new Comment(requestDto,board);
        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment);
    }



    @Transactional
    public CommentResponseDto modifyComment(Long id,CommentRequestDto requestDto,HttpServletRequest req) {
       //토큰 검증
        String tokenValue = validateToken(req);
        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        String username = info.getSubject();
        //DB저장 유무
        Board board = findBoard(id);

        // 당사자인지 확인
        if (board.getUsername().equals(username)) {
            Comment comment = new Comment(requestDto,board);
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        } else {
            throw new IllegalArgumentException("실패");
        }
    }

    public StringResponseDto deleteComment(Long id, HttpServletRequest req) {
        //토큰 검증
        String tokenValue = validateToken(req);
        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        String username = info.getSubject();
        //DB저장 유무
        Board board = findBoard(id);
        if (board.getUsername().equals(username)) {
            Comment comment = new Comment(board);
            commentRepository.delete(comment);
            return new StringResponseDto("삭제를 성공하였음");
        } else{
            throw new IllegalArgumentException("작성자가 일치하지 않습니다");
        }
    }
    private String validateToken(HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);
        tokenValue = jwtUtil.substringToken(tokenValue);
        if (!jwtUtil.validateToken(tokenValue)) {
            throw new IllegalArgumentException("토큰이 유효하지 않음");
        }
        return tokenValue;
    }
    private Board findBoard(Long id){
        return boardRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다")
        );
    }
}
