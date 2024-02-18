package com.lodestar.lodestar_server.career.dto.response;

import com.lodestar.lodestar_server.career.dto.response.CareerDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
