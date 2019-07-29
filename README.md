[![Build Status](https://travis-ci.org/billy-mosse/jconsume.svg?branch=master)](https://travis-ci.org/billy-mosse/jconsume)

Authors: Diego Garbervetsky and Guillermo Mosse

Collaborators: Matias Grunberg, Gaston Krasny, Martin Rouaux and Edgardo Zoppi.

Introduction
============

This document contains the necessary information to install the tool, execute it and understand the project's structure.

The document is divided in several sections:

	1) Description
	2) Dependencies
		2.1) Docker
		2.2) VirtualBox
		2.3) Maven
		2.4) Barvinok
		2.5) Graphviz
	3) Project's structure
	4) Tool configuration
	5) Execution
		5.1) Introduction
		5.2) Docker
		5.3) Some examples



Description
============

JConsume (2.0) is a memory consumption analyzer for Java bytecode. It computes parametric expressions that over approximate the maximum number of simultaneously live objects ever created by a method, where by live we mean irreclaimable by any garbage collector. Computing the number of live objects is a key underlying step in all client analysis techniques aiming at computing dynamic-memory requirements.

JConsume implements a new compositional quantitative analysis aimed at inferring non-linear upper-bounds on the number of irreclaimable objects that may be stored in the heap at any point in the execution of Java-like programs, including polymorphism and implicit memory management. The analysis is based on the compositional construction of live objects summaries for every method under analysis. More concretely, it over-approximates both a) the maximum amount of fresh objects which are simultaneously alive during the execution of the method and b) the number of created objects that may remain alive after the method finishes its execution.

Summaries are built up using method local information (e.g., their own allocations) and precomputed summaries of called methods. Since the behavior of a method can vary a lot with the arguments it is called with, summaries are parametric in order to provide bounds that can depend on the arguments.

For more information, see http://lafhis.dc.uba.ar/users/~diegog/JConsume2/ and the paper "Summary-based inference of quantitative bounds of live heap objects" (2014, Braverman, Garbervetsky, Hym, Yovine).

Dependencies
============

Docker
--------

Install docker. Then, download the docker image with the command:

```docker pull gmosse/jconsume-1```

Create the container:

```docker create gmosse/jconsume-1```

This command will return you a container_id.

Jump in:

```docker exec -i -t #container_id /bin/bash```

You are now inside the container. The project is located in /root/Projects/jconsume/java-project.

VirtualBox:
--------

This section describes the dependencies of the project. A step-by-step description of the commands used to successfully install the tool in an openSUSE 64-bit Virtual Machine can be found in install_all.sh. The steps should be really similar in any Linux distribution.

The steps would be:

1) Download Virtualbox or the corresponding program you use to manage VM's in your OS.

2) Download an Ubuntu image. For example: https://www.ubuntu.com/download/desktop/thank-you?version=18.04&architecture=amd64.

3) Create the VM.

4) Log in.

5) Download git with ```sudo apt-get install git```.

6) Clone the repository from https://github.com/billy-mosse/jconsume with the command ```git clone https://github.com/billy-mosse/jconsume.git```

7) Run the script install_all.sh. Maybe an error will be thrown when installing GMP, but it doesn't matter. If it happens, remove the -e flag from the script so that it doesn't stop and resume the installation.


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

A note about Eclipse:

If you run the tool from eclipse (or other IDEs) instead of from the command line, you have to replace soot's ReachableMethods.class by our custom one, that is located in target/lasses/soot/jimple/toolkits/callgraph. For that, you have to add it as a User Entry in Classpath and then put it first in Order and Export.



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

We also added an assignment **p_init = p**. That's because sometimes the parameters change during the run of a method, and we want to generate loop invariants binded to the initial parameters of the method.

