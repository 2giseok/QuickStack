package com.project.admin.ai;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class PromptManager {

    @Value("classpath:prompt.txt")
    private Resource promptResource;
    private String prompt;

    @PostConstruct
    public void init() throws Exception {
        this.prompt = promptResource.getContentAsString(StandardCharsets.UTF_8);
    }

    public String buildPrompt(String text) {
        return prompt + text;
    }
}
