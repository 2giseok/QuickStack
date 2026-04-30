plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.spring")
}

dependencies {

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // webflux
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("io.projectreactor:reactor-test")

    // aws - secretsmanager
    implementation("software.amazon.awssdk:secretsmanager:2.42.34")

    // objectmapper
    implementation("tools.jackson.core:jackson-databind:3.1.2")

    // aop
    implementation("org.aspectj:aspectjweaver")

    // rss
    implementation("com.rometools:rome:2.1.0")

    // okhttp (WAF 우회용 TLS fingerprint)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // html crawling
    implementation("org.jsoup:jsoup:1.18.3")

    // playwright (JS 렌더링 HTML 수집)
    implementation("com.microsoft.playwright:playwright:1.59.0")

    // r2dbc
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.asyncer:r2dbc-mysql")

    // test
    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")

    // testcontainers
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.20.6"))
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:junit-jupiter")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

