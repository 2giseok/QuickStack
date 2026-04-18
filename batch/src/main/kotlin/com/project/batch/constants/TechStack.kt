package com.project.batch.constants

enum class TechStack(val owner: String, val repo: String, val displayName: String, val en: String, val ko: String, val category: Category) {

    // Frontend
    REACT("facebook", "react", "React", "React", "리액트", Category.FRONTEND),
    NEXT_JS("vercel", "next.js", "Next.js", "Next.js", "넥스트", Category.FRONTEND),
    VUE("vuejs", "core", "Vue.js", "Vue.js", "뷰", Category.FRONTEND),
    SVELTE("sveltejs", "svelte", "Svelte", "Svelte", "스벨트", Category.FRONTEND),
    ANGULAR("angular", "angular", "Angular", "Angular", "앵귤러", Category.FRONTEND),
    TYPESCRIPT("microsoft", "TypeScript", "TypeScript", "TypeScript", "타입스크립트", Category.FRONTEND),
    VITE("vitejs", "vite", "Vite", "Vite", "바이트", Category.FRONTEND),
    TAILWIND_CSS("tailwindlabs", "tailwindcss", "Tailwind CSS", "Tailwind CSS", "테일윈드", Category.FRONTEND),
    TANSTACK_QUERY("TanStack", "query", "TanStack Query", "TanStack Query", "탄스택 쿼리", Category.FRONTEND),
    ZOD("colinhacks", "zod", "Zod", "Zod", "조드", Category.FRONTEND),

    // Backend
    SPRING_BOOT("spring-projects", "spring-boot", "Spring Boot", "Spring Boot", "스프링 부트", Category.BACKEND),
    SPRING_SECURITY("spring-projects", "spring-security", "Spring Security", "Spring Security", "스프링 시큐리티", Category.BACKEND),
    SPRING_BATCH("spring-projects", "spring-batch", "Spring Batch", "Spring Batch", "스프링 배치", Category.BACKEND),
    NEST_JS("nestjs", "nest", "NestJS", "NestJS", "네스트", Category.BACKEND),
    FASTAPI("fastapi", "fastapi", "FastAPI", "FastAPI", "패스트에이피아이", Category.BACKEND),
    DJANGO("django", "django", "Django", "Django", "장고", Category.BACKEND),
    EXPRESS("expressjs", "express", "Express", "Express", "익스프레스", Category.BACKEND),
    GIN("gin-gonic", "gin", "Gin", "Gin", "진", Category.BACKEND),
    KAFKA("apache", "kafka", "Kafka", "Kafka", "카프카", Category.BACKEND),
    REDIS("redis", "redis", "Redis", "Redis", "레디스", Category.BACKEND),

    // DevOps
    DOCKER("moby", "moby", "Docker", "Docker", "도커", Category.DEVOPS),
    KUBERNETES("kubernetes", "kubernetes", "Kubernetes", "Kubernetes", "쿠버네티스", Category.DEVOPS),
    TERRAFORM("hashicorp", "terraform", "Terraform", "Terraform", "테라폼", Category.DEVOPS),
    AWS_CDK("aws", "aws-cdk", "AWS CDK", "AWS CDK", "에이더블유에스 씨디케이", Category.DEVOPS),
    PULUMI("pulumi", "pulumi", "Pulumi", "Pulumi", "풀루미", Category.DEVOPS),
    HELM("helm", "helm", "Helm", "Helm", "헬름", Category.DEVOPS),
    ARGO_CD("argoproj", "argo-cd", "ArgoCD", "ArgoCD", "아르고씨디", Category.DEVOPS),
    GRAFANA("grafana", "grafana", "Grafana", "Grafana", "그라파나", Category.DEVOPS),

    // Language
    PYTHON("python", "cpython", "Python", "Python", "파이썬", Category.LANGUAGE),
    GO("golang", "go", "Go", "Go", "고", Category.LANGUAGE),
    RUST("rust-lang", "rust", "Rust", "Rust", "러스트", Category.LANGUAGE),
    KOTLIN("JetBrains", "kotlin", "Kotlin", "Kotlin", "코틀린", Category.LANGUAGE),

    // AI
    LANGCHAIN("langchain-ai", "langchain", "LangChain", "LangChain", "랭체인", Category.AI),
    LANGGRAPH("langchain-ai", "langgraph", "LangGraph", "LangGraph", "랭그래프", Category.AI),
    LLAMA_INDEX("run-llama", "llama_index", "LlamaIndex", "LlamaIndex", "라마인덱스", Category.AI),
    TRANSFORMERS("huggingface", "transformers", "Transformers", "Transformers", "트랜스포머스", Category.AI),
    PYTORCH("pytorch", "pytorch", "PyTorch", "PyTorch", "파이토치", Category.AI),
    OLLAMA("ollama", "ollama", "Ollama", "Ollama", "올라마", Category.AI),
    CREWAI("crewaiinc", "crewai", "CrewAI", "CrewAI", "크루에이아이", Category.AI),
    OPENAI_PYTHON("openai", "openai-python", "OpenAI Python", "OpenAI Python", "오픈에이아이 파이썬", Category.AI),
}