#No anda muy bien DynComp, filtra de mas
#Version 1, con DynComp:


#Estoy haciendo trampa porque genero traza para todo
#Hago esto porque sino no se da cuenta de los invariantes....


CLASSPATH=/home/billy/Programs/daikon/daikon-5.5.2/daikon.jar:/usr/lib/jvm/java-8-oracle/jre/bin/jre/lib/rt.jar:/usr/lib/jvm/java-8-oracle/jre/bin/lib/tools.jar:/home/billy/Projects/git/jconsume/java-project/src/main/examples/

java -cp "/home/billy/Downloads/daikon/daikon-5.4.6/daikon.jar:." daikon.DynComp $1$2 10

java -cp .:$CLASSPATH daikon.Chicory \
     --comparability-file=$2.decls-DynComp \
     $1$2 10

java daikon.Daikon "--ppt-select-pattern=InstrumentedMethod" $2.dtrace.gz --nohierarchy --config_option daikon.Daikon.enable_floats=false --config_option daikon.inv.binary.twoScalar.FloatEqual.enabled=false --config_option daikon.inv.binary.twoScalar.LinearBinaryFloat.enabled=false --config_option  daikon.inv.ternary.threeScalar.LinearTernaryFloat.enabled=false --config_option  daikon.inv.unary.scalar.Modulus.enabled=true --config_option  daikon.inv.unary.scalar.NonModulus.enabled=true







#Version 2, sin DynComp:


#java -cp .:$CLASSPATH daikon.Chicory \
    #     $1$2 10

#java daikon.Daikon "--ppt-select-pattern=InstrumentedMethod" $2.dtrace.gz --nohierarchy --config_option daikon.Daikon.enable_floats=false --config_option daikon.inv.binary.twoScalar.FloatEqual.enabled=false --config_option daikon.inv.binary.twoScalar.LinearBinaryFloat.enabled=false --config_option  daikon.inv.ternary.threeScalar.LinearTernaryFloat.enabled=false --config_option  daikon.inv.unary.scalar.Modulus.enabled=true --config_option  daikon.inv.unary.scalar.NonModulus.enabled=true