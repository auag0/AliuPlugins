import com.aliucord.gradle.AliucordExtension
import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.aliucord.com/snapshots")
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0")
        classpath("com.aliucord:gradle:main-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.aliucord.com/snapshots")
    }
}

fun Project.aliucord(
    configuration: AliucordExtension.() -> Unit
) = extensions.getByName<AliucordExtension>("aliucord").configuration()

fun Project.android(
    configuration: BaseExtension.() -> Unit
) = extensions.getByName<BaseExtension>("android").configuration()

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "com.aliucord.gradle")
    apply(plugin = "kotlin-android")

    aliucord {
        author("azarasi.", 540811752518975508L)
        updateUrl.set("https://raw.githubusercontent.com/auag0/AliucordPlugins/builds/updater.json")
        buildUrl.set("https://raw.githubusercontent.com/auag0/AliucordPlugins/builds/%s.zip")
    }

    android {
        compileSdkVersion(34)

        defaultConfig {
            minSdk = 24
            targetSdk = 34
            versionCode = 1
            versionName = "1.0.0"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "11"
                freeCompilerArgs = freeCompilerArgs +
                        "-Xno-call-assertions" +
                        "-Xno-param-assertions" +
                        "-Xno-receiver-assertions"
            }
        }
    }

    dependencies {
        val discord by configurations
        val implementation by configurations

        discord("com.discord:discord:aliucord-SNAPSHOT")
        implementation("com.aliucord:Aliucord:main-SNAPSHOT")
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
