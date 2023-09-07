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
    

    @PutMapping("/comment/{id}/{id2}")
    public ResponseEntity<CommentResponseDto> modifyComment(@PathVariable Long id,@PathVariable Long id2,@RequestBody CommentRequestDto requestDto,HttpServletRequest req){

        return ResponseEntity.ok(commentService.modifyComment(id,id2,requestDto,req));
    }

    @DeleteMapping("/comment/{id}/{id2}")
    public ResponseEntity<StringResponseDto> deleteComment(@PathVariable Long id,@PathVariable Long id2,HttpServletRequest req) {
        return ResponseEntity.ok(commentService.deleteComment(id,id2,req));
    }
}
