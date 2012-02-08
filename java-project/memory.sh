#!/bin/bash

CURRNET_DIR=$(dirname $0)
PROJECT_PATH=$CURRNET_DIR
SCRIPTS_PATH=$PROJECT_PATH/bin

cp target/classes/application.rinard.properties  target/classes/application.properties

$SCRIPTS_PATH/memory.sh $1 config.properties "$2"
