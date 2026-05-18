package com.project.admin.controller.dto.request.techblog;

import com.project.admin.constant.Status;
import java.util.List;

public record TechBlogUpdateStatusRequest(List<Long> ids, Status status) {
}
