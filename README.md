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

Dependencias
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
