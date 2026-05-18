package com.project.admin.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    FRONTEND("프론트엔드", "Frontend"),
    BACKEND("백엔드", "Backend"),
    DEVOPS("데브옵스", "Devops"),
    LANGUAGE("프로그래밍 언어", "Programming Language"),
    AI("인공지능", "AI");

    private final String ko;
    private final String en;


}