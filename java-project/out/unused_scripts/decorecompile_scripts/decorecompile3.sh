M2_REPO=/home/billy/.m2/repository


export CLASSPATH=/home/billy/Downloads/asm-3.3.1.jar:/home/billy/.m2/repository/ca/mcgill/sable/soot/3.0.0-SNAPSHOT/soot-3.0.0-SNAPSHOT.jar:/home/billy/.m2/repository/ca/mcgill/sable/soot/3.0.0-SNAPSHOT/soot-3.0.0-SNAPSHOT-sources.jar:/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar:/usr/lib/jvm/java-8-oracle/jre/lib/jce.jar:/home/billy/.m2/repository/com/google/guava/guava/19.0/guava-19.0.jar:/home/billy/.m2/repository/ca/mcgill/sable/jasmin/2.5.0-SNAPSHOT/jasmin-2.5.0-SNAPSHOT.jar:/home/billy/.m2/repository/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:.

java soot.Main -f dava ar.uba.dc.daikon.Ins21 -keep-line-number -p jb use-original-names:true -w

#java soot.Main -f dava ar.uba.dc.daikon.Ins21Test -keep-line-number -p jb use-original-names:true

#java soot.Main -f dava ar.uba.dc.daikon.RichNumberPublic -keep-line-number -p jb use-original-names:true


#cd sootOutput/dava/src

#export CLASSPATH=CLASSPATH:/home/billy/Projects/git/jconsume-global/jconsume-global/out

#javac -g ar.uba.dc.daikon.Ins19 InstrumentedMethod ar.uba.dc.daikon.RichNumberPublic
