<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins13">
		 <method decl="java.lang.String[] duplicate(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="38">
				<variables>size_args, __i1, size_args_init, aux_1</variables>
				<inductives>size_args, __i1, size_args_init, aux_1</inductives>
				<constraints>
					<![CDATA[args == args_init && args == args_init && __i1 - 2 * size_args == 0 && __i1 > size_args && 1<=aux_1 && aux_1<=__i1]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="22">
				<variables>size_args, l2, l3, size_args_init</variables>
				<inductives>size_args, l2, l3, size_args_init</inductives>
				<callee>ar.uba.dc.daikon.Ins13: void doNothing()</callee>
				<constraints>
					<![CDATA[args == args_init && args == args_init && l2 == size_args && l2 == size_args_init && l3 >= 0 && l3 <= size_args-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="26">
				<variables>size_args, size_args_init</variables>
				<inductives>size_args, size_args_init, </inductives>
				<callee>ar.uba.dc.daikon.Ins13: java.lang.String[] duplicate(java.lang.String[])</callee>
				<constraints>
					<![CDATA[args == args_init && args == args_init]]>
				</constraints>
				<binding>$t.size_args_init == size_args</binding>
			 </call-site>
			 <call-site offset="2" srccode-offset="31">
				<variables>l2, size___r0, l3, size_args_init, size_args</variables>
				<inductives>l2, size___r0, l3, size_args_init, size_args</inductives>
				<callee>ar.uba.dc.daikon.Ins13: void doNothing()</callee>
				<constraints>
					<![CDATA[l2 == size___r0 && args_init == args && args_init == args && l3 >= 0 && l2 - 2 * size_args_init == 0 && l3 <= size___r0-1 && size___r0-1 >= size_args_init]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
