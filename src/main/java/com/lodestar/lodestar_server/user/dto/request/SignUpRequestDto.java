package com.lodestar.lodestar_server.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(name = "회원가입에 필요한 값")
public class SignUpRequestDto {

    @Schema(name = "유저 아이디")
    @NotBlank(message = "아이디는 null 또는 공백일 수 없습니다.")
    private String username;

    @Schema(name = "비밀번호")
    @Size(min = 8, max = 20, message = "비밀번호는 최소 8자리여야 합니다.")
    private String password;

    @Schema(name = "이메일")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

}
