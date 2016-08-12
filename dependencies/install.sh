
#!/bin/bash
#Installing 3rd party JARs

#Madeja
mvn install:install-file -Dfile=madeja-1.0.jar -DgroupId=com.verimag -DartifactId=madeja -Dversion=1.0 -Dpackaging=jar

#Soot
mvn install:install-file -Dfile=sootlib/sootclasses.jar -DgroupId=ca.mcgill.sable -DartifactId=soot -Dversion=2.4.0 -Dpackaging=jar
mvn install:install-file -Dfile=sootlib/jasminclasses-2.5.0.jar -DgroupId=ca.mcgill.sable -DartifactId=jasmin -Dversion=2.4.0 -Dpackaging=jar
mvn install:install-file -Dfile=sootlib/polyglotclasses-1.3.5.jar -DgroupId=ca.mcgill.sable -DartifactId=polyglot -Dversion=1.3.5 -Dpackaging=jar


#jep
mvn install:install-file -Dfile=jeplib/jep-2.4.1.jar -DgroupId=jep -DartifactId=jep -Dversion=2.4.1 -Dpackaging=jar
