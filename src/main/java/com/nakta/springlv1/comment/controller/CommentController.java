package com.nakta.springlv1.comment.controller;

import com.nakta.springlv1.comment.dto.CommentRequestDto;
import com.nakta.springlv1.comment.dto.CommentResponseDto;
import com.nakta.springlv1.comment.service.CommentService;
import com.nakta.springlv1.user.dto.StringResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, HttpServletRequest req) {
        return commentService.createComment(requestDto, req);
    }
    

    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentResponseDto> modifyComment(@PathVariable Long id,@RequestBody CommentRequestDto requestDto,HttpServletRequest req){

        return ResponseEntity.ok(commentService.modifyComment(id,requestDto,req));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<StringResponseDto> deleteComment(@PathVariable Long id, HttpServletRequest req) {
        return ResponseEntity.ok(commentService.deleteComment(id,req));
    }
}
