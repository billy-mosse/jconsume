#!/bin/bash
set -e

die() {

echo "$1" 
 2> error
exit 1

}


#JCONSUME_GLOBAL_PATH=/home/billy/Projects/git/jconsume-global/jconsume-global
JCONSUME_PATH=$HOME/Projects/git/jconsume/java-project


#cd $JCONSUME_GLOBAL_PATH

#sh doAll.sh $1 $2 $3




if [ -n "$1" ]; then
	DIRECTORY="results/rinard/escape/json/$1"
	if [ -d "$DIRECTORY" ]; then
    	rm -Rf $DIRECTORY
	fi
fi




sh invariants_IM.sh $1 $2

sh memory.sh "--program" $1 "--ir" "--memory"


cd results/rinard

DATE_WITH_TIME=`date "+%Y%m%d-%H%M%S"`
cp -R report report_$1.$2
#_$DATE_WITH_TIME

# $1 es NombrePrograma. La idea es que haya un Test que lo llame 100 veces.
# $2 es los parametros (100, por ejemplo, para que el loop del test llegue hasta 100)
# $3 es NombrePrograma. Ya se,
