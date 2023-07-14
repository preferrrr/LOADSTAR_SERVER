package com.lodestar.lodestar_server.dto;

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
@Schema(description = "사용자 경력")
public class CareerDto {

    @Schema(example = "알고리즘")
    private String x;
    @Schema(example = "1672498800000, 1688137200000")
    private List<Long> y;
    @Schema(example = "0k2a1n1lk")
    private String rangeName;

}
