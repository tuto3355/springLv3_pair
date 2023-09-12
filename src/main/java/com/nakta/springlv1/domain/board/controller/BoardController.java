package com.nakta.springlv1.domain.board.controller;

import com.nakta.springlv1.domain.board.dto.BoardRequestDto;
import com.nakta.springlv1.domain.board.dto.BoardResponseDto;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.board.service.BoardService;
import com.nakta.springlv1.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(boardService.createBoard(requestDto, userDetails.getUser()));
    }

    @GetMapping("")
    public ResponseEntity<Page<BoardResponseDto>> getAllBoard(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc) {
        return ResponseEntity.ok(boardService.getAllBoard(page-1,size,sortBy,isAsc));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> getOneBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getOneBoard(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDto> modifyBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(boardService.modifyBoard(id, requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StringResponseDto> deleteBoard(@PathVariable Long id,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(boardService.deleteBoard(id, userDetails.getUser()));
    }

    @GetMapping("/{id}/like")
    public ResponseEntity<StringResponseDto> likeBoard(@PathVariable Long id,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(boardService.likeBoard(id, userDetails.getUser()));
    }

}
