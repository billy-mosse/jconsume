<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins31">
		 <method decl="void f(ar.uba.dc.daikon.A)">
			<relevant-parameters>obj3_init__f__a</relevant-parameters>
			 <creation-site offset="0" srccode-offset="28">
				<variables>obj3__f__a, i, obj3_init__f__a</variables>
				<inductives>obj3__f__a, i, obj3_init__f__a</inductives>
				<constraints>
					<![CDATA[obj3 == obj3_init && obj3__f__a == obj3_init__f__a && obj3__f__c == obj3__f__b && obj3__f__c == obj3_init__f__c && obj3__f__c == obj3_init__f__b && obj3__f__c == 0 && i >= 1 && obj3__f__a > obj3__f__c && obj3__f__a > i && obj3__f__c < i]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="13">
				<variables>size___r0, size_args_init, size_args</variables>
				<inductives>size___r0, size_args_init, size_args</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="14">
				<variables>__i0, size_args_init, size_args</variables>
				<inductives>__i0, size_args_init, size_args</inductives>
				<constraints>
					<![CDATA[args_init == args && args_init == args && size_args_init == 1 && __i0 >= size_args_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="16">
				<variables>__r1__f__a, size_args_init, size_args</variables>
				<inductives>__r1__f__a, size_args_init, size_args, </inductives>
				<callee>ar.uba.dc.daikon.Ins31: void mainParameters(ar.uba.dc.daikon.A)</callee>
				<constraints>
					<![CDATA[__r1__f__c == __r1__f__b && __r1__f__c == size_args_init-1 && __r1__f__c == size_args-1 && args_init == args && args_init == args && size_args_init == 1 && __r1__f__a >= size_args_init]]>
				</constraints>
				<binding>$t.obj2_init__f__a == __r1__f__a</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(ar.uba.dc.daikon.A)">
			<relevant-parameters>obj2_init__f__a</relevant-parameters>
			 <call-site offset="0" srccode-offset="21">
				<variables>obj2__f__a, obj2_init__f__a</variables>
				<inductives>obj2__f__a, obj2_init__f__a, </inductives>
				<callee>ar.uba.dc.daikon.Ins31: void f(ar.uba.dc.daikon.A)</callee>
				<constraints>
					<![CDATA[obj2 == obj2_init && obj2__f__a == obj2_init__f__a && obj2__f__c == obj2__f__b && obj2__f__c == obj2_init__f__c && obj2__f__c == obj2_init__f__b && obj2__f__c == 0 && obj2__f__a > obj2__f__c]]>
				</constraints>
				<binding>$t.obj3_init__f__a == obj2__f__a</binding>
			 </call-site>
		</method>
	</class>
</spec>
