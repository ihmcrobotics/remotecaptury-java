# RemoteCaptury-java
Java JNI bindings for Captury SDK "RemoteCaptury" API. Found here: https://captury.com/resources/

## Usage
Supported platforms:
- Linux (Ubuntu 20.04+ or similar x86_64, arm64)
- Windows (Windows 10+ x86_64)
- macOS (macOS 12+ Intel, Apple Silicon)

Requires Java 17

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
    implementation("us.ihmc:remotecaptury-java:1.0.2")
}
```
#### Gradle with ihmc-build
```
mainDependencies {
   [...]   
   api("us.ihmc:remotecaptury-java:1.0.2") {
      // You may need to exclude javacpp from being transitively included
      exclude(group = "org.bytedeco", module = "javacpp")
   }
}
```
### Setup
You must manually load the library first before using it.
```
RemoteCapturyNativeLibrary.load();
```
Ensure [this test](https://github.com/ihmcrobotics/remotecaptury-java/blob/main/src/test/java/us/ihmc/remotecaptury/test/TestNativeLibraryLoads.java) runs on your machine before proceeding.
