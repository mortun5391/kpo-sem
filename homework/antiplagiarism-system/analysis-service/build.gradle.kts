plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
}

group = "com.antiplagiarism"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Kafka
    implementation("org.springframework.kafka:spring-kafka")

    // База данных
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")

    // OpenAPI документация (Swagger)
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    // Обработка PDF и DOCX
    implementation("org.apache.pdfbox:pdfbox:3.0.0")
    implementation("org.apache.poi:poi-ooxml:5.2.4")

    // NLP утилиты
    implementation("org.apache.lucene:lucene-analyzers-common:8.11.2")
    implementation("edu.stanford.nlp:stanford-corenlp:4.5.4")
    implementation("com.github.vbmacher:java-cup:11b-20160615")

    // Общие зависимости
    implementation(project(":shared"))

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    runtimeOnly("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // Тестирование
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:1.19.1")
    testImplementation("org.testcontainers:junit-jupiter:1.19.1")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}