# Dockerfile

#docker create -t -i jconsume2-img bash
FROM debian:jessie

ENV JAVA_VERSION 1.8.0
ENV http_proxy "proxy.fcen.uba.ar"
ENV https_proxy "proxy.fcen.uba.ar"


#yo quiero oracle, no openjdk, y tengo que hacer cosas horribles para eso.
#ver https://stackoverflow.com/questions/48301257/how-to-install-oracle-java8-installer-on-docker-debianjessie?noredirect=1&lq=1
#Lo malo es que no anda desde el install...
#RUN echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" > /etc/apt/sources.list.d/webupd8team-java.list
#RUN echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" >> /etc/apt/sources.list.d/webupd8team-java.list
#RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886
#RUN echo "debconf shared/accepted-oracle-license-v1-1 select true" | /usr/bin/debconf-set-selections
#RUN apt-get update
#RUN apt-get install -y --force-yes vim
#RUN apt-get -y install oracle-java8-installer || true
#RUN cd /var/lib/dpkg/info
#RUN sed -i 's|JAVA_VERSION=8u151|JAVA_VERSION=8u162|' oracle-java8-installer.*
#RUN sed -i 's|PARTNER_URL=http://download.oracle.com/otn-pub/java/jdk/8u151-b12/e758a0de34e24606bca991d704f6dcbf/|PARTNER_URL=http://download.oracle.com/otn-pub/java/jdk/8u162-b12/0da788060d494f5095bf8624735fa2f1/|' oracle-java8-installer.*
#RUN sed -i 's|SHA256SUM_TGZ="c78200ce409367b296ec39be4427f020e2c585470c4eed01021feada576f027f"|SHA256SUM_TGZ="68ec82d47fd9c2b8eb84225b6db398a72008285fafc98631b1ff8d2229680257"|' oracle-java8-installer.*
#RUN sed -i 's|J_DIR=jdk1.8.0_151|J_DIR=jdk1.8.0_162|' oracle-java8-installer.*
#RUN apt-get install -f -y
#RUN apt-get install -y oracle-java8-set-default


EXPOSE 8080

ARG JCONSUME_PATH=/root/Projects/jconsume/java-project


ADD java-project/install_java_oracle.sh install_java_oracle.sh
RUN sh install_java_oracle.sh

#Creo que esto esta de mas
RUN mkdir -p $HOME/Projects/jconsume/java-project

ADD docker_install_all.sh docker_install_all.sh
ADD docker_install_all.sh docker_install_all.sh

RUN sh docker_install_all.sh

#Ojo que aca esta la version de barvinok hardcodeada
#Falta chequear que estos dos comandos anden
#####

RUN cp /barvinok/barvinok-0.39 $JCONSUME_PATH/barvinok

RUN cp $JCONSUME_PATH/barvinok/iscc $JCONSUME_PATH/bin/iscc
#####


ADD java-project/target/jconsume2-0.0.1.jar $JCONSUME_PATH/jconsume2-0.0.1.jar
ADD java-project/docker_full_analysis.sh $JCONSUME_PATH/full_analysis.sh
RUN mkdir $JCONSUME_PATH/out
ADD java-project/docker_invariants_IM.sh $JCONSUME_PATH/invariants_IM.sh

ADD java-project/ii $JCONSUME_PATH/ii

ADD java-project/docker_memory.sh $JCONSUME_PATH/out/memory.sh
ADD java-project/barvinok.sh $JCONSUME_PATH/barvinok.sh
ADD java-project/invariants $JCONSUME_PATH/invariants
ADD java-project/out/spec.sh $JCONSUME_PATH/out/spec.sh
RUN mkdir $JCONSUME_PATH/out/daikon-output
ADD java-project/results $JCONSUME_PATH/results
ADD java-project/bin $JCONSUME_PATH/bin

#Hack
RUN mkdir -p $JCONSUME_PATH/target/classes
RUN mkdir -p $JCONSUME_PATH/src/main/examples
ADD java-project/src/main/examples $JCONSUME_PATH/src/main/examples



#ENTRYPOINT ["java","-jar","jconsume2-0.0.1.jar"]
