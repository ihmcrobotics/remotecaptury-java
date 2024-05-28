#!/bin/bash
# This build script is designed to work on Linux and Windows. For Windows, run from a bash shell launched with launchBashWindows.bat
pushd .
mkdir cppbuild
cd cppbuild

if [ ! -f "CapturyLiveSDK.tar.gz" ]; then
  curl -O https://dn-cap.com/CapturyLiveSDK.tar.gz
fi

tar -xvf CapturyLiveSDK.tar.gz

cp ../CMakeLists.txt.RemoteCaptury sdk/network/CMakeLists.txt
cp ../patches/RemoteCaptury.h.patch sdk/network/RemoteCaptury.h.patch
cp ../patches/RemoteCaptury.cpp.patch sdk/network/RemoteCaptury.cpp.patch

# Convert all line endings to unix
find ./sdk -type f -print0 | xargs -0 dos2unix

cd sdk/network

patch RemoteCaptury.h RemoteCaptury.h.patch
patch RemoteCaptury.cpp RemoteCaptury.cpp.patch

mkdir build
cd build

if [ "$MAC_CROSS_COMPILE_ARM" == "1" ]; then
  cmake -DCMAKE_OSX_ARCHITECTURES="arm64" ..
elif [ "$LINUX_CROSS_COMPILE_ARM" == "1" ]; then
  cmake -DCMAKE_C_COMPILER=aarch64-linux-gnu-gcc \
        -DCMAKE_CXX_COMPILER=aarch64-linux-gnu-g++ \
        -DCMAKE_FIND_ROOT_PATH=/usr/aarch64-linux-gnu \
        -DCMAKE_PROGRAM_PATH=/usr/aarch64-linux-gnu/bin \
        ..
else
  cmake ..
fi
cmake --build . --config Release
cmake --build . --target install

popd
#### Java generation ####
cd cppbuild
cp -r ../src/main/java/* .

JAVACPP_VERSION=1.5.10
if [ ! -f javacpp.jar ]; then
  curl -L https://github.com/bytedeco/javacpp/releases/download/$JAVACPP_VERSION/javacpp-platform-$JAVACPP_VERSION-bin.zip -o javacpp-platform-$JAVACPP_VERSION-bin.zip
  unzip -j javacpp-platform-$JAVACPP_VERSION-bin.zip
fi

java -jar javacpp.jar us/ihmc/remotecaptury/RemoteCapturyConfig.java
cp us/ihmc/remotecaptury/*.java ../src/main/java/us/ihmc/remotecaptury
cp us/ihmc/remotecaptury/global/*.java ../src/main/java/us/ihmc/remotecaptury/global/

#### JNI compilation ####
if [ "$MAC_CROSS_COMPILE_ARM" == "1" ]; then
  java -jar javacpp.jar -properties macosx-arm64 us/ihmc/remotecaptury/*.java us/ihmc/remotecaptury/global/*.java -d javainstall
elif [ "$LINUX_CROSS_COMPILE_ARM" == "1" ]; then
  java -jar javacpp.jar -properties linux-arm64 -Dplatform.compiler=aarch64-linux-gnu-g++ us/ihmc/remotecaptury/*.java us/ihmc/remotecaptury/global/*.java -d javainstall
else
  java -jar javacpp.jar us/ihmc/remotecaptury/*.java us/ihmc/remotecaptury/global/*.java -d javainstall
fi

#### Copy shared libs to resources ####
# Linux
mkdir -p ../src/main/resources/remotecaptury-java/native/linux-x86_64
mkdir -p ../src/main/resources/remotecaptury-java/native/linux-arm64
if [ -f "javainstall/libjniremotecaptury.so" ]; then
  if [ "$LINUX_CROSS_COMPILE_ARM" == "1" ]; then
    cp javainstall/libjniremotecaptury.so ../src/main/resources/remotecaptury-java/native/linux-arm64
  else
    cp javainstall/libjniremotecaptury.so ../src/main/resources/remotecaptury-java/native/linux-x86_64
  fi
fi
if [ -f "lib/libRemoteCaptury.so" ]; then
  if [ "$LINUX_CROSS_COMPILE_ARM" == "1" ]; then
    cp lib/libRemoteCaptury.so ../src/main/resources/remotecaptury-java/native/linux-arm64
  else
    cp lib/libRemoteCaptury.so ../src/main/resources/remotecaptury-java/native/linux-x86_64
  fi
fi
# Windows
mkdir -p ../src/main/resources/remotecaptury-java/native/windows-x86_64
if [ -f "javainstall/jniremotecaptury.dll" ]; then
  cp javainstall/jniremotecaptury.dll ../src/main/resources/remotecaptury-java/native/windows-x86_64
fi
if [ -f "bin/RemoteCaptury.dll" ]; then
  cp bin/RemoteCaptury.dll ../src/main/resources/remotecaptury-java/native/windows-x86_64
fi
# macOS
mkdir -p ../src/main/resources/remotecaptury-java/native/macos-x86_64
mkdir -p ../src/main/resources/remotecaptury-java/native/macos-arm64
if [ -f "javainstall/libjniremotecaptury.dylib" ]; then
  if [ "$MAC_CROSS_COMPILE_ARM" == "1" ]; then
    cp javainstall/libjniremotecaptury.dylib ../src/main/resources/remotecaptury-java/native/macos-arm64
  else
    cp javainstall/libjniremotecaptury.dylib ../src/main/resources/remotecaptury-java/native/macos-x86_64
  fi
fi
if [ -f "lib/libRemoteCaptury.dylib" ]; then
  if [ "$MAC_CROSS_COMPILE_ARM" == "1" ]; then
    cp lib/libRemoteCaptury.dylib ../src/main/resources/remotecaptury-java/native/macos-arm64
  else
    cp lib/libRemoteCaptury.dylib ../src/main/resources/remotecaptury-java/native/macos-x86_64
  fi
fi