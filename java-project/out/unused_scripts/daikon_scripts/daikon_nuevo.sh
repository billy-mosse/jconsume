#!/bin/bash


DAIKON_JAR=/home/billy/Programs/daikon/daikon-5.5.2/daikon.jar


CLASSPATH=.

CHICORY=/home/billy/Programs/daikon/daikon-5.5.2/java/ChicoryPremain.jar

#java -cp "/home/billy/Downloads/daikon/daikon-5.4.6/daikon.jar:." daikon.DynComp $1$2 10

#java -cp .:$CLASSPATH daikon.Chicory \
#     --comparability-file=$2.decls-DynComp \
#     $1$2 10

#java daikon.Daikon $2.dtrace.gz

echo $CLASSPATH


java -cp $CLASSPATH:$DAIKON_JAR daikon.Chicory --premain=$CHICORY --verbose \
         $1$2 $3

#java daikon.Daikon "--ppt-select-pattern=InstrumentedMethod" $2.dtrace.gz --nohierarchy --config_option daikon.Daikon.enable_floats=false --config_option daikon.inv.binary.twoScalar.FloatEqual.enabled=false --config_option daikon.inv.binary.twoScalar.LinearBinaryFloat.enabled=false --config_option  daikon.inv.ternary.threeScalar.LinearTernaryFloat.enabled=false --config_option  daikon.inv.unary.scalar.Modulus.enabled=true --config_option  daikon.inv.unary.scalar.NonModulus.enabled=true
