plugins {
    java
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.graalvm.buildtools.native") version "0.10.6"
}

group = "com.fafeichter.eocs"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(23)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://packages.atlassian.com/maven/repository/public") }
}

extra["springAiVersion"] = "1.0.0-M5"
dependencies {
    implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")
    implementation("com.atlassian.jira:jira-rest-java-client-core:6.0.2") {
        exclude(group = "org.glassfish.jersey.core", module = "jersey-common")
            .because("Jira REST client still uses Java EE instead of Jakarta EE")
    }
    implementation("org.glassfish.jersey.core:jersey-common:2.27") {
        because("Jersey 2.27 is the last version that uses Java EE required by Jira REST client")
    }
    implementation("io.atlassian.fugue:fugue:6.1.2") {
        because("Required by JIRA REST client")
    }
    implementation("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}