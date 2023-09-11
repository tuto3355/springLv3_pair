package com.nakta.springlv1.domain.comment.service;

import com.nakta.springlv1.domain.board.entity.Board;
import com.nakta.springlv1.domain.board.repository.BoardRepository;
import com.nakta.springlv1.domain.comment.dto.CommentRequestDto;
import com.nakta.springlv1.domain.comment.dto.CommentResponseDto;
import com.nakta.springlv1.domain.comment.entity.Comment;
import com.nakta.springlv1.domain.comment.repository.CommentRepository;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.jwt.JwtUtil;
import com.nakta.springlv1.domain.user.jwt.UserRoleEnum;
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

    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {

        Board board = findBoard(requestDto.getPostId());
        Comment comment = new Comment(requestDto, user, board);

        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment);
    }

    @Transactional
    public CommentResponseDto modifyComment(Long id, CommentRequestDto requestDto, User user) {

        Comment comment = findComment(id);
        if (user.getRole()== UserRoleEnum.ADMIN || user.getUsername().equals(comment.getUsername())) {
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        } else {
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
    }

    public StringResponseDto deleteComment(Long id, User user) {

        Comment comment = findComment(id);
        if (user.getRole()== UserRoleEnum.ADMIN || user.getUsername().equals(comment.getUsername())) {
            commentRepository.delete(comment);
            return new StringResponseDto("삭제를 성공하였음");
        } else {
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
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
