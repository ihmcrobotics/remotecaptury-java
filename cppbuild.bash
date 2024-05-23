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
cp ../RemoteCaptury.cpp.patch sdk/network/RemoteCaptury.cpp.patch

# Convert all line endings to unix
find ./sdk -type f -print0 | xargs -0 dos2unix

cd sdk/network

patch RemoteCaptury.cpp RemoteCaptury.cpp.patch

mkdir build
cd build

cmake -DCMAKE_BUILD_TYPE=Release ..
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
java -jar javacpp.jar us/ihmc/remotecaptury/*.java us/ihmc/remotecaptury/global/*.java -d javainstall

#### Copy shared libs to resources ####
# Linux
if [ -f "javainstall/libjniremotecaptury.so" ]; then
  cp javainstall/libjniremotecaptury.so ../src/main/resources/native/linux-x86_64
fi
if [ -f "lib/libRemoteCaptury.so" ]; then
  cp lib/libRemoteCaptury.so ../src/main/resources/native/linux-x86_64
fi
# Windows
if [ -f "javainstall/jniremotecaptury.dll" ]; then
  cp javainstall/jniremotecaptury.dll ../src/main/resources/native/windows-x86_64
fi
if [ -f "bin/RemoteCaptury.dll" ]; then
  cp bin/RemoteCaptury.dll ../src/main/resources/native/windows-x86_64
fi
