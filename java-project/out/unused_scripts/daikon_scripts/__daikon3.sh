#!/bin/bash

#CLASSPATH=$CLASSPATH:../pruebas/test:../pruebas/jolden:../pruebas/jGrandeV2/:../pruebas/:../pruebas/flanagan.jar:jdom.jar:bin:/home/billy/.m2/repository/ca/mcgill/sable/jasmin/2.4.0/jasmin-2.4.0.jar:/home/billy/.m2/repository/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:/home/billy/.m2/repository/ca/mcgill/sable/soot/2.4.0/soot-2.4.0.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/examples

echo Ejecutando Chicory

#CHICORY=/home/billy/Projects/git/jconsume-global/jconsume-global/utils/daikon/ChicoryPremain.jar
DAIKON_JAR=/home/billy/Programs/daikon/daikon-5.5.2/daikon.jar

#DAIKON_JAR=../utils/daikon/daikon4.jar



#$CLASSPATH=$CLASSPATH:/usr/lib/jvm/java-7-oracle/lib/tools.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/examples:/home/billy/Projects/git/jconsume-global/jconsume-global/bin/pruebas:/home/billy/Projects/git/jconsume-global/jconsume-global/src/pruebas:/home/billy/Projects/git/jconsume-global/jconsume-global/out:/home/billy/Projects/git/jconsume-global/jconsume-global/src:/home/billy/Projects/git/jconsume-global/jconsume-global:/home/billy/Projects/git/jconsume-global/jconsume-global/bin/examples:/home/billy/Projects/git/jconsume-global/jconsume-global/bin:/usr/lib/jvm/java-7-oracle/bin:.:

#echo $CLASSPATH


#JAVA4CP=$JAVA4HOME/bundle/Classes/classes.jar


#CLASSPATH=$DAIKON_JAR:/usr/lib/jvm/java-8-oracle/lib/tools.jar:/usr/lib/jvm/java-8-oracle/jre/bin/jre/lib/rt.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/:/home/billy/Projects/git/jconsume-global/jconsume-global/out/:/home/billy/Projects/git/jconsume-global/jconsume-global/src/main/examples/:$JAVA4CP:.



CLASSPATH=/home/billy/Programs/daikon/daikon-5.5.14/daikon.jar:/usr/lib/jvm/java-8-oracle/jre/bin/jre/lib/rt.jar:/usr/lib/jvm/java-8-oracle/jre/bin/lib/tools.jar:/home/billy/Projects/git/jconsume/java-project/out/StackAr/


#echo "DynComp"

#java -cp $CLASSPATH:$DAIKON_JAR daikon.DynComp "--ppt-select-pattern=InstrumentedMethod" $1 $3

echo "Chicory"


#echo $1$2

#le saque el "--ppt-select-pattern=InstrumentedMethod" a ver si asi me agrega informacion!! creo que era eso <------ esto esta mal porque genero traza para todo y no escala

# creo que std-visibility pisa el pattern y tambien esta mal
# la verdad no estoy seguro, porque hice la prueba y no lo pisa
# pero no estoy seguro de que hace exactamente, pese a la descripcion de la pagina
#--instrument-clinit?
#"--ppt-omit-pattern=List.a*"

#"--ppt-select-pattern=ar\.uba\.dc\.daikon\.Ins5\.f\(*"
#"--ppt-select-pattern=A\.doNothingForInstrmentedMethod\("
#"--ppt-select-pattern=ar.\uba\.\dc\.daikon\.A:::PATTERN"
java -cp $CLASSPATH daikon.Chicory "--ppt-select-pattern=DataStructures\.MyInteger\.doNothingForInstrmentedMethod\(" "--ppt-select-pattern=DataStructures\.StackArTester\.doNothingForInstrmentedMethod\(" --nesting-depth=3 $1

#echo Ejecutando Daikon



#--config_option daikon.inv.binary.twoScalar.LinearBinary.enabled=true ax+by+c=0. Ojo que este es un formato nuevo que barvinok aceptaria pero hay que tener cuidado a la hora de parsearlo
#java -cp $DAIKON_JAR::$CLASSPATH daikon.Daikon ./daikon-output/$1Test.dtrace.gz \
#"--ppt-select-pattern=InstrumentedMethod.*:::ENTER" --nohierarchy --config_option daikon.Daikon.enable_floats=false --config_option daikon.inv.binary.twoScalar.FloatEqual.enabled=false --config_option daikon.inv.binary.twoScalar.LinearBinaryFloat.enabled=false  --config_option daikon.inv.ternary.threeScalar.LinearTernaryFloat.enabled=false --config_option daikon.inv.unary.scalar.Modulus.enabled=false --config_option  daikon.inv.unary.scalar.NonModulus.enabled=false  --config_option daikon.inv.unary.scalar.LowerBound.enabled=true --config_option daikon.inv.binary.twoScalar.IntGreaterThan.enabled=true --config_option daikon.inv.binary.twoScalar.IntGreaterEqual.enabled=true --config_option daikon.inv.binary.twoScalar.IntLessThan.enabled=true --config_option daikon.inv.binary.twoScalar.IntNonEqual.enabled=true --config_option daikon.derive.Derivation.disable_derived_variables=false -o ./daikon-output/$1Test.inv.gz

#--config_option daikon.PrintInvariants.print_all=true