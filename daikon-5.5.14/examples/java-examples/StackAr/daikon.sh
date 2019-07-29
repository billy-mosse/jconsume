CLASSPATH=/home/billy/Programs/daikon/daikon-5.5.14/daikon.jar:/usr/lib/jvm/java-8-oracle/jre/bin/jre/lib/rt.jar:/usr/lib/jvm/java-8-oracle/jre/bin/lib/tools.jar:.

java -cp .:$CLASSPATH daikon.Chicory "--ppt-select-pattern=MyInteger\.dummyMethod\(" "--ppt-select-pattern=StackArTester\.ff"  DataStructures.StackArTester
