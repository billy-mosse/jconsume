#!/bin/bash

# Descripcion:
# ------------
# 
# Este script permite verificar si el codigo de los summaries existentes en el repositorio han cambiado.
# El script toma una clase y analiza los summaries de aquellos metodos necesarios para efectuar un analisis de escape 
# del programa partiendo de la clase dada como parametro. Para c/metodo se intenta deserializar su summary.
# El tercer parametro es un filtro para saber que metodo no verificar  
# El archivo properties se usa, entre otras cosas, para levantar el classpath con el cual deserializar los summaries  

# PARAMETROS:
# 
# 1: Nombre de la clase que queremos procesar. Ejemplo: ar.uba.dc.simple.EjemploSimple
# 2: Configuracion a utilizar para el proceso (en archivo *.properties).
# 3: Filtro para los metodos a verificar. Si el toString del metodo de soot asociado comienza con el filtro, entonces sera procesado

CURRNET_DIR=$(dirname $0)

$CURRNET_DIR/../run.sh "ar.uba.dc.tools.analysis.summary.RepositoryUpdateChecker" "$1" "$2" "$3"