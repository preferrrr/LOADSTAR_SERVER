package com.lodestar.lodestar_server.dto.request;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String username;
    private String password;
    private String email;
    private boolean usernameCheck;
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
