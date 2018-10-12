#!/usr/bin/env bash

set -e


die() {

echo "$1" 
 2> error
exit 1

}

assert() {
  if ! eval $* ; then
      echo
      echo "===== Assertion failed:  \"$*\" ====="
      echo "File \"$0\", line:$LINENO line:${BASH_LINENO[*]}"
      echo line:$(caller 0)
      exit 99
  fi  
}

JCONSUME_FOLDER=$HOME/Projects/git/jconsume
PROJECT_FOLDER=$JCONSUME_FOLDER/java-project



FULLNAMETEST=$1
PACKAGE="${FULLNAMETEST%.*}"

NAME="${FULLNAMETEST##*.}"

#assert [[ $FULLNAMETEST == $PACKAGE.$NAME ]]

M2_REPO=$HOME/.m2/repository


M2_REPOSITORY_PATH=$HOME/.m2/repository


PROJECT_FOLDER=$HOME/Projects/git/jconsume/java-project

JAVA4HOME=/usr/lib/jvm/java-8-oracle
JAVA4BIN=$JAVA4HOME/bin


CLASSPATH_JCONSUME_FOLDERS=$PROJECT_FOLDER/target/classes/:$PROJECT_FOLDER/src/:$JCONSUME_FOLDER/jpfHelper/src:$PROJECT_FOLDER/src/main/java




CLASSPATH_JARS=$HOME/Downloads/soot-trunk.jar:$JCONSUME_FOLDER/dependencies/sootlib/jasminclasses-2.5.0.jar:$JCONSUME_FOLDER/dependencies/sootlib/polyglotclasses-1.3.5.jar:$M2_REPO/com/google/guava/guava/19.0/guava-19.0.jar


CLASSPATH2=$PROJECT_FOLDER/heros-trunk.jar:$PROJECT_FOLDER/herosclasses-trunk-sources.jar:$PROJECT_FOLDER/herosclasses-trunk.jar:$PROJECT_FOLDER/jdom.jar:$HOME/Projects/git/jconsume/dependencies/sootlib/sootclasses.jar:M2_REPO/ca/mcgill/sable/jasmin/2.5.0-SNAPSHOT/jasmin-2.5.0-20170511.223644-105.jar:M2_REPO/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:M2_REPO/cglib/cglib-nodep/2.1_3/cglib-nodep-2.1_3.jar:M2_REPO/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar:M2_REPO/com/google/guava/guava/19.0/guava-19.0.jar:M2_REPO/com/thoughtworks/xstream/xstream/1.3.1/xstream-1.3.1.jar:M2_REPO/com/verimag/madeja/1.0/madeja-1.0.jar:M2_REPO/commons-beanutils/commons-beanutils-core/1.8.0/commons-beanutils-core-1.8.0.jar:M2_REPO/commons-cli/commons-cli/1.2/commons-cli-1.2.jar:M2_REPO/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:M2_REPO/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar:M2_REPO/commons-digester/commons-digester/1.8/commons-digester-1.8.jar:M2_REPO/commons-io/commons-io/1.4/commons-io-1.4.jar:M2_REPO/commons-lang/commons-lang/2.5/commons-lang-2.5.jar:M2_REPO/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:M2_REPO/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:M2_REPO/jep/jep/2.4.1/jep-2.4.1.jar:M2_REPO/junit/junit-dep/4.4/junit-dep-4.4.jar:M2_REPO/junit/junit/4.8.1/junit-4.8.1.jar:M2_REPO/log4j/log4j/1.2.12/log4j-1.2.12.jar:M2_REPO/org/apache/velocity/velocity/1.5/velocity-1.5.jar:M2_REPO/org/hamcrest/hamcrest-all/1.1/hamcrest-all-1.1.jar:M2_REPO/org/hamcrest/hamcrest-core/1.1/hamcrest-core-1.1.jar:M2_REPO/org/hamcrest/hamcrest-library/1.1/hamcrest-library-1.1.jar:M2_REPO/org/jmock/jmock-junit4/2.5.1/jmock-junit4-2.5.1.jar:M2_REPO/org/jmock/jmock-legacy/2.5.1/jmock-legacy-2.5.1.jar:M2_REPO/org/jmock/jmock/2.5.1/jmock-2.5.1.jar:M2_REPO/org/objenesis/objenesis/1.0/objenesis-1.0.jar:M2_REPO/oro/oro/2.0.8/oro-2.0.8.jar:M2_REPO/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar:M2_REPO/xpp3/xpp3_min/1.1.4c/xpp3_min-1.1.4c.jar:

M2_REPO=$HOME/.m2/repository



