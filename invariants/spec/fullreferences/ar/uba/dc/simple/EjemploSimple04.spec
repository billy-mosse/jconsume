<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.simple.EjemploSimple04">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>args.length</relevant-parameters>
			<call-site offset="1,2" srccode-offset="">
      			<constraints>$t.n == 10</constraints>
    		</call-site>
    		<call-site offset="3" srccode-offset="">
      			<constraints>$t.j == 10</constraints>
    		</call-site>
    		<call-site offset="4" srccode-offset="">
      			<constraints>$t.k == args.length</constraints>
    		</call-site>
		</method>
		
		<method decl="java.lang.Integer[] m0(int)">
			<relevant-parameters>n</relevant-parameters>
			
			<invariant id="loop_invariant">
				<constraints><![CDATA[0 <= i < n]]></constraints>
			</invariant>
			
			<call-site offset="1" srccode-offset="">
      			<constraints>@loop_invariant</constraints>
    		</call-site>
    		
    		<creation-site offset="1" srccode-offset="">
      			<constraints><![CDATA[1 <= k <= n]]></constraints>
    		</creation-site>
    		
    		<creation-site offset="2" srccode-offset="">
      			<constraints>@loop_invariant</constraints>
    		</creation-site>
		</method>
		
		<method decl="void m1(int)">
			<relevant-parameters>j</relevant-parameters>
			
			<invariant id="loop_invariant">
				<constraints><![CDATA[0 <= i < j]]></constraints>
			</invariant>
			
			<creation-site offset="0" srccode-offset="">
      			<constraints><![CDATA[1 <= k <= j]]></constraints>
    		</creation-site>
		
			<creation-site offset="1" srccode-offset="">
      			<constraints>@loop_invariant</constraints>
    		</creation-site>
		
			<call-site offset="0" srccode-offset="">
      			<constraints>@loop_invariant</constraints>
    		</call-site>
		
			<call-site offset="1" srccode-offset="">
      			<constraints>$t.values.length == vector.length and vector.length == j</constraints>
    		</call-site>
		</method>
		
		<method decl="java.lang.Integer m2(java.lang.Integer[])">
			<relevant-parameters>values.length</relevant-parameters>
			
			<call-site offset="0" srccode-offset="">
      			<constraints><![CDATA[0 <= i < values.length]]></constraints>
    		</call-site>
		</method>
		
		<method decl="java.lang.Integer[] m3(int)">
			<relevant-parameters>k</relevant-parameters>
			
			<invariant id="loop_invariant">
				<constraints><![CDATA[0 <= i < k]]></constraints>
			</invariant>
			
			<creation-site offset="0" srccode-offset="">
      			<constraints><![CDATA[1 <= j <= k]]></constraints>
    		</creation-site>
			
			<call-site offset="0" srccode-offset="">
      			<constraints>@loop_invariant and $t.n == i</constraints>
    		</call-site>
    		
    		<call-site offset="1" srccode-offset="">
      			<constraints>@loop_invariant and $t.values.length == i</constraints>
    		</call-site>
		</method>
	</class>
</spec>