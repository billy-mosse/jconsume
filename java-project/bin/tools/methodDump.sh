#!/bin/bash

# Descripcion:
# ------------
# 
# Este script permite ejecutar el tool que imprime por consola las instrucciones interesantes (call y news) para el analisis de memoria. 

# PARAMETROS:
#
# 1: Nombre de la clase que queremos procesar. Ejemplo: ar.uba.dc.simple.EjemploSimple
# 2: Signatura del metodo a imprimir. Ejemplo: java.lang.Integer sum(java.lang.Long[])
# 3: Configuracion a utilizar para el proceso (en archivo *.properties).

CURRNET_DIR=$(dirname $0)

$CURRNET_DIR/../run.sh "ar.uba.dc.tools.invariant.MethodDummper" $1 "$2" $3