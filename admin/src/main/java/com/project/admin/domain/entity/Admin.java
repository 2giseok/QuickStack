package com.project.admin.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false,length = 20)
    private String loginId;
    @Column(nullable = false)
    private String password;

    private Admin(String name, String loginId, String password) {

        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }


    public static Admin create(String name, String loginId, String password) {

        return new Admin(name, loginId, password);


    }


}
