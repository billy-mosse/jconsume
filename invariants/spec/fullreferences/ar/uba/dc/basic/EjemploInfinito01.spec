<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.basic.EjemploInfinito01">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>args</relevant-parameters>
			
			<call-site offset="0-4" srccode-offset="">
      			<constraints><![CDATA[$t.n == 5]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void testNumerosHastaSinSentido(int)">
			<relevant-parameters>n</relevant-parameters>
    		    					
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[$t.n == n]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void testNumerosHastaSinSentido2(int)">
			<relevant-parameters>n</relevant-parameters>
    		    					
			<call-site offset="0-1" srccode-offset="">
      			<constraints><![CDATA[$t.n == n]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.basic.Numero[] testNumerosHastaConResidual(int)">
			<relevant-parameters>n</relevant-parameters>
    		    					
			<call-site offset="0-1" srccode-offset="">
      			<constraints><![CDATA[$t.n == n]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void testNumerosHastaSinResidual(int)">
			<relevant-parameters>n</relevant-parameters>
    		    					
			<call-site offset="0-1" srccode-offset="">
      			<constraints><![CDATA[$t.n == n]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void testNumerosHastaConCallYNews(int)">
			<relevant-parameters>n</relevant-parameters>
    		    					
			<call-site offset="0-1" srccode-offset="">
      			<constraints><![CDATA[$t.n == n]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.basic.Numero[] testNumerosHastaConCall(int)">
			<relevant-parameters>n</relevant-parameters>
    		    					
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[$t.n == n]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.basic.Numero[] testNumerosHastaConNew(int)">
			<relevant-parameters>n</relevant-parameters>
    		    					
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[$t.n == n]]></constraints>
    		</call-site>
		</method>

		
		<method decl="ar.uba.dc.basic.Numero[] numerosHasta(int)">
			<relevant-parameters>n</relevant-parameters>
			
			<creation-site offset="0" srccode-offset="">
      			 <constraints><![CDATA[1 <= k <= n]]></constraints>
    		</creation-site>
    		
    		<creation-site id="loop" offset="1" srccode-offset="">
      			 <constraints><![CDATA[1 <= j <= n]]></constraints>
    		</creation-site>
    		    					
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[@loop and $t.num == j]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.basic.Numero[] numerosHastaConNew(int)">
			<relevant-parameters>n</relevant-parameters>
    		
    		<creation-site offset="0" srccode-offset="">
      			 <constraints><![CDATA[1 <= k <= n]]></constraints>
    		</creation-site>
    		
    		<creation-site offset="1" srccode-offset="">
      			 <constraints><![CDATA[1 <= j]]></constraints>
    		</creation-site>
    		    					
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[1 <= j <= n and $t.num == j]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.basic.Numero[] numerosHastaConCall(int)">
			<relevant-parameters>n</relevant-parameters>
			
			<creation-site offset="0" srccode-offset="">
      			 <constraints><![CDATA[1 <= k <= n]]></constraints>
    		</creation-site>
    		
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[1 <= j and $t.num == j]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.basic.Numero[] numerosHastaSinSentido(int)">
			<relevant-parameters>n</relevant-parameters>
			
			<creation-site offset="0-1" srccode-offset="">
      			 <constraints><![CDATA[1 <= k <= n]]></constraints>
    		</creation-site>
    		
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[1 <= j and $t.num == j]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.basic.Numero[] numerosHastaSinSentido2(int)">
			<relevant-parameters>n</relevant-parameters>
			
			<creation-site offset="0-1" srccode-offset="">
      			 <constraints><![CDATA[1 <= k <= n]]></constraints>
    		</creation-site>
    		
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[1 <= j <= n and $t.num == j]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.basic.Numero crearNumero(int)">
			<relevant-parameters>num</relevant-parameters>
    		
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[$t.num == num]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void cambiarNumeros(ar.uba.dc.basic.Numero,ar.uba.dc.basic.Numero)">
			<relevant-parameters>n.numero, m.numero</relevant-parameters>
    		
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[$t.num == n.numero]]></constraints>
    		</call-site>
		</method>
		
	</class>
</spec>