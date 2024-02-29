package com.lodestar.lodestar_server.career.dto.response;

import com.lodestar.lodestar_server.career.entity.Career;
import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Schema(description = "사용자 경력")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CareerDto {

    @Schema(example = "알고리즘")
    private String x;
    @Schema(example = "1672498800000, 1688137200000")
    private List<Long> y;
    @Schema(example = "0k2a1n1lk")
    private String rangeName;

    @Builder
    private CareerDto(String x, List<Long> y, String rangeName) {
        this.x = x;
        this.y = y;
        this.rangeName = rangeName;
    }

    public static CareerDto of(Career career) {
        return CareerDto.builder()
                .x(career.getX())
                .y(List.of(career.getY1(), career.getY2()))
                .rangeName(career.getRangeName())
                .build();
    }

    public void validateFieldsNotNull() {
        if(x == null || x.isEmpty() || x.isBlank())
            throw new InvalidRequestParameterException("Invalid x");
        if(y.size() != 2 || y.isEmpty())
            throw new InvalidRequestParameterException("Invalid y");
        if(rangeName == null || rangeName.isEmpty() || rangeName.isBlank())
            throw new InvalidRequestParameterException("Invalid rangeName");
    }

}
