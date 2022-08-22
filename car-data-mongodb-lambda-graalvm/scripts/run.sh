#!/bin/sh

./gradlew car-data-mongodb-lambda-graalvm:buildNativeLambda
docker-compose -f car-data-mongodb-lambda-graalvm/docker-compose.yaml up -d local-run
sleep 2
curl -X POST "http://localhost:9001/2015-03-31/functions/function/invocations" -H 'Content-Type: application/json' -d '
{
  "path": "/cars",
  "httpMethod": "GET"
}'
docker-compose -f car-data-mongodb-lambda-graalvm/docker-compose.yaml stop local-run