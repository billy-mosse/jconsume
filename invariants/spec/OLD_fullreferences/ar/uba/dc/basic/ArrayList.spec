<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.basic.ArrayList">
		<method decl="ar.uba.dc.basic.Numero head()">
		</method>
		
		<method decl="void add(ar.uba.dc.basic.Numero)">
			<relevant-parameters>numero.numero, list.size</relevant-parameters>
			
			<creation-site offset="0" srccode-offset="">
      			<constraints><![CDATA[1 <= k <= list.size + 1]]></constraints>
    		</creation-site>
		</method>
		
		<method decl="ar.uba.dc.basic.IIterator iterator()">
			<relevant-parameters>this.list.size</relevant-parameters>
    		    					
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[$t.list.size == this.list.size]]></constraints>
    		</call-site>
		</method>
	</class>
</spec>