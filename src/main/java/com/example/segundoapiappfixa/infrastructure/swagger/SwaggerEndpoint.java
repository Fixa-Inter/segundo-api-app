package com.example.segundoapiappfixa.infrastructure.swagger;

public record SwaggerEndpoint(
        String method,
        String path,
        String tag,
        String summary,
        String description
) {
}
