<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.basic.EnlList">
		<method decl="ar.uba.dc.basic.Numero head()">
		</method>
		
		<method decl="void add(ar.uba.dc.basic.Numero)">
			<relevant-parameters>numero.numero</relevant-parameters>
			
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[$t.d == numero.numero]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.basic.IIterator iterator()">
			<relevant-parameters>this.head</relevant-parameters>
    		    					
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[$t.head == this.head]]></constraints>
    		</call-site>
		</method>
	</class>
</spec>