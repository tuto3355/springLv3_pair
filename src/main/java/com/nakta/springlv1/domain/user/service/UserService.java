package com.nakta.springlv1.domain.user.service;

import com.nakta.springlv1.domain.user.dto.SignupRequestDto;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.dto.UserDeleteRequestDto;
import com.nakta.springlv1.domain.user.entity.User;
import com.nakta.springlv1.global.security.JwtUtil;
import com.nakta.springlv1.domain.user.entity.UserRoleEnum;
import com.nakta.springlv1.domain.user.repository.UserRepository;
import com.nakta.springlv1.global.exception.ErrorCode;
import com.nakta.springlv1.global.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
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

    public ResponseEntity<StringResponseDto> signup(SignupRequestDto requestDto, BindingResult result) { //void리턴??

        if (result.hasErrors()) {
            String tmp = "";
            List<FieldError> list = result.getFieldErrors();
            for (FieldError error : list) {
                tmp = tmp + error.getDefaultMessage() + '\n';
            }
            return ResponseEntity.status(400).body(new StringResponseDto(tmp));
        }

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
        User user = new User(username, password, role);
        userRepository.save(user);
        return ResponseEntity.ok(new StringResponseDto("새로운 아이디 저장 성공 ㅋㅋ"));

    }

    public StringResponseDto delete(User user, UserDeleteRequestDto requestDto) {
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        } else {
            userRepository.deleteById(user.getId());
            return new StringResponseDto("아이디 삭제 성공 ㅋㅋ");
        }
    }

}
