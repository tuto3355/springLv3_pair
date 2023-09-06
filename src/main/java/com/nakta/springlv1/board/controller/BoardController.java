package com.nakta.springlv1.board.controller;

import com.nakta.springlv1.board.dto.BoardRequestDto;
import com.nakta.springlv1.board.dto.BoardResponseDto;
import com.nakta.springlv1.user.dto.StringResponseDto;
import com.nakta.springlv1.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest req) {
        return ResponseEntity.ok(boardService.createBoard(requestDto, req));
    }

    @GetMapping("/board")
    public ResponseEntity<List<BoardResponseDto>> getAllBoard() {
        return ResponseEntity.ok(boardService.getAllBoard());
    }
    @GetMapping("/board/{id}")
    public ResponseEntity<BoardResponseDto> getOneBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getOneBoard(id));
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<BoardResponseDto> modifyBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest req) {
        return ResponseEntity.ok(boardService.modifyBoard(id, requestDto, req));
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<StringResponseDto> deleteBoard(@PathVariable Long id, HttpServletRequest req) {
        return ResponseEntity.ok(boardService.deleteBoard(id, req));
    }

}
