<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins1Test">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="5">
				<variables>__r0, r2_init</variables>
				<inductives>__r0, r2_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[r1__getClass______getName____ == java__lang__String______class && of length 1 && r1______toString == __10__ && size__r1______ == 1 && r0 in r1____ && r0__toString in r1______toString]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="8">
				<variables>i0, i4, r2_init</variables>
				<inductives>i0, i4, r2_init</inductives>
				<constraints>
					<![CDATA[i0 == 10 && r0__getClass______getName____ == java__lang__String______class && of length 1 && r0______toString == __10__ && size__r0______ == 1 && i0 >= i1 && i1 >= size__r0______]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="9">
				<variables>i0, i4, r2, r2_init</variables>
				<inductives>i0, i4, r2, r2_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[size__r0______ == size__r1______ && i0 == 10 && r0__getClass______getName____ == java__lang__String______class && r0____ == __null__ && r0______toString == __null__ && of length 1 && r1______toString == __10__ && size__r0______ == 1 && i0 >= i1 && i1 >= size__r0______ && r0__getClass______getName____ == r1__getClass______getName____]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="10">
				<variables>i0, i4, __r0, r2_init</variables>
				<inductives>i0, i4, __r0, r2_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[i0 == 10 && r1__getClass______getName____ == java__lang__String______class && of length 1 && r1______toString == __10__ && size__r1______ == 1 && i0 >= i1 && i1 >= size__r1______]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="11">
				<variables>i0, i4, __i1, r2_init</variables>
				<inductives>i0, i4, __i1, r2_init, </inductives>
				<callee>ar.uba.dc.daikon.Ins1: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[i1 == i2 && i0 == 10 && r0__getClass______getName____ == java__lang__String______class && of length 1 && r0______toString == __10__ && size__r0______ == 1 && i0 >= i1 && i1 >= size__r0______ && $t__n_init == __i1]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
