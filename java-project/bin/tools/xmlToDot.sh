#!/bin/bash

# Descripcion:
# ------------
# 
# Este script permite convertir los summaries que se encuentren en el directorio especificado de XML a Dot.
# El script toma un directorio y lo recorre recursivamente buscando summaries. Para c/summary encontrado genera
# el correspondiente grafo si puede.
# Se recibe ademas un segundo parametro especificando el tipo de summary almacenado en el directorio especificado
# "escape" para indicar que son de escape y "memory" para indicar que son de memoria / consumo.
# El archivo properties se usa, entre otras cosas, para levantar el classpath con el cual deserializar los summaries  

# PARAMETROS:
# 
# 1: Directorio sobre el cual trabajar.
# 2: Tipo de summary a levantar. Puede tomar dos valores "escape" o "memory"
# 3: Configuracion a utilizar para el proceso (en archivo *.properties).

CURRNET_DIR=$(dirname $0)

$CURRNET_DIR/../run.sh "ar.uba.dc.tools.analysis.summary.SummaryToGraphvizConverter" "$1" "$2" "$3"