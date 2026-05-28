plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    kotlin("plugin.jpa")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

val springdocVersion: String by project
val awsSdkVersion: String by project
val mockkVersion: String by project

dependencies {

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // Swagger Docs
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")

    // jackson
    implementation(libs.jackson.module.kotlin)

    // AWS
    implementation("software.amazon.awssdk:secretsmanager:$awsSdkVersion")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Test
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.mockk:mockk:$mockkVersion")
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21    
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}
