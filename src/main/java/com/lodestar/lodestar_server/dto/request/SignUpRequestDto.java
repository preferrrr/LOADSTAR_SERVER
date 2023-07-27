package com.lodestar.lodestar_server.dto.request;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "회원가입에 필요한 값")
public class SignUpRequestDto {
    @Schema(name = "유저 아이디")
    private String username;
    @Schema(name = "비밀번호")
    private String password;
    @Schema(name = "이메일")
    private String email;
    @Schema(name = "아이디 중복확인 체크 여부", description = "true가 되어야 회원가입 가능")
    private boolean usernameCheck;
    @Schema(name = "이메일 인증확인 여부", description = "true가 되어야 회원가입 가능")
    private boolean emailCheck;

    public void validateFieldsNotNull() {
        if(username == null || username.isEmpty() || username.isBlank())
            throw new InvalidRequestParameterException("Invalid userId");
        if(password == null || password.isEmpty() || password.isBlank())
            throw new InvalidRequestParameterException("Invalid password");
        if(email == null || email.isEmpty() || email.isBlank())
            throw new InvalidRequestParameterException("Invalid email");
    }
}
