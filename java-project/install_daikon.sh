#https://plse.cs.washington.edu/daikon/download/doc/daikon.html#Installing-Daikon

DAIKONPARENT=/home/billy/Downloads/daikontest

wget http://plse.cs.washington.edu/daikon/history/daikon-5.5.2/daikon-5.5.2.tar.gz
tar zxf daikon-5.5.2.tar.gz

# The absolute pathname of the directory that contains Daikon
export DAIKONDIR=$DAIKONPARENT/daikon-5.6.4
# The absolute pathname of the directory that contains the Java JDK
export JAVA_HOME=/usr/lib/jvm/java
source $DAIKONDIR/scripts/daikon.bashrc
export JAVA_HOME=${JAVA_HOME:-$(dirname $(dirname $(dirname $(readlink -f $(/usr/bin/which java)))))}


