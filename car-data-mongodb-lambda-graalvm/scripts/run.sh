#!/bin/sh

./gradlew car-data-mongodb-lambda-graalvm:buildNativeLambda
docker-compose up -d native-agent-run
sleep 2
curl -X POST "http://localhost:9001/2015-03-31/functions/function/invocations" -H 'Content-Type: application/json' -d '
{
  "path": "/cars",
  "httpMethod": "GET"
}'
docker-compose stop native-agent-run


