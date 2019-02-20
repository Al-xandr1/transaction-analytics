import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.21"
}

group = "rd-test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("org.apache.poi:poi:3.17")
    compile("org.apache.poi:poi-ooxml:3.17")
    compile("org.apache.commons:commons-math3:3.6.1")
    testCompile("junit", "junit", "4.12")
}

//jar {
//    manifest {
//        attributes "Main-Class": "com.rd.transaction.MainJava"
//    }
//
//    from {
//        configurations.compile.collect { it.isDirectory() ? it : zipTree(it) }
//    }
//}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}