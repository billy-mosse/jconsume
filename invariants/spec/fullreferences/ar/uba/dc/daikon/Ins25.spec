<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins25">
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
			 <creation-site offset="0" srccode-offset="1300">
				<variables>args_init, args</variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[args_init == args && args_init == args]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="1301">
				<variables>__r1, args_init, args</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.daikon.A: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="1500">
				<variables>__r1, args_init, args</variables>
				<inductives>__r1__f__a</inductives>
				<callee>ar.uba.dc.daikon.Ins25: void mainParameters(ar.uba.dc.daikon.A)</callee>
				<constraints>
					<![CDATA[__r1__f__c == __r1__f__b && __r1__f__c == size_args_init-1 && __r1__f__c == size_args-1 && args_init == args && args_init == args && __r1__f__a > size_args_init]]>
				</constraints>
				<binding>$t.obj_init__f__a == __r1__f__a</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(ar.uba.dc.daikon.A)">
			<relevant-parameters>obj_init__f__a</relevant-parameters>
			 <creation-site offset="0" srccode-offset="2500">
				<variables>obj, i, obj_init</variables>
				<inductives>obj__f__a, i</inductives>
				<constraints>
					<![CDATA[obj == obj_init && obj__f__a == obj_init__f__a && obj__f__c == obj__f__b && obj__f__c == obj_init__f__c && obj__f__c == obj_init__f__b && obj__f__c >= 0 && i >= 1 && obj__f__a > obj__f__c && obj__f__a > i && obj__f__c < i]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="2501">
				<variables>obj, i, __r0, obj_init</variables>
				<inductives>obj__f__a, i</inductives>
				<callee>ar.uba.dc.daikon.A: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[obj == obj_init && obj__f__a == obj_init__f__a && obj__f__c == obj__f__b && obj__f__c == obj_init__f__c && obj__f__c == obj_init__f__b && obj__f__c >= 0 && i >= 1 && obj__f__a > obj__f__c && obj__f__a > i && obj__f__c < i]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
