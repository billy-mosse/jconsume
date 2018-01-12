Autores: Diego Garbervetsky y Guillermo Mosse

Participaron además del proyecto: Matias Grunberg, Gaston Krasny, Martin Rouaux, Edgardo Zoppi.


Introduccion
============

Este documento contiene la informacion necesaria para instalar la herramienta, ejecutarla y comprender como esta estructurado el proyecto.
El documento se encuentra dividio en distintas secciones:
	1) Dependencias
		1.1) Maven
		1.2) Barvinok
		1.3) Graphviz
	2) Estructura del proyecto
	3) Herramientas desarrolladas
	4) Configuracion de la herramienta
	5) Ejecucion
		5.1) Introduccion
		5.2) Jlayer
		
Dependencias
============

Maven
-----

La herramienta fue desarrollada utilizando como gestor de proyectos. 
La documentación de esta herramienta puede encontrarse en http://maven.apache.org/.
En linux alcanza con utilizar alguna herramienta para gestion packetes para instalar la versión 2 de maven (apt-get install maven2)
Una vez instalado maven, para facilitar el desarrollo dentro de eclipse, es necesario instalar el plugin para maven. El mismo se encuentra en http://m2eclipse.sonatype.org/

Es importante instalar maven pues el mismo gestiona las dependencias del proyecto y los scripts generados dentro de la herramienta asumen 
la existencia del repositorio local. 

Por esta razon una vez instalada la aplicacion se recomienda:

_ Ejecutar el comando "sh install.sh" en la carpeta del archivo para que maven instale las librerías de soot.
_ Ejecutar el comando "mvn compile" para compilar el proyecto y forzar a que todas las dependencias
sean bajadas al repositorio local de maven. El comando debe ejecutarse en el directorio root del proyecto (donde se encuentre el archivo pom.xml).  

Nota: es importante importar el proyecto desde eclipse como mvn project.

Barvinok
--------

#TODO: la versión de barvinok que estamos usando es más nueva.
La herramienta utiliza como soporte para operar con expresiones paramétricas la versión 0.32.1 de la libreria barvinok. La misma puede bajarse
desde http://freshmeat.net/projects/barvinok/.

Una vez bajada la libreria es necesario compilarla. Para esto es necesario seguir los siguientes pasos

- instalar libginac1.5 y libginac-dev (apt-get)
- instalar libcln
- instalar libntl-5.4.2 y libntl-dev
- instalar libgmp
- instalar automake
- hacer un restart de la maquina
- cd <dir donde este la libreria>
- Ejecutar ./configure
- Ejecutar ./make
- Ejecutar ./make check
- Ejecutar ./make install 

Graphviz
--------

Para generar los resumenes en forma de grafo la herramienta se basa en el comando dot incluido en graphviz. Por esta razon esta herramienta debe 
estar instalada. Para mas informacion sobre la misma puede consultarse http://www.graphviz.org/



Eclipse
=======================
Por ahora estamos pisando el file ReachableMethods de soot con uno nuestro, que se encuentra en target/classes/soot/jimple/toolkits/callgraph.

Para que soot use nuestro archivo y no el suyo, hay que agregar target/classes al classpath. Esto ya está hecho en bin/run.sh, pero para que funcione en eclipse hay que agregar la carpeta, no en Environment como parte del classpath (como variable de entorno), sino en Classpath, como un User Entry.


Estructura del proyecto
=======================

El proyecto toma como base la estructura sugerida por maven para el desarrollo. Las carpetas estan organizadas de la siguiente manera

