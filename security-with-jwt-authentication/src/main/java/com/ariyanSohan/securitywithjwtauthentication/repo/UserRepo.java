package com.ariyanSohan.securitywithjwtauthentication.repo;

import com.ariyanSohan.securitywithjwtauthentication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    boolean existsByEmailOrPhoneNo(String email, String phoneNo);

    @Query("SELECT re FROM UserEntity re WHERE re.email = :emailOrPhoneNo OR re.phoneNo = :emailOrPhoneNo")
    Optional<UserEntity> findByEmailOrPhoneNo(@Param("emailOrPhoneNo") String emailOrPhoneNo);

    UserEntity getByEmailOrPhoneNo(String emailOrPhoneNo, String emailOrPhoneNo1);
}
