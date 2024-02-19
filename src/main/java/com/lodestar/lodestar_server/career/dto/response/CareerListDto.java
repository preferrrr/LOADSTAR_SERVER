package com.lodestar.lodestar_server.career.dto.response;

import com.lodestar.lodestar_server.career.entity.Career;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Schema(name = "그래프 요소들")
public class CareerListDto {

    @Schema(description = "각 요소들 리스트")
    private List<CareerDto> careerDtoList;

    public void validateFieldsNotNull() {
        for(CareerDto dto : careerDtoList) {
            dto.validateFieldsNotNull();
        }
    }

    @Builder
    private CareerListDto(List<CareerDto> careerDtoList) {
        this.careerDtoList = careerDtoList;
    }

    public static CareerListDto of(List<Career> careers) {
        return CareerListDto.builder()
                .careerDtoList(careers.stream()
                        .map(career -> CareerDto.of(career))
                        .collect(Collectors.toList()))
                .build();
    }
}
