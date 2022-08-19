#!/bin/sh

./gradlew PrepareArtworkToPrintFunction:buildNativeLambda
docker-compose up -d prepare-artwork-to-print-run
sleep 2
curl -X POST "http://localhost:9001/2015-03-31/functions/function/invocations" -H 'Content-Type: application/json' -d '
{
 "Records": [
   {
     "messageId": "19dd0b57-b21e-4ac1-bd88-01bbb068cb78",
     "receiptHandle": "MessageReceiptHandle",
     "body": "{\"output\":{\"artworkDimension\":{\"width\":2378,\"height\":3314},\"licenseNoteDimension\":{\"width\":2520,\"height\":3456},\"location\":{\"bucket\":\"adam-kita-test\",\"key\":\"result.pdf\"}},\"artworkLocation\":{\"bucket\":\"displate-static-uat\",\"key\":\"displates/artwork/2019-06-15/fcdbadd89177995cdc56ff9197a47f33_1b3a725c92a6fb66f9682b3f96b9e4c6.jpg\"},\"licenseNoteLocation\":{\"bucket\":\"adam-kita-test\",\"key\":\"M_standard.jpg\"},\"annotation\":\"DESIGN NO. 613402_1001_MATTE\"}",
     "attributes": {
       "ApproximateReceiveCount": "1",
       "SentTimestamp": "1523232000000",
       "SenderId": "123456789012",
       "ApproximateFirstReceiveTimestamp": "1523232000001"
     },
     "messageAttributes": {},
     "md5OfBody": "{{{md5_of_body}}}",
     "eventSource": "aws:sqs",
     "eventSourceARN": "arn:aws:sqs:us-east-1:123456789012:MyQueue",
     "awsRegion": "us-east-1"
   }
 ]
}'
docker-compose stop prepare-artwork-to-print-run


