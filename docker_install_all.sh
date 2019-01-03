#This makes it stop if an error is encountered
set -e

apt-get update -y


apt-get install gcc -y
apt-get install m4 -y --fix-missing

mkdir -p barvinok

cd barvinok

CWD=$(pwd)


#apt-get install software-properties-common

#yes "" | add-apt-repository ppa:webupd8team/java

#apt-get update

#apt-get install oracle-java8-installer
####################################################
# GMP

wget https://gmplib.org/download/gmp/gmp-6.1.0.tar.lz

apt-get install lzip -y

tar --lzip -xvf gmp-6.1.0.tar.lz

cd gmp-6.1.0

./configure --prefix=$HOME/sw

#Tal vez podria instalar solo make...
apt-get install build-essential -y

make

make check

make install


######################################################

#NTL


cd ..

wget http://www.shoup.net/ntl/ntl-6.2.1.tar.gz

tar -xvzf ntl-6.2.1.tar.gz

cd ntl-6.2.1/src

./configure PREFIX=$HOME/sw GMP_PREFIX=$HOME/sw/ NTL_GMP_LIP=on NTL_STD_CXX=on

make

make check

make install


###################################################
#Barvinok


mkdir -p barvinok

cd barvinok

wget -P . http://barvinok.gforge.inria.fr/barvinok-0.39.tar.bz2

tar -xvjf barvinok-0.39.tar.bz2

cd barvinok-0.39

#It whines about automake 1.14 but after reinstalling apparently it works...
#No, in reality it works to install it outside the folder (???)
apt-get remove automake -y

apt-get install automake -y

./configure --prefix=$HOME/sw --with-gmp-prefix=$HOME/sw --with-ntl-prefix=$HOME/sw NTL_GMP_LIP=on

make

make check

make install

echo "Finished installing barvinok..."

cd ..

#cd ../dependencies

#sh install.sh

#cd ../java-project

#mvn install

#mvn clean compile

export CLASSPATH=$CLASSPATH:.