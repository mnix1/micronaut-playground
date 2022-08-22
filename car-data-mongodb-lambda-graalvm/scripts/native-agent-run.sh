#!/bin/sh

./gradlew car-data-mongodb-lambda-graalvm:dockerfileNative
./gradlew car-data-mongodb-lambda-graalvm:shadowJar
docker-compose -f car-data-mongodb-lambda-graalvm/docker-compose.yaml up native-agent-run