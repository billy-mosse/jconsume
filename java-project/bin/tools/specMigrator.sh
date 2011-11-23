#!/bin/bash

# Descripcion:
# ------------
# 
# Este script permite migrar los invarantes de formato spec al formato soportado por la herramienta "Madeja".
# El script toma todas las especificaciones que se encuentren dentro del directorio indicado por "invariants.repository" 
# y sus subdirectorios, los migra al formato que entiende "Madeja" y los deposita en la carpeta "output.folder".
# El script permite indicar el subdirectorio del "invariants.repository" a procesar (por si no se desea trabajar con todo el repositorio" 

# PARAMETROS:
#
# 1: Configuracion a utilizar para el proceso (en archivo *.properties).
# 2: Subdirectorio del repositorio al cual deben pertenecer las especificaciones
# 3: Flag que indica si se deben incluir las especificaciones vacias (metodos sin requerimientos y/o sites o bien las clases sin metodos).
#    Si esta en true seran incluidas. Si esta en false no seran incluidas

CURRNET_DIR=$(dirname $0)

$CURRNET_DIR/../run.sh "ar.uba.dc.tools.invariant.migration.spec.SpecToMadejaMigrator" "$1" "$2" "$3"