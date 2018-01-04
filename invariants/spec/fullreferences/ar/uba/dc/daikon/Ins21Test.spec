<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins21Test">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="5">
				<variables>__r0, r2_init</variables>
				<inductives>__r0, r2_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="8">
				<variables>i0, i4, r2_init</variables>
				<inductives>i0, i4, r2_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="9">
				<variables>i0, i4, r2, r2_init</variables>
				<inductives>i0, i4, r2, r2_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="13">
				<variables>i0, i4, __r0, r2_init</variables>
				<inductives>i0, i4, __r0, r2_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="15">
				<variables>i0, i4, __i1, r2_init</variables>
				<inductives>i0, i4, __i1, r2_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="4" srccode-offset="17">
				<variables>i0, i4, r3, __i1, r2_init</variables>
				<inductives>i0, i4, r3, __i1, r2_init</inductives>
				<callee>ar.uba.dc.daikon.RichNumberPublic: void f()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="19">
				<variables>i0, i4, r3, r2_init</variables>
				<inductives>i0, i4, r3, r2_init, </inductives>
				<callee>ar.uba.dc.daikon.Ins21: void mainParameters(ar.uba.dc.daikon.RichNumberPublic)</callee>
				<constraints>
					<![CDATA[$t__r_init == r3]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
