<?xml version="1.0" encoding="UTF-8"?>
<!-- 
Para esta especificacion el analisis es conservador por lo siguiente:
decodeFrame de layerI y II y  III no son distinguibles, toma el maximo.
no se codifica el initialized en Decoder.decodeFrame, luego es parte del residual en cada llamada.
No distinguimos el retrieveDecoder que depende del tipo de layer.
-->
<spec>
    <class decl="javazoom.jl.decoder.Equalizer">
    <method decl="void &lt;init&gt;()">
      <relevant-parameters/>
        <creation-site offset="0">
        <constraints>32</constraints>
      </creation-site>
    </method>
     <method decl="float[] getBandFactors()">
      <relevant-parameters/>
      <creation-site offset="0">
        <constraints>32</constraints>
      </creation-site>
    </method>
     </class>
</spec>