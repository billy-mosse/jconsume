#!/usr/bash


JAVA4HOME=/usr/lib/jvm/java-8-openjdk-amd64
JAVA4BIN=$JAVA4HOME/bin


#CLASSPATH=/home/billy/Projects/git/jconsume-global/jconsume-global/src/:/home/billy/Projects/git/jconsume/java-project/src/main/examples/:/home/billy/Projects/git/jconsume/java-project/target/classes/:/home/billy/Projects/git/jconsume-global/jconsume-global/:\
#:/home/billy/Projects/git/jconsume/java-project/src/:/home/billy/Projects/git/jconsume/jpfHelper/src:\
#/home/billy/Projects/git/jconsume/dependencies/sootlib/sootclasses.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/jdom.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/jasminclasses-2.4.0.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/polyglotclasses-1.3.5.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/bin/:


#Hack completamente asqueroso
$JAVA4BIN/java -cp ../target/jconsume2-0.0.1.jar -Xmx400m ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.invariantwriter.SpecInvariantWriter $FULLNAMETEST/$FULLNAMETEST $PROJECT_FOLDER/invariants/spec/fullreferences
