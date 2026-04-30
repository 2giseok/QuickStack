package com.project.api.constants

enum class TechStack(val code: String, val ko: String, val en: String) {

    // Frontend
    REACT("REACT", "리액트", "React"),
    NEXT_JS("NEXT_JS", "넥스트", "Next.js"),
    VUE("VUE", "뷰", "Vue"),
    ANGULAR("ANGULAR", "앵귤러", "Angular"),
    TYPESCRIPT("TYPESCRIPT", "타입스크립트", "TypeScript"),
    VITE("VITE", "바이트", "Vite"),
    TAILWIND_CSS("TAILWIND_CSS", "테일윈드", "Tailwind CSS"),
    TANSTACK_QUERY("TANSTACK_QUERY", "탠스택쿼리", "TanStack Query"),
    SVELTE("SVELTE", "스벨트", "Svelte"),
    NUXT_JS("NUXT_JS", "넉스트", "Nuxt.js"),
    ASTRO("ASTRO", "아스트로", "Astro"),
    ZUSTAND("ZUSTAND", "주스탠드", "Zustand"),
    VITEST("VITEST", "바이테스트", "Vitest"),

    // Backend
    SPRING_FRAMEWORK("SPRING_FRAMEWORK", "스프링", "Spring Framework"),
    SPRING_BOOT("SPRING_BOOT", "스프링부트", "Spring Boot"),
    SPRING_SECURITY("SPRING_SECURITY", "스프링시큐리티", "Spring Security"),
    SPRING_BATCH("SPRING_BATCH", "스프링배치", "Spring Batch"),
    SPRING_DATA_JPA("SPRING_DATA_JPA", "스프링데이터JPA", "Spring Data JPA"),
    SPRING_DATA_MONGODB("SPRING_DATA_MONGODB", "스프링데이터몽고DB", "Spring Data MongoDB"),
    SPRING_DATA_REDIS("SPRING_DATA_REDIS", "스프링데이터레디스", "Spring Data Redis"),
    SPRING_AUTHORIZATION_SERVER("SPRING_AUTHORIZATION_SERVER", "스프링인가서버", "Spring Authorization Server"),
    SPRING_SESSION("SPRING_SESSION", "스프링세션", "Spring Session"),
    SPRING_KAFKA("SPRING_KAFKA", "스프링카프카", "Spring Kafka"),
    SPRING_AMQP("SPRING_AMQP", "스프링AMQP", "Spring AMQP"),
    SPRING_GRAPHQL("SPRING_GRAPHQL", "스프링그래프QL", "Spring GraphQL"),
    SPRING_INTEGRATION("SPRING_INTEGRATION", "스프링인티그레이션", "Spring Integration"),
    SPRING_CLOUD_GATEWAY("SPRING_CLOUD_GATEWAY", "스프링클라우드게이트웨이", "Spring Cloud Gateway"),
    SPRING_CLOUD_CONFIG("SPRING_CLOUD_CONFIG", "스프링클라우드컨피그", "Spring Cloud Config"),
    SPRING_CLOUD_OPENFEIGN("SPRING_CLOUD_OPENFEIGN", "스프링클라우드오픈페인", "Spring Cloud OpenFeign"),
    NEST_JS("NEST_JS", "네스트", "NestJS"),
    FASTAPI("FASTAPI", "패스트API", "FastAPI"),
    DJANGO("DJANGO", "장고", "Django"),
    EXPRESS("EXPRESS", "익스프레스", "Express"),
    KAFKA("KAFKA", "카프카", "Kafka"),
    REDIS("REDIS", "레디스", "Redis"),
    FLASK("FLASK", "플라스크", "Flask"),
    HONO("HONO", "호노", "Hono"),
    GIN("GIN", "진", "Gin"),
    FIBER("FIBER", "파이버", "Fiber"),
    PRISMA("PRISMA", "프리즈마", "Prisma"),

    // DevOps
    DOCKER("DOCKER", "도커", "Docker"),
    KUBERNETES("KUBERNETES", "쿠버네티스", "Kubernetes"),
    TERRAFORM("TERRAFORM", "테라폼", "Terraform"),
    AWS_CDK("AWS_CDK", "AWS CDK", "AWS CDK"),
    HELM("HELM", "헬름", "Helm"),
    ARGO_CD("ARGO_CD", "아르고CD", "Argo CD"),
    GRAFANA("GRAFANA", "그라파나", "Grafana"),
    PROMETHEUS("PROMETHEUS", "프로메테우스", "Prometheus"),
    ISTIO("ISTIO", "이스티오", "Istio"),
    FLUX("FLUX", "플럭스", "Flux"),
    KUSTOMIZE("KUSTOMIZE", "커스터마이즈", "Kustomize"),

    // Language
    PYTHON("PYTHON", "파이썬", "Python"),
    GO("GO", "고", "Go"),
    RUST("RUST", "러스트", "Rust"),
    KOTLIN("KOTLIN", "코틀린", "Kotlin"),
    RUBY("RUBY", "루비", "Ruby"),
    ZIG("ZIG", "지그", "Zig"),
    ELIXIR("ELIXIR", "엘릭서", "Elixir"),

    // AI
    LANGCHAIN("LANGCHAIN", "랭체인", "LangChain"),
    LANGGRAPH("LANGGRAPH", "랭그래프", "LangGraph"),
    LLAMA_INDEX("LLAMA_INDEX", "라마인덱스", "LlamaIndex"),
    TRANSFORMERS("TRANSFORMERS", "트랜스포머스", "Transformers"),
    PYTORCH("PYTORCH", "파이토치", "PyTorch"),
    OLLAMA("OLLAMA", "올라마", "Ollama"),
    CREWAI("CREWAI", "크루AI", "CrewAI"),
    OPENAI_PYTHON("OPENAI_PYTHON", "오픈AI", "OpenAI Python"),
    VLLM("VLLM", "vLLM", "vLLM"),
    LITELLM("LITELLM", "라이트LLM", "LiteLLM"),
    AUTOGEN("AUTOGEN", "오토젠", "AutoGen"),
    DSPY("DSPY", "DSPy", "DSPy"),
    PYDANTIC_AI("PYDANTIC_AI", "파이단틱AI", "Pydantic AI"),

    ;

    companion object {
        private val codeMap = entries.associateBy { it.code }
        fun fromCode(code: String): TechStack? = codeMap[code]
        fun search(keyword: String): Set<TechStack> =
            entries.filter { it.ko.contains(keyword, ignoreCase = true) || it.en.contains(keyword, ignoreCase = true) }.toSet()
    }
}