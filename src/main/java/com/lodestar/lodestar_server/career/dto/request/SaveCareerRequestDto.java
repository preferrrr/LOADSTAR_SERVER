package com.lodestar.lodestar_server.career.dto.request;


import com.lodestar.lodestar_server.career.entity.Career;
import com.lodestar.lodestar_server.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class SaveCareerRequestDto {

    private List<CareerDto> careers;

    @Builder
    private SaveCareerRequestDto(List<CareerDto> careers) {
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

    public List<Career> toEntities(User user) {
        return careers.stream()
                .map(careerDto -> Career.create(user, careerDto.x, careerDto.y.get(0), careerDto.y.get(1), careerDto.rangeName))
                .collect(Collectors.toList());
    }
}
