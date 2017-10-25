#!/usr/bin/env bash

set -e


die() {

echo "$1.$2" 
 2> error
exit 1

}

M2_REPO=/home/billy/.m2/repository

#JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.4.2-leopard/Home

M2_REPOSITORY_PATH=/home/billy/.m2/repository


#SOOTCP=utils/sootclasses-2.2.1.jar:utils/polyglot.jar:utils/jasminclasses-sable.jar
#SOOTCP=$M2_REPOSITORY_PATH/ca/mcgill/sable/jasmin/2.4.0/jasmin-2.4.0.jar:$M2_REPOSITORY_PATH/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:$M2_REPOSITORY_PATH/ca/mcgill/sable/soot/2.4.0/soot-2.4.0.jar


#SOOTCP=/home/billy/Projects/git/jconsume-global/jconsume-global/out/:/home/billy/Projects/git/jconsume-global/jconsume-global/src/:/home/billy/Projects/git/jconsume-global/jconsume-global/jdom.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/sootclasses.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/sootsrc-2.4.0.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/jasminclasses-2.4.0.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/polyglotclasses-1.3.5.jar


JAVA4HOME=/usr/lib/jvm/java-8-oracle
JAVA4BIN=$JAVA4HOME/bin
#JAVA4CP=$JAVA4HOME/bundle/Classes/classes.jar

#SAMPLES=/home/billy/Projects/git/jconsume/java-project/src/main/examples
#/home/diegog/workspace-memoria/compositional-samples/bin

#CLASSPATH=$SOOTCP:../pruebas/test:../pruebas/jolden:../pruebas/jGrandeV2/:../pruebas/:../pruebas/flanagan.jar:jdom.jar:bin



#BASURA=$CLASSPATH:$JAVA4CP:/home/billy/Projects/git/jconsume/java-project/src/:/usr/lib/jvm/java-7-oracle/lib/tools.jar::/home/billy/.m2/repository/ca/mcgill/sable/jasmin/2.4.0/jasmin-2.4.0.jar:/home/billy/.m2/repository/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:/home/billy/.m2/repository/ca/mcgill/sable/soot/2.4.0/soot-2.4.0.jar

#BASURA=/usr/lib/jvm/java-7-oracle/lib/tools.jar:

#CLASSPATH1=/home/billy/Projects/git/jconsume/java-project/src/main/examples/:/home/billy/Projects/git/jconsume/java-project/target/classes/:\
#:/home/billy/Downloads/soot-trunk.jar:/home/billy/Projects/git/jconsume/java-project/src/:/home/billy/Projects/git/jconsume/jpfHelper/src:/home/billy/Projects/git/jconsume/dependencies/sootlib/jasminclasses-2.5.0.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/polyglotclasses-1.3.5.jar:$M2_REPO/com/google/guava/guava/19.0/guava-19.0.jar


#CLASSPATH2=/home/billy/Projects/git/jconsume/java-project/target/classes/:/home/billy/.m2/repository/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:/home/billy/.m2/repository/ca/mcgill/sable/jasmin/2.4.0/jasmin-2.4.0.jar:/home/billy/.m2/repository/com/verimag/madeja/1.0/madeja-1.0.jar:/home/billy/.m2/repository/com/thoughtworks/xstream/xstream/1.3.1/xstream-1.3.1.jar:/home/billy/.m2/repository/xpp3/xpp3_min/1.1.4c/xpp3_min-1.1.4c.jar:/home/billy/.m2/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:/home/billy/.m2/repository/junit/junit/4.8.1/junit-4.8.1.jar:/home/billy/.m2/repository/log4j/log4j/1.2.12/log4j-1.2.12.jar:/home/billy/.m2/repository/org/hamcrest/hamcrest-all/1.1/hamcrest-all-1.1.jar:/home/billy/.m2/repository/commons-lang/commons-lang/2.5/commons-lang-2.5.jar:/home/billy/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:/home/billy/.m2/repository/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar:/home/billy/.m2/repository/commons-digester/commons-digester/1.8/commons-digester-1.8.jar:/home/billy/.m2/repository/commons-beanutils/commons-beanutils-core/1.8.0/commons-beanutils-core-1.8.0.jar:/home/billy/.m2/repository/org/jmock/jmock/2.5.1/jmock-2.5.1.jar:/home/billy/.m2/repository/org/hamcrest/hamcrest-core/1.1/hamcrest-core-1.1.jar:/home/billy/.m2/repository/org/hamcrest/hamcrest-library/1.1/hamcrest-library-1.1.jar:/home/billy/.m2/repository/org/jmock/jmock-junit4/2.5.1/jmock-junit4-2.5.1.jar:/home/billy/.m2/repository/junit/junit-dep/4.4/junit-dep-4.4.jar:/home/billy/.m2/repository/org/jmock/jmock-legacy/2.5.1/jmock-legacy-2.5.1.jar:/home/billy/.m2/repository/org/objenesis/objenesis/1.0/objenesis-1.0.jar:/home/billy/.m2/repository/cglib/cglib-nodep/2.1_3/cglib-nodep-2.1_3.jar:/home/billy/.m2/repository/jep/jep/2.4.1/jep-2.4.1.jar:/home/billy/.m2/repository/commons-io/commons-io/1.4/commons-io-1.4.jar:/home/billy/.m2/repository/org/apache/velocity/velocity/1.5/velocity-1.5.jar:/home/billy/.m2/repository/oro/oro/2.0.8/oro-2.0.8.jar:/home/billy/.m2/repository/commons-beanutils/commons-beanutils/1.8.0/commons-beanutils-1.8.0.jar:/home/billy/.m2/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:/home/billy/.m2/repository/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar:/home/billy/.m2/repository/commons-cli/commons-cli/1.2/commons-cli-1.2.jar:/home/billy/.m2/repository/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar:/home/billy/.m2/repository/com/google/guava/guava/19.0/guava-19.0.jar:/home/billy/.m2/repository/org/antlr/antlr4/4.5.3/antlr4-4.5.3.jar:/home/billy/.m2/repository/args4j/args4j-tools/2.33/args4j-tools-2.33.jar:/home/billy/.m2/repository/args4j/args4j/2.33/args4j-2.33.jar:/usr/lib/jvm/java-8-oracle/jre/../lib/tools.jar


