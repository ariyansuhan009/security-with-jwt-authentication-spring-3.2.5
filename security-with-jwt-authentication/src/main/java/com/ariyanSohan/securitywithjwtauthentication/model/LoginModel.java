package com.ariyanSohan.securitywithjwtauthentication.model;

import lombok.Data;

@Data
public class LoginModel {

    private String emailOrPhoneNo;
    private String password;
}
