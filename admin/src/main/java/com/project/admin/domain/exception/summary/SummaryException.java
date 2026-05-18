package com.project.admin.domain.exception.summary;

import com.project.admin.exception.BaseException;

public class SummaryException extends BaseException {

    public SummaryException(SummaryErrorCode errorCode) {
        super(errorCode);
    }
}