CLASSPATH_ECLIPSE=$HOME/.m2/repository/ca/mcgill/sable/soot/3.0.0-SNAPSHOT/soot-3.0.0-SNAPSHOT.jar:$M2_REPO/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:$M2_REPO/ca/mcgill/sable/jasmin/2.5.0-SNAPSHOT/jasmin-2.5.0-20170511.223644-105.jar:$M2_REPO/com/verimag/madeja/1.0/madeja-1.0.jar:$M2_REPO/com/thoughtworks/xstream/xstream/1.3.1/xstream-1.3.1.jar:$M2_REPO/xpp3/xpp3_min/1.1.4c/xpp3_min-1.1.4c.jar:$M2_REPO/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:$M2_REPO/junit/junit/4.8.1/junit-4.8.1.jar:$M2_REPO/log4j/log4j/1.2.12/log4j-1.2.12.jar:$M2_REPO/org/hamcrest/hamcrest-all/1.1/hamcrest-all-1.1.jar:$M2_REPO/commons-lang/commons-lang/2.5/commons-lang-2.5.jar:$M2_REPO/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:$M2_REPO/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar:$M2_REPO/commons-digester/commons-digester/1.8/commons-digester-1.8.jar:$M2_REPO/commons-beanutils/commons-beanutils/1.8.0/commons-beanutils-1.8.0.jar:$M2_REPO/commons-beanutils/commons-beanutils-core/1.8.0/commons-beanutils-core-1.8.0.jar:$M2_REPO/org/jmock/jmock/2.5.1/jmock-2.5.1.jar:$M2_REPO/org/hamcrest/hamcrest-core/1.1/hamcrest-core-1.1.jar:$M2_REPO/org/hamcrest/hamcrest-library/1.1/hamcrest-library-1.1.jar:$M2_REPO/org/jmock/jmock-junit4/2.5.1/jmock-junit4-2.5.1.jar:$M2_REPO/junit/junit-dep/4.4/junit-dep-4.4.jar:$M2_REPO/org/jmock/jmock-legacy/2.5.1/jmock-legacy-2.5.1.jar:$M2_REPO/org/objenesis/objenesis/1.0/objenesis-1.0.jar:$M2_REPO/cglib/cglib-nodep/2.1_3/cglib-nodep-2.1_3.jar:home/billy/Projects/git/jconsume/dependencies/sootlib/sootclasses.jar:$HOME/Projects/git/jconsume/dependencies/sootlib/sootsrc-2.5.0.jar:$M2_REPO/jep/jep/2.4.1/jep-2.4.1.jar:$M2_REPO/commons-io/commons-io/1.4/commons-io-1.4.jar:$M2_REPO/org/apache/velocity/velocity/1.5/velocity-1.5.jar:$M2_REPO/oro/oro/2.0.8/oro-2.0.8.jar:$M2_REPO/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:$M2_REPO/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar:$M2_REPO/commons-cli/commons-cli/1.2/commons-cli-1.2.jar:$M2_REPO/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar:$M2_REPO/com/google/guava/guava/19.0/guava-19.0.jar:$HOME/.m2/repository/heros/heros/0.0.1-SNAPSHOT/heros-0.0.1-SNAPSHOT.jar:$PROJECT_FOLDER/herosclasses-trunk.jar:$PROJECT_FOLDER/herosclasses-trunk-sources.jar:$PROJECT_FOLDER/heros-trunk.jar:$PROJECT_FOLDER/jdom.jar


#CLASSPATH=$CLASSPATH_JCONSUME_FOLDERS:$CLASSPATH_ECLIPSE:$CLASSPATH_JARS:$CLASSPATH2

CLASSPATH=$CLASSPATH_JCONSUME_FOLDERS:$CLASSPATH_JARS:$CLASSPATH_ECLIPSE:$CLASSPATH_JARS:$CLASSPATH2


#echo before comment
#: <<'END'
rm -f -R out/$FULLNAMETEST 
mkdir -p out/$FULLNAMETEST

echo Fase 1: Instrumentation 
. ./ii $FULLNAMETEST > instrumenta.log || die "Problema en fase 1" 


#exit 0
echo Fase 2: Compilation of InstrumentedMehod Class for Daikon

MYPATH=`echo $PACKAGE | sed 's/\./\//g'`

echo "Copio el Test class"
cp target/classes/$MYPATH/${NAME}Test.class out/$FULLNAMETEST/$MYPATH/
cd out

echo "Compilo (los) InstrumentedMethod"

#el {} es un placeholder para el resultado del find
#A veces las variables tienen un numeral y es super molesto....
#como hack los cambio por un guion bajo pero me gustaria ver si hay una opcion para que no genere nombres con guion bajo
find $FULLNAMETEST -name InstrumentedMethod.java -exec sed -i -e 's/#/_/g' {} \; -exec javac -g {} \;

sed -i -e 's/#/_/g' $FULLNAMETEST/$FULLNAMETEST.cs
sed -i -e 's/#/_/g' $FULLNAMETEST/$FULLNAMETEST.cc
sed -i -e 's/#/_/g' $FULLNAMETEST/$FULLNAMETEST.indFake


javac -g $FULLNAMETEST/$MYPATH/InstrumentedMethod.java || die "Problema en fase 2"


echo Fase 3: Daikon for generating invariants

./daikon.sh $FULLNAMETEST 5 > $FULLNAMETEST/$FULLNAMETEST.txt || die "Problema en fase 3"
#END


mv $FULLNAMETEST/$FULLNAMETEST.indFake $FULLNAMETEST/$FULLNAMETEST.ind

sed -i -n -e '/===========================================================================/,$p' $FULLNAMETEST/$FULLNAMETEST.txt


echo Fase 4: Imprimir invariantes para formato de jconsume
. ./spec.sh
