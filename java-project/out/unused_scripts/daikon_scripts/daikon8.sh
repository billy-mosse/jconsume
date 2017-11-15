#!/bin/bash

#CLASSPATH=$CLASSPATH:../pruebas/test:../pruebas/jolden:../pruebas/jGrandeV2/:../pruebas/:../pruebas/flanagan.jar:jdom.jar:bin:/home/billy/.m2/repository/ca/mcgill/sable/jasmin/2.4.0/jasmin-2.4.0.jar:/home/billy/.m2/repository/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:/home/billy/.m2/repository/ca/mcgill/sable/soot/2.4.0/soot-2.4.0.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/examples

CHICORY=../utils/daikon/ChicoryPremain.jar

#CHICORY=/home/billy/Projects/git/jconsume-global/jconsume-global/utils/daikon/ChicoryPremain.jar
DAIKON_JAR=/home/billy/Downloads/daikon/daikon-5.4.6/daikon.jar

#DAIKON_JAR=../utils/daikon/daikon4.jar



#$CLASSPATH=$CLASSPATH:/usr/lib/jvm/java-7-oracle/lib/tools.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/examples:/home/billy/Projects/git/jconsume-global/jconsume-global/bin/pruebas:/home/billy/Projects/git/jconsume-global/jconsume-global/src/pruebas:/home/billy/Projects/git/jconsume-global/jconsume-global/out:/home/billy/Projects/git/jconsume-global/jconsume-global/src:/home/billy/Projects/git/jconsume-global/jconsume-global:/home/billy/Projects/git/jconsume-global/jconsume-global/bin/examples:/home/billy/Projects/git/jconsume-global/jconsume-global/bin:/usr/lib/jvm/java-7-oracle/bin:.:

#echo $CLASSPATH


JAVA4CP=$JAVA4HOME/bundle/Classes/classes.jar


#CLASSPATH=/usr/lib/jvm/java-8-oracle/lib/tools.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/:/home/billy/Projects/git/jconsume-global/jconsume-global/out/:/home/billy/.m2/repository/ca/mcgill/sable/jasmin/2.5.0/jasmin-2.5.0.jar:/home/billy/.m2/repository/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:/home/billy/.m2/repository/ca/mcgill/sable/soot/3.0.0-SNAPSHOT/soot-3.0.0-SNAPSHOT.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/main/examples/:$JAVA4CP:.

CLASSPATH=/usr/lib/jvm/java-8-oracle/lib/tools.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/:/home/billy/Projects/git/jconsume-global/jconsume-global/out/:/home/billy/.m2/repository/ca/mcgill/sable/jasmin/2.5.0/jasmin-2.5.0.jar:/home/billy/.m2/repository/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:/home/billy/.m2/repository/ca/mcgill/sable/soot/2.5.0/soot-2.5.0.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/main/examples/:$JAVA4CP:.


echo "Chicory"


#echo $1$2

#java -cp $DAIKON_JAR::$CLASSPATH daikon.Chicory --comparability-file=$2.decls-DynComp "--output-dir=./daikon-output" --dtrace-file=$1.dtrace.gz $1$2 $3

java -cp $DAIKON_JAR::$CLASSPATH daikon.Chicory --premain=$CHICORY "--output-dir=./daikon-output" --dtrace-file=$1$2.dtrace.gz $1$2 $a3


echo Ejecutando Daikon

#java -cp $DAIKON_JAR::$CLASSPATH daikon.Daikon ./daikon-output/$1$2.dtrace.gz "--ppt-select-pattern=InstrumentedMethod.*:::ENTER" -o ./daikon-output/$1$2.inv.gz

