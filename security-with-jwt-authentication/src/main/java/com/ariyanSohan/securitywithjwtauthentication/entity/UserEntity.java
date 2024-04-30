package com.ariyanSohan.securitywithjwtauthentication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "UserInfo")
public class UserEntity {

    @Id
    @SequenceGenerator(name = "user_entity_sequence", sequenceName = "user_entity_sequence", initialValue = 10000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_entity_sequence")
    @Column(name = "user_id", updatable = false)
    private Long id;

    private String name;
    private String email;
    private String phoneNo;
    private String password;
}
