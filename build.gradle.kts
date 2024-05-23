plugins {
    id("java")
}

group = "us.ihmc"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.bytedeco:javacpp:1.5.10")
    implementation("us.ihmc:ihmc-native-library-loader:2.0.2")
}

tasks.test {
    useJUnitPlatform()
}