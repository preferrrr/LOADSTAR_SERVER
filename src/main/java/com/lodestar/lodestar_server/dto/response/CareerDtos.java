package com.lodestar.lodestar_server.dto.response;

import com.lodestar.lodestar_server.dto.response.CareerDto;
import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CareerDtos {

    private List<CareerDto> arr;

    public void validateFieldsNotNull() {
        for(CareerDto dto : arr) {
            dto.validateFieldsNotNull();
        }
    }
}
