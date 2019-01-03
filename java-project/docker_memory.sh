#!/usr/bin/env bash

set -e

die() {

echo "$1" 
 2> error
exit 1

}

M2_REPO=$HOME/.m2/repository
JAVA4HOME=/usr/lib/jvm/java-8-oracle
JAVA4BIN=$JAVA4HOME/bin

JCONSUME_PATH=$HOME/Projects/jconsume

#CLASSPATH=$M2_REPO/ca/mcgill/sable/soot/2.4.0/soot-2.4.0.jar:$M2_REPO/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:$M2_REPO/ca/mcgill/sable/jasmin/2.4.0/jasmin-2.4.0.jar:$M2_REPO/com/verimag/madeja/1.0/madeja-1.0.jar:$M2_REPO/com/thoughtworks/xstream/xstream/1.3.1/xstream-1.3.1.jar:$M2_REPO/xpp3/xpp3_min/1.1.4c/xpp3_min-1.1.4c.jar:$M2_REPO/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:$M2_REPO/junit/junit/4.8.1/junit-4.8.1.jar:$M2_REPO/log4j/log4j/1.2.12/log4j-1.2.12.jar:$M2_REPO/org/hamcrest/hamcrest-all/1.1/hamcrest-all-1.1.jar:$M2_REPO/commons-lang/commons-lang/2.5/commons-lang-2.5.jar:$M2_REPO/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:$M2_REPO/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar:$M2_REPO/commons-digester/commons-digester/1.8/commons-digester-1.8.jar:$M2_REPO/commons-beanutils/commons-beanutils/1.8.0/commons-beanutils-1.8.0.jar:$M2_REPO/commons-beanutils/commons-beanutils-core/1.8.0/commons-beanutils-core-1.8.0.jar:$M2_REPO/org/jmock/jmock/2.5.1/jmock-2.5.1.jar:$M2_REPO/org/hamcrest/hamcrest-core/1.1/hamcrest-core-1.1.jar:$M2_REPO/org/hamcrest/hamcrest-library/1.1/hamcrest-library-1.1.jar:$M2_REPO/org/jmock/jmock-junit4/2.5.1/jmock-junit4-2.5.1.jar:$M2_REPO/junit/junit-dep/4.4/junit-dep-4.4.jar:$M2_REPO/org/jmock/jmock-legacy/2.5.1/jmock-legacy-2.5.1.jar:$M2_REPO/org/objenesis/objenesis/1.0/objenesis-1.0.jar:$M2_REPO/cglib/cglib-nodep/2.1_3/cglib-nodep-2.1_3.jar:$M2_REPO/jep/jep/2.4.1/jep-2.4.1.jar:$M2_REPO/commons-io/commons-io/1.4/commons-io-1.4.jar:$M2_REPO/org/apache/velocity/velocity/1.5/velocity-1.5.jar:$M2_REPO/oro/oro/2.0.8/oro-2.0.8.jar:$M2_REPO/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:$M2_REPO/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar:$M2_REPO/commons-cli/commons-cli/1.2/commons-cli-1.2.jar:$M2_REPO/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar:$M2_REPO/com/google/guava/guava/19.0/guava-19.0.jar:$JCONSUME_PATH/java-project/target/classes

#CLASSPATH=$JCONSUME_PATH/java-project/target/classes

CLASSPATH=$JCONSUME_PATH/java-project/target/classes/:$HOME/Downloads/soot-trunk.jar:$HOME/.m2/repository/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:$HOME/.m2/repository/ca/mcgill/sable/jasmin/2.5.0/jasmin-2.5.0.jar:$HOME/.m2/repository/com/verimag/madeja/1.0/madeja-1.0.jar:$HOME/.m2/repository/com/thoughtworks/xstream/xstream/1.3.1/xstream-1.3.1.jar:$HOME/.m2/repository/xpp3/xpp3_min/1.1.4c/xpp3_min-1.1.4c.jar:$HOME/.m2/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:$HOME/.m2/repository/junit/junit/4.8.1/junit-4.8.1.jar:$HOME/.m2/repository/log4j/log4j/1.2.12/log4j-1.2.12.jar:$HOME/.m2/repository/org/hamcrest/hamcrest-all/1.1/hamcrest-all-1.1.jar:$HOME/.m2/repository/commons-lang/commons-lang/2.5/commons-lang-2.5.jar:$HOME/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:$HOME/.m2/repository/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar:$HOME/.m2/repository/commons-digester/commons-digester/1.8/commons-digester-1.8.jar:$HOME/.m2/repository/commons-beanutils/commons-beanutils-core/1.8.0/commons-beanutils-core-1.8.0.jar:$HOME/.m2/repository/org/jmock/jmock/2.5.1/jmock-2.5.1.jar:$HOME/.m2/repository/org/hamcrest/hamcrest-core/1.1/hamcrest-core-1.1.jar:$HOME/.m2/repository/org/hamcrest/hamcrest-library/1.1/hamcrest-library-1.1.jar:$HOME/.m2/repository/org/jmock/jmock-junit4/2.5.1/jmock-junit4-2.5.1.jar:$HOME/.m2/repository/junit/junit-dep/4.4/junit-dep-4.4.jar:$HOME/.m2/repository/org/jmock/jmock-legacy/2.5.1/jmock-legacy-2.5.1.jar:$HOME/.m2/repository/org/objenesis/objenesis/1.0/objenesis-1.0.jar:$HOME/.m2/repository/cglib/cglib-nodep/2.1_3/cglib-nodep-2.1_3.jar:$HOME/.m2/repository/jep/jep/2.4.1/jep-2.4.1.jar:$HOME/.m2/repository/commons-io/commons-io/1.4/commons-io-1.4.jar:$HOME/.m2/repository/org/apache/velocity/velocity/1.5/velocity-1.5.jar:$HOME/.m2/repository/oro/oro/2.0.8/oro-2.0.8.jar:$HOME/.m2/repository/commons-beanutils/commons-beanutils/1.8.0/commons-beanutils-1.8.0.jar:$HOME/.m2/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:$HOME/.m2/repository/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar:$HOME/.m2/repository/commons-cli/commons-cli/1.2/commons-cli-1.2.jar:$HOME/.m2/repository/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar:$HOME/.m2/repository/com/google/guava/guava/19.0/guava-19.0.jar:$HOME/.m2/repository/org/antlr/antlr4/4.5.3/antlr4-4.5.3.jar:$HOME/.m2/repository/args4j/args4j-tools/2.33/args4j-tools-2.33.jar:$HOME/.m2/repository/args4j/args4j/2.33/args4j-2.33.jar:/usr/lib/jvm/java-8-oracle/jre/../lib/tools.jar



$JAVA4BIN/java -cp $CLASSPATH -Xmx400m "ar.uba.dc.analysis.common.MainRunner" "--config" "config.properties" "--main" "\"void main(java.lang.String[])\"" $*


cd results/rinard

DATE_WITH_TIME=`date "+%Y%m%d-%H%M%S"`
cp -R report historical_reports/report_$2
