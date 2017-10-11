#!/bin/bash

#CLASSPATH=$CLASSPATH:../pruebas/test:../pruebas/jolden:../pruebas/jGrandeV2/:../pruebas/:../pruebas/flanagan.jar:jdom.jar:bin:/home/billy/.m2/repository/ca/mcgill/sable/jasmin/2.4.0/jasmin-2.4.0.jar:/home/billy/.m2/repository/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:/home/billy/.m2/repository/ca/mcgill/sable/soot/2.4.0/soot-2.4.0.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/examples

echo Ejecutando Chicory

#CHICORY=/home/billy/Projects/git/jconsume-global/jconsume-global/utils/daikon/ChicoryPremain.jar
DAIKON_JAR=/home/billy/Downloads/daikon/daikon-5.5.2/daikon.jar

#DAIKON_JAR=../utils/daikon/daikon4.jar



#$CLASSPATH=$CLASSPATH:/usr/lib/jvm/java-7-oracle/lib/tools.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/examples:/home/billy/Projects/git/jconsume-global/jconsume-global/bin/pruebas:/home/billy/Projects/git/jconsume-global/jconsume-global/src/pruebas:/home/billy/Projects/git/jconsume-global/jconsume-global/out:/home/billy/Projects/git/jconsume-global/jconsume-global/src:/home/billy/Projects/git/jconsume-global/jconsume-global:/home/billy/Projects/git/jconsume-global/jconsume-global/bin/examples:/home/billy/Projects/git/jconsume-global/jconsume-global/bin:/usr/lib/jvm/java-7-oracle/bin:.:

#echo $CLASSPATH


#JAVA4CP=$JAVA4HOME/bundle/Classes/classes.jar


#CLASSPATH=$DAIKON_JAR:/usr/lib/jvm/java-8-oracle/lib/tools.jar:/usr/lib/jvm/java-8-oracle/jre/bin/jre/lib/rt.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/:/home/billy/Projects/git/jconsume-global/jconsume-global/out/:/home/billy/Projects/git/jconsume-global/jconsume-global/src/main/examples/:$JAVA4CP:.



CLASSPATH=/home/billy/Programs/daikon/daikon-5.5.2/daikon.jar:/usr/lib/jvm/java-8-oracle/jre/bin/jre/lib/rt.jar:/usr/lib/jvm/java-8-oracle/jre/bin/lib/tools.jar:/home/billy/Projects/git/jconsume/java-project/out/$1/


#echo "DynComp"

#java -cp $CLASSPATH:$DAIKON_JAR daikon.DynComp "--ppt-select-pattern=InstrumentedMethod" $1 $3

echo "Chicory"


#echo $1$2

#le saque el "--ppt-select-pattern=InstrumentedMethod" a ver si asi me agrega informacion!! creo que era eso <------ esto esta mal porque genero traza para todo y no escala

# creo que std-visibility pisa el pattern y tambien esta mal
# la verdad no estoy seguro, porque hice la prueba y no lo pisa
# pero no estoy seguro de que hace exactamente, pese a la descripcion de la pagina
#--instrument-clinit?
java -cp $CLASSPATH daikon.Chicory "--output-dir=./daikon-output" "--ppt-select-pattern=InstrumentedMethod.*:::ENTER" --std-visibility --nesting-depth=3 --dtrace-file=$1Test.dtrace.gz $1Test $2

echo Ejecutando Daikon




java -cp $DAIKON_JAR::$CLASSPATH daikon.Daikon ./daikon-output/$1Test.dtrace.gz \
"--ppt-select-pattern=InstrumentedMethod.*:::ENTER" --nohierarchy --config_option daikon.Daikon.enable_floats=false --config_option daikon.inv.binary.twoScalar.FloatEqual.enabled=false --config_option daikon.inv.binary.twoScalar.LinearBinaryFloat.enabled=false --config_option  daikon.inv.ternary.threeScalar.LinearTernaryFloat.enabled=false --config_option  daikon.inv.unary.scalar.Modulus.enabled=true --config_option  daikon.inv.unary.scalar.NonModulus.enabled=true --conf_limit 0.20 -o ./daikon-output/$1Test.inv.gz

