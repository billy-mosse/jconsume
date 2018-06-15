set -e

###############################################################

cd dependencies

sh install.sh

cd ../java-project

mvn compile
