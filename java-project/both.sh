#!/usr/bin/env bash

die() {

echo "$1" 
 2> error
exit 1

}


JCONSUME_GLOBAL_PATH=/home/billy/Projects/git/jconsume-global/jconsume-global
JCONSUME_PATH=/home/billy/Projects/git/jconsume/java-project


cd $JCONSUME_GLOBAL_PATH

sh doAll.sh $1Test $2

cd $JCONSUME_PATH

sh doAll.sh "--program" $1 "--ir" "--memory"


# $1 es NombrePrograma. La idea es que haya un Test que lo llame 100 veces.
# $2 es los parametros (100, por ejemplo, para que el loop del test llegue hasta 100)
# $3 es NombrePrograma. Ya se,