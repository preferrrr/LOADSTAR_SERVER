package com.lodestar.lodestar_server.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataResponse<T> extends BaseResponse {

    private T data;

    private DataResponse(HttpStatus status, T data) {
        super(status);
        this.data = data;
    }

    public static <T> DataResponse<T> of(HttpStatus status, T data) {
        return new DataResponse<T>(status, data);
    }

}
