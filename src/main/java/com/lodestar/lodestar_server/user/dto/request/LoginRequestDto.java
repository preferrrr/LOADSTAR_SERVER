package com.lodestar.lodestar_server.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(name = "아이디, 비밀번호")
public class LoginRequestDto {

    @Schema(name = "아이디")
    @NotBlank(message = "아이디는 null 또는 공백일 수 없습니다.")
    private String username;

    @Schema(name = "비밀번호")
    @NotBlank(message = "비밀번호는 null 또는 공백일 수 없습니다.")
    private String password;

}
