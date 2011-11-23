#!/bin/bash

# Descripcion:
# ------------
# 
# Este script permite ejecutar una herramienta que levanta los invariantes del repositorio para una clase particular
# y genera el jimple de la clase anotado con los invariantes que encontro 

# PARAMETROS:
#
# 1: Clase que queremos procesar. Ejemplo: ar.uba.dc.simple.EjemploSimple
# 2: Configuracion a utilizar para el proceso (en archivo *.properties). Entre otras cosas dice que provider de invariantes usar

CURRNET_DIR=$(dirname $0)

$CURRNET_DIR/../run.sh "ar.uba.dc.tools.invariant.InvariantTagger" "$1" "$2"