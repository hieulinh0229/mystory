package com.example.mystory.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class LoginModel {
    @Length(min = 10,max = 20,message = " must over than 10 and less than 20")
    private String userName;
    @Length(min = 10,max = 20,message = " must over than 10 and less than 20")
    private String password;
}
