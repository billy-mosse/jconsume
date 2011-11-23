<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.basic.EjemploPolimorfismo01">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>args</relevant-parameters>
			
			<invariant id="iList1_size">
				<constraints><![CDATA[iList1_size == 10]]></constraints>
			</invariant>
			<invariant id="arrayList1_size">
				<constraints><![CDATA[arrayList1_size == 10]]></constraints>
			</invariant>
			<invariant id="enlList1_size">
				<constraints><![CDATA[enlList1_size == 10]]></constraints>
			</invariant>
			
			<call-site offset="1" srccode-offset="">
      			<constraints><![CDATA[@iList1_size and $t.list.size == iList1_size and $t.n == 10]]></constraints>
    		</call-site>
    		
			<call-site offset="2-3" srccode-offset="">
      			<constraints><![CDATA[@iList1_size and $t.list.size == iList1_size]]></constraints>
    		</call-site>
    		
    		<call-site offset="5" srccode-offset="">
      			<constraints><![CDATA[@arrayList1_size and $t.list.size == arrayList1_size and $t.n == 10]]></constraints>
    		</call-site>
    		
			<call-site offset="6-7" srccode-offset="">
      			<constraints><![CDATA[@arrayList1_size and $t.list.size == arrayList1_size]]></constraints>
    		</call-site>
    		
    		<call-site offset="9" srccode-offset="">
      			<constraints><![CDATA[@enlList1_size and $t.list.size == enlList1_size and $t.n == 10]]></constraints>
    		</call-site>
    		
			<call-site offset="10-11" srccode-offset="">
      			<constraints><![CDATA[@enlList1_size and $t.list.size == enlList1_size]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void agregarPrimerosNIList(ar.uba.dc.basic.IList,int)">
			<relevant-parameters>list.size, n</relevant-parameters>
			
			<creation-site id="loop" offset="0" srccode-offset="">
      			 <constraints><![CDATA[1 <= i <= n]]></constraints>
    		</creation-site>
			
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[@loop and $t.num == i]]></constraints>
    		</call-site>
    		
    		<call-site offset="1" srccode-offset="">
      			<constraints><![CDATA[@loop and $t.numero.numero == i and $t.list.size == i - 1]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void agregarPrimerosNArrayList(ar.uba.dc.basic.ArrayList,int)">
			<relevant-parameters>list.size, n</relevant-parameters>
			
			<creation-site id="loop" offset="0" srccode-offset="">
      			 <constraints><![CDATA[1 <= i <= n]]></constraints>
    		</creation-site>
			
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[@loop and $t.num == i]]></constraints>
    		</call-site>
    		
    		<call-site offset="1" srccode-offset="">
      			<constraints><![CDATA[@loop and $t.numero.numero == i and $t.list.size == i - 1]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void agregarPrimerosNEnlList(ar.uba.dc.basic.EnlList,int)">
			<relevant-parameters>list.size, n</relevant-parameters>
			
			<creation-site id="loop" offset="0" srccode-offset="">
      			 <constraints><![CDATA[1 <= i <= n]]></constraints>
    		</creation-site>
			
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[@loop and $t.num == i]]></constraints>
    		</call-site>
    		
    		<call-site offset="1" srccode-offset="">
      			<constraints><![CDATA[@loop and $t.numero.numero == i]]></constraints>
    		</call-site>
		</method>
		
		<method decl="int sumaIList(ar.uba.dc.basic.IList)">
			<relevant-parameters>list.size</relevant-parameters>

    		<call-site offset="2" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= list.size]]></constraints>
    		</call-site>
    				
			<call-site offset="1" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= list.size + 1]]></constraints>
    		</call-site>
		</method>
		
		<method decl="int sumaArrayList(ar.uba.dc.basic.ArrayList)">
			<relevant-parameters>list.size</relevant-parameters>

    		<call-site offset="2" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= list.size]]></constraints>
    		</call-site>
    				
			<call-site offset="1" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= list.size + 1]]></constraints>
    		</call-site>
		</method>
		
		<method decl="int sumaEnlList(ar.uba.dc.basic.EnlList)">
			<relevant-parameters>list.size</relevant-parameters>

    		<call-site offset="2" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= list.size]]></constraints>
    		</call-site>
    				
			<call-site offset="1" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= list.size + 1]]></constraints>
    		</call-site>
		</method>
	
		<method decl="ar.uba.dc.basic.Numero obtenerPrimeroIList(ar.uba.dc.basic.IList)">
			<relevant-parameters>list.size</relevant-parameters>
		</method>
		
		<method decl="ar.uba.dc.basic.Numero obtenerPrimeroArrayList(ar.uba.dc.basic.ArrayList)">
			<relevant-parameters>list.size</relevant-parameters>
		</method>
		
		<method decl="ar.uba.dc.basic.Numero obtenerPrimeroEnlList(ar.uba.dc.basic.EnlList)">
			<relevant-parameters>list.size</relevant-parameters>
		</method>
	</class>
</spec>