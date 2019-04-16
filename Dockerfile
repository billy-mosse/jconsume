# Dockerfile

#docker create -t -i jconsume2-img bash
FROM openjdk:8

ENV JAVA_VERSION 1.8.0

EXPOSE 8080

ARG JCONSUME_PATH=/root/Projects/jconsume/java-project

#ADD java-project/install_java_oracle.sh install_java_oracle.sh
#RUN sh install_java_oracle.sh

#Creo que esto esta de mas
RUN mkdir -p $HOME/Projects/jconsume/java-project

ADD docker_install_all_except_barvinok.sh docker_install_all_except_barvinok.sh
ADD docker_install_barvinok.sh docker_install_barvinok.sh

RUN sh docker_install_all_except_barvinok.sh

RUN sh docker_install_barvinok.sh

#Ojo que aca esta la version de barvinok hardcodeada
#Falta chequear que estos dos comandos anden
#al final lo saque porque si lo muevo hay un quilombo de librerias.
#####
#RUN mv /barvinok/barvinok-0.39 $JCONSUME_PATH/barvinok
#RUN mv $JCONSUME_PATH/barvinok/iscc $JCONSUME_PATH/bin/iscc
#####


ADD java-project/target/jconsume2-0.0.1.jar $JCONSUME_PATH/target/jconsume2-0.0.1.jar
ADD java-project/docker_full_analysis.sh $JCONSUME_PATH/full_analysis.sh
RUN mkdir $JCONSUME_PATH/out
ADD java-project/docker_invariants_IM.sh $JCONSUME_PATH/invariants_IM.sh

ADD java-project/ii $JCONSUME_PATH/ii

ADD java-project/docker_memory.sh $JCONSUME_PATH/out/memory.sh
ADD java-project/barvinok.sh $JCONSUME_PATH/barvinok.sh

ADD java-project/invariants $JCONSUME_PATH/invariants
RUN mkdir $JCONSUME_PATH/out/daikon-output
ADD java-project/results $JCONSUME_PATH/results
ADD java-project/bin $JCONSUME_PATH/bin
#RUN mv $JCONSUME_PATH/bin/docker_barvinok.sh $JCONSUME_PATH/bin/barvinok.sh

#Hack
RUN mkdir -p $JCONSUME_PATH/target/classes
RUN mkdir -p $JCONSUME_PATH/src/main/examples
ADD java-project/src/main/examples $JCONSUME_PATH/src/main/examples

#tengo que copiar archivos de la misma carpeta que el Dockerfile.
RUN mkdir -p /root/Programs/daikon/daikon-5.5.14
ADD daikon-5.5.14 /root/Programs/daikon/daikon-5.5.14

ADD java-project/docker_ii $JCONSUME_PATH/ii
ADD java-project/docker_memory.sh $JCONSUME_PATH/memory.sh

ADD java-project/out/docker_daikon.sh $JCONSUME_PATH/out/daikon.sh
ADD java-project/out/docker_spec.sh $JCONSUME_PATH/out/spec.sh


ADD java-project/out/docker_daikon.sh $JCONSUME_PATH/out/daikon.sh

#Hack totalmente al pedo.
RUN mkdir -p $JCONSUME_PATH/src/main/java

#Hack para que ya estén compilados...
ADD java-project/src/main/examples/ $JCONSUME_PATH/target/classes/
RUN apt-get -y install vim

#Lo que hay que hacer a mano es mover la carpeta de barvinok...

#ENTRYPOINT ["java","-jar","jconsume2-0.0.1.jar"]


RUN mkdir -p $JCONSUME_PATH/target/classes/ar/uba/dc/analysis/automaticinvariants
ADD java-project/target/classes/ar/uba/dc/analysis/automaticinvariants/VarTest.class $JCONSUME_PATH/target/classes/ar/uba/dc/analysis/automaticinvariants/


#no se si esto es realmente necesario.
ADD java-project/site $JCONSUME_PATH/

ADD java-project/bin/docker_barvinok.sh $JCONSUME_PATH/bin/barvinok.sh
