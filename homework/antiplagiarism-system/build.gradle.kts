plugins {
    id("java")
}

allprojects {
    group = "com.antiplagiarism"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        "testImplementation"("org.springframework.boot:spring-boot-starter-test")
    }
}