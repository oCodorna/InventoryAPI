import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    java
    `java-library`
    id("com.gradleup.shadow") version "8.3.0"
    `maven-publish`
}


group = "com.github.oCodorna"
version = "1.0.3"


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:24.1.0")
    val lombokVersion = "1.18.34"
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

tasks.processResources {
    from(sourceSets.main.get().resources.srcDirs) {
        filter(ReplaceTokens::class, "tokens" to mapOf("version" to project.version))
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}

tasks.shadowJar {
    archiveBaseName.set(project.name)
    archiveClassifier.set("")
    archiveVersion.set(project.version.toString())
}


tasks.build {
    dependsOn(tasks.shadowJar)
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}