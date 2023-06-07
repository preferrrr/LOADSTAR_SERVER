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
public class FirstQuestionRequestDto {

    private Long userId;
    private String major;
    private String frontBack;
    private String current;
    private Integer year;
    private Integer month;

    public void validateFieldsNotNull() {
        if(userId == null)
            throw new InvalidRequestParameterException("Invalid userId");
        if(major == null || major.isEmpty() || major.isBlank())
            throw new InvalidRequestParameterException("Invalid major");
        if(frontBack == null || frontBack.isEmpty() || frontBack.isBlank())
            throw new InvalidRequestParameterException("Invalid frontBack");
        if(current == null || current.isEmpty() || current.isBlank())
            throw new InvalidRequestParameterException("Invalid current");
        if(year == null)
            throw new InvalidRequestParameterException("Invalid year");
        if(month == null)
            throw new InvalidRequestParameterException("Invalid month");
    }
}
