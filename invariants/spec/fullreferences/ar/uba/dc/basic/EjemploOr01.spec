<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.basic.EjemploOr01">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>n</relevant-parameters>
			
			<creation-site offset="0" srccode-offset="">
      			<constraints><![CDATA[n > 0]]></constraints>
    		</creation-site>
    		
    		<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[n > 0]]></constraints>
    		</call-site>
    		
			<call-site offset="1" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= n and n > 0]]></constraints>
    		</call-site>
    		
    		<creation-site offset="1" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= n and n > 0]]></constraints>
    		</creation-site>
		</method>
		
		<method decl="void testOr1(int)">
			<relevant-parameters>n</relevant-parameters>
			
			<creation-site offset="0" srccode-offset="">
      			<constraints><![CDATA[n > 0]]></constraints>
    		</creation-site>
    		    					
			<call-site offset="1" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= n and n > 0]]></constraints>
    		</call-site>
		</method>
	</class>
</spec>