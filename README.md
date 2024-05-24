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
#### Gradle with ihmc-build
```
mainDependencies {
   [...]   
   api("us.ihmc:remotecaptury-java:1.0.0") {
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
