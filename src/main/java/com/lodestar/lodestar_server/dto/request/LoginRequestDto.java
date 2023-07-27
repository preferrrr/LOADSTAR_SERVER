package com.lodestar.lodestar_server.dto.request;


import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "아이디, 비밀번호")
public class LoginRequestDto {

    @Schema(name = "아이디")
    private String username;
    @Schema(name = "비밀번호")
    private String password;


    public void validateFieldsNotNull() {
        if(username == null || username.isEmpty() || username.isBlank())
            throw new InvalidRequestParameterException("Invalid userId");
        if(password == null || password.isEmpty() || username.isBlank())
            throw new InvalidRequestParameterException("Invalid password");
    }
}
