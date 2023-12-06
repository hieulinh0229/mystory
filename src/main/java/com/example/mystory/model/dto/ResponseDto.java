package com.example.mystory.model.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private Object data;
    private String mess;
    private boolean isError;
}
