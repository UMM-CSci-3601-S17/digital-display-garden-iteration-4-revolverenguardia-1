#!/bin/bash

rm -r ~/server
rm ~/server.tar
./gradlew clean
./gradlew build
cp server/build/distributions/server.tar ~
cd ~
pwd
tar xvf server.tar
sleep 1
cd server
pwd
head -c 32 /dev/random > jwtsalt.txt
bin/server

