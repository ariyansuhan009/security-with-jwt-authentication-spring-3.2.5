package com.ariyanSohan.securitywithjwtauthentication.controller;

import com.ariyanSohan.securitywithjwtauthentication.model.LoginModel;
import com.ariyanSohan.securitywithjwtauthentication.model.UserModel;
import com.ariyanSohan.securitywithjwtauthentication.service.AuthService;
import com.ariyanSohan.securitywithjwtauthentication.util.BaseResponse;
import com.ariyanSohan.securitywithjwtauthentication.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private  AuthService authService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public HashMap<String, String> generateToken(@RequestBody LoginModel request) {
        HashMap<String, String> hashMap = new HashMap<>();
        try{
            if(authService.checkBerify(request.getEmailOrPhoneNo())) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmailOrPhoneNo(), request.getPassword())
                );
                hashMap.put("errorCode", "200");
                hashMap.put("errorMsg", "Success");
                hashMap.put("token", JwtUtils.generateToken(request.getEmailOrPhoneNo()));

            }else{
                hashMap.put("errorCode", "404");
                hashMap.put("errorMsg", "You are not registered !");
            }

        }catch (Exception e){
            hashMap.put("errorCode", "404");
            hashMap.put("errorMsg", "Incorrect password !");
        }

        return hashMap;
    }


    @PostMapping("/sign-up")
    BaseResponse signUp(@RequestBody UserModel request) {
        return authService.signUp(request);
    }

    @GetMapping("/getAllUser")
    BaseResponse getAllUserInfo() {
        return authService.getAllUser();
    }
}
