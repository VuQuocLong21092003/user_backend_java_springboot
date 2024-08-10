package com.example.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseData<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private final String message;
    private final int status;


    //PUT,PATCH,DELETE
    public ResponseData(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // GET,POST
    public ResponseData(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