- src/main/java: contiene los fuentes de la herramienta. En particular esta carpeta esta organizada en distintos paquetes
	- ar.uba.dc.analysis:
		Codigo para implementar los analisis de escape y de consumo
	- ar.uba.dc.barvinok:
		Codigo para comunicarse con la calculadora iscc de la libreria barvinok via linea de comandos, parsear
		y procesar los resultados. El SA esta implementado adaptando clases de este paquete
	- ar.uba.dc.config:
		Codigo para generar la configuracion de la aplicacion en base a un archivo configuration.properties. 
		La herramienta se apoya en la libreria commons-configuration de apache (http://commons.apache.org/configuration/)
	- ar.uba.dc.invariant:
		Clases para implementar el proveedor de invariantes. Estas clases permiten implementar 4 formatos de invariantes:
			- simple: es un diccionario <statement, invariant>
			- fast: permite definir un archivo .spec donde no se permiten referencias a otros invariantes
			- simple-reference: permite definir un archivo .spec donde se permiten referencias pero no se resuelve la clausura transitiva de las mismas
			- full-reference: permite definir un archivo .spec donde se resuelven las clausuras transitivas
		A diferencia del formato simple, para generar los invariantes de los otros tres formatos, se compila el archivo .spec de input. El formato del archivo es el mismo para los 3 tipos de invariantes compilables
		Para mas informacion referirse al codigo fuente y propiedad "invariants.provider" del archivo application.properties. 
	- ar.uba.dc.soot:
		Contiene clases desarrolladas que involucran el uso del Framework de analisis de codigo soot (http://www.sable.mcgill.ca/soot/)
	- ar.uba.dc.tools:
		Contiene las herramientas auxiliares que fueron desarrolladas. 
		Dentro de las herramientas no se incluyen los analisis de escape ni de consumo.
		Para mas informacion ver la seccion "Herramientas desarrolladas"		 
	- ar.uba.dc.util:
		Clases utilitarias que resuelven funcionalidad comun a todos los modulos
	- soot.jimple.toolkits.callgraph: 
		Para poder cortar el callgraph y no incluir en el armado del mismo las clases del core de java se 
		debio agregar este paquete. Esto permitio construir el callgraph de forma mas veloz
- src/test: Test desarrollados para probar la herramienta. No se recomienda su ejecucion por linea de comandos puesto que algunos fallan. Solo funcionan completamente corriendolos desde eclipse
- src/main/examples: 
	Codigo utilizado de ejemplo para correr la herramienta. En el mismo esta el codigo de 
	los ejemplos motivacionales, em3d, mst y jlayer.
- bin/: Scripts para correr los analisis y las herramientas auxiliares desarrolladas. Cada script tiene un comentario que explica para que sirve la herramienta
- invariants/: 
		Repositorio de invariantes. Las corridas realizadas usan el formato full-reference. Se deja un ejemplo de los otros formatos a modo ilustrativo.
		El modo full-reference es el más completo pero tambien es el que requiere más tiempo de compilacion. Los otros formatos existen accidentalmente, 
		fueron una evolucion hasta llegar a tener el full-reference pero se dejaron porque se compilan mas rapido (aunque es mas dificil generalos manualmente).
- results/: Resultados de las distintas corridas
- <root>: El root contiene archivos que invocan a los scripts en la carpeta bin pero precargando algunos parametros. Esto facilita la ejecucion de los scripts.		
 

Herramientas desarrolladas
==========================

Para ver una referencia de las herramientas desarrolladas es necesario leer el archivo "tools". En el se describen con ejemplos
el uso de las mismas

Configuracion de la herramienta
===============================

Archivos de configuracion
-------------------------

La herramienta necesita poca configuración para poder ser ejecutada. Fue desarrollada de forma tal que sea
lo mas flexible posible.

La ejecucion de los analisis se toma de 5 archivos:
	- application.properties: Configuracion general de la aplicacion
	- config-soot.properties: Configuracion para Soot
	- config-escape.properties: Configuracion para la fase de analisis de escape
	- config-memory.properties: Configuracion para la fase de analisis de consumo
	- config-instrumentation.properties: Configuracion para la fase de instrumentacion de codigo

Estos 4 archivos son un template. Si se desea se los puede modificar. Otra alternativa es pasar a los scripts
de la carpeta bin un archivo config.properties (como hacen los scripts del root). Toda propiedad que se defina
en ese archivo sobre-escribe las existentes en los 4 archivos. Hay que tener cuidado con las propiedades de tipo lista (misma propiedad definida multiples veces),
si se sobre-escribe esta propiedad, la lista es sobreescrita completamente, no se hace un merge de las propiedades existentes y las nuevas.

En los 4 archivos estan explicadas las propiedades que pueden asignarse y que afectan a los analisis de escape
y de consumo.

Las herramientas auxiliares pueden verse influenciadas por esta configuracion. En cada script se hace mencion de
si la herramienta usa un archivo de configuracion y se explica que propiedades utiliza.

Factory
-------

Para entender lo que viene a continuacion es recomendable leer la seccion de creacion de objetos de la libreria (http://commons.apache.org/configuration/userguide/howto_beans.html#Declaring_and_Creating_Beans)

La otra parte importante de la flexibilidad de la herramienta radica en el Contenedor de objetos utilizado para inyectar objetos.
El contenedor utilizado es el provisto por la libreria commons-configuration de apache (http://commons.apache.org/configuration/).
Por defecto el contendor se configura mediante el archivo factory-config.xml pero puede ser reemplazado por otro archivo mediante la
propiedad ${configuration.factory.file}.

Para poder configurar el contenedor de forma flexible, el mismo fue extendido agregando dos factories nuevos (atributo config-factory):
	1) custom
	2) container

El primer factory agrega la posibilidad de definir un scope y de inyectar un objeto referenciando a otro. De esta forma mediante el atributo
"config-scope" se puede especificar si se desea que el objeto creado sea "singleton" utilizando dicho valor. Esto se complementa con el atributo
"config-ref-to" el cual indica el path desde el root del objeto al cual hace referencia el tag, es decir, que cuando se resuelva el objeto
en realidad se terminará instanciando el objeto apuntado por el atributo "config-ref-to". Si este es singleton nos se creará una nueva instancia.
El factory es implementado mediante dos clases: ar.uba.dc.config.factory.ReferenceBeanFactory y ar.uba.dc.config.factory.SingletonBeanFactory

El segundo factory permite definir objetos de tipo "contenedor". Estos objetos al ser creados contienen otros objetos del mismo tipo que deben "registrarse".
Los atributos que define este factory son:
	- config-source: Tag base para los objetos que seran registrados
	- config-select-key: propiedad que contiene la lista (separada por ",") de objetos a incluir. 
	- config-default: Objeto a registrar por default si la propiedad no existe. Ejemplo: si toma valor "null", entonces se registra el objeto ${config-source}.null
El codigo del factory se encuentra en la clase ar.uba.dc.config.factory.ContainerBeanFactory 


Por alguna razón al abrir eclipse el factory de resources pisa al de target/classes.

Metodos no analizables
----------------------

La herramienta tiene una politica para trabajar con los metodos no analizables. Esto afecta al analisis de escape, al analisis de consumo y a toda
herramienta que requiera calcular el callgraph.
La politica es heredada del PurityAnalisis y considera un a un metodo no analizable si este es nativo y/o bien existe un resumen ad-hoc para el mismo.
En el PurityAnalisis las reglas para determinar cuando existe un resumen ad-hoc para un metodo estaban fijas. La herramienta toma el concepto y lo extiende.

Existe un archivo unanalizable_rules.xml que contiene reglas de matching para metodos.
Cada regla representa un tipo. Dentro de c/regla existe un matching para el package y dentro del matching de package un matching para nombre (no firma, solo el nombre) de un metodo.
De esta forma un metodo matchea una regla si matchea tanto el package como el nombre.

Los tipos de reglas posibles son:
	- exclude: el metodo no es analizado y tampoco se lo considera para armar el callgraph
	- pure:    metodos no analizables que no modifican sus parametros. A efectos del consumo, solo tendran un nodo de tipo MethodNode si el metodo retorna algo.
	- alter:   metodos no analizables que modifican sus parametros pero sin efectos colaterales. A efectos del consumo, los parametros y el nodo retornado (si lo tiene) escapan globalmente
	- impure:  metodos no analizables que modifican sus parametros pero que modifica todo el ambiente. A efectos del consumo, los parametros y el nodo retornado (si lo tiene) escapan globalmente. 
			   En otras palabras, a nivel escape si hay diferencia pero a nivel consumo no difiera de los metodo alter.

Todas las reglas tienen un consumo temporal y residual que sera utilizado para la construccion de resumenes ad-hoc de consumo.

A efectos de la generacion del callgraph si un metodo matchea con alguna de las reglas del archivo se considera no analizable y queda
excluido del callgraph.

A efectos de los analisis, los metodos que matcheen alguna regla que no sea exclude se asumira que tienen un resumen ad-hoc y no seran procesados.

Esta estrategia para tratar los metodos permite acortar el tiempo de armado de un callgraph y a su vez procesar menos metodos. Sin embargo
no pretende solucionar el problema de los metodos no analizables.

La evaluacion de reglas para determinar si un metodo matchea alguna se hace de forma secuencias, procesando una a una las reglas en el orden
en que se encuentran definidas. La unica excepcion son las reglas de tipo "exclude". Para considerar que un metodo esta excluido debe matchear
una regla de tipo exclude pero ningun otro tipo de regla. Esto si bien rompe con la evaluacion secuencial, permite flexibilizar la 
especificacion de reglas exclude.

Repositorios
------------

Tanto el analisis de escape como de consumo pueden reutilizar los resultados de analisis anteriores. Esto da forma a la nocion de repositorio
para los resumenes. Ambos analisis cuentan con dos niveles de repositorios: un repositorio general y el directorio donde se dejan los resultados.

El objetivo de esta division es contar con dos tipos de repositorios: el confiable (${escape.summary.repository.dir} y ${memory.summary.repository.dir})
y (${escape.summary.output.dir} y ${memory.summary.output.dir}) el temporal.

Los resumenes se recuperan primero del temporal y luego del confiable. De esta forma se pueden realizar pruebas hasta determinar que el resumen
es confiable y se pasa al otro repositorio.

Ejecucion
=========
	
Introduccion
------------

Para que la herramienta funcione es necesario verificar previamente dos configuraciones:

- El archivo "bin/barvinok.sh" contiene la invocacion a la calculadora "iscc" pasando un archivo como parametro. Es necesario verificar que el archivo este apuntando a la calculadora (verificar el path).
- El archivo "bin/run.sh" contiene la invocacion a la Java Virtual Machine pasando como parametro el classpath. Es necesario verificar que las rutas del classpath esten bien apuntadas pues sino no se podran ejecutar
  las herramientas. En particular, solo es necesario verificar que la ruta al repositorio de maven este bien configurada.

Analizar otros ejemplos
------------------------

Invariantes
***********

Siempre que agreguemos un nuevo ejemplo debemos agregar la especificacion de invariantes. Esta especificacion debe estar escrita
en el formato definidio por invariants.provider. Es importante decir que la aplicacion maneja un archivo de especificacion por c/clase
y que buscara dentro del directorio especificado por la propiedad invariants.repository el archivo para una clase en una estructura tipo package.

Por ejemplo: los invariantes de la clase ar.uba.dc.simple.Rinard seran buscados en 
	
	${invariants.repository}/ar/uba/dc/simple/Rinard.spec

Nota: está en proceso la agregación de una herramienta que calcule invariantes de loops automáticamente utilizando daikon.

Classpath
*********

Si no agregamos el ejemplo en la carpeta src/main/examples y compilamos desde eclipse, tendremos que cambiar la propiedad application.classpath

Rinard se corre por defecto, salvo que esté configurado que se corra madeja.
 

EjemploSimple02 (memoria)
---

javac -g EjemploSimple02.java (el 01 por ahora no compila). Esto se hace porque mvn compile por ahora no compila los archivos de ejemplo.

./escape.sh ar.uba.dc.simple.EjemploSimple02 (OJO: no poner la extensión)

Para analizar un binario, hay que agregar la carpeta donde se encuentra al soot-class-path. Para EjemploSimple02 no hace falta porque ya está agregada a .classpath (agregar rutas ahí si se quieren correr otros ejemplos)



Para correrlo desde eclipse, se debe:
En argumentos poner: ar.uba.dc.simple.EjemploSimple02 config.properties

o era ar.uba.dc.simple.EjemploSimple04 config.properties "void main(java.lang.String[])" ?


--program ar.uba.dc.jolden.em3d.Em3d --config config.properties --main "void main(java.lang.String[])" --ir --memory 


En Main.class, ar.uba.dc.analysis.memory.impl.runner.MainSootRunner
En Environment, agregar la variable classpath, con el valor correspondiente (la mía es ./bin/../target/classes:./bin/../../jpfHelper/bin:/home/billy/Projects/git2/jconsume//pruebas/:/home/billy/Projects/git2/jconsume//pruebas/jolden:/home/billy/Projects/git2/jconsume//java_project/:/home/billy/.m2/repository/ca/mcgill/sable/jasmin/2.4.0/jasmin-2.4.0.jar:/home/billy/.m2/repository/ca/mcgill/sable/polyglot/1.3.5/polyglot-1.3.5.jar:/home/billy/.m2/repository/ca/mcgill/sable/soot/2.4.0/soot-2.4.0.jar:/home/billy/.m2/repository/jep/jep/2.4.1/jep-2.4.1.jar:/home/billy/.m2/repository/com/verimag/madeja/1.0/madeja-1.0.jar:/home/billy/.m2/repository/com/thoughtworks/xstream/xstream/1.3.1/xstream-1.3.1.jar:/home/billy/.m2/repository/xpp3/xpp3_min/1.1.4c/xpp3_min-1.1.4c.jar:/home/billy/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:/home/billy/.m2/repository/commons-lang/commons-lang/2.5/commons-lang-2.5.jar:/home/billy/.m2/repository/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar:/home/billy/.m2/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:/home/billy/.m2/repository/commons-beanutils/commons-beanutils/1.8.0/commons-beanutils-1.8.0.jar:/home/billy/.m2/repository/commons-io/commons-io/1.4/commons-io-1.4.jar:/home/billy/.m2/repository/log4j/log4j/1.2.12/log4j-1.2.12.jar:/home/billy/.m2/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:/home/billy/.m2/repository/org/apache/velocity/velocity/1.5/velocity-1.5.jar

)
Agregar el bin de jpfHelper como library en el Java Build Path


Em3d
----

Para ejecutar el analisis de escape para el programa em3d alcanza con ejecutar

./escape.sh ar.uba.dc.jolden.em3d.Em3d

Cuidado: al compilar el proyecto con maven, el archivo target/classes/config.properties suele ser borrado. Regenerarlo.

El analisis deja en la carpeta ${escape.summary.output.dir} los summaries de escape en formato XML y la representacion grafica de los mismos.

Para ejecutar el analisis de memoria para el programa em3d alcanza con ejecutar

./memory.sh ar.uba.dc.jolden.em3d.Em3d

El analisis deja en la carpeta ${memory.summary.output.dir} los summaries de memoria en formato XML y la representacion grafica de los mismos.
Ademas deja ${memory.report.output.dir} el reporte HTML producto del analisis.

Para instrumentar el programa em3d alcanza con ejecutar

./instrumentation.sh ar.uba.dc.jolden.em3d.Em3d true

El resultado de la instrumentacion quedará en la carpeta ${instrumented.code.output.dir}.

Todos los scripts a los que se hacen referencia estan en el root de la herramienta

Jlayer
------

Jlayer es un caso especial. El problema que tiene este programa es que la clase javazoom.jl.decoder.huffcodetab tiene entre las lineas 103 a 436 la
creacion de multiples arreglos que hacen que soot no pueda procesar esta clase (requiere de mucha memoria para hacerlo).

Como solucion se crearon clases para c/u de los arreglos que son creados. Cada clase contiene un metodo int [][] VAL() que retorna
uno de los arreglos.

Ademas, para correr jlayer es necesario configurar la variable "escape.recursion.watchdog" en 1 (agregar "escape.recursion.watchdog = 1" al archivo de configuracion).
Esto se debe a que sino los constructores estaticos de algunas clases se invocan recursivamente haciendo que el analisis nunca termine. 

Asi para ejecutar el analisis de escape y memoria para jlayer debemos ejecutar la siguiente
secuencia de pasos:

ejecutar "./escape.sh javazoom.jl.player.jlp"
ejecutar "./memory.sh javazoom.jl.player.jlp"

Para instrumentar jlayer debemos: seguir la siguiente secuencia de pasos

ejecutar "./escape.sh javazoom.jl.player.jlp"
ejecutar "./instrumentation.sh javazoom.jl.player.jlp"

copiar los archivos .des que se encuentran en la carpeta decoder del codigo fuente de los ejemplos a la carpeta decoder de la version instrumentada

Instrumentador
--------------

El instrumentador permite ejecutar un programa simulando que ejecuta bajo una administracion de memoria basada en scopes.
Por defecto el instrumentador retorna el consumo del metodo main del programa pero puede ser adaptado para que de mas informacion.
Para esto se utiliza el archivo tracker.properties que debe se encuentra en la carpeta ar/uba/dc/tools/instrumentation/resource/tracker
del directorio ${instrumented.code.output.dir}.

En el mismo pueden especificarse las siguientes propiedades:
	- tracker.log.enabled: Flag para indicar si se debe loguear o no informacion. Poner el flag en false sirve unicamente para ver si el programa instrumentado ejecuta. 
	- tracker.log.mode.debug.enabled: Flag para indicar si el instrumentador corre en modo debug. En modo debug se genera informacion para todos los metodos ejecutados en lugar de solo para el main.
	- tracker.track.method.<i>: Estas propiedades definen la lista de metodos para los cuales queremos informacion. Si se especifica, tiene presedencia sobre la propiedad ${tracker.log.mode.debug.enabled}.
								Es importante que la lista no tenga intervalos vacios. Es decir, si se especifican 3 metodos, el valor de <i> debe variar de 0 a 2. 

Ejemplo para jlayer:
	  
	tracker.log.enabled = true
	tracker.log.mode.debug.enabled = false

	tracker.track.method.0 = <javazoom.jl.player.jlp: void main(java.lang.String[])>
	tracker.track.method.1 = <javazoom.jl.player.jlp: void play()>
	tracker.track.method.2 = <javazoom.jl.player.Player: boolean play()>
	tracker.track.method.3 = <javazoom.jl.player.Player: boolean play(int)>
	tracker.track.method.4 = <javazoom.jl.player.jlp: javazoom.jl.player.jlp createInstance(java.lang.String[])>
	tracker.track.method.5 = <javazoom.jl.player.Player: boolean decodeFrame()>
	
Notar que los metodos deben especificarse en formato:

	<[ClassName completo]: [firma del metodo]>
