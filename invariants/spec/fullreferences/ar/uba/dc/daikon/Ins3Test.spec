<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins3Test">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="5">
				<variables>__r0, r2_init</variables>
				<inductives>__r0, r2_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[r2_init__getClass______getName____ == java__lang__String______class && of length 1 && r2_init______toString == __10__ && size__r2_init______ == 1 && __r0 inr2_init____ && __r0__toString inr2_init______toString]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="8">
				<variables>i0, i4, r2_init</variables>
				<inductives>i0, i4, r2_init</inductives>
				<constraints>
					<![CDATA[i0 == 10 && r2_init__getClass______getName____ == java__lang__String______class && of length 1 && r2_init______toString == __10__ && size__r2_init______ == 1 && i0 >=i4 && i4 >= size__r2_init______]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="9">
				<variables>i0, i4, r2, r2_init</variables>
				<inductives>i0, i4, r2, r2_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[size__r2______ == size__r2_init______ && i0 == 10 && r2__getClass______getName____ == java__lang__String______class && r2____ == __null__ && r2______toString == __null__ && of length 1 && r2_init______toString == __10__ && size__r2______ == 1 && i0 >=i4 && i4 >= size__r2______ && r2__getClass______getName____ ==r2_init__getClass______getName____]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="11">
				<variables>i0, i4, __r0, r2_init</variables>
				<inductives>i0, i4, __r0, r2_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[i0 == 10 && r2_init__getClass______getName____ == java__lang__String______class && of length 1 && r2_init______toString == __10__ && size__r2_init______ == 1 && i0 >=i4 && i4 >= size__r2_init______]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="13">
				<variables>i0, i4, __i1, r2_init</variables>
				<inductives>i0, i4, __i1, r2_init, </inductives>
				<callee>ar.uba.dc.daikon.Ins3: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[i4 ==__i1 && i0 == 10 && r2_init__getClass______getName____ == java__lang__String______class && of length 1 && r2_init______toString == __10__ && size__r2_init______ == 1 && i0 >=i4 && i4 >= size__r2_init______ && $t__i0_init == __i1]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
