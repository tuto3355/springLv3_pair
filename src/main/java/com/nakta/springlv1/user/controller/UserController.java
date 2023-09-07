package com.nakta.springlv1.user.controller;

import com.nakta.springlv1.user.dto.LoginRequestDto;
import com.nakta.springlv1.user.dto.SignupRequestDto;
import com.nakta.springlv1.user.dto.StringResponseDto;
import com.nakta.springlv1.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    @PostMapping("/user/signup")
    public ResponseEntity<StringResponseDto> signup( @RequestBody  SignupRequestDto requestDto) {
        return ResponseEntity.ok(userService.signup(requestDto));
    }

    @PostMapping("/user/login")
    public ResponseEntity<StringResponseDto> login(@RequestBody  LoginRequestDto requestDto, HttpServletResponse res) {
        return ResponseEntity.ok(userService.login(requestDto, res));
    }
}
