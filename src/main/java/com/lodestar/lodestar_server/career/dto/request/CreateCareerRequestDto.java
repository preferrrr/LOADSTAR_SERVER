package com.lodestar.lodestar_server.career.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateCareerRequestDto {

    List<CareerDto> careers;

    @Builder
    private CreateCareerRequestDto(List<CareerDto> careers) {
        this.careers = careers;
    }

    static class CareerDto {
        private String x;
        private List<Long> y;
        private String rangeName;

        @Builder
        private CareerDto(String x, List<Long> y, String rangeName) {
            this.x = x;
            this.y = y;
            this.rangeName = rangeName;
        }

    }
}
