#!/bin/bash

CURRNET_DIR=$(dirname $0)
PROJECT_PATH=$CURRNET_DIR
SCRIPTS_PATH=$PROJECT_PATH/bin

$SCRIPTS_PATH/tools/metrics.sh $1 config.properties "$2"