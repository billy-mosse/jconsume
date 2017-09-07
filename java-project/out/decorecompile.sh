
#me encantaria saber por que tengo que decompilar con java 7 y soot 2.5

#export CLASSPATH=/home/billy/Downloads/asm-3.3.1.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/soot-2.5.0.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/sootclasses-2.5.0.jar:/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar:.


export CLASSPATH=/home/billy/Projects/git/jconsume-global/jconsume-global/asm-3.1.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/soot-2.5.0.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/sootclasses-2.5.0.jar:/usr/lib/jvm/java-7-oracle/jre/lib/rt.jar:.


#export CLASSPATH=/home/billy/Downloads/asm-6.0_BETA/lib/asm-6.0_BETA.jar:/home/billy/Downloads/soot-trunk.jar:/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar:.



#/home/billy/Projects/git/jconsume-global/jconsume-global/asm-3.1.jar

#TODO guardar todos los .class en una carpeta especial. Listarlos y decompilar a todos. Necesitare hacerlos en orden?

rm sootOutput/dava/src/ar/uba/dc/daikon/*


cp InstrumentedMethod.java sootOutput/dava/src/ar/uba/dc/daikon/
mv InstrumentedMethod.java ar/uba/dc/daikon/

touch InstrumentedMethod.java

java soot.Main -f dava $1.$2


#/home/billy/Projects/git/jconsume/java-project/src/main/examples
#java soot.Main -cp .:/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar:/usr/lib/jvm/java-8-oracle/jre/lib/jce.jar -f jimp $1.$2 -W

#java -cp /home/billy/Downloads/soot-trunk.jar soot.Main -cp .:/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar:/home/billy/Downloads/asm-6.0_BETA/lib/asm-6.0_BETA.jar -f dava $1.$2

java soot.Main -f dava $1.$2Test


#Hack feo
#java soot.Main -f dava ar.uba.dc.daikon.RichNumber


java soot.Main -f dava ar.uba.dc.daikon.A


#cp ar/uba/dc/daikon/InstrumentedMethod.java .

#Tratar de ir a una carpeta y despues compilar todo es una pesima idea, ahora que lo pienso
#cd sootOutput/dava/src/ar/uba/dc/daikon/

#javac -g RichNumberPublic.java InstrumentedMethod.java $2.java $2Test.java A.java

#javac -g InstrumentedMethod.java $2.java $2Test.java

javac -g sootOutput/dava/src/ar/uba/dc/daikon/*.java

#cd sootOutput/dava/src

#export CLASSPATH=CLASSPATH:/home/billy/Projects/git/jconsume-global/jconsume-global/out

#javac -g ar.uba.dc.daikon.Ins19 InstrumentedMethod ar.uba.dc.daikon.RichNumberPublic
