<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.paper.Program5Test">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="7">
				<variables>size___r1, size_args_init</variables>
				<inductives>size___r1, size_args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[size___r1 == 3 && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="10">
				<variables>count, i, b___b3, size_args_init, aux_1</variables>
				<inductives>count, i, b___b3, size_args_init, aux_1</inductives>
				<constraints>
					<![CDATA[b___b3 ==size_args_init && count == 100 && b___b3 == 1 && count >=i && 1<=aux_1 && aux_1<=b___b3]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="11">
				<variables>count, i, size_argsTest, size___r0, __i1, size_args_init</variables>
				<inductives>count, i, size_argsTest, size___r0, __i1, size_args_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[i ==__i1 && size_argsTest ==size___r0 && size_argsTest ==size_args_init && count == 100 && size_argsTest == 1 && count >=i]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="12">
				<variables>count, i, size___r0, size_args_init</variables>
				<inductives>count, i, size___r0, size_args_init</inductives>
				<callee>ar.uba.dc.paper.Program5: void main(java.lang.String[])</callee>
				<constraints>
					<![CDATA[size___r0 ==size_args_init && count == 100 && size___r0 == 1 && count >=i]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
