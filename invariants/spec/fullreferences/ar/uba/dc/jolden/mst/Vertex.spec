<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.jolden.mst.Vertex">
		<method decl="void &lt;init&gt;(ar.uba.dc.jolden.mst.Vertex,int)">
			<relevant-parameters>numvert</relevant-parameters>
			<requires><![CDATA[numvert > 0]]></requires>
			
			<call-site offset="1" srccode-offset="">
      			<!--<constraints><![CDATA[$t.size == numvert]]></constraints>-->
      			<binding>$t.size == numvert</binding>
      			
      			<!-- 
      				Cuando se solucione el problema de exists con la iscc vamos a poder poner este invariante
      				que es en realidad la cota verdadera 
      			-->
      			<!-- <constraints><![CDATA[4 * $t.size == numvert]]></constraints>  -->
    		</call-site>
		</method>
	</class>
</spec>