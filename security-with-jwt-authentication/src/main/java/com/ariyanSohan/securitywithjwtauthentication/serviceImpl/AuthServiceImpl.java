package com.ariyanSohan.securitywithjwtauthentication.serviceImpl;

import com.ariyanSohan.securitywithjwtauthentication.entity.UserEntity;
import com.ariyanSohan.securitywithjwtauthentication.model.UserModel;
import com.ariyanSohan.securitywithjwtauthentication.repo.UserRepo;
import com.ariyanSohan.securitywithjwtauthentication.service.AuthService;
import com.ariyanSohan.securitywithjwtauthentication.util.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Override
    public boolean checkBerify(String emailOrPhoneNo) {
        try {

            UserEntity usersInfo = userRepo.getByEmailOrPhoneNo(emailOrPhoneNo, emailOrPhoneNo);

            if(usersInfo.getEmail().contentEquals(emailOrPhoneNo) || usersInfo.getPhoneNo().contentEquals(emailOrPhoneNo)) {
                return true;
            }else {
                return false;
            }

        }catch(Exception ex) {
            return false;
        }
    }


    @Override
    public BaseResponse signUp(UserModel request) {
        BaseResponse res = new BaseResponse();

        if(userRepo.existsByEmailOrPhoneNo(request.getEmail(), request.getPhoneNo())) {
            throw new RuntimeException("User already exists");
        }
        var encodedPass = passwordEncoder.encode(request.getPassword());

        UserEntity userEntity = userModelToEntity(request);
        userEntity.setPassword(encodedPass);

        try{
            userRepo.save(userEntity);
            res.setMessage("Success");
        }catch (Exception e) {
            res.setMessage(e.getMessage());
        }

        return res;
    }

    private UserEntity userModelToEntity(UserModel request) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(request, userEntity);
        return userEntity;
    }

    @Override
    public BaseResponse getAllUser() {
        BaseResponse res = new BaseResponse();
        try{
            List<UserEntity> users = userRepo.findAll();
            res.setAllUsers(users);
        }catch (Exception e) {
            res.setMessage(e.getMessage());
        }

        return res;
    }


}
