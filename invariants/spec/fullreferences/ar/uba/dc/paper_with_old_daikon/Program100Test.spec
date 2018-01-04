<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.paper.Program100Test">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="5">
				<variables>size___r1, size_args_init</variables>
				<inductives>size___r1, size_args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="8">
				<variables>count, i, b___b3, size_args_init, aux_1</variables>
				<inductives>count, i, b___b3, size_args_init, aux_1</inductives>
				<constraints>
					<![CDATA[1<=aux_1 && aux_1<=b___b3]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="9">
				<variables>count, i, size_argsTest, size___r0, __i1, size_args_init</variables>
				<inductives>count, i, size_argsTest, size___r0, __i1, size_args_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="10">
				<variables>count, i, size___r0, size_args_init</variables>
				<inductives>count, i, size___r0, size_args_init, </inductives>
				<callee>ar.uba.dc.paper.Program100: void main(java.lang.String[])</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.size_args_init == size___r0</binding>
			 </call-site>
		</method>
	</class>
</spec>
