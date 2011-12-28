#!/bin/bash

# Descripcion:
# ------------
# 
# Este script permite instrumentar el codigo para luego ser ejecutado y obtener metricas sobre el consumo asumiendo un esquema por scopes. 

# PARAMETROS:
#
# 1: Nombre de la clase que queremos procesar. Ejemplo: ar.uba.dc.simple.EjemploSimple
# 2: Configuracion a utilizar para el proceso (en archivo *.properties).
# 3: Indica si correr previa a la instrumentacion el escape analisis o no (con true se corre)

CURRNET_DIR=$(dirname $0)

$CURRNET_DIR/../run.sh "ar.uba.dc.tools.instrumentation.resource.tracker.madeja.InstrumentationJPFTool" "$1" "$2" "$3"
