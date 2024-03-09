package com.lodestar.lodestar_server.email.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(name = "비밀번호 찾기 요청 body")
public class FindPwdRequestDto {
    
    @Schema(name = "아이디")
    @NotBlank(message = "아이디는 null 또는 공백일 수 없습니다.")
    private String username;

    @Schema(name = "이메일")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    
}
