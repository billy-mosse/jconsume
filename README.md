Authors: Diego Garbervetsky and Guillermo Mosse

Collaborators: Matias Grunberg, Gaston Krasny, Martin Rouaux and Edgardo Zoppi.

Introduction
============

This document contains the necessary information to install the tool, execute it and understand the project's structure.

The document is divided in several sections::

	1) Dependencies
		1.1) Maven
		1.2) Barvinok
		1.3) Graphviz
	2) Project's structure (in construction)
	3) Developed tools (in construction)
	4) Tool configuration (in construction)
	5) Execution
		5.1) Introduction
		5.2) Some examples

Dependencies
============

This section describes the dependencies of the project. A step-by-step description of the commands used to successfully install the tool in an openSUSE 64-bit Virtual Machine can be found in install_all.sh. The steps should be really similar in any Linux distribution.

The steps would be:

1) Download Virtualbox or the corresponding program you use to manage VM's in your OS.

2) Download Ubuntu. I used this one: https://www.ubuntu.com/download/desktop/thank-you?version=18.04&architecture=amd64

3) Create the VM

4) Log in

5) Download git with sudo apt-get install git

6) Clone the repository from https://github.com/billy-mosse/jconsume with the command

```git clone https://github.com/billy-mosse/jconsume.git```

I ran first ```mkdir -p ~/Projects/git; cd ~Projects/git``` so that the repository would be there.

7) Run the script install.sh. Maybe an error will be thrown when installing GMP, but it doesn't matter. If it happens, remove the -e flag from the script so that it doesn't stop and resume the installation.


Maven
-----

Maven's documentation can be found in http://maven.apache.org/.

In linux you can install it with the following command:
```
apt-get install maven
```
If using eclipse, once you installed maven, it might be a good idea to install the plugin. You can download it from here: http://m2eclipse.sonatype.org/
 

- After this, go to the dependencies folder and execute the script "install.sh". so that maven installs some libraries.

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



Project structure
=======================

The project uses the structure suggested by maven as a base for the development. The folders are organized in the following way:

