#!/bin/bash
### java soot.Main -f c InstrumentedMethod -d . -src-prec jimple


##java -Xmx300M soot.Main --app --f dava -p jb use-original-names:true  --src-prec J -d . InstrumentedMethod
##javac -g ./dava/src/InstrumentedMethod.java  -d .
javac -g InstrumentedMethod.java  -d .