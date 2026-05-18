package com.project.admin.controller;

import com.project.admin.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @PostMapping("/{techBlogId}")
    public ResponseEntity<String> summary(@PathVariable Long techBlogId) throws Exception {

        String summarize = summaryService.summarize(techBlogId).join();

        return ResponseEntity.ok(summarize);
    }
}
