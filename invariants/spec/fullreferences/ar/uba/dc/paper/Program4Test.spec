<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.paper.Program4Test">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="7">
				<variables>size___r1, size_args_init</variables>
				<inductives>size___r1, size_args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[size___r1 == 2 && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="8">
				<variables>count, size_args_init</variables>
				<inductives>count, size_args_init</inductives>
				<constraints>
					<![CDATA[count == 30 && size_args_init == 1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="8">
				<variables>count, size_args_init</variables>
				<inductives>count, size_args_init</inductives>
				<callee>java.util.Random: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[count == 30 && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="10">
				<variables>count, __i3, size_args_init</variables>
				<inductives>count, __i3, size_args_init</inductives>
				<callee>java.lang.String: java.lang.String valueOf(int)</callee>
				<constraints>
					<![CDATA[count ==__i3 && count == 30 && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="10">
				<variables>count, size___r5, size_args_init</variables>
				<inductives>count, size___r5, size_args_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[count == 30 && size___r5 == 2 && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="16">
				<variables>i, count, j, b___b4, size_args_init, aux_1</variables>
				<inductives>i, count, j, b___b4, size_args_init, aux_1</inductives>
				<constraints>
					<![CDATA[i >= 0 && count == 30 && j >= 0 && b___b4 == 3 && size_args_init == 1 && i <=count && count >=j && 1<=aux_1 && aux_1<=b___b4]]>
				</constraints>
			 </creation-site>
			 <call-site offset="4" srccode-offset="17">
				<variables>size_argsTest, i, count, j, size___r0, __i1, size_args_init</variables>
				<inductives>size_argsTest, i, count, j, size___r0, __i1, size_args_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[size_argsTest ==size___r0 && i ==__i1 && size_argsTest == 3 && i >= 0 && count == 30 && j >= 0 && size_args_init == 1 && i <=count && count >=j]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="18">
				<variables>size_argsTest, i, count, j, size___r0, __i1, size_args_init</variables>
				<inductives>size_argsTest, i, count, j, size___r0, __i1, size_args_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[size_argsTest ==size___r0 && j ==__i1 && size_argsTest == 3 && i >= 0 && count == 30 && j >= 0 && size_args_init == 1 && i <=count && count >=j]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="19">
				<variables>size_argsTest, i, count, j, size_args_init</variables>
				<inductives>size_argsTest, i, count, j, size_args_init</inductives>
				<callee>java.util.Random: boolean nextBoolean()</callee>
				<constraints>
					<![CDATA[size_argsTest == 3 && i >= 0 && count == 30 && j >= 0 && size_args_init == 1 && i <=count && count >=j]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="21">
				<variables>i, count, j, size___r0, size_args_init</variables>
				<inductives>i, count, j, size___r0, size_args_init, </inductives>
				<callee>ar.uba.dc.paper.Program4: void main(java.lang.String[])</callee>
				<constraints>
					<![CDATA[i >= 0 && count == 30 && j >= 0 && size___r0 == 3 && size_args_init == 1 && i <=count && count >=j]]>
				</constraints>
				<binding>$t.size_args_init == size___r0</binding>
			 </call-site>
		</method>
	</class>
</spec>