- src/main/java: it contains tool's source files. In particular, the folder is organized in different packages:
	- ar.uba.dc.analysis:
		Code for escape and memory analysis
	- ar.uba.dc.barvinok:
		Code for communicating with barvinok's iscc calculator via the command line. The code is also used to parse and process the results.
	- ar.uba.dc.config:
		Code for generating the application's configuration, based on the configuration.properties file.
		The tool uses the commons-configuration library, of apache (http://commons.apache.org/configuration/)
	- ar.uba.dc.invariant:
		Classes used to implement the invariant provider. These classes make possible 4 different formats:
			- simple: dictionary <statement, invariant>
			- fast: lets you define a .spec file where references to other invariants are forbidden
			- simple-reference: lets you define a .spec file where references to other invariants are possible but transitive closures are not solved
			- full-reference: as last one, but this time transitive closures are solved
		In contrast to the simple format, to generate the invariants of the other three formats, you must compile the input .spec files. The format of the file is the same for the three types of invariants. For more info check the source code and the "invariants.provider" property of the file application.properties.
	- ar.uba.dc.soot:
		Contains classes developed that use the soot Framework (http://www.sable.mcgill.ca/soot/)
	- ar.uba.dc.tools:
		Contains auxiliary tools (as in, not escape nor memory). For more information check "Developed Tools" sections.
	- ar.uba.dc.util:
		Utilitarian classes common to all modules.
	- soot.jimple.toolkits.callgraph: 
		In order to cut the callgraph and not include in its construction classes from the java core, this package had to be included. This let us build the callgraph faster.
- src/test: Tests developed for the tool. Their execution via de command line is not recommended as some of them fail. It's better to run from eclipse.
- src/main/examples: 
	Example programs for the tool to analyze. Some real world programs, like em3d, mst and jlayer are included. For more information check Execution - some examples section.
 bin/: Some scripts used by the tool.
- invariants/: 
		Invariants repository. We used the full-reference format. In the folder there also are some illustrative examples of the other formats.
		Full-reference format is the most complete one but it also needs more compilation time. The other formats exist accidentally, as they were part of an evolution to get to the last format. We keep them because they compile faster (although they are harder to manually generate).
- results/: Escape and memory results.

Data flow
======================
![Data Flow](https://github.com/billy-mosse/jconsume/blob/master/java-project/README_files/flow2.png)

What follows is a short summary of what the program does:



###### Invariants
--------
As we want to compute the memory consumption of all methods of the program, we need to infer how many times each statement of a method is un. This is not a number but a polynomial expression that depends on the parameters of the method.

We call those expressions _invariants_ and we compute them with a tool called [Daikon](https://plse.cs.washington.edu/daikon/download/doc/daikon.html).

Daikon's main feature is to generate method invariants (pre and postconditions). We don't want exactly _that_ but we use Daikon via the following trick, described by an example.

Consider the following code:

```
void m(int p)
{
	int n = 42;
	System.out.println(n + " is an interesting number.");

	int t=0;
	for(int i=1; i<p;i++)
	{
		t+=2;
		doSomeStuff(t);
	}
}

```

We want to know how many invocations to **doSomeStuff(t)** occur, depending on parameter **p**.

We first perform a Live variable analysis to know which variables will be relevant for the invariant. This excludes variable **n**.

Then we perform an inductive analysis to know which variables are the ones that actually count the number of iterations of the loop. Ideally, we get **{i,p}**, but as we are over approximating the memory consumption, a superset also works.

(We have an experimental inductive analysis that basically gets all variable that the loop header condition depends on, but in the release we are over approximating the inductive analysis with the live variable analysis and then remove fake inductives, if any).

Then we 'instrument' the code. For simplicity let's assume that we are only interested in the number of iterations to **doSomeStuff(t)**, though in reality we would be interested also in the call to **System.out.println**. The instrumented code looks like this:

```
void m(int p)
{
	int p_init = p; //NEW STATEMENT
	int n = 42;
	System.out.println(n + " is an interesting number.");

	int t=0;
	for(int i=1; i<p;i++)
	{
		t+=2;

		InstrumentedMethod.dummyFunction_1(i,p,p_init, t) //NEW STATEMENT
		doSomeStuff(t);
	}
}

```

What happened? We added a call to a dummy method. _That_ is what we are going to tell daikon to focus on. 

We also added an assignement **p_init = p**. That's because sometimes the parameters change during the run of a method, and we want to generate loop invariants binded to the initial parameters of the method.

Daikon will run the program several times with different parameters (assigned by us), will generate trace information **only** of **InstrumentedMethod.dummyFunction_1(int, int, int, int)**, and will return likely preconditions for it. This method does nothing, so a precondition (or postcondition, it's the same) of the method is actually an invariant of the loop. Between other invariants, we will see these:


```p == p_init```

```i >= 1```

```i<p```

```t <= 2*p```



###### IR

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


**Feel free to explore Ins#N, with #N ranging from 1 to 25.**

Ins3 is temporarily unavailable; descriptions for each example will be added soon.

<!-- Some examples need to be ran in two parts, as the inductives analaysis overapproximates the set of inductive variables. For example, consider the following example: -->

<!-- This generates automatic invariants for the classes used in invariants/spec/fullreferences/

Ins4 uses ListC.class and there is an inductive variable that must be removed. We are currently tweaking th inductives analysis. In the future it will output a more adjusted over approximation of inductive variables.

Go to java-project/spec/fullreferences/ar/uba/dc/daikon/Ins2.spec and remove ```__r0__f__elementData__f__size``` as an inductive.

Then go again to jconsume/java-project and run the following command:

```sh memory.sh --program "ar.uba.dc.daikon.Ins2" --ir --memory```

This generates the memory consumption analysis. Results can be seen in java-project/results/rinard/report_ar.uba.dc.daikon.Ins4/index.html-->


<!--______________________________________________


**Ins4**:

Go to java-project and run the following command:

```sh invariants_IM.sh "ar.uba.dc.daikon.Ins4" 10```

This generates automatic invariants for the classes used in invariants/spec/fullreferences/

Ins4 uses ListC.class and there is an inductive variable that must be removed. We are currently tweaking th inductives analysis. In the future it will output a more adjusted over approximation of inductive variables.

Go to java-project/spec/fullreferences/ar/uba/dc/util/ListC.spec and remove ```this__f__size``` as an inductive everywhere.


Then go again to jconsume/java-project and run the following command:

```sh memory.sh --program "ar.uba.dc.daikon.Ins4" --ir --memory```

This generates the memory consumption analysis. Results can be seen in java-project/results/rinard/report_ar.uba.dc.daikon.Ins4/index.html

TODO: add the graph format.

______________________________________________

There are more toy examples, under the name Ins#n ¡Explore them! We will continue to add an explain them soon.
-->

______________________________________________

**MST**

This is a real world program! It's part of the Jolden benchmark.

Go to java-project and just run the following command:

```sh full_analysis.sh "ar.uba.dc.jolden.mst.MST" 5```

<!--
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

```__r1__f__array__f__size``` to ```this__f__nodes__f__size```

This is not a bug but an automated feature that is missing in the code.


After doing all this, go again to jconsume/java-project and run the following command:

```sh memory.sh --program "ar.uba.dc.jolden.mst.MST" --ir --memory```

This generates the memory consumption analysis.-->

Results can be seen in java-project/results/rinard/report_ar.uba.dc.jolden.mst.MST/index.html. They can be compared with our paper "Summary-based inference of quantitative bounds of live heap objects": https://dblp.org/rec/html/journals/scp/BrabermanGHY14


**Em3d**

This is another real world program, also part of the Jolden benchmark.


Go to java-project and just run the following command:

```sh invariants_IM.sh "ar.uba.dc.jolden.em3d.Em3d" 5```

This time 2 manual fixed are needed.

Now go to the invariants folder (jconsume/java-project/invariants/spec/fullreferences/ar/uba/dc/jolden/em3d).

Open the BiGraph.spec file and find the ```compute``` method. Add the following relevant parameter: ```this_init__f__eNodes__f__fromCount```

(You just need to add ```<relevant-parameters>this_init__f__eNodes__f__fromCount</relevant-parameters>``` before the first instrumentation site)

This relevant parameter will have to be bound with a variable in the caller, so you need to open Em3d.spec, find the ```mainParameters``` method. There's a call to the ```compute``` method there. ADd the following binding (just below the constraints):


```<binding>$t.this_init__f__eNodes__f__fromCount == __r0__f__eNodes__f__fromCount</binding>```

(or the name of the variable bounded by numNodes_init instead of ```__r0__f__eNodes__f__fromCount``` if it was named differently - named depend on how the jimple was generated)

Finally, go back to the Bigraph.spec. The ```create``` method has 2 calls to ```makeFromNodes```, and the binding is wrong.

So change 

```<binding>$t.this_init__f__fromCount == n1__f__fromCount</binding>```


to

```<binding>$t.this_init__f__fromCount == numDegree</binding>```

in both calls.

There. Now you can run ```sh memory.sh "ar.uba.dc.jolden.em3d.Em3d"``` and you'll get the memory consumption. The results can be compared with the paper, just like MST.




______________________________________________

**Paper programs**

The paper also has some simple examples. They are implemented and can be automatically analyzed; just follow the instructions:

**Program 1**

Go to java-project and run the following command:

<!--```sh invariants_IM.sh "ar.uba.dc.paper.Program1" 10```

This generates automatic invariants for the classes used in invariants/spec/fullreferences/

Ins4 uses ListC.class and there is an inductive variable that must be removed. We are currently tweaking the inductives analysis. In the future it will output a more adjusted over approximation of inductive variables.

Go to java-project/spec/fullreferences/ar/uba/dc/paper/Program1.spec and:



Remove ```\_\_r1\_\_f\_\_size``` from CreationSite #0 and CallSite #0 of void "line(ar.uba.dc.paper.A[][],int)"" 


Then go again to jconsume/java-project and run the following command:

```sh memory.sh --program "ar.uba.dc.paper.Program1" --ir --memory```-->

```sh full_analysis.sh --program "ar.uba.dc.paper.Program1" --ir --memory```

This generates the memory consumption analysis. Results can be seen in java-project/results/rinard/report_ar.uba.dc.paper.Program1/index.html

**Porgram 2**

Same as before, but change the 1 for a 2.

<!--Run ```invariants_IM.sh``` with the corresponding parameters and remove ```result``` as an inductive from CreationSite #2 in Op.spec. The file is in the same directory as Program2.spec (which should have just been generated) and Program1.spec (generated before).-->


<!-- <> Test -->
