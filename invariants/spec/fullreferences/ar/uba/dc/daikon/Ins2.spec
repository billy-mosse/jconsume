<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins2">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="15">
				<variables>size___r0, size_args_init, size_args</variables>
				<inductives>size___r0, size_args_init, size_args</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="17">
				<variables>__i0, size_args_init, size_args</variables>
				<inductives>__i0, size_args_init, size_args, </inductives>
				<callee>ar.uba.dc.daikon.Ins2: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && size_args_init == 1 && __i0 >= size_args_init]]>
				</constraints>
				<binding>$t.n_init == size_args</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>n_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="22">
				<variables>n, n_init</variables>
				<inductives>n, n_init</inductives>
				<constraints>
					<![CDATA[n == n_init]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="26">
				<variables>size___r0, n, i, n_init</variables>
				<inductives>size___r0, n, i, n_init</inductives>
				<constraints>
					<![CDATA[n == n_init && i == size___r0 && n > i]]>
				</constraints>
			 </creation-site>
			 <call-site offset="3" srccode-offset="29">
				<variables>size___r0, n_init, n, cont___r1</variables>
				<inductives>size___r0, n_init, n, cont___r1</inductives>
				<callee>java.util.ArrayList: java.util.Iterator iterator()</callee>
				<constraints>
					<![CDATA[n_init == n && n_init == size___r0 && cont___r1 == 0 && cont___r1 <= size___r0-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="31">
				<variables>n_init, n, cont___r1</variables>
				<inductives>n_init, n, cont___r1</inductives>
				<callee>java.util.Iterator: boolean hasNext()</callee>
				<constraints>
					<![CDATA[n_init == n && cont___r1 >= 0 && n_init >= cont___r1]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="33">
				<variables>n_init, n, cont___r1</variables>
				<inductives>n_init, n, cont___r1</inductives>
				<callee>java.util.Iterator: java.lang.Object next()</callee>
				<constraints>
					<![CDATA[n_init == n && cont___r1 >= 1 && n_init >= cont___r1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="34">
				<variables>n_init, n, cont___r1</variables>
				<inductives>n_init, n, cont___r1</inductives>
				<constraints>
					<![CDATA[n_init == n && cont___r1 >= 1 && n_init >= cont___r1]]>
				</constraints>
			 </creation-site>
		</method>
	</class>
</spec>
