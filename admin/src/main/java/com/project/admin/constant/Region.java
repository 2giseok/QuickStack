package com.project.admin.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Region {
    DOMESTIC("국내"),
    INTERNATIONAL("국외");

    private final String ko;


}
