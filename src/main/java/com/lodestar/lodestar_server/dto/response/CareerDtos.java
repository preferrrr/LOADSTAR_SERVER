package com.lodestar.lodestar_server.dto.response;

import com.lodestar.lodestar_server.dto.response.CareerDto;
import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "그래프 요소들")
public class CareerDtos {

    @Schema(description = "각 요소들 리스트")
    private List<CareerDto> arr;

    public void validateFieldsNotNull() {
        for(CareerDto dto : arr) {
            dto.validateFieldsNotNull();
        }
    }
}
