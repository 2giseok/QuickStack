package com.project.admin.domain.exception.release;

import com.project.admin.exception.BaseException;
import lombok.Getter;

@Getter
public class ReleaseException extends BaseException {


    public ReleaseException(ReleaseErrorCode errorCode) {
        super(errorCode);

    }
}
