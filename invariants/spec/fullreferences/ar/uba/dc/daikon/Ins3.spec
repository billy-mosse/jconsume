<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins3">
		 <method decl="void m(java.util.List)">
			<relevant-parameters>size_d_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="33">
				<variables>size_d, size_d_init, cont___r0</variables>
				<inductives>size_d, size_d_init, cont___r0</inductives>
				<callee>java.util.List: java.util.Iterator iterator()</callee>
				<constraints>
					<![CDATA[d == d_init && d == d_init && cont___r0 == 0 && cont___r0 <= size_d-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="34">
				<variables>size_d_init, size_d, cont___r0</variables>
				<inductives>size_d_init, size_d, cont___r0</inductives>
				<callee>java.util.Iterator: boolean hasNext()</callee>
				<constraints>
					<![CDATA[d_init == d && d_init == d && cont___r0 >= 0 && cont___r0 <= size_d_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="36">
				<variables>size_d_init, size_d, cont___r0</variables>
				<inductives>size_d_init, size_d, cont___r0</inductives>
				<callee>java.util.Iterator: java.lang.Object next()</callee>
				<constraints>
					<![CDATA[d_init == d && d_init == d && cont___r0 >= 1 && cont___r0 <= size_d_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="37">
				<variables>r, size_d_init, size_d, cont___r0</variables>
				<inductives>r, size_d_init, size_d, cont___r0</inductives>
				<constraints>
					<![CDATA[d_init == d && d_init == d && cont___r0 >= 1 && cont___r0 <= size_d_init]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="16">
				<variables>size___r0, size_args_init, size_args</variables>
				<inductives>size___r0, size_args_init, size_args</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="18">
				<variables>__i0, size_args_init, size_args</variables>
				<inductives>__i0, size_args_init, size_args, </inductives>
				<callee>ar.uba.dc.daikon.Ins3: void mainParameters(int)</callee>
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
			 <creation-site offset="1" srccode-offset="25">
				<variables>size___r0, n, i, n_init</variables>
				<inductives>size___r0, n, i, n_init</inductives>
				<constraints>
					<![CDATA[n == n_init && i == size___r0 && n > i]]>
				</constraints>
			 </creation-site>
			 <call-site offset="3" srccode-offset="28">
				<variables>size___r0, n_init, n</variables>
				<inductives>size___r0, n_init, n, </inductives>
				<callee>ar.uba.dc.daikon.Ins3: void m(java.util.List)</callee>
				<constraints>
					<![CDATA[n_init == n && n_init == size___r0]]>
				</constraints>
				<binding>$t.size_d_init == n</binding>
			 </call-site>
		</method>
	</class>
</spec>
