#!/bin/bash

# Descripcion:
# ------------
# 
# Este script permite ejecutar el tool que imprime en el output folder el callgraph a utilizar y su version pre-procesada por el analisis. 

# PARAMETROS:
#
# 1: Nombre de la clase que queremos procesar. Ejemplo: ar.uba.dc.simple.EjemploSimple
# 2: Configuracion a utilizar para el proceso (en archivo *.properties).
# 3: Signatura del metodo a tomar como punto de partida para el armado del callgraph (puede no ser el main). Ejemplo: java.lang.Integer sum(java.lang.Long[])

CURRNET_DIR=$(dirname $0)

$CURRNET_DIR/../run.sh "ar.uba.dc.tools.soot.CallGraphDumper" "$1" "$2" "$3"