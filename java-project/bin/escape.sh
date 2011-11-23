#!/bin/bash

# Descripcion:
# ------------
# 
# Este script permite ejecutar el analisis de memoria para el metodo de una clase. 

# PARAMETROS:
#
# 1: Nombre de la clase que queremos procesar. Ejemplo: ar.uba.dc.simple.EjemploSimple
# 2: Configuracion a utilizar para el proceso (en archivo *.properties).
# 3: Signatura del metodo a tomar como punto de partida para el analisis (puede no ser el main). Ejemplo: java.lang.Integer sum(java.lang.Long[])

CURRNET_DIR=$(dirname $0)

$CURRNET_DIR/run.sh "ar.uba.dc.analysis.escape.runner.StandAloneRunner" "$1" "$2" "$3"