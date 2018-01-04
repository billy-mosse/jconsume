<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.simple.EjemploSimple">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>args</relevant-parameters>
			
			<call-site offset="2" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= args and $t.m == 10 + i]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void m0(int)">
			<relevant-parameters>m</relevant-parameters>
			<requires><![CDATA[m < 50]]></requires>
			<requires><![CDATA[m > 2]]></requires>
			
			<invariant id="loop">
				<constraints><![CDATA[1 <= c <= m]]></constraints>
			</invariant>
			
			<call-site offset="0" srccode-offset="">
      			<constraints>@loop and $t.k == c</constraints>
    		</call-site>
    		
    		<call-site offset="1" srccode-offset="">
      			<constraints>@loop and $t.n == c</constraints>
    		</call-site>
		</method>
		
		<method decl="void m1(int)">
			<relevant-parameters>k</relevant-parameters>
			
			<invariant id="loop_invariant">
				<constraints><![CDATA[1 <= i <= k]]></constraints>
			</invariant>
    		
    		<creation-site offset="0" srccode-offset="">
    			<variables>i</variables>
    			<inductives>i</inductives>
      			<constraints>@loop_invariant</constraints>
    		</creation-site>
    		
    		<call-site offset="1" srccode-offset="">
      			<constraints>@loop_invariant and $t.n == i</constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.simple.B[] m2(int)">
			<relevant-parameters>n</relevant-parameters>
			
			<creation-site offset="0">					
				<variables>k</variables>
				<inductives>k</inductives>
      			<constraints><![CDATA[1 <= k <= n]]></constraints>
    		</creation-site>
			
			<invariant id="loop_invariant">
				<constraints><![CDATA[0 <= j < n]]></constraints>
			</invariant>
			
			<creation-site offset="1" srccode-offset="">
    		 	<variables>j</variables>
				<inductives>j</inductives>
      			<constraints>@loop_invariant</constraints>
    		</creation-site>
		</method>
	</class>
</spec>