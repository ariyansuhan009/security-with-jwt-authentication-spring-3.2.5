package com.ariyanSohan.securitywithjwtauthentication.service;

import com.ariyanSohan.securitywithjwtauthentication.model.UserModel;
import com.ariyanSohan.securitywithjwtauthentication.util.BaseResponse;

public interface AuthService {

    BaseResponse signUp(UserModel request);

    boolean checkBerify(String emailOrPhoneNo);

    BaseResponse getAllUser();
}
