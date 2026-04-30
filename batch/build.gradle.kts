plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.spring")
}

val awsSdkVersion: String by project
val romeVersion: String by project
val okhttpVersion: String by project
val jsoupVersion: String by project
val playwrightVersion: String by project
val mockkVersion: String by project
val testcontainersVersion: String by project

dependencies {

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // webflux
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // aws - secretsmanager
    implementation("software.amazon.awssdk:secretsmanager:$awsSdkVersion")

    // aop
    implementation("org.aspectj:aspectjweaver")

    // rss
    implementation("com.rometools:rome:$romeVersion")

    // okhttp (WAF 우회용 TLS fingerprint)
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")

    // html crawling
    implementation("org.jsoup:jsoup:$jsoupVersion")

    // playwright (JS 렌더링 HTML 수집)
    implementation("com.microsoft.playwright:playwright:$playwrightVersion")

    // jackson
    implementation(libs.jackson.module.kotlin)

    // r2dbc
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.asyncer:r2dbc-mysql")

    // test
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")

    // testcontainers
    testImplementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:junit-jupiter")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

