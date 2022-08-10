#!/bin/bash
echo "########### Creating profile ###########"
aws configure set aws_access_key_id TEST_ACCESS_KEY_ID --profile=dynamo-db-local
aws configure set aws_secret_access_key TEST_SECRET_KEY --profile=dynamo-db-local
aws configure set region eu-central-1 --profile=dynamo-db-local

echo "########### Listing profile ###########"
aws configure list