#CLASSPATH3=/home/billy/Projects/git/jconsume/java-project/target/classes/:/home/billy/Downloads/soot-trunk.jar:/home/billy/.m2/repository/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:/home/billy/.m2/repository/ca/mcgill/sable/jasmin/2.5.0/jasmin-2.5.0.jar:/home/billy/.m2/repository/com/verimag/madeja/1.0/madeja-1.0.jar:/home/billy/.m2/repository/com/thoughtworks/xstream/xstream/1.3.1/xstream-1.3.1.jar:/home/billy/.m2/repository/xpp3/xpp3_min/1.1.4c/xpp3_min-1.1.4c.jar:/home/billy/.m2/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:/home/billy/.m2/repository/junit/junit/4.8.1/junit-4.8.1.jar:/home/billy/.m2/repository/log4j/log4j/1.2.12/log4j-1.2.12.jar:/home/billy/.m2/repository/org/hamcrest/hamcrest-all/1.1/hamcrest-all-1.1.jar:/home/billy/.m2/repository/commons-lang/commons-lang/2.5/commons-lang-2.5.jar:/home/billy/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:/home/billy/.m2/repository/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar:/home/billy/.m2/repository/commons-digester/commons-digester/1.8/commons-digester-1.8.jar:/home/billy/.m2/repository/commons-beanutils/commons-beanutils-core/1.8.0/commons-beanutils-core-1.8.0.jar:/home/billy/.m2/repository/org/jmock/jmock/2.5.1/jmock-2.5.1.jar:/home/billy/.m2/repository/org/hamcrest/hamcrest-core/1.1/hamcrest-core-1.1.jar:/home/billy/.m2/repository/org/hamcrest/hamcrest-library/1.1/hamcrest-library-1.1.jar:/home/billy/.m2/repository/org/jmock/jmock-junit4/2.5.1/jmock-junit4-2.5.1.jar:/home/billy/.m2/repository/junit/junit-dep/4.4/junit-dep-4.4.jar:/home/billy/.m2/repository/org/jmock/jmock-legacy/2.5.1/jmock-legacy-2.5.1.jar:/home/billy/.m2/repository/org/objenesis/objenesis/1.0/objenesis-1.0.jar:/home/billy/.m2/repository/cglib/cglib-nodep/2.1_3/cglib-nodep-2.1_3.jar:/home/billy/.m2/repository/jep/jep/2.4.1/jep-2.4.1.jar:/home/billy/.m2/repository/commons-io/commons-io/1.4/commons-io-1.4.jar:/home/billy/.m2/repository/org/apache/velocity/velocity/1.5/velocity-1.5.jar:/home/billy/.m2/repository/oro/oro/2.0.8/oro-2.0.8.jar:/home/billy/.m2/repository/commons-beanutils/commons-beanutils/1.8.0/commons-beanutils-1.8.0.jar:/home/billy/.m2/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:/home/billy/.m2/repository/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar:/home/billy/.m2/repository/commons-cli/commons-cli/1.2/commons-cli-1.2.jar:/home/billy/.m2/repository/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar:/home/billy/.m2/repository/com/google/guava/guava/19.0/guava-19.0.jar:/home/billy/.m2/repository/org/antlr/antlr4/4.5.3/antlr4-4.5.3.jar:/home/billy/.m2/repository/args4j/args4j-tools/2.33/args4j-tools-2.33.jar:/home/billy/.m2/repository/args4j/args4j/2.33/args4j-2.33.jar:/usr/lib/jvm/java-8-oracle/jre/../lib/tools.jar



#/home/billy/Projects/git/jconsume-global/jconsume-global/asm-3.1.jar

#CLASSPATH=$CLASSPATH2:$CLASSPATH1:$CLASSPATH3:.

