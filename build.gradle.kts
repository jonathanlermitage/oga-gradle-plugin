plugins {
    idea
    java
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.3.1" // https://plugins.gradle.org/plugin/com.gradle.plugin-publish
}

group = "biz.lermitage.oga"
version = "2.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

gradlePlugin {
    website = "https://github.com/jonathanlermitage/oga-gradle-plugin"
    vcsUrl = "https://github.com/jonathanlermitage/oga-gradle-plugin.git"
    plugins {
        create("ogaCheckPlugin") {
            id = "biz.lermitage.oga"
            displayName = "Plugin that checks for deprecated groupId+artifactId"
            description = "Old GroupIds Alerter - A Gradle plugin that checks for deprecated groupId+artifactId (e.g. did you know that graphql-spring-boot-starter moved from com.graphql-java to com.graphql-java-kickstart?)."
            implementationClass = "biz.lermitage.oga.OgaCheckPlugin"
            tags = listOf("dependency-analysis")
        }
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.9.0")

    testImplementation("junit:junit:4.13.1")
}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    maxParallelForks = 1 + (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)
}

