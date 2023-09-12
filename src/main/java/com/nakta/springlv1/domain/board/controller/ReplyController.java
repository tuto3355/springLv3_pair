package com.nakta.springlv1.domain.board.controller;

import com.nakta.springlv1.domain.board.dto.ReplyRequestDto;
import com.nakta.springlv1.domain.board.dto.ReplyResponseDto;
import com.nakta.springlv1.domain.board.service.ReplyService;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("")
    public ResponseEntity<ReplyResponseDto> createReply(@RequestBody ReplyRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(replyService.createReply(requestDto, userDetails.getUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReplyResponseDto> modifyReply(
            @PathVariable Long id, @RequestBody ReplyRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(replyService.modifyReply(id, requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StringResponseDto> deleteReply(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(replyService.deleteReply(id, userDetails.getUser()));
    }
}
