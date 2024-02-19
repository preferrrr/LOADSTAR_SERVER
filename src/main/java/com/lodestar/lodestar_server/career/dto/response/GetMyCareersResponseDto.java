package com.lodestar.lodestar_server.career.dto.response;

import com.lodestar.lodestar_server.career.entity.Career;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetMyCareersResponseDto {

    private List<MyCareerDto> careers;

    @Builder
    private GetMyCareersResponseDto(List<MyCareerDto> careers) {
        this.careers = careers;
    }

    public static GetMyCareersResponseDto of(List<Career> careers) {
        return GetMyCareersResponseDto.builder()
                .careers(careers.stream()
                        .map(career -> MyCareerDto.of(career))
                        .collect(Collectors.toList()))
                .build();
    }

    static class MyCareerDto {
        private Long id;
        private String x;
        private List<Long> y;
        private String rangeName;

        @Builder
        private MyCareerDto(Long id, String x, List<Long> y, String rangeName) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.rangeName = rangeName;
        }

        static MyCareerDto of(Career career) {
            return MyCareerDto.builder()
                    .id(career.getId())
                    .x(career.getX())
                    .y(List.of(career.getY1(), career.getY2()))
                    .rangeName(career.getRangeName())
                    .build();
        }
    }

}
