package com.lodestar.lodestar_server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordResponseDto {
    private boolean result;
    private String message;
    private Long userId;
}
