<?xml version="1.0" encoding="UTF-8"?>
<!-- 
Para esta especificacion el analisis es conservador por lo siguiente:
decodeFrame de layerI y II y  III no son distinguibles, toma el maximo.
no se codifica el initialized en Decoder.decodeFrame, luego es parte del residual en cada llamada.
No distinguimos el retrieveDecoder que depende del tipo de layer.
-->
<spec>
 <class decl="javazoom.jl.decoder.LayerIIIDecoder$temporaire">
    <method decl="void &lt;init&gt;()">
      <relevant-parameters></relevant-parameters>
      <creation-site offset="0">
       <constraints>4</constraints>
      </creation-site>
      <creation-site offset="1">
       <constraints>2</constraints>
      </creation-site>
    </method>
  </class>  
</spec>