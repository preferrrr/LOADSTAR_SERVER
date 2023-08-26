package com.lodestar.lodestar_server.dto.request;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Schema(name = "비밀번호 찾기 요청 body")
public class FindPwdRequestDto {
    
    @Schema(name = "아이디")
    private String username;
    @Schema(name = "이메일")
    private String email;
    
    public void validateFieldsNotNull() {
        if(username == null || username.isEmpty() || username.isBlank())
            throw new InvalidRequestParameterException("Invalid username");
        if(email == null || email.isEmpty() || email.isBlank())
            throw new InvalidRequestParameterException("Invalid email");
    }
    
}
