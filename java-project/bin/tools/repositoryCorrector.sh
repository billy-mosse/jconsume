#!/bin/bash

# Descripcion:
# ------------
# 
# Este script permite solucionar los problemas que surgen al correr un analisis escape habiendo comentado para jlayer 
# los arreglos estaticos que hay en la clase huffcodetab.
# El script analiza el programa a partir de la clase especificada y para c/metodo corrige los summaries tomando en cuenta
# los cambios de linea producidos por huffman. 
# El archivo properties se usa, entre otras cosas, para levantar el classpath con el cual deserializar los summaries  

# PARAMETROS:
# 
# 1: Configuracion a utilizar para el proceso (en archivo *.properties).
# 2: Filtro. Si el toString del sootmethod comienza con el filtro, entonces es procesado

CURRNET_DIR=$(dirname $0)

$CURRNET_DIR/../run.sh "ar.uba.dc.tools.analysis.summary.RepositoryCorrector" "javazoom.jl.player.jlp" "$1" "$2"