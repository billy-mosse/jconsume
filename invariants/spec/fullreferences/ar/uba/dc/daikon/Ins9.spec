<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins9">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="16">
				<variables>__r0, args_init, args</variables>
				<inductives>__r0, args_init, args</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init ==args && args_init____ ==args____ && args_init__getClass______getName____ == java__lang__String______class && size__args_init______ == 1 && __r0 inargs_init____ && __r0__toString inargs_init______toString && args_init__getClass______getName____ ==args__getClass______getName____]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="18">
				<variables>__i0, args_init, args</variables>
				<inductives>__i0, args_init, args, </inductives>
				<callee>ar.uba.dc.daikon.Ins9: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[args_init ==args && args_init____ ==args____ && args_init__getClass______getName____ == java__lang__String______class && size__args_init______ == 1 && __i0 >= size__args_init______ && args_init__getClass______getName____ ==args__getClass______getName____ && $t__n_init == __i0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>n_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="24">
				<variables>n, n_init</variables>
				<inductives>n, n_init</inductives>
				<constraints>
					<![CDATA[n ==n_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="30">
				<variables>n, __r0, s4, __r1, n_init</variables>
				<inductives>n, __r0, s4, __r1, n_init</inductives>
				<callee>java.io.PrintStream: void println(int)</callee>
				<constraints>
					<![CDATA[n ==n_init && __r0____ == ____ && __r0______getClass______getName____ == ____ && s4 == 3 && n > size____r0______]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="36">
				<variables>n, i, __r0, n_init</variables>
				<inductives>n, i, __r0, n_init</inductives>
				<constraints>
					<![CDATA[n ==n_init && i == size____r0______ && n >i]]>
				</constraints>
			 </creation-site>
		</method>
	</class>
</spec>
