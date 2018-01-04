<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins19">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="12">
				<variables>__r0, args_init, args</variables>
				<inductives>__r0, args_init, args</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init ==args && args_init____ ==args____ && args_init__getClass______getName____ == java__lang__String______class && size__args_init______ == 1 && __r0 inargs_init____ && __r0__toString inargs_init______toString && args_init__getClass______getName____ ==args__getClass______getName____]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="14">
				<variables>__i0, args_init, args</variables>
				<inductives>__i0, args_init, args</inductives>
				<constraints>
					<![CDATA[args_init ==args && args_init____ ==args____ && args_init__getClass______getName____ == java__lang__String______class && size__args_init______ == 1 && __i0 >= size__args_init______ && args_init__getClass______getName____ ==args__getClass______getName____]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="16">
				<variables>__r1, args_init, args</variables>
				<inductives>__r1, args_init, args, </inductives>
				<callee>ar.uba.dc.daikon.Ins19: void mainParameters(ar.uba.dc.daikon.RichNumberPublic)</callee>
				<constraints>
					<![CDATA[args_init ==args && args_init____ ==args____ && args_init__getClass______getName____ == java__lang__String______class && size__args_init______ == 1 && __r1__number >= size__args_init______ && args_init__getClass______getName____ ==args__getClass______getName____]]>
				</constraints>
				<binding>$t.r_init == args</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(ar.uba.dc.daikon.RichNumberPublic)">
			<relevant-parameters>r_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="26">
				<variables>n, i, r_init, r</variables>
				<inductives>n, i, r_init, r</inductives>
				<constraints>
					<![CDATA[n ==r_init__number && n ==r__number && r_init ==r && i >= 1 && n >i]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="27">
				<variables>n, i, __r0, __r1, r_init, r</variables>
				<inductives>n, i, __r0, __r1, r_init, r</inductives>
				<callee>java.io.PrintStream: void println(java.lang.Object)</callee>
				<constraints>
					<![CDATA[n ==r_init__number && n ==r__number && r_init ==r && i >= 1 && n >i]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
