#!/bin/bash

CURRENT_DIR=$(dirname $0)
PROJECT_PATH=${CURRENT_DIR}/../../../..

SCRIPTS_PATH=$PROJECT_PATH/bin

$SCRIPTS_PATH/memory.sh $1 ${CURRENT_DIR}/add_-1.properties "$2"
$SCRIPTS_PATH/memory.sh $1 ${CURRENT_DIR}/add_0.properties "$2"
$SCRIPTS_PATH/memory.sh $1 ${CURRENT_DIR}/add_1.properties "$2"
$SCRIPTS_PATH/memory.sh $1 ${CURRENT_DIR}/lazy_-1.properties "$2"
$SCRIPTS_PATH/memory.sh $1 ${CURRENT_DIR}/lazy_0.properties "$2"
$SCRIPTS_PATH/memory.sh $1 ${CURRENT_DIR}/lazy_1.properties "$2"
