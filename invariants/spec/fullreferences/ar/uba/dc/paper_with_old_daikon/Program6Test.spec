<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.paper.Program6Test">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="5">
				<variables>size___r1, size_args_init</variables>
				<inductives>size___r1, size_args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[size___r1 == 2 && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="10">
				<variables>i, count, j, b___b3, size_args_init, aux_1</variables>
				<inductives>i, count, j, b___b3, size_args_init, aux_1</inductives>
				<constraints>
					<![CDATA[i >= 1 && count == 10 && j >= 1 && b___b3 == 2 && size_args_init == 1 && i <=count && i >=size_args_init && count >=j && j >=size_args_init && 1<=aux_1 && aux_1<=b___b3]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="11">
				<variables>i, count, j, size_argsTest, size___r0, __i1, size_args_init</variables>
				<inductives>i, count, j, size_argsTest, size___r0, __i1, size_args_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[i ==__i1 && size_argsTest ==size___r0 && i >= 1 && count == 10 && j >= 1 && size_argsTest == 2 && size_args_init == 1 && i <=count && i >=size_args_init && count >=j && j >=size_args_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="12">
				<variables>i, count, j, size_argsTest, size___r0, __i1, size_args_init</variables>
				<inductives>i, count, j, size_argsTest, size___r0, __i1, size_args_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[j ==__i1 && size_argsTest ==size___r0 && i >= 1 && count == 10 && j >= 1 && size_argsTest == 2 && size_args_init == 1 && i <=count && i >=size_args_init && count >=j && j >=size_args_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="13">
				<variables>i, count, j, size___r0, size_args_init</variables>
				<inductives>i, count, j, size___r0, size_args_init, </inductives>
				<callee>ar.uba.dc.paper.Program6: void main(java.lang.String[])</callee>
				<constraints>
					<![CDATA[i >= 1 && count == 10 && j >= 1 && size___r0 == 2 && size_args_init == 1 && i <=count && i >=size_args_init && count >=j && j >=size_args_init]]>
				</constraints>
				<binding>$t.size_args_init == size___r0</binding>
			 </call-site>
		</method>
	</class>
</spec>
