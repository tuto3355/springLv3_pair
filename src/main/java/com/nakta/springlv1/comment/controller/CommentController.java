package com.nakta.springlv1.comment.controller;

import com.nakta.springlv1.user.dto.StringResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

public class CommentController {
    @PostMapping("")
    public ResponseEntity<> createComment() {
        return null;
    }

    @PutMapping("")
    public ResponseEntity<> modifyComment() {
        return null;
    }

    @DeleteMapping("")
    public ResponseEntity<> deleteComment() {
        return null;
    }
}
