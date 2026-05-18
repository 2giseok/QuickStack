package com.project.admin.domain.exception.techblog;

import com.project.admin.exception.BaseException;
import lombok.Getter;

@Getter
public class TechBlogException extends BaseException {


    public TechBlogException(TechBlogErrorCode errorCode) {
        super(errorCode);

    }
}
