#!/usr/bin/env bash

set -e


die() {

echo "$1.$2" 
 2> error
exit 1

}

M2_REPO=/home/billy/.m2/repository


M2_REPOSITORY_PATH=/home/billy/.m2/repository




JAVA4HOME=/usr/lib/jvm/java-8-oracle
JAVA4BIN=$JAVA4HOME/bin


CLASSPATH_JCONSUME_FOLDERS=/home/billy/Projects/git/jconsume/java-project/target/classes/:/home/billy/Projects/git/jconsume/java-project/src/:/home/billy/Projects/git/jconsume/jpfHelper/src:/home/billy/Projects/git/jconsume/java-project/src/main/java




CLASSPATH_JARS=/home/billy/Downloads/soot-trunk.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/jasminclasses-2.5.0.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/polyglotclasses-1.3.5.jar:$M2_REPO/com/google/guava/guava/19.0/guava-19.0.jar


CLASSPATH2=/home/billy/Downloads/heros-trunk.jar:/home/billy/Downloads/herosclasses-trunk-sources.jar:/home/billy/Downloads/herosclasses-trunk.jar:/home/billy/Projects/git/jconsume/java-project/jdom.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/sootclasses.jar:M2_REPO/ca/mcgill/sable/jasmin/2.5.0-SNAPSHOT/jasmin-2.5.0-20170511.223644-105.jar:M2_REPO/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:M2_REPO/cglib/cglib-nodep/2.1_3/cglib-nodep-2.1_3.jar:M2_REPO/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar:M2_REPO/com/google/guava/guava/19.0/guava-19.0.jar:M2_REPO/com/thoughtworks/xstream/xstream/1.3.1/xstream-1.3.1.jar:M2_REPO/com/verimag/madeja/1.0/madeja-1.0.jar:M2_REPO/commons-beanutils/commons-beanutils-core/1.8.0/commons-beanutils-core-1.8.0.jar:M2_REPO/commons-cli/commons-cli/1.2/commons-cli-1.2.jar:M2_REPO/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:M2_REPO/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar:M2_REPO/commons-digester/commons-digester/1.8/commons-digester-1.8.jar:M2_REPO/commons-io/commons-io/1.4/commons-io-1.4.jar:M2_REPO/commons-lang/commons-lang/2.5/commons-lang-2.5.jar:M2_REPO/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:M2_REPO/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:M2_REPO/jep/jep/2.4.1/jep-2.4.1.jar:M2_REPO/junit/junit-dep/4.4/junit-dep-4.4.jar:M2_REPO/junit/junit/4.8.1/junit-4.8.1.jar:M2_REPO/log4j/log4j/1.2.12/log4j-1.2.12.jar:M2_REPO/org/apache/velocity/velocity/1.5/velocity-1.5.jar:M2_REPO/org/hamcrest/hamcrest-all/1.1/hamcrest-all-1.1.jar:M2_REPO/org/hamcrest/hamcrest-core/1.1/hamcrest-core-1.1.jar:M2_REPO/org/hamcrest/hamcrest-library/1.1/hamcrest-library-1.1.jar:M2_REPO/org/jmock/jmock-junit4/2.5.1/jmock-junit4-2.5.1.jar:M2_REPO/org/jmock/jmock-legacy/2.5.1/jmock-legacy-2.5.1.jar:M2_REPO/org/jmock/jmock/2.5.1/jmock-2.5.1.jar:M2_REPO/org/objenesis/objenesis/1.0/objenesis-1.0.jar:M2_REPO/oro/oro/2.0.8/oro-2.0.8.jar:M2_REPO/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar:M2_REPO/xpp3/xpp3_min/1.1.4c/xpp3_min-1.1.4c.jar:



#no tengo la mas puta idea de por que si lo llamo FULLNAME no lo pasa bien
FULLNAMETEST=$1.$2

CLASSPATH=$CLASSPATH_JCONSUME_FOLDERS:$CLASSPATH_JARS:$CLASSPATH2

rm -f -R out/$FULLNAMETEST 
mkdir -p out/$FULLNAMETEST

echo Fase 1: Instrumentation 
. ./ii $FULLNAMETEST > instrumenta.log || die "Problema en fase 1" 


#exit 0
echo Fase 2: Compilation of InstrumentedMehod Class for Daikon

MYPATH=`echo $1 | sed 's/\./\//g'`

echo "Copio el Test class"
cp target/classes/$MYPATH/$2Test.class out/$FULLNAMETEST/$MYPATH/
cd out

echo "Compilo (los) InstrumentedMethod"

#el {} es un placeholder para el resultado del find
#A veces las variables tienen un numeral y es super molesto....
#como hack los cambio por un guion bajo pero me gustaria ver si hay una opcion para que no genere nombres con guion bajo
#-exec sed -i -e 's/#/_/g' {} \;
find $FULLNAMETEST -name InstrumentedMethod.java -exec sed -i -e 's/#/_/g' {} \; -exec javac -g {} \;

javac -g $FULLNAMETEST/$MYPATH/InstrumentedMethod.java || die "Problema en fase 2"


echo Fase 3: Daikon for generating invariants


./daikon.sh $FULLNAMETEST 10 > $1.$2/$FULLNAMETEST.txt || die "Problema en fase 3"

mv $1.$2/$FULLNAMETEST.indFake $1.$2/$FULLNAMETEST.ind

sed -i -n -e '/===========================================================================/,$p' $1.$2/$FULLNAMETEST.txt


echo Fase 4: Imprimir invariantes para formato de jconsume
. ./spec.sh
