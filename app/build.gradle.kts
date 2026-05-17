import java.util.Properties

plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.javamodularity.moduleplugin") version "2.0.0"
    id("org.beryx.jlink") version "3.1.1"
}

val project = Properties().apply {
    file("project.properties").inputStream().use { load(it) }
}

group = "io.github.gleidsonmt"
version = project["VERSION"] as String

repositories {
    mavenCentral()
    maven {
        url = uri("https://sandec.jfrog.io/artifactory/repo")
    }
    maven { url = uri("'https://jitpack.io") }
}

dependencies {
    // --------------------- Comile/Runtime only ------------------------

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly("org.jetbrains:annotations:26.1.0")
    testCompileOnly("org.jetbrains:annotations:26.1.0")

    // ----------------------- Projects ------------------------------

    implementation(project(":glad"))

    // -------------------- UI component libraries -----------------------

    implementation("com.dlsc.gemsfx:gemsfx:2.16.0")

    // --------------------- Additional libraries  ------------------------

    implementation("org.yaml:snakeyaml:2.5")
    implementation("io.github.classgraph:classgraph:4.8.165")
    implementation("com.dustinredmond.fxtrayicon:FXTrayIcon:4.2.3")
    implementation("org.xerial:sqlite-jdbc:3.45.3.0") // Use the latest version

    // ------------------ Local libraries --------
    implementation(fileTree(mapOf("dir" to "lib", "include" to listOf("*.jar"))))

    // ------------------------------- Tests ---------------------------

    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)
    // This dependency is used by the application.
    implementation(libs.guava)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(23)
    }
    // The line resolves the problem with the Java Language Server in VS Code, but it is unnecessary for Gradle run.
    // Without this line, Gradle run works, but the Java Language Server in VS Code reports “module not found” for module jars that are on the Gradle module path.
    // modularity.inferModulePath = true
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainClass = "io.github.gleidsonmt.todo.Launcher"
    mainModule = "io.github.gleidsonmt.todo"
}

javafx {
    version = "23.0.2"
    modules("javafx.controls", "javafx.web", "javafx.fxml", "javafx.graphics", "javafx.swing")
}

jlink {

    // Loading a custom file in build.gradle
    options = listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    launcher {
        name = "JavaFx ToDo"
    }
    jpackage {
        imageOptions = listOf("--icon", project["ICON"] as String)
        installerType = "exe"
        appVersion = version as String?
        installerOptions = listOf(
            "--description", project["DESCRIPTION"] as String,
            "--copyright", project["COPYRIGHT"] as String,
            "--vendor", project["VENDOR"] as String,
            "--icon", project["ICON"] as String,
            "--win-dir-chooser",
            "--win-shortcut",
        )
    }
    addExtraDependencies("javafx")
}

tasks.named<JavaExec>("run") {
    args = listOf("level-off")
}

tasks.register("debug") {
    group = "application"
    tasks.run.configure {
        args = listOf("debug")
    }
    dependsOn("run")
}

tasks.register("log") {
    group = "application"
    tasks.run.configure {
        args = listOf("log")
    }
    dependsOn("run")
}


tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

