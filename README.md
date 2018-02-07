Authors: Diego Garbervetsky y Guillermo Mosse

Collaborators: Matias Grunberg, Gaston Krasny, Martin Rouaux, Edgardo Zoppi.

Introduction
============

This document contains the necessary information to install the tool, execute it and understand the project's structure.

The document is divided in several sections::

	1) Dependencies
		1.1) Maven
		1.2) Barvinok
		1.3) Graphviz
	2) Project's structure
	3) Developed tools
	4) Tool configuration
	5) Execution
		5.1) Introduction
		5.2) Some examples

Dependencies
============

Maven
-----

Maven's documentation can be found in http://maven.apache.org/.

In linux you can install it with the following command:
```
apt-get install maven
```
If using eclipse, once you installed maven, it might be a good idea to install the plugin. You can download it from here: http://m2eclipse.sonatype.org/
 

- After this, execute "sh install.sh" in the project folder jconsume/ so that maven installs some libraries.

- Execute also the command "mvn compile" to compile the project and to force all the dependencies to be downloaded to the local maven repository. The command must be executed in jconsume/java-project, where the pom.xml file is located.

If using eclipse, it's important to import the project as a maven project.

Barvinok
--------

The tool utilizes barvinok as support to operate with parametric expression. You may download the 0.40 version (or try the latest one, but it doesn't usually maintain backwards compatibility) from http://barvinok.gforge.inria.fr/

Barvinok's README can be found here: http://repo.or.cz/w/barvinok.git/blob/HEAD:/README

However, to install NTL with GMP support I found these to be the best instructions: http://www.shoup.net/ntl/doc/tour-gmp.html

Graphviz
--------

To generate summaries in a graph format the tool uses the dot command included in graphviz. Install it from http://www.graphviz.org/

Eclipse
=======================

If you run the tool from eclipse instead of from the command line, you have to replace soot's ReachableMethods.class by our custom one, that is located in target/lasses/soot/jimple/toolkits/callgraph. For that, you have to add it as a User Entry in Classpath and then put it first in Order and Export.


Execution
=======================

Here we'll present several analysis toy examples and some of real programs (Em3d and MST)

Toy examples
--------

**Ins0**

The simplest example: a for loop from 1 to n.

Just go to java-project and run the following command:

```sh full_analysis.sh "ar.uba.dc.daikon.Ins0" 10```

The second parameter is for how many runs of the program (with increasing numeric parameters) are used to infer likely loop invariants.

This will calculate what we call the loop invariants [TODO explain elsewhere] and then use them to calculate the memory consumption of the program.

Results can be seen in java-project/results/rinard/report_ar.uba.dc.daikon.Ins0/index.html

**Ins1**

This example is pretty similar to Ins0, but the loop goes up to the field of an object.

As with Ins0, just go to java-project and run the following command:

```sh full_analysis.sh "ar.uba.dc.daikon.Ins1" 10```

This will calculate what we call the loop invariants [TODO explain elsewhere] and then use them to calculate the memory consumption of the program.

Results can be seen in java-project/results/rinard/report_ar.uba.dc.daikon.Ins1/index.html

______________________________________________


Some exampled need to be ran in two parts, as the inductives analaysis overapproximates the set of inductive variables. For example, consider the following example:

**Ins2**

Go to java-project and run the following command:

```sh invariants_IM.sh "ar.uba.dc.daikon.Ins4" 10```

This generates automatic invariants for the classes used in invariants/spec/fullreferences/

Ins4 uses ListC.class and there is an inductive variable that must be removed. We are currently tweaking th inductives analysis. In the future it will output a more adjusted over approximation of inductive variables.

Go to java-project/spec/fullreferences/ar/uba/dc/daikon/Ins2.spec and remove ```__r0__f__elementData__f__size``` as an inductive.

Then go again to jconsume/java-project and run the following command:

```sh memory.sh --program "ar.uba.dc.daikon.Ins2" --ir --memory```

This generates the memory consumption analysis. Results can be seen in java-project/results/rinard/report_ar.uba.dc.daikon.Ins4/index.html

TODO: add the graph format.


______________________________________________


**Ins4**:

Go to java-project and run the following command:

```sh invariants_IM.sh "ar.uba.dc.daikon.Ins4" 10```

This generates automatic invariants for the classes used in invariants/spec/fullreferences/

Ins4 uses ListC.class and there is an inductive variable that must be removed. We are currently tweaking th inductives analysis. In the future it will output a more adjusted over approximation of inductive variables.

Go to java-project/spec/fullreferences/ar/uba/dc/util/ListC.spec and remove "this__f__size" as an inductive.


Then go again to jconsume/java-project and run the following command:

```sh memory.sh --program "ar.uba.dc.daikon.Ins4" --ir --memory```

This generates the memory consumption analysis. Results can be seen in java-project/results/rinard/report_ar.uba.dc.daikon.Ins4/index.html

TODO: add the graph format.

______________________________________________

There are more toy examples, under the name Ins#n ¡Explore them! We will continue to add an explain them soon.


______________________________________________

**MST**

This is a real world program [...]

Go to java-project and run the following command:

```sh invariants_IM.sh "ar.uba.dc.daikon.Ins4" 10```

This generates automatic invariants for the classes used in invariants/spec/fullreferences/

Go to java-project/spec/fullreferences/ar/uba/dc/jolden/mst. Several inductive variables need to be removed from different files:


| File | Method | Site | Inductives |
| ------------- | ------------- | -------------| ------------- |
| MST.spec | ar.uba.dc.jolden.mst.BlueReturn BlueRule(ar.uba.dc.jolden.mst.Vertex,ar.uba.dc.jolden.mst.Vertex) | CreationSite #0 | \_\_r5\_\_f\_\_array\_\_f\_\_size, \_\_r5\_\_f\_\_size, \_\_r20\_\_f\_\_mindist, \_\_r1\_\_f\_\_size, \_\_r17\_\_f\_\_mindist, \_\_r1\_\_f\_\_array\_\_f\_\_size, vlist\_\_f\_\_mindist |
| MST.spec | void mainParameters(int,boolean,boolean) | CreationSite #0 | \_\_r16\_\_f\_\_count, \_\_r16\_\_f\_\_value\_\_f\_\_size, \_\_r12\_\_f\_\_count, \_\_r12\_\_f\_\_value\_\_f\_\_size, \_\_r8\_\_f\_\_count, \_\_r8\_\_f\_\_value\_\_f\_\_size, \_\_r3\_\_f\_\_count, \_\_r3\_\_f\_\_value\_\_f\_\_size, pVertices, \_\_r25\_\_f\_\_count, \_\_r25\_\_f\_\_value\_\_f\_\_size |
| MST.spec | void mainParameters(int,boolean,boolean) | CreationSite #1 | \_\_r16\_\_f\_\_count, \_\_r16\_\_f\_\_value\_\_f\_\_size, \_\_l0, \_\_r12\_\_f\_\_count, \_\_r12\_\_f\_\_value\_\_f\_\_size, \_\_r8 |\_\_f\_\_count, \_\_r8\_\_f\_\_value\_\_f\_\_size, \_\_r3\_\_f\_\_count, \_\_r3\_\_f\_\_value\_\_f\_\_size
| MST.spec | void parseCmdLine(java.lang.String[]) | CreationSite #0 | arg\_\_f\_\_value\_\_f\_\_size |
| MST.spec | void parseCmdLine(java.lang.String[]) | CallSite #1 | arg\_\_f\_\_value\_\_f\_\_size, i |
| Graph.spec | void &lt;init&gt;(int) | CreationSite #0 | numvert__f__size |
| Graph.spec | void addEdges(int) | CreationSite #0 | \_r1\_\_f\_\_array\_\_f\_\_size, \_\_r1\_\_f\_\_size, \_\_i0 | 
| Graph.spec | void addEdges(int) | CallSite #3 | \_\_r1\_\_f\_\_array\_\_f\_\_size, \_\_r1\_\_f\_\_size |
| Hashtable.spec | void &lt;init&gt;(int) | CreationSite #0 | \_\_i0\_\_f\_\_size |
| Vertex.spec | void &lt;init&gt;(ar.uba.dc.jolden.mst.Vertex,int) | CallSite #1 | \_\_i0 |


TODO: explain what you must do with addEdges CallSite #3 binding 

In Graph.spec, method void "addEdges(int)", CallSite #3 (callee is "ar.uba.dc.jolden.mst.Hashtable: void put(java.lang.Object,java.lang.Object)"), the binding needs the following change:

\_\_r1\_\_f\_\_array\_\_f\_\_size to this\_\_f\_\_nodes\_\_f\_\_size

This is not a bug but an automated feature that is missing in the code.


After doing all this, go again to jconsume/java-project and run the following command:

```sh memory.sh --program "ar.uba.dc.jolden.mst.MST" --ir --memory```

This generates the memory consumption analysis. Results can be seen in java-project/results/rinard/report_ar.uba.dc.jolden.mst.MST/index.html

______________________________________________

Paper programs

**Program 1**

Go to java-project and run the following command:

```sh invariants_IM.sh "ar.uba.dc.paper.Program1" 10```

This generates automatic invariants for the classes used in invariants/spec/fullreferences/

Ins4 uses ListC.class and there is an inductive variable that must be removed. We are currently tweaking th inductives analysis. In the future it will output a more adjusted over approximation of inductive variables.

Go to java-project/spec/fullreferences/ar/uba/dc/paper/Program1.spec and:

Remove "\_\_r1\_\_f\_\_size" from CreationSite #0 and CallSite #0 of void "line(ar.uba.dc.paper.A[][],int)"" 


Then go again to jconsume/java-project and run the following command:

```sh memory.sh --program "ar.uba.dc.paper.Program1" --ir --memory```

This generates the memory consumption analysis. Results can be seen in java-project/results/rinard/report_ar.uba.dc.paper.Program1/index.html

