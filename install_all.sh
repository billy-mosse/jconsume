#This makes it stop if an error is encountered
set -e

sudo apt-get update

#Install maven to manage the project
sudo apt-get install maven


#Install java
sudo add-apt-repository ppa:webupd8team/java

sudo apt-get update

sudo apt-get install oracle-java8-installer

#You will have to press enter to accept the installation


mkdir -p barvinok

cd barvinok

CWD=$(pwd)
####################################################
# GMP

wget https://gmplib.org/download/gmp/gmp-6.1.0.tar.lz

sudo apt-get install lzip

tar --lzip -xvf gmp-6.1.0.tar.lz

cd gmp-6.1.0

./configure --prefix=$HOME/sw

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

cd ~/Downloads

mkdir -p barvinok

cd barvinok

wget -P . http://barvinok.gforge.inria.fr/barvinok-0.39.tar.bz2

tar -xvjf barvinok-0.39.tar.bz2

cd barvinok-0.39

#It whines about automake 1.14 but after reinstalling apparently it works...
#No, in reality it works to install it outside the folder (???)
sudo apt-get remove automake

sudo apt-get install automake

./configure --prefix=$HOME/sw --with-gmp-prefix=$HOME/sw --with-ntl-prefix=$HOME/sw NTL_GMP_LIP=on

make

make check

make install

cd ..

mv barvinok-0.39 $CWD 

cd $CWD

sh dependencies/install.sh

cd java-project

mvn install

mvn clean compile

export CLASSPATH=$CLASSPATH:.