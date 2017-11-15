
export CLASSPATH=/home/billy/Downloads/asm-3.3.1.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/soot-2.5.0.jar:/home/billy/Projects/git/jconsume/dependencies/sootlib/sootclasses-2.5.0.jar:/usr/lib/jvm/java-7-oracle/jre/lib/rt.jar:.

java soot.Main -cp . -f dava -pp -allow-phantom-refs -process-dir ar/uba/dc/daikon/ -src-prec jimple -keep-line-number -p jb use-original-names:false -p jb.a enabled:false -p jb.ne enabled:false -p jb.uce enabled:false -p jb.dae enabled:false -p jb.ule enabled:false -p jb.cp enabled:false
