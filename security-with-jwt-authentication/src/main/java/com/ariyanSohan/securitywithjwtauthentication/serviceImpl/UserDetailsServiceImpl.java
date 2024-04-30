package com.ariyanSohan.securitywithjwtauthentication.serviceImpl;

import com.ariyanSohan.securitywithjwtauthentication.entity.UserEntity;
import com.ariyanSohan.securitywithjwtauthentication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String emailOrPhonNo) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userRepo.findByEmailOrPhoneNo(emailOrPhonNo);

        UserEntity user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("User not found with email or phone number: " + emailOrPhonNo));

        String userName;

        if(emailOrPhonNo.contains("@")) {
            userName = user.getEmail();
        }else {
            userName = user.getPhoneNo();
        }

        return new User(userName, user.getPassword(), new ArrayList<>());
    }
}
