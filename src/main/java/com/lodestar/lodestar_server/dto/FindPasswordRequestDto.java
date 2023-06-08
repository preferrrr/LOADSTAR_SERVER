package com.lodestar.lodestar_server.dto;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordRequestDto {
    private Long userId;
    private String password;

    public void validateFieldsNotNull() {
        if(userId == null)
            throw new InvalidRequestParameterException("Invalid userId");
        if(password == null || password.isEmpty() || password.isBlank())
            throw new InvalidRequestParameterException("Invalid email");
    }
}