CLASSPATH1=/home/billy/Projects/git/jconsume/java-project/src/main/examples/:/home/billy/Projects/git/jconsume/java-project/target/classes/:/home/billy/Downloads/soot-trunk.jar:/home/billy/Projects/git/jconsume/java-project/src/:/home/billy/Projects/git/jconsume/jpfHelper/src:/home/billy/Projects/git/jconsume-global/jconsume-global/jdom.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/jasminclasses-2.5.0.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/polyglotclasses-1.3.5.jar:$M2_REPO/com/google/guava/guava/19.0/guava-19.0.jar


CLASSPATH2=/home/billy/Downloads/heros-trunk.jar:/home/billy/Downloads/herosclasses-trunk-sources.jar:/home/billy/Downloads/herosclasses-trunk.jar:/home/billy/Projects/git/jconsume/java-project/jdom.jar:/home/billy/.m2/repository/ca/mcgill/sable/soot/3.0.0-SNAPSHOT/soot-3.0.0-SNAPSHOT.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/sootclasses.jar:M2_REPO/ca/mcgill/sable/jasmin/2.5.0-SNAPSHOT/jasmin-2.5.0-20170511.223644-105.jar:M2_REPO/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:M2_REPO/cglib/cglib-nodep/2.1_3/cglib-nodep-2.1_3.jar:M2_REPO/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar:M2_REPO/com/google/guava/guava/19.0/guava-19.0.jar:M2_REPO/com/thoughtworks/xstream/xstream/1.3.1/xstream-1.3.1.jar:M2_REPO/com/verimag/madeja/1.0/madeja-1.0.jar:M2_REPO/commons-beanutils/commons-beanutils-core/1.8.0/commons-beanutils-core-1.8.0.jar:M2_REPO/commons-cli/commons-cli/1.2/commons-cli-1.2.jar:M2_REPO/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:M2_REPO/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar:M2_REPO/commons-digester/commons-digester/1.8/commons-digester-1.8.jar:M2_REPO/commons-io/commons-io/1.4/commons-io-1.4.jar:M2_REPO/commons-lang/commons-lang/2.5/commons-lang-2.5.jar:M2_REPO/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:M2_REPO/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:M2_REPO/jep/jep/2.4.1/jep-2.4.1.jar:M2_REPO/junit/junit-dep/4.4/junit-dep-4.4.jar:M2_REPO/junit/junit/4.8.1/junit-4.8.1.jar:M2_REPO/log4j/log4j/1.2.12/log4j-1.2.12.jar:M2_REPO/org/apache/velocity/velocity/1.5/velocity-1.5.jar:M2_REPO/org/hamcrest/hamcrest-all/1.1/hamcrest-all-1.1.jar:M2_REPO/org/hamcrest/hamcrest-core/1.1/hamcrest-core-1.1.jar:M2_REPO/org/hamcrest/hamcrest-library/1.1/hamcrest-library-1.1.jar:M2_REPO/org/jmock/jmock-junit4/2.5.1/jmock-junit4-2.5.1.jar:M2_REPO/org/jmock/jmock-legacy/2.5.1/jmock-legacy-2.5.1.jar:M2_REPO/org/jmock/jmock/2.5.1/jmock-2.5.1.jar:M2_REPO/org/objenesis/objenesis/1.0/objenesis-1.0.jar:M2_REPO/oro/oro/2.0.8/oro-2.0.8.jar:M2_REPO/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar:M2_REPO/xpp3/xpp3_min/1.1.4c/xpp3_min-1.1.4c.jar:/home/billy/Projects/git/jconsume/java-project/target/classes/:/home/billy/Projects/git/jconsume/java-project/src/main/examples/:/home/billy/Projects/git/jconsume/java-project/src/main/java/



#no tengo la mas puta idea de por que si lo llamo FULLNAME no lo pasa bien
FULLNAMETEST=$1.$2

CLASSPATH=$CLASSPATH1:$CLASSPATH2

rm -f -R out/$FULLNAMETEST 
mkdir -p out/$FULLNAMETEST

echo Fase 1: Instrumentation 
. ./ii $FULLNAMETEST > instrumenta.log || die "Problema en fase 1" 


echo Fase 2: Decompilation of InstrumentedMehod Class for Daikon

MYPATH=`echo $1 | sed 's/\./\//g'`

cp src/main/examples/$MYPATH/$2Test.class out/$FULLNAMETEST/$MYPATH/
cd out/$FULLNAMETEST
javac -g InstrumentedMethod.java
cd ..

#./decorecompile2.sh $1 $2 || die "Problema en fase 2"


echo Fase 3: Daikon for generating invariants


./__daikon2.sh $FULLNAMETEST 10 > $1.$2/$FULLNAMETEST.txt

mv $1.$2/$FULLNAMETEST.indFake $1.$2/$FULLNAMETEST.ind

sed -i -n -e '/===========================================================================/,$p' $1.$2/$FULLNAMETEST.txt


echo Fase 4: Imprimir invariantes para formato de jconsume
. ./spec.sh
