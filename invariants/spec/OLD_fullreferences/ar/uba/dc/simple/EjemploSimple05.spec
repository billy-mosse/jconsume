<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.simple.EjemploSimple05">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>args</relevant-parameters>
			
			<call-site offset="1" srccode-offset="">
      			<constraints><![CDATA[0 <= i < args and $t.n == i]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void m0(int)">
			<relevant-parameters>n</relevant-parameters>
			
			<call-site id="loop_invariant" offset="0" srccode-offset="">
      			<constraints><![CDATA[0 <= j < 10]]></constraints>
    		</call-site>
    		
    		<creation-site offset="0" srccode-offset="">
      			<constraints>@loop_invariant</constraints>
    		</creation-site>
		</method>
	</class>
</spec>