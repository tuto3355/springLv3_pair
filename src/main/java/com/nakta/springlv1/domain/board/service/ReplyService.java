package com.nakta.springlv1.domain.board.service;

import com.nakta.springlv1.domain.board.entity.Board;
import com.nakta.springlv1.domain.board.repository.BoardRepository;
import com.nakta.springlv1.domain.board.dto.ReplyRequestDto;
import com.nakta.springlv1.domain.board.dto.ReplyResponseDto;
import com.nakta.springlv1.domain.board.entity.Comment;
import com.nakta.springlv1.domain.board.entity.Reply;
import com.nakta.springlv1.domain.board.repository.CommentRepository;
import com.nakta.springlv1.domain.board.repository.ReplyRepository;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.entity.User;
import com.nakta.springlv1.domain.user.entity.UserRoleEnum;
import com.nakta.springlv1.global.exception.CustomException;
import com.nakta.springlv1.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    public ReplyResponseDto createReply(ReplyRequestDto requestDto, User user) {

        Comment comment = validateComment(requestDto);
        Reply reply = new Reply(requestDto, user, comment);

        Reply saveReply = replyRepository.save(reply);
        return new ReplyResponseDto(saveReply);
    }

    @Transactional
    public ReplyResponseDto modifyReply(Long id, ReplyRequestDto requestDto, User user) {

        Reply reply = findReply(id);
        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(reply.getUsername())) {
            reply.update(requestDto);
            return new ReplyResponseDto(reply);
        } else {
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
    }

    public StringResponseDto deleteReply(Long id, User user) {
        Reply reply = findReply(id);
        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(reply.getUsername())) {
            replyRepository.delete(reply);
            return new StringResponseDto("삭제를 성공하였음");
        } else {
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
    }

    private Board findBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND)
        );
        return board;
    }

    private Comment findCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
        return comment;
    }

    private Reply findReply(Long id) {
        return replyRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.Reply_NOT_FOUND)
        );
    }
    private Comment validateComment(ReplyRequestDto requestDto) {
        Board board = findBoardById(requestDto.getPostId());
        Comment comment = findCommentById(requestDto.getCommentId());
        if (!comment.getBoard().equals(board)) {
            throw new CustomException(ErrorCode.COMMENT_BOARD_NOT_MATCH);
        }
        return comment;
    }
}
