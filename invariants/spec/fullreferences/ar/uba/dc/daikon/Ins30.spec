<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins30">
		 <method decl="void f(int)">
			<relevant-parameters>r_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="25">
				<variables>r, r_init</variables>
				<inductives>r, r_init</inductives>
				<constraints>
					<![CDATA[r == r_init]]>
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
			 <call-site offset="1" srccode-offset="14">
				<variables>__i0, size_args_init, size_args</variables>
				<inductives>__i0, size_args_init, size_args, </inductives>
				<callee>ar.uba.dc.daikon.Ins30: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && size_args_init == 1 && __i0 >= size_args_init]]>
				</constraints>
				<binding>$t.n_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>n_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="20">
				<variables>s, n_init, n</variables>
				<inductives>s, n_init, n, </inductives>
				<callee>ar.uba.dc.daikon.Ins30: void f(int)</callee>
				<constraints>
					<![CDATA[n_init == n && s - n_init - 1 == 0]]>
				</constraints>
				<binding>$t.r_init == s</binding>
			 </call-site>
		</method>
	</class>
</spec>
