package com.lodestar.lodestar_server.dto.request;


import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    private String username;
    private String password;


    public void validateFieldsNotNull() {
        if(username == null || username.isEmpty() || username.isBlank())
            throw new InvalidRequestParameterException("Invalid userId");
        if(password == null || password.isEmpty() || username.isBlank())
            throw new InvalidRequestParameterException("Invalid password");
    }
}
