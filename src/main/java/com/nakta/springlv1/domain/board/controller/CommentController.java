package com.nakta.springlv1.domain.board.controller;

import com.nakta.springlv1.domain.board.dto.CommentRequestDto;
import com.nakta.springlv1.domain.board.dto.CommentResponseDto;
import com.nakta.springlv1.domain.board.service.CommentService;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.createComment(requestDto, userDetails.getUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> modifyComment(
            @PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.modifyComment(id, requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StringResponseDto> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.deleteComment(id, userDetails.getUser()));
    }

    @GetMapping("/{id}/like")
    public ResponseEntity<StringResponseDto> likeComment(@PathVariable Long id,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.likeBoard(id, userDetails.getUser()));
    }
}
