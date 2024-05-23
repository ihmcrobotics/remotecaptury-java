# RemoteCaptury-java
Java JNI bindings for Captury SDK "RemoteCaptury" API. Found here: https://captury.com/resources/

## Usage
Supported platforms:
- linux-x86_64 (Ubuntu 22.04 or similar)
- windows-x86_64 (Windows 10+)

### Gradle
```
repositories {
    [...]
    maven {
        url = uri("https://s01.oss.sonatype.org/content/repositories/releases")
    }
}

dependencies {
    [...]
    implementation("us.ihmc:remotecaptury-java:1.0.0")
}
```
