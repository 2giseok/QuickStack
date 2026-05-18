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

    @Column(unique = true, length = 20)
    private String loginId;
    @Column(name = "password")
    private String password;

    private Admin(String loginId, String password) {

        this.loginId = loginId;
        this.password = password;
    }

    public static Admin create(String loginId, String password) {
        return new Admin(loginId, password);
    }
}
