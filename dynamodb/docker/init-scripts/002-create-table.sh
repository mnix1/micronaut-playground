#!/bin/bash

#echo "########### Creating table with global secondary index ###########"
#aws   $AWS_ENDPOINT \
#      dynamodb create-table \
#         --table-name customer-loc \
#         --attribute-definitions \
#           AttributeName=id,AttributeType=S \
#           AttributeName=phone_number,AttributeType=S \
#        --key-schema AttributeName=id,KeyType=HASH \
#        --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
#        --global-secondary-indexes \
#                  "[
#                      {
#                          \"IndexName\": \"phone-number\",
#                          \"KeySchema\": [{\"AttributeName\":\"phone_number\",\"KeyType\":\"HASH\"}],
#                          \"Projection\":{
#                              \"ProjectionType\":\"ALL\"
#                          },
#                          \"ProvisionedThroughput\": {
#                              \"ReadCapacityUnits\": 5,
#                              \"WriteCapacityUnits\": 5
#                          }
#                      }
#                  ]"
#
#echo "########### Describing a table ###########"
#aws   $AWS_ENDPOINT \
#      dynamodb describe-table --table-name customer-loc --output table
#
#echo "########### Inserting test data into a table ###########"
#aws   $AWS_ENDPOINT \
#      dynamodb put-item --table-name customer-loc --item "{\"id\":{\"S\":\"29ae2e26-76df-4211-a8e8-f26f11b11588\"},
#                                                     \"phone_number\":{\"S\":\"1-962-894-4629\"},
#                                                     \"fullName\":{\"S\":\"Jarrod Kub V\"},
#                                                     \"address\":{\"S\":\"94912 Kihn Club, Lake Twana, AR 58508\"},
#                                                     \"createdAt\":{\"S\":\"2021-09-26\"},
#                                                     \"purchaseTransactions\":{\"L\":[{
#                                                                                    \"M\":{
#                                                                                           \"id\":{\"S\":\"29ae2e26-76df-4211-a8e8-f26f11b11588\"},
#                                                                                           \"paymentType\":{\"S\":\"VISA\"},
#                                                                                           \"amount\":{\"N\":\"53.96\"},
#                                                                                           \"createdAt\":{\"S\":\"2019-04-05\"}
#                                                                                           }
#                                                                                      }]
#                                                            }}"
#
#
#
#echo "########### Selecting all data from a table ###########"
#aws   $AWS_ENDPOINT \
#      dynamodb scan --table-name customer-loc