package com.lodestar.lodestar_server.dto.response;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 경력")
public class CareerDto {

    @Schema(example = "알고리즘")
    private String x;
    @Schema(example = "1672498800000, 1688137200000")
    private List<Long> y;
    @Schema(example = "0k2a1n1lk")
    private String rangeName;

    public void validateFieldsNotNull() {
        if(x == null || x.isEmpty() || x.isBlank())
            throw new InvalidRequestParameterException("Invalid x");
        if(y.size() != 2 || y.isEmpty())
            throw new InvalidRequestParameterException("Invalid y");
        if(rangeName == null || rangeName.isEmpty() || rangeName.isBlank())
            throw new InvalidRequestParameterException("Invalid rangeName");
    }

}
