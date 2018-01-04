#!/bin/bash

CURRNET_DIR=$(dirname $0)
PROJECT_PATH=$CURRNET_DIR/..
SCRIPTS_PATH=$PROJECT_PATH/bin

$SCRIPTS_PATH/tools/specMigrator.sh config.properties "$1" "$2"