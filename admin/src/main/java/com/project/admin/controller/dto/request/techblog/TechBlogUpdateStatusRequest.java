package com.project.admin.controller.dto.request.techblog;

import com.project.admin.constant.Status;
import java.util.List;

public record TechBlogUpdateStatusRequest(List<String> ids, Status status) {
}
