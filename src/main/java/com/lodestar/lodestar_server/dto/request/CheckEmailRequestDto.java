package com.lodestar.lodestar_server.dto.request;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckEmailRequestDto {
    String email;

    public void validateFieldsNotNull() {
        if(email == null || email.isEmpty() || email.isBlank())
            throw new InvalidRequestParameterException("Invalid email");
    }
}
