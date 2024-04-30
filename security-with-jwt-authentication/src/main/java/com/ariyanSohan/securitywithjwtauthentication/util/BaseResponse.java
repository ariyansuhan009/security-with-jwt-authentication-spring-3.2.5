package com.ariyanSohan.securitywithjwtauthentication.util;

import com.ariyanSohan.securitywithjwtauthentication.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    String message;
    List<UserEntity> allUsers;
}
