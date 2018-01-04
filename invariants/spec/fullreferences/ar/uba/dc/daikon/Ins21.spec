<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins21">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="13">
				<variables>__r0, r0_init</variables>
				<inductives>__r0, r0_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="15">
				<variables>i0, r0_init</variables>
				<inductives>i0, r0_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="3" srccode-offset="19">
				<variables>r1, r0_init</variables>
				<inductives>r1, r0_init, </inductives>
				<callee>ar.uba.dc.daikon.Ins21: void mainParameters(ar.uba.dc.daikon.RichNumberPublic)</callee>
				<constraints>
					<![CDATA[$t__r_init == r1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="100000">
				<variables>r0_init</variables>
				<inductives>r0_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="4" srccode-offset="100001">
				<variables>r3, r0_init</variables>
				<inductives>r3, r0_init</inductives>
				<callee>java.lang.NullPointerException: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void mainParameters(ar.uba.dc.daikon.RichNumberPublic)">
			<relevant-parameters>r_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="26">
				<variables>n, r_init</variables>
				<inductives>n, r_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="31">
				<variables>n, r, r_init</variables>
				<inductives>n, r, r_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
		</method>
	</class>
</spec>
