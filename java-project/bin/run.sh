#!/bin/bash

PROJECT_PATH=$(dirname $0)/..
CLASS_PATH=$PROJECT_PATH/target/classes
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/ca/mcgill/sable/jasmin/2.4.0/jasmin-2.4.0.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/ca/mcgill/sable/soot/2.4.0/soot-2.4.0.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/jep/jep/2.4.1/jep-2.4.1.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/com/verimag/madeja/1.0/madeja-1.0.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/com/thoughtworks/xstream/xstream/1.3.1/xstream-1.3.1.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/xpp3/xpp3_min/1.1.4c/xpp3_min-1.1.4c.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/commons-lang/commons-lang/2.5/commons-lang-2.5.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/commons-beanutils/commons-beanutils/1.8.0/commons-beanutils-1.8.0.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/commons-io/commons-io/1.4/commons-io-1.4.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/log4j/log4j/1.2.12/log4j-1.2.12.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar
CLASS_PATH=$CLASS_PATH:$M2_REPOSITORY_PATH/org/apache/velocity/velocity/1.5/velocity-1.5.jar

echo  $CLASS_PATH
java -Xmx2048m -classpath $CLASS_PATH $1 "$2" "$3" "$4" "$5" "$6"