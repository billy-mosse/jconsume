<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.paper.Op">
		 <method decl="java.lang.Object apply(java.lang.Object)">
			<relevant-parameters>o_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="11">
				<variables>o, o_init</variables>
				<inductives>o, o_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="13">
				<variables>o, __r0, o_init</variables>
				<inductives>o, __r0, o_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="3" srccode-offset="14">
				<variables>__r1, __r2, o_init, o</variables>
				<inductives>__r1, __r2, o_init, o</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="16">
				<variables>__i1, __r1, o_init, o</variables>
				<inductives>__i1, __r1, o_init, o</inductives>
				<callee>java.util.Random: int nextInt()</callee>
				<constraints>
					<![CDATA[__i1 >= 0]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="18">
				<variables>result, o_init, o</variables>
				<inductives>result, o_init, o</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="100001">
				<variables>this</variables>
				<inductives>this</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
