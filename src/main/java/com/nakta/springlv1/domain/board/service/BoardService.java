package com.nakta.springlv1.domain.board.service;

import com.nakta.springlv1.domain.board.dto.BoardRequestDto;
import com.nakta.springlv1.domain.board.dto.BoardResponseDto;
import com.nakta.springlv1.domain.board.entity.BoardCategory;
import com.nakta.springlv1.domain.board.entity.BoardLike;
import com.nakta.springlv1.domain.board.repository.BoardLikeRepository;
import com.nakta.springlv1.domain.board.entity.Board;
import com.nakta.springlv1.domain.board.repository.BoardRepository;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.entity.UserRoleEnum;
import com.nakta.springlv1.global.exception.CustomException;
import com.nakta.springlv1.global.exception.ErrorCode;
import com.nakta.springlv1.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = new Board(requestDto, user); //username을 따로 받기 위한 생성자 생성
        Board newboard = boardRepository.save(board);
        return new BoardResponseDto(newboard);
    }

    @Transactional(readOnly = true)
    public Page<BoardResponseDto> getAllBoard(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Board> boardList = boardRepository.findAll(pageable);

        return boardList.map(BoardResponseDto::new);
    }

    public List<BoardResponseDto> getBoardByCategory(BoardCategory category) {
        return boardRepository.findAllByCategory(category).stream().map(BoardResponseDto::new).toList();
    }

    public BoardResponseDto getOneBoard(Long id) {
        Board board = findById(id);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto modifyBoard(Long id, BoardRequestDto requestDto, User user) {

        Board board = findById(id);

        if (user.getRole()== UserRoleEnum.ADMIN||user.getUsername().equals(board.getUsername())) {
            board.update(requestDto);
            return new BoardResponseDto(board);
        } else {
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
    }

    public StringResponseDto deleteBoard(Long id, User user) {

        Board board = findById(id);

        if (user.getRole()== UserRoleEnum.ADMIN||user.getUsername().equals(board.getUsername())) {
            boardRepository.deleteById(id);
            return new StringResponseDto("삭제를 성공하였음");
        } else {
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
    }

    @Transactional
    public StringResponseDto likeBoard(Long id, User user) {
        Board board = findById(id);
        Optional<BoardLike> boardLike = boardLikeRepository.findByUserAndBoard(user, board);
        if (boardLike.isPresent()) {
            boardLikeRepository.deleteByUserAndBoard(user, board);
            return new StringResponseDto("좋아요 취소 성공!!");
        } else {
            boardLikeRepository.save(new BoardLike(user, board));
            return new StringResponseDto("좋아요 성공!!");
        }
    }

    private Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }



}