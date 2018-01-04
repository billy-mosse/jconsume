<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.binding.Binding1">
		 <method decl="void m1(ar.uba.dc.daikon.A)">
			<relevant-parameters>a_init, a_init__f__a</relevant-parameters>
			 <call-site offset="0" srccode-offset="1200">
				<variables>a, a_init</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.binding.Binding1: void m2(ar.uba.dc.daikon.A)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.b_init == a and $t.b_init__f__a == a__f__a</binding>
			 </call-site>
		</method>
		 <method decl="void m2(ar.uba.dc.daikon.A)">
			<relevant-parameters>b_init, b_init__f__a</relevant-parameters>
			 <call-site offset="0" srccode-offset="1700">
				<variables>b, b_init</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.binding.Binding1: void m3(ar.uba.dc.daikon.A)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.c_init == b and $t.c_init__f__a == b__f__a</binding>
			 </call-site>
		</method>
		 <method decl="void m3(ar.uba.dc.daikon.A)">
			<relevant-parameters>c_init, c_init__f__a</relevant-parameters>
			 <creation-site offset="0" srccode-offset="2600">
				<variables>c, i, c_init</variables>
				<inductives>c__f__a, i</inductives>
				<constraints>
					<![CDATA[c__f__c == c__f__b && c__f__c >= 0 && i >= 1 && c__f__a > c__f__c && c__f__a > i && c__f__c < i]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="2601">
				<variables>c, i, __r0, aux_0, c_init</variables>
				<inductives>c__f__a, i</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[c__f__c == c__f__b && c__f__c >= 0 && i >= 1 && c__f__a > c__f__c && c__f__a > i && c__f__a < aux_0 && c__f__c < i && c__f__c < aux_0 && i < aux_0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="100000">
				<variables></variables>
				<inductives></inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="700">
				<variables>__r0</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.daikon.A: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="800">
				<variables>__r0</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.binding.Binding1: void m1(ar.uba.dc.daikon.A)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.a_init == __r0 and $t.a_init__f__a == __r0__f__a</binding>
			 </call-site>
		</method>
	</class>
</spec>
