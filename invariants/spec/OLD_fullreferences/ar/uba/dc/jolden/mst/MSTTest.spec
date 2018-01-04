<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.mst.MSTTest">
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="16">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="19">
				<variables>size___r2, size_args_init</variables>
				<inductives>size___r2, size_args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[size___r2 == 2 && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="23">
				<variables>count, i, __i2, size_args_init, aux_1</variables>
				<inductives>count, i, __i2, size_args_init, aux_1</inductives>
				<constraints>
					<![CDATA[count == 10 && __i2 == 2 && size_args_init == 1 && count >=i && i >=size_args_init && 1<=aux_1 && aux_1<=__i2]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="25">
				<variables>count, i, size_argsTest, size___r0, __i0, __i3, size_args_init</variables>
				<inductives>count, i, size_args_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[size_argsTest ==size___r0 && __i0 ==size_args_init && count == 10 && size_argsTest == 2 && __i0 == 1 && count >=i && count <__i3 && i >=__i0 && i -__i3 + 10 == 0 && size_argsTest <__i3 && __i0 <__i3]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="26">
				<variables>count, i, size___r0, size_args_init</variables>
				<inductives>count, i, size___r0, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[count == 10 && size___r0 == 2 && size_args_init == 1 && count >=i && i >=size_args_init]]>
				</constraints>
				<binding>$t.size_args_init == size___r0</binding>
			 </call-site>
		</method>
	</class>
</spec>
