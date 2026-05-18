package com.project.admin.ai.dto;

import java.util.List;

public record GeminiRequest(List<Content> contents) {

    private record Content(List<Part> parts) {
    }

    private record Part(String text) {
    }

    public static GeminiRequest of(String text) {
        Part part = new Part(text);
        Content content = new Content(List.of(part));
        return new GeminiRequest(List.of(content));
    }
}
