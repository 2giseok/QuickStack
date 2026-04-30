package com.project.admin.domain.exception;

import com.project.admin.exception.BaseException;
import lombok.Getter;

@Getter
public class AdminException extends BaseException {


    public AdminException(AdminErrorCode errorCode) {
        super(errorCode);

    }
}
