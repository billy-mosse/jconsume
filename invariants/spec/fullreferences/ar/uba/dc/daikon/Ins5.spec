<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins5">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="1200">
				<variables>__r0, args_init, args</variables>
				<inductives></inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && __r0__f__toString__f__length >= size_args_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="1300">
				<variables>__i0, args_init, args</variables>
				<inductives>__i0</inductives>
				<callee>ar.uba.dc.daikon.Ins5: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && __i0 >= size_args_init]]>
				</constraints>
				<binding>$t.n_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>n_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="1800">
				<variables>n, n_init</variables>
				<inductives>n</inductives>
				<constraints>
					<![CDATA[n == n_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="1801">
				<variables>n, __r0, n_init</variables>
				<inductives>n</inductives>
				<callee>ar.uba.dc.daikon.A: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[n == n_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="2400">
				<variables>n, __r0, n_init</variables>
				<inductives>n, __r0__f__a</inductives>
				<constraints>
					<![CDATA[n == n_init && __r0__f__c == __r0__f__b && __r0__f__a >= 1 && __r0__f__c >= 0 && n > __r0__f__a && n > __r0__f__c && __r0__f__a > __r0__f__c]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="2401">
				<variables>n, __r0, __r1, n_init</variables>
				<inductives>n, __r0__f__a</inductives>
				<callee>ar.uba.dc.daikon.A: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[n == n_init && __r0__f__c == __r0__f__b && __r0__f__a >= 1 && __r0__f__c >= 0 && n > __r0__f__a && n > __r0__f__c && __r0__f__a > __r0__f__c]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
