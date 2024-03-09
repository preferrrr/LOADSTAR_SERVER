package com.lodestar.lodestar_server.user.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ModifyPasswordRequestDto {

    @NotBlank(message = "현재 비밀번호는 null 또는 공백일 수 없습니다.")
    private String currentPassword;

    @Size(min = 8, max = 20, message = "비밀번호는 최소 8자리여야 합니다.")
    private String modifyPassword;

}
