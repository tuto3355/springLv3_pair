package com.nakta.springlv1.domain.user.service;

import com.nakta.springlv1.domain.user.dto.LoginRequestDto;
import com.nakta.springlv1.domain.user.dto.SignupRequestDto;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.entity.User;
import com.nakta.springlv1.domain.user.jwt.JwtUtil;
import com.nakta.springlv1.domain.user.jwt.UserRoleEnum;
import com.nakta.springlv1.domain.user.repository.UserRepository;
import com.nakta.springlv1.global.exception.ErrorCode;
import com.nakta.springlv1.global.exception.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public StringResponseDto signup(SignupRequestDto requestDto) { //void리턴??
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> tmpUser = userRepository.findByUsername(username);
        if (tmpUser.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_ID);
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER; // 일반 사용자 권한 넣어줌
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new CustomException(ErrorCode.ADMINTOKEN_NOT_MATCH);
            }
            role = UserRoleEnum.ADMIN; // 일치하면 ADMIN 권한으로 덮어씌움
        }
        User user = new User(username,password,role);
        userRepository.save(user);
        return new StringResponseDto( "새로운 아이디 저장 성공 ㅋㅋ");

    }

    public StringResponseDto login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new CustomException(ErrorCode.ID_NOT_FOUND)
        );
        
        if (!passwordEncoder.matches(password,user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        String token = jwtUtil.createToken(user.getUsername(),user.getRole());
        jwtUtil.addJwtToCookie(token, res);
        return new StringResponseDto("로그인 성공 ㅋㅋ");

    }
}
