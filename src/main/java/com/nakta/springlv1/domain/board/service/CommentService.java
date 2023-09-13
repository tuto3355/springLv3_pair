package com.nakta.springlv1.domain.board.service;

import com.nakta.springlv1.domain.board.entity.Board;
import com.nakta.springlv1.domain.board.repository.BoardRepository;
import com.nakta.springlv1.domain.board.dto.CommentRequestDto;
import com.nakta.springlv1.domain.board.dto.CommentResponseDto;
import com.nakta.springlv1.domain.board.entity.Comment;
import com.nakta.springlv1.domain.board.entity.CommentLike;
import com.nakta.springlv1.domain.board.repository.CommentLikeRepository;
import com.nakta.springlv1.domain.board.repository.CommentRepository;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.entity.UserRoleEnum;
import com.nakta.springlv1.global.exception.CustomException;
import com.nakta.springlv1.global.exception.ErrorCode;
import com.nakta.springlv1.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

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

    @Transactional
    public StringResponseDto likeBoard(Long id, User user) {
        Comment comment = findComment(id);
        Optional<CommentLike> commentLike = commentLikeRepository.findByUserAndComment(user, comment);
        if (commentLike.isPresent()) {
            commentLikeRepository.deleteByUserAndComment(user, comment);
            return new StringResponseDto("좋아요 취소 성공!!");
        } else {
            commentLikeRepository.save(new CommentLike(user, comment));
            return new StringResponseDto("좋아요 성공!!");
        }

    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND)
        );
    }


}
