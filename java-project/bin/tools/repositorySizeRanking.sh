#!/bin/bash

# Descripcion:
# ------------
# 
# Este script genera un ranking de los summaries que se encuentren en el directorio especificado en base a la cantidad de nodos que tienen.
# El script toma un directorio y lo recorre recursivamente buscando summaries. 
# Se recibe ademas un segundo parametro especificando el tipo de summary almacenado en el directorio especificado
# "escape" para indicar que son de escape y "memory" para indicar que son de memoria / consumo.
# El archivo properties se usa, entre otras cosas, para levantar el classpath con el cual deserializar los summaries  

# PARAMETROS:
# 
# 1: Directorio sobre el cual trabajar.
# 2: Tipo de summary a levantar. Puede tomar dos valores "escape" o "memory"
# 3: Configuracion a utilizar para el proceso (en archivo *.properties).
# 4: (Opcional) propiedad que indica el directorio con el cual trabajar. Si se especifica este parametro, se utilizara en lugar del parametro 1
# 5: (Opcional) el directorio se debe recorrer recursivamente? Por defecto toma valor true

CURRNET_DIR=$(dirname $0)

$CURRNET_DIR/../run.sh "ar.uba.dc.tools.analysis.summary.NodesCounterTool" "$1" "$2" "$3" "$4" "$5"