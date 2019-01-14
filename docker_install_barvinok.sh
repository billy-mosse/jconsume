
###################################################
#Barvinok

cd /barvinok

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