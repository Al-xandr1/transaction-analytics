#!/usr/bin/env bash

cd $(dirname $0)

# For specifying random filename:
# DATA_FILE=/Users/Alexander/dataset_remain.xls
# By default takes dataset file from jar
DATA_FILE=

java -jar transaction-analytics-1.0-SNAPSHOT.jar $DATA_FILE