#!/bin/bash

#CLASSPATH=$CLASSPATH:../pruebas/test:../pruebas/jolden:../pruebas/jGrandeV2/:../pruebas/:../pruebas/flanagan.jar:jdom.jar:bin:/home/billy/.m2/repository/ca/mcgill/sable/jasmin/2.4.0/jasmin-2.4.0.jar:/home/billy/.m2/repository/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:/home/billy/.m2/repository/ca/mcgill/sable/soot/2.4.0/soot-2.4.0.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/examples

echo Ejecutando Chicory

#CHICORY=/home/billy/Projects/git/jconsume-global/jconsume-global/utils/daikon/ChicoryPremain.jar
DAIKON_JAR=/home/billy/Programs/daikon/daikon-5.5.2/daikon.jar

#DAIKON_JAR=../utils/daikon/daikon4.jar

JAVA_HOME=/usr/lib/jvm/java-8-oracle/jre/bin

#$CLASSPATH=$CLASSPATH:/usr/lib/jvm/java-7-oracle/lib/tools.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/examples:/home/billy/Projects/git/jconsume-global/jconsume-global/bin/pruebas:/home/billy/Projects/git/jconsume-global/jconsume-global/src/pruebas:/home/billy/Projects/git/jconsume-global/jconsume-global/out:/home/billy/Projects/git/jconsume-global/jconsume-global/src:/home/billy/Projects/git/jconsume-global/jconsume-global:/home/billy/Projects/git/jconsume-global/jconsume-global/bin/examples:/home/billy/Projects/git/jconsume-global/jconsume-global/bin:/usr/lib/jvm/java-7-oracle/bin:.:

#echo $CLASSPATH


#JAVA4CP=$JAVA4HOME/bundle/Classes/classes.jar


#CLASSPATH=$DAIKON_JAR:/usr/lib/jvm/java-8-oracle/lib/tools.jar:/usr/lib/jvm/java-8-oracle/jre/bin/jre/lib/rt.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/src/:/home/billy/Projects/git/jconsume-global/jconsume-global/out/:/home/billy/Projects/git/jconsume-global/jconsume-global/src/main/examples/:$JAVA4CP:.



CLASSPATH_DAIKON=/home/billy/Programs/daikon/daikon-5.5.14/daikon.jar:/usr/lib/jvm/java-8-oracle/jre/bin/jre/lib/rt.jar:/usr/lib/jvm/java-8-oracle/jre/bin/lib/tools.jar:/home/billy/Projects/git/jconsume/java-project/out/$1/


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



#TODO: dejar el pattern para InstrumentedMethod en general
#creo que anda "--ppt-select-pattern=$.InstrumentedMethod.*:::ENTER"
#"--ppt-select-pattern=ar\.uba\.dc\.daikon\.Ins5\.f\(*"
#"--ppt-select-pattern=A\.doNothingForInstrmentedMethod\("
#"--ppt-select-pattern=ar.\uba\.\dc\.daikon\.A:::PATTERN"
#"--ppt-select-pattern=dummyMethod$"
java -cp $CLASSPATH_DAIKON daikon.Chicory "--output-dir=./daikon-output" "--ppt-select-pattern=.+\.InstrumentedMethod.*:::ENTER" "--ppt-select-pattern=dummyMethodForInstrumentation$" --dtrace-file=$1Test.dtrace.gz $1Test $2 

echo Ejecutando Daikon


