package com.lodestar.lodestar_server.dto.request;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class ModifyPasswordRequestDto {
    private String currentPassword;
    private String modifyPassword;

    public void validateFieldsNotNull() {
        if(currentPassword == null || currentPassword.isEmpty() || currentPassword.isBlank())
            throw new InvalidRequestParameterException("Invalid currentPassword");
        if(modifyPassword == null || modifyPassword.isEmpty() || modifyPassword.isBlank())
            throw new InvalidRequestParameterException("Invalid modifyPassword");
    }
}
