package com.project.api.constants

import com.project.api.exception.TechStackErrorCode
import com.project.api.exception.QuickStackException

enum class Category(
    val code: String,
    val display: String,
    val techStacks: Set<TechStack>,
) {
    ALL("ALL", "All", emptySet()),

    FRONTEND(
        "FRONTEND", "FrontEnd", setOf(
            TechStack.REACT,
            TechStack.NEXT_JS,
            TechStack.VUE,
            TechStack.ANGULAR,
            TechStack.TYPESCRIPT,
            TechStack.VITE,
            TechStack.TAILWIND_CSS,
            TechStack.TANSTACK_QUERY,
            TechStack.SVELTE,
            TechStack.NUXT_JS,
            TechStack.ASTRO,
            TechStack.ZUSTAND,
            TechStack.VITEST,
        )
    ),

    BACKEND(
        "BACKEND", "BackEnd", setOf(
            TechStack.SPRING_FRAMEWORK,
            TechStack.SPRING_BOOT,
            TechStack.SPRING_SECURITY,
            TechStack.SPRING_BATCH,
            TechStack.SPRING_DATA_JPA,
            TechStack.SPRING_DATA_MONGODB,
            TechStack.SPRING_DATA_REDIS,
            TechStack.SPRING_AUTHORIZATION_SERVER,
            TechStack.SPRING_SESSION,
            TechStack.SPRING_KAFKA,
            TechStack.SPRING_AMQP,
            TechStack.SPRING_GRAPHQL,
            TechStack.SPRING_INTEGRATION,
            TechStack.SPRING_CLOUD_GATEWAY,
            TechStack.SPRING_CLOUD_CONFIG,
            TechStack.SPRING_CLOUD_OPENFEIGN,
            TechStack.NEST_JS,
            TechStack.FASTAPI,
            TechStack.DJANGO,
            TechStack.EXPRESS,
            TechStack.KAFKA,
            TechStack.REDIS,
            TechStack.FLASK,
            TechStack.HONO,
            TechStack.GIN,
            TechStack.FIBER,
            TechStack.PRISMA,
        )
    ),

    DEVOPS(
        "DEVOPS", "DevOps", setOf(
            TechStack.DOCKER,
            TechStack.KUBERNETES,
            TechStack.TERRAFORM,
            TechStack.AWS_CDK,
            TechStack.HELM,
            TechStack.ARGO_CD,
            TechStack.GRAFANA,
            TechStack.PROMETHEUS,
            TechStack.ISTIO,
            TechStack.FLUX,
            TechStack.KUSTOMIZE,
        )
    ),

    LANGUAGE(
        "LANGUAGE", "Language", setOf(
            TechStack.PYTHON,
            TechStack.GO,
            TechStack.RUST,
            TechStack.KOTLIN,
            TechStack.RUBY,
            TechStack.ZIG,
            TechStack.ELIXIR,
        )
    ),

    AI(
        "AI", "AI", setOf(
            TechStack.LANGCHAIN,
            TechStack.LANGGRAPH,
            TechStack.LLAMA_INDEX,
            TechStack.TRANSFORMERS,
            TechStack.PYTORCH,
            TechStack.OLLAMA,
            TechStack.CREWAI,
            TechStack.OPENAI_PYTHON,
            TechStack.VLLM,
            TechStack.LITELLM,
            TechStack.AUTOGEN,
            TechStack.DSPY,
            TechStack.PYDANTIC_AI,
        )
    );

    fun contains(techStack: TechStack): Boolean =
        this == ALL || techStacks.contains(techStack)

    companion object {
        private val techStackToCategory: Map<TechStack, Category> =
            entries.filter { it != ALL }
                .flatMap { category -> category.techStacks.map { techStack -> techStack to category } }
                .toMap()

        fun from(techStack: TechStack): Category = techStackToCategory[techStack] ?: ALL

        fun fromCode(code: String): Category =
            entries.firstOrNull { it.code.equals(code, ignoreCase = true) }
                ?: throw QuickStackException(TechStackErrorCode.INVALID_CATEGORY)
    }
}