package com.nakta.springlv1.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]+$")
    String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9\\W]+$")
    String password;
    private boolean admin = false;
    private String adminToken = "";



}