Daikon will run the program several times with different parameters (assigned by us), will generate trace information **only** of **InstrumentedMethod.dummyFunction_1(int, int, int, int)**, and will return likely preconditions for it. This method does nothing, so a precondition (or postcondition, it's the same) of the method is actually an invariant of the loop. Between other invariants, we will see these:


```p == p_init```

```i >= 1```

```i<p```

```t <= 2*p```



###### IR

Execution
=======================

Docker
--------

For how to access our container, see the previous section. Let's compile and run an example:

```cd /root/Projects/jconsume/java-project/src/main/examples```
```javac -g ar/uba/dc/jolden/mst/*.java```

```cd /root/Projects/jconsume/java-project```


This command runs the full analysis. The number 10 is related to the amount of iterations daikon does to find invariants.
```./full_analysis.sh "ar.uba.dc.jolden.mst.MST" 10```


The results are stored in results/rinard/report. There are two formats: html and json.

```cd results/rinard/report```

You can check the json format reading this file:

```cat report.json```

Here we'll present several analysis of toy examples, examples from the [original paper](https://www.sciencedirect.com/science/article/pii/S0167642313003298) some of real world programs (Em3d and MST), and point to examples that require some annotations to be processed.

Examples
--------

**Ins0**

The simplest example: a for loop from 1 to n.

Just go to java-project and run the following command:

```./full_analysis.sh "ar.uba.dc.daikon.Ins0" 10```

The second parameter is for how many runs of the program (with increasing numeric parameters) are used to infer likely loop invariants.

This will calculate what we call the loop invariants [TODO explain elsewhere] and then use them to calculate the memory consumption of the program.

Results can be seen in java-project/results/rinard/report_ar.uba.dc.daikon.Ins0/index.html

**Ins1**

This example is pretty similar to Ins0, but the loop goes up to the field of an object.

As with Ins0, just go to java-project and run the following command:

```./full_analysis.sh "ar.uba.dc.daikon.Ins1" 10```

Results can be seen in java-project/results/rinard/report_ar.uba.dc.daikon.Ins1/index.html

More examples (also starting with Ins) can be explored in the correspondir folder.

<!--Ins3 is temporarily unavailable; descriptions for each example will be added soon. -->

<!-- Some examples need to be ran in two parts, as the inductives analaysis overapproximates the set of inductive variables. For example, consider the following example: -->

______________________________________________

**Paper programs**

The paper also has some simple examples. They are implemented and can be automatically analyzed; just follow the instructions:

**Program 1**

Go to java-project and run the following command:

```./full_analysis.sh --program "ar.uba.dc.paper.Program1" --ir --memory```

This generates the memory consumption analysis. Results can be seen in java-project/results/rinard/report_ar.uba.dc.paper.Program1/index.html

You can also see the result of the escape analysis in java-project/images/escape. It is based on a Points to Graph analysis. For more information, check the paper.

Programs 2 and 3 are similar.

**Program4**

For this program, we had to introduce a new parameter bounding the size of test's lists, as described by the corresponding section of the paper. In particular, we needed to annotate that the third callsite had the extra constraint l.size == maxSize, with maxSize being a new method parameter. The annotation used was the following:

@InstrumentationSiteInvariantList(invariants={
			@InstrumentationSiteInvariant(
			isCallSite=true,
			index=3,
			constraints={"l.size == maxSize", "l.size >= 0"},
		    newRelevantParameters={"maxSize"}, newInductives = {  }, newVariables = {  })}
	)

Notice that the annotation can be added to the source code. Feel free to try other annotations on your own programs.

______________________________________________

**Real world programs**

**MST**

This is a real world program! It's part of the Jolden benchmark.

Go to java-project and just run the following command:

```./full_analysis.sh "ar.uba.dc.jolden.mst.MST" 5```

As daikon, the tool we use to generate program invariants, isn't exhaustive (no tool can be), sometimes we need to *annotate* them. An example of this feature can be seen in the constructor of the Vertex class of MST. There, you can find a InstrumentationSiteInvariantList with only one annotated invariant, that establishes a linear relationship between numvert and a temporal variable. The name of the temporal variable can be obtained from the file we generated with daikon, that is, Vertex.spec.

Results can be seen in java-project/results/rinard/report_ar.uba.dc.jolden.mst.MST/index.html. They can be compared with our paper "Summary-based inference of quantitative bounds of live heap objects" (Braberman, Garbervetsky, Hym, Yovine).


**Em3d**

This is another real world program, also part of the Jolden benchmark.

Go to java-project and just run the following command:

```./full_analysis.sh "ar.uba.dc.jolden.mst.MST" 5```

As it happened with MST, this program also has annotated invariants not captured by daikon. There are three of them: two for the method create(), and one for compute().

______________________________________________

**Escape Annotations**

In the src/main/resources/annotations/escape folder you can see examples for annotations one can manually introduce for unanalyzable methods. These annotations share their ideas from the paper [Annotations for (more) precise points-to analysis
](https://www.microsoft.com/en-us/research/publication/annotations-for-more-precise-points-to-analysis/), by Diego Garbervetsky, Francesco Logozzo, Michael Barnett, belonging to Microsoft Research. The annotations include:

- Memory and escape consumption.
- Relevant parameters.
- Whether the method can access this/the parameters.
- Whether this/the parameters can be overwritten by other variables.
