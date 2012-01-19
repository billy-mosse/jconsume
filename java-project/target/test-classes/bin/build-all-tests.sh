#!/bin/bash

find_index() {
	index=-1
	idx=0
	
	# Create a string containing "TEST_CLASSES[*]"
    local array_string="$2[*]"

    # assign loc_array value to ${TEST_CLASSES[*]} using indirect variable reference
    local loc_array=(${!array_string})
	
	for element in ${loc_array[@]}
	do
		if [ $element == $1 ]; then
			index=$idx
			break
		fi
		idx=$((idx+1))
	done
}

USER_DIR=$(pwd)
CURRENT_DIR=$(dirname $0)
SUMMARIES_DIR=$CURRENT_DIR/..

TEST_CLASSES=("ar.uba.dc.simple.EjemploSimple01")
CLASS_DIR=("ejemplosimple01")
CLASS_SENSITIVITY=("-1,0")

TEST_CLASSES=( ${TEST_CLASSES[@]} "ar.uba.dc.rinard.BasicTest" )
CLASS_DIR=( ${CLASS_DIR[@]} "rinard" )
CLASS_SENSITIVITY=( ${CLASS_SENSITIVITY[@]} "-1,0" )

TEST_CLASSES=( ${TEST_CLASSES[@]} "ar.uba.dc.jolden.mst.MST" )
CLASS_DIR=( ${CLASS_DIR[@]} "mst" )
CLASS_SENSITIVITY=( ${CLASS_SENSITIVITY[@]} "-1,0" )

TEST_CLASSES=( ${TEST_CLASSES[@]} "ar.uba.dc.tacas.Snippet01" )
CLASS_DIR=( ${CLASS_DIR[@]} "tacas" )
CLASS_SENSITIVITY=( ${CLASS_SENSITIVITY[@]} "-1,0" )

TEST_CLASSES=( ${TEST_CLASSES[@]} "ar.uba.dc.basic.polymorphism.expectation.BasicTest" )
CLASS_DIR=( ${CLASS_DIR[@]} "algoritmo" )
CLASS_SENSITIVITY=( ${CLASS_SENSITIVITY[@]} "1" )

TO_PROCESS=$1
if [ "x$1" == "x" ]; then
	TO_PROCESS=${TEST_CLASSES[@]}	
fi 

for className in $TO_PROCESS 
do
	echo "* Building test for ${className}"
	
	$CURRENT_DIR/build-one-test.sh $className
	
	echo "* Building done"
	echo "* Moving files to test-folder"
	
	find_index $className TEST_CLASSES
	
	for sensitivity in $(echo ${CLASS_SENSITIVITY[$index]} | tr "," " " ) # hacemos el split de , usando el comando tr
	do
		for compareStrategy in add lazy
		do
			mkdir -p $SUMMARIES_DIR/escape/summary/${CLASS_DIR[$index]}-k-${sensitivity}
			cp -t $SUMMARIES_DIR/escape/summary/${CLASS_DIR[$index]}-k-${sensitivity} ${USER_DIR}/test/building/${sensitivity}/cha/${compareStrategy}/escape/*
			
			mkdir -p $SUMMARIES_DIR/memory/summary/${CLASS_DIR[$index]}-${compareStrategy}-k-${sensitivity}
			cp -t $SUMMARIES_DIR/memory/summary/${CLASS_DIR[$index]}-${compareStrategy}-k-${sensitivity} ${USER_DIR}/test/building/${sensitivity}/cha/${compareStrategy}/*
		done
	done
	
	echo "* Moving done"
	echo "* Deleting building files"

	rm -R -f ${USER_DIR}/test/building

	echo "* Deleting done"
	
	echo ""
	index=$((index+1))
done