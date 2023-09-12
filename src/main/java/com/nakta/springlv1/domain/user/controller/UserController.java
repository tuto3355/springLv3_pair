package com.nakta.springlv1.domain.user.controller;

import com.nakta.springlv1.domain.user.dto.LoginRequestDto;
import com.nakta.springlv1.domain.user.dto.SignupRequestDto;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.dto.UserDeleteRequestDto;
import com.nakta.springlv1.domain.user.service.UserService;
import com.nakta.springlv1.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<StringResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto,
                                                    BindingResult result) {
        return userService.signup(requestDto, result);
    }

    @ResponseBody
    @PostMapping("/delete")
    public ResponseEntity<StringResponseDto> delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @RequestBody UserDeleteRequestDto requestDto) {
        return ResponseEntity.ok(userService.delete(userDetails.getUser(), requestDto));
    }

}
