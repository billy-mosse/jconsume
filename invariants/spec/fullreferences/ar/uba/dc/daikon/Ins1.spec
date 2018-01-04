<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins1">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="10">
				<variables>size___r0, size_args_init, size_args</variables>
				<inductives>size___r0, size_args_init, size_args</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="11">
				<variables>__i0, size_args_init, size_args</variables>
				<inductives>__i0, size_args_init, size_args, </inductives>
				<callee>ar.uba.dc.daikon.Ins1: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && size_args_init == 1 && __i0 >= size_args_init]]>
				</constraints>
				<binding>$t.n_init == size_args</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>n_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="16">
				<variables>n, n_init</variables>
				<inductives>n, n_init</inductives>
				<constraints>
					<![CDATA[n == n_init]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="21">
				<variables>__r0__f__a, i, n_init, n</variables>
				<inductives>__r0__f__a, i, n_init, n</inductives>
				<constraints>
					<![CDATA[__r0__f__a == n_init && n_init == n && __r0__f__b == 0 && i >= 1 && __r0__f__b < i && __r0__f__b < n_init && i < n_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="22">
				<variables>__r0__f__a, i, __r1, n_init, n</variables>
				<inductives>__r0__f__a, i, __r1, n_init, n</inductives>
				<callee>java.lang.Integer: java.lang.Integer valueOf(int)</callee>
				<constraints>
					<![CDATA[__r0__f__a == n_init && n_init == n && __r0__f__b == 0 && i >= 1 && __r0__f__b < i && __r0__f__b < n_init && i < n_init]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
