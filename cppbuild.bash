#!/bin/bash
pushd .
mkdir cppbuild
cd cppbuild

wget -nc https://dn-cap.com/CapturyLiveSDK.tar.gz
tar -xvf CapturyLiveSDK.tar.gz

cp ../CMakeLists.txt.RemoteCaptury sdk/network/CMakeLists.txt
cp ../RemoteCaptury.cpp.patch sdk/network/RemoteCaptury.cpp.patch

cd sdk/network

patch RemoteCaptury.cpp RemoteCaptury.cpp.patch

mkdir build
cd build
cmake ..
make
make install
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
cp javainstall/libjniremotecaptury.so ../src/main/resources/native/linux-x86_64
cp lib/libRemoteCaptury.so ../src/main/resources/native/linux-x86_64
