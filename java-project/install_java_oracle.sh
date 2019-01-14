echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" > /etc/apt/sources.list.d/webupd8team-java.list
echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" >> /etc/apt/sources.list.d/webupd8team-java.list
apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886
echo "debconf shared/accepted-oracle-license-v1-1 select true" | /usr/bin/debconf-set-selections
apt-get update
apt-get install -y --force-yes vim
apt-get -y install oracle-java8-installer || true
cd /var/lib/dpkg/info

#instalar manualmente, ya fue.
#sed -i 's|JAVA_VERSION=8u151|JAVA_VERSION=8u191|' oracle-java8-installer.*
#sed -i 's|PARTNER_URL=http://download.oracle.com/otn-pub/java/jdk/8u151-b12/e758a0de34e24606bca991d704f6dcbf/|PARTNER_URL=http://download.oracle.com/otn-pub/java/jdk/8u191-b12/0da788060d494f5095bf8624735fa2f1/|' oracle-java8-installer.*
#sed -i 's|SHA256SUM_TGZ="c78200ce409367b296ec39be4427f020e2c585470c4eed01021feada576f027f"|SHA256SUM_TGZ="68ec82d47fd9c2b8eb84225b6db398a72008285fafc98631b1ff8d2229680257"|' oracle-java8-installer.*
#sed -i 's|J_DIR=jdk1.8.0_151|J_DIR=jdk1.8.0_162|' oracle-java8-installer.*

#apt-get install -f -y
#apt-get install -y oracle-java8-set-default