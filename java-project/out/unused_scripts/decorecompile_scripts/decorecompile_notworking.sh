export CLASSPATH=/home/billy/Downloads/asm-3.3.1.jar:/home/billy/Downloads/soot-trunk.jar:/home/billy/Projects/git/jconsume/java-project/src/:/home/billy/Projects/git/jconsume/jpfHelper/src:/home/billy/Projects/git/jconsume-global/jconsume-global/jdom.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/jasminclasses-2.5.0.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/polyglotclasses-1.3.5.jar:/home/billy/Projects/git/jconsume-global/jconsume-global/bin/:$M2_REPO/com/google/guava/guava/19.0/guava-19.0.jar:/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar:.


#/home/billy/Projects/git/jconsume-global/jconsume-global/asm-3.1.jar

#TODO guardar todos los .class en una carpeta especial. Listarlos y decompilar a todos. Necesitare hacerlos en orden?

rm sootOutput/dava/src/ar/uba/dc/daikon/*

java soot.Main -f dava $1.$2 #-w
java soot.Main -f dava $1.$2Test


#Hack feo
#java soot.Main -f dava ar.uba.dc.daikon.RichNumber


#java soot.Main -f dava ar.uba.dc.daikon.A

#mv InstrumentedMethod.java sootOutput/dava/src/ar/uba/dc/daikon/

#cd sootOutput/dava/src/ar/uba/dc/daikon/

#javac -g RichNumberPublic.java InstrumentedMethod.java $2.java $2Test.java A.java

#javac -g InstrumentedMethod.java $2.java $2Test.java

#javac -g *.java

#cd sootOutput/dava/src

#export CLASSPATH=CLASSPATH:/home/billy/Projects/git/jconsume-global/jconsume-global/out

#javac -g ar.uba.dc.daikon.Ins19 InstrumentedMethod ar.uba.dc.daikon.RichNumberPublic
