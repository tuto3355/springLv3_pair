package com.nakta.springlv1.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.entity.UserRoleEnum;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String accessTokenValue = jwtUtil.getTokenFromRequest(req, "Authorization");
        String refreshTokenValue = jwtUtil.getTokenFromRequest(req, "AuthorizationR");

        if (!StringUtils.hasText(accessTokenValue)) {
            if (StringUtils.hasText(refreshTokenValue)) {
                validateRefreshToken(res,refreshTokenValue);
            }
        }else {
            accessTokenValue = jwtUtil.substringToken(accessTokenValue);
            if (!jwtUtil.validateToken(accessTokenValue)) {
                if (StringUtils.hasText(refreshTokenValue)) {
                    validateRefreshToken(res, refreshTokenValue);
                } else {
                    log.error("Token Error");
                    makeResMessage(res, "토큰 검증 오류 ㅋㅋㅋ");
                    return;
                }
            } else { //토큰 인증
                Claims info = jwtUtil.getUserInfoFromToken(accessTokenValue);
                try {
                    setAuthentication(info.getSubject());
                } catch (Exception e) {
                    log.error(e.getMessage());
                    makeResMessage(res, "토큰 인증 오류 ㅋㅋㅋ");
                    return;
                }
            }
        }
        filterChain.doFilter(req, res);
    }



    public void validateRefreshToken(HttpServletResponse res, String refreshTokenValue) throws ServletException, IOException {

        refreshTokenValue = jwtUtil.substringToken(refreshTokenValue);
        log.info(refreshTokenValue);
        if (jwtUtil.validateToken(refreshTokenValue)) {
            Claims info = jwtUtil.getUserInfoFromToken(refreshTokenValue);
            String username = info.getSubject();
            String authority = info.get("auth", String.class);
            UserRoleEnum role;
            if (authority.equals("ADMIN")) {
                role = UserRoleEnum.ADMIN;
            } else {
                role = UserRoleEnum.USER;
            }
            String accessToken = jwtUtil.createToken(username, role, 1);
            jwtUtil.addJwtToCookie(accessToken, res, "Authorization");
        }
        //토큰 인증
        Claims info = jwtUtil.getUserInfoFromToken(refreshTokenValue);
        try {
            setAuthentication(info.getSubject());
        } catch (Exception e) {
            log.error(e.getMessage());
            makeResMessage(res, "토큰 인증 오류 ㅋㅋㅋ");
        }
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private static void makeResMessage(HttpServletResponse res, String msg) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(new StringResponseDto(msg));
        res.getWriter().write(result);
        res.setStatus(400);
    }
}
