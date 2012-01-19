#/bin/bash

g++  -o libHeapJVMTI.so -shared  -I$JAVA_HOME/include/ -I$JAVA_HOME/include/linux/  -fPIC  HeapJVMTI.c

