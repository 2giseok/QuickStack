package com.project.api.constants

enum class TechStack(val code: String, val ko: String, val en: String,val owner:String , val repo: String) {

    // Frontend
    REACT("REACT", "리액트", "React","facebook", "react"),
    NEXT_JS("NEXT_JS", "넥스트", "Next.js","vercel", "next.js"),
    VUE("VUE", "뷰", "Vue","vuejs", "core"),
    ANGULAR("ANGULAR", "앵귤러", "Angular","angular", "angular"),
    TYPESCRIPT("TYPESCRIPT", "타입스크립트", "TypeScript","microsoft", "TypeScript"),
    VITE("VITE", "바이트", "Vite","vitejs", "vite"),
    TAILWIND_CSS("TAILWIND_CSS", "테일윈드", "Tailwind CSS","tailwindlabs", "tailwindcss"),
    TANSTACK_QUERY("TANSTACK_QUERY", "탠스택쿼리", "TanStack Query","TanStack", "query"),
    SVELTE("SVELTE", "스벨트", "Svelte","sveltejs", "svelte"),
    NUXT_JS("NUXT_JS", "넉스트", "Nuxt.js","nuxt", "nuxt"),
    ASTRO("ASTRO", "아스트로", "Astro","withastro", "astro"),
    ZUSTAND("ZUSTAND", "주스탠드", "Zustand","pmndrs", "zustand"),
    VITEST("VITEST", "바이테스트", "Vitest","vitest-dev", "vitest"),

    // Backend
    SPRING_FRAMEWORK("SPRING_FRAMEWORK", "스프링", "Spring Framework","spring-projects", "spring-framework"),
    SPRING_BOOT("SPRING_BOOT", "스프링부트", "Spring Boot","spring-projects", "spring-boot"),
    SPRING_SECURITY("SPRING_SECURITY", "스프링시큐리티", "Spring Security","spring-projects", "spring-security"),
    SPRING_BATCH("SPRING_BATCH", "스프링배치", "Spring Batch","spring-projects", "spring-batch"),
    SPRING_DATA_JPA("SPRING_DATA_JPA", "스프링데이터JPA", "Spring Data JPA","spring-projects", "spring-data-jpa"),
    SPRING_DATA_MONGODB("SPRING_DATA_MONGODB", "스프링데이터몽고DB", "Spring Data MongoDB","spring-projects", "spring-data-mongodb"),
    SPRING_DATA_REDIS("SPRING_DATA_REDIS", "스프링데이터레디스", "Spring Data Redis","spring-projects", "spring-data-redis"),
    SPRING_AUTHORIZATION_SERVER("SPRING_AUTHORIZATION_SERVER", "스프링인가서버", "Spring Authorization Server","spring-projects", "spring-authorization-server"),
    SPRING_SESSION("SPRING_SESSION", "스프링세션", "Spring Session","spring-projects", "spring-authorization-server"),
    SPRING_KAFKA("SPRING_KAFKA", "스프링카프카", "Spring Kafka","spring-projects", "spring-kafka"),
    SPRING_AMQP("SPRING_AMQP", "스프링AMQP", "Spring AMQP","spring-projects", "spring-amqp"),
    SPRING_GRAPHQL("SPRING_GRAPHQL", "스프링그래프QL", "Spring GraphQL","spring-projects", "spring-graphql"),
    SPRING_INTEGRATION("SPRING_INTEGRATION", "스프링인티그레이션", "Spring Integration","spring-projects", "spring-integration"),
    SPRING_CLOUD_GATEWAY("SPRING_CLOUD_GATEWAY", "스프링클라우드게이트웨이", "Spring Cloud Gateway","spring-cloud", "spring-cloud-gateway"),
    SPRING_CLOUD_CONFIG("SPRING_CLOUD_CONFIG", "스프링클라우드컨피그", "Spring Cloud Config","spring-cloud", "spring-cloud-config"),
    SPRING_CLOUD_OPENFEIGN("SPRING_CLOUD_OPENFEIGN", "스프링클라우드오픈페인", "Spring Cloud OpenFeign","spring-cloud", "spring-cloud-openfeign"),
    NEST_JS("NEST_JS", "네스트", "NestJS","nestjs", "nest"),
    FASTAPI("FASTAPI", "패스트API", "FastAPI","fastapi", "fastapi"),
    DJANGO("DJANGO", "장고", "Django","django", "django"),
    EXPRESS("EXPRESS", "익스프레스", "Express","expressjs", "express"),
    KAFKA("KAFKA", "카프카", "Kafka","apache", "kafka"),
    REDIS("REDIS", "레디스", "Redis","redis", "redis"),
    FLASK("FLASK", "플라스크", "Flask","pallets", "flask"),
    HONO("HONO", "호노", "Hono","honojs", "hono"),
    GIN("GIN", "진", "Gin","gin-gonic", "gin"),
    FIBER("FIBER", "파이버", "Fiber","gofiber", "fiber"),
    PRISMA("PRISMA", "프리즈마", "Prisma","prisma", "prisma"),

    // DevOps
    DOCKER("DOCKER", "도커", "Docker","moby", "moby"),
    KUBERNETES("KUBERNETES", "쿠버네티스", "Kubernetes","kubernetes", "kubernetes"),
    TERRAFORM("TERRAFORM", "테라폼", "Terraform","hashicorp", "terraform"),
    AWS_CDK("AWS_CDK", "AWS CDK", "AWS CDK","aws", "aws-cdk"),
    HELM("HELM", "헬름", "Helm","helm", "helm"),
    ARGO_CD("ARGO_CD", "아르고CD", "Argo CD","argoproj", "argo-cd"),
    GRAFANA("GRAFANA", "그라파나", "Grafana","grafana", "grafana"),
    PROMETHEUS("PROMETHEUS", "프로메테우스", "Prometheus","prometheus", "prometheus"),
    ISTIO("ISTIO", "이스티오", "Istio","istio", "istio"),
    FLUX("FLUX", "플럭스", "Flux","fluxcd", "flux2"),
    KUSTOMIZE("KUSTOMIZE", "커스터마이즈", "Kustomize","kubernetes-sigs", "kustomize"),

    // Language
    PYTHON("PYTHON", "파이썬", "Python","python", "cpython"),
    GO("GO", "고", "Go","golang", "go"),
    RUST("RUST", "러스트", "Rust","rust-lang", "rust"),
    KOTLIN("KOTLIN", "코틀린", "Kotlin","JetBrains", "kotlin"),
    RUBY("RUBY", "루비", "Ruby","ruby", "ruby"),
    ZIG("ZIG", "지그", "Zig","ziglang", "zig"),
    ELIXIR("ELIXIR", "엘릭서", "Elixir","elixir-lang", "elixir"),

    // AI
    LANGCHAIN("LANGCHAIN", "랭체인", "LangChain","langchain-ai", "langchain"),
    LANGGRAPH("LANGGRAPH", "랭그래프", "LangGraph","langchain-ai", "langgraph"),
    LLAMA_INDEX("LLAMA_INDEX", "라마인덱스", "LlamaIndex","run-llama", "llama_index"),
    TRANSFORMERS("TRANSFORMERS", "트랜스포머스", "Transformers","huggingface", "transformers"),
    PYTORCH("PYTORCH", "파이토치", "PyTorch","pytorch", "pytorch"),
    OLLAMA("OLLAMA", "올라마", "Ollama","ollama", "ollama"),
    CREWAI("CREWAI", "크루AI", "CrewAI","crewaiinc", "crewai"),
    OPENAI_PYTHON("OPENAI_PYTHON", "오픈AI", "OpenAI Python","openai", "openai-python"),
    VLLM("VLLM", "vLLM", "vLLM","vllm-project", "vllm"),
    LITELLM("LITELLM", "라이트LLM", "LiteLLM","BerriAI", "litellm"),
    AUTOGEN("AUTOGEN", "오토젠", "AutoGen","microsoft", "autogen"),
    DSPY("DSPY", "DSPy", "DSPy","stanfordnlp", "dspy"),
    PYDANTIC_AI("PYDANTIC_AI", "파이단틱AI", "Pydantic AI","pydantic", "pydantic-ai"),
    ;

    companion object {
        private val codeMap = entries.associateBy { it.code }
        fun fromCode(code: String): TechStack? = codeMap[code]
        fun search(keyword: String): Set<TechStack> =
            entries.filter { it.ko.contains(keyword, ignoreCase = true) || it.en.contains(keyword, ignoreCase = true) }.toSet()
    }

    fun releaseUrl(tagName: String) =  "http://github.com/$owner/$repo/releases/tag/$tagName"
    fun url(tagName: String) =  "http://github.com/$owner/$repo/releases"
}