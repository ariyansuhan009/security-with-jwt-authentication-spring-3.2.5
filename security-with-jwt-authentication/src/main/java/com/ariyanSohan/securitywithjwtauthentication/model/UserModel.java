package com.ariyanSohan.securitywithjwtauthentication.model;

import lombok.Data;

@Data
public class UserModel {
    private String name;
    private String email;
    private String phoneNo;
    private String password;
}
