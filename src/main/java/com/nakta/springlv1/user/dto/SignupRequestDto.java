package com.nakta.springlv1.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z|0-9]*$")
    @Size(min = 4, max = 10)
    String username;

    @Pattern(regexp = "^[a-z|A-Z|0-9]*$")
    @Size(min = 8, max = 16)
    String password;

}
