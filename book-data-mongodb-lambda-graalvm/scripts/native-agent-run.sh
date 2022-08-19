#!/bin/sh

./gradlew data-mongodb-lambda-graalvm:dockerfileNative
./gradlew data-mongodb-lambda-graalvm:shadowJar
docker-compose up native-agent-run