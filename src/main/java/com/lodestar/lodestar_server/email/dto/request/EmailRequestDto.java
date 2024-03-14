package com.lodestar.lodestar_server.email.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
@Schema(name = "이메일")
public class EmailRequestDto {

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

}
