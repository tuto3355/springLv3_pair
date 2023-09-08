package com.nakta.springlv1.domain.comment.service;

import com.nakta.springlv1.domain.board.entity.Board;
import com.nakta.springlv1.domain.board.repository.BoardRepository;
import com.nakta.springlv1.domain.comment.dto.CommentRequestDto;
import com.nakta.springlv1.domain.comment.dto.CommentResponseDto;
import com.nakta.springlv1.domain.comment.entity.Comment;
import com.nakta.springlv1.domain.comment.repository.CommentRepository;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.jwt.JwtUtil;
import com.nakta.springlv1.global.exception.CustomException;
import com.nakta.springlv1.global.exception.ErrorCode;
import com.nakta.springlv1.domain.user.entity.User;
import com.nakta.springlv1.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final JwtUtil jwtUtil;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto, HttpServletRequest req) {

        //토큰 검증
        String tokenValue = validateToken(req);
        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.ID_NOT_FOUND);
        });

        Board board = findBoard(requestDto.getPostId());// DB에 해당 username 있는지 확인
        Comment comment = new Comment(requestDto, board, info.getSubject());
        comment.setUser(user);

        Comment saveComment = commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(saveComment);
        return commentResponseDto;
    }

    @Transactional
    public CommentResponseDto modifyComment(Long id, CommentRequestDto requestDto, HttpServletRequest req) {
        //토큰 검증
        String tokenValue = validateToken(req);
        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        String username = info.getSubject();
        String authority = info.get("auth", String.class); // "auth" 클레임의 값을 가져옴

        //DB저장 유무
        Comment comment = findComment(id);
        if (authority.equals("ADMIN") || comment.getUsername().equals(username)) {

            comment.update(requestDto);
            return new CommentResponseDto(comment);
        } else {
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
    }

    public StringResponseDto deleteComment(Long id, HttpServletRequest req) {
        //토큰 검증
        String tokenValue = validateToken(req);
        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        String username = info.getSubject();
        String authority = info.get("auth", String.class); // "auth" 클레임의 값을 가져옴

        //DB저장 유무
        Comment comment = findComment(id);
        if (authority.equals("ADMIN") || comment.getUsername().equals(username)) {

            commentRepository.delete(comment);
            return new StringResponseDto("삭제를 성공하였음");
        } else {
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
    }

    private String validateToken(HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);
        tokenValue = jwtUtil.substringToken(tokenValue);
        if (!jwtUtil.validateToken(tokenValue)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        return tokenValue;
    }

    private Comment findComment(Long id2) {
        return commentRepository.findById(id2).orElseThrow(() ->
                new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND)
        );
    }
}
