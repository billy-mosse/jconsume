<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.basic.array.EjemploArray01">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>size</relevant-parameters>
			
			<call-site offset="0-3" srccode-offset="">
      			<constraints><![CDATA[$t.size == size]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void testCrearArrayNoEspecificado(int)">
			<relevant-parameters>size</relevant-parameters>
		</method>
		
		<method decl="void testCrearArrayEspecificado(int)">
			<relevant-parameters>size</relevant-parameters>
			
			<creation-site offset="0" srccode-offset="">
      			<constraints><![CDATA[1 <= k <= size]]></constraints>
    		</creation-site>
		</method>
		
		<method decl="void testCrearArrayEnForNoEspecificado(int)">
			<relevant-parameters>size</relevant-parameters>
			
			<creation-site offset="0" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= size]]></constraints>
    		</creation-site>
		</method>
		
		<method decl="void testCrearArrayEnForEspecificado(int)">
			<relevant-parameters>size</relevant-parameters>
			
			<creation-site offset="0" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= size and 1 <= k <= i]]></constraints>
    		</creation-site>
		</method>
	</class>
</spec>