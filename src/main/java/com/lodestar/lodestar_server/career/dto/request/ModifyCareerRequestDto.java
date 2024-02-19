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
public class ModifyCareerRequestDto {

    private List<NewCareerDto> newCareerDtoList;
    private List<Long> deleteCareers;

    @Builder
    private ModifyCareerRequestDto(List<NewCareerDto> newCareerDtoList, List<Long> deleteCareers) {
        this.newCareerDtoList = newCareerDtoList;
        this.deleteCareers = deleteCareers;
    }

    static class NewCareerDto {
        private String x;
        private List<Long> y;
        private String rangeName;

        @Builder
        private NewCareerDto(String x, List<Long> y, String rangeName) {
            this.x = x;
            this.y = y;
            this.rangeName = rangeName;
        }
    }

    public List<Career> newCareerToEntity(User user) {
        return this.newCareerDtoList.stream()
                .map(newCareerDto -> Career.create(user, newCareerDto.x, newCareerDto.y.get(0), newCareerDto.y.get(1), newCareerDto.rangeName))
                .collect(Collectors.toList());
    }


}
