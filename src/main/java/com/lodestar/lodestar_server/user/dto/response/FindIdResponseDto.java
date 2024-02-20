package com.lodestar.lodestar_server.user.dto.response;

import lombok.*;

@Getter
public class FindIdResponseDto {

    private String username;

    @Builder
    private FindIdResponseDto(String username) {
        this.username = username;
    }

    public static FindIdResponseDto of(String username) {
        return FindIdResponseDto.builder()
                .username(username)
                .build();
    }
}