#
#--config_option daikon.inv.binary.twoScalar.LinearBinary.enabled=true ax+by+c=0. Ojo que este es un formato nuevo que barvinok aceptaria pero hay que tener cuidado a la hora de parsearlo
java -cp $DAIKON_JAR::$CLASSPATH_DAIKON daikon.Daikon ./daikon-output/$1Test.dtrace.gz \
"--ppt-select-pattern=.+\.InstrumentedMethod.*:::ENTER" --nohierarchy --config_option daikon.Daikon.enable_floats=true --config_option daikon.inv.binary.twoScalar.FloatEqual.enabled=false --config_option daikon.inv.binary.twoScalar.LinearBinaryFloat.enabled=false  --config_option daikon.inv.ternary.threeScalar.LinearTernaryFloat.enabled=false --config_option daikon.inv.unary.scalar.Modulus.enabled=false --config_option  daikon.inv.unary.scalar.NonModulus.enabled=false  --config_option daikon.inv.unary.scalar.LowerBound.enabled=true --config_option daikon.inv.binary.twoScalar.IntGreaterThan.enabled=true --config_option daikon.inv.binary.twoScalar.IntGreaterEqual.enabled=true --config_option daikon.inv.binary.twoScalar.IntEqual.enabled=true --config_option daikon.inv.binary.twoScalar.IntLessThan.enabled=true --config_option daikon.inv.binary.twoScalar.IntNonEqual.enabled=true --config_option daikon.derive.Derivation.disable_derived_variables=false --config_option daikon.Debug.show_stack_trace=true --config_option daikon.derive.binary.SequenceFloatSubscript.enabled=false --config_option daikon.inv.unary.sequence.EltOneOf.enabled=false --config_option daikon.inv.binary.twoSequence.Reverse.enabled=false --config_option daikon.inv.binary.twoSequence.ReverseFloat.enabled=false --config_option  daikon.inv.binary.sequenceScalar.SeqIntEqual.enabled=false --config_option daikon.inv.unary.sequence.EltLowerBound.enabled=false --config_option daikon.inv.unary.sequence.EltUpperBound.enabled=false --config_option daikon.derive.unary.StringLength.enabled=true --config_option daikon.inv.unary.sequence.EltOneOf.size=0 --config_option daikon.inv.binary.sequenceScalar.Member.enabled=false --config_option daikon.inv.unary.string.OneOfString.size=0  --config_option daikon.inv.unary.scalar.RangeInt.PowerOfTwo.enabled=false --config_option daikon.inv.binary.sequenceString.MemberString.enabled=false --config_option daikon.inv.binary.sequenceScalar.MemberFloat.enabled=false --config_option daikon.inv.binary.sequenceScalar.SeqFloatEqual.enabled=false --config_option daikon.inv.binary.sequenceScalar.SeqFloatGreaterEqual.enabled=false --config_option daikon.inv.binary.sequenceScalar.SeqFloatGreaterThan.enabled=false --config_option daikon.inv.binary.sequenceScalar.SeqFloatLessEqual.enabled=false --config_option daikon.inv.binary.sequenceScalar.SeqFloatLessThan.enabled=false --config_option daikon.inv.binary.sequenceScalar.SeqIntEqual.enabled=false --config_option daikon.inv.binary.sequenceScalar.SeqIntGreaterEqual.enabled=false --config_option daikon.inv.binary.sequenceScalar.SeqIntGreaterThan.enabled=false --config_option daikon.inv.binary.sequenceScalar.SeqIntLessEqual.enabled=false --config_option daikon.inv.binary.sequenceScalar.SeqIntLessThan.enabled=false --config_option daikon.inv.unary.sequence.EltLowerBound.enabled=false --config_option daikon.inv.unary.sequence.EltLowerBoundFloat.enabled=false --config_option daikon.inv.unary.sequence.EltNonZero.enabled=false --config_option daikon.inv.unary.sequence.EltNonZeroFloat.enabled=false --config_option daikon.inv.unary.sequence.EltOneOf.enabled=false --config_option daikon.inv.unary.sequence.EltOneOfFloat.enabled=false --config_option daikon.inv.unary.sequence.EltRangeInt.Even.enabled=false --config_option daikon.inv.unary.sequence.EltRangeInt.PowerOfTwo.enabled=false --config_option daikon.inv.unary.sequence.EltUpperBound.enabled=false --config_option daikon.inv.unary.sequence.EltwiseFloatGreaterEqual.enabled=false --config_option daikon.inv.unary.sequence.EltwiseFloatGreaterThan.enabled=false --config_option daikon.inv.unary.sequence.EltwiseFloatLessEqual.enabled=false --config_option daikon.inv.unary.sequence.EltwiseFloatLessThan.enabled=false --config_option daikon.inv.unary.sequence.EltwiseIntEqual.enabled=false --config_option daikon.inv.unary.sequence.EltwiseIntGreaterEqual.enabled=false --config_option daikon.inv.unary.sequence.EltwiseIntGreaterThan.enabled=false --config_option daikon.inv.unary.sequence.EltwiseIntLessEqual.enabled=false --config_option daikon.inv.unary.sequence.EltwiseIntLessThan.enabled=false -o ./daikon-output/$1Test.inv.gz


# --config_option daikon.inv.unary.scalar.OneOfScalar.size=0 --config_option daikon.inv.unary.scalar.CompleteOneOfScalar.enabled=false