<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.em3d.Em3dTest">
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
					<![CDATA[size___r2 ==size_args_init && size___r2 == 1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="22">
				<variables>__i3, count, i, size_args_init, aux_1</variables>
				<inductives>__i3, count, i, size_args_init, aux_1</inductives>
				<constraints>
					<![CDATA[__i3 == 4 && count == 6 && size_args_init == 1 && 1<=aux_1 && aux_1<=__i3]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="24">
				<variables>count, i, size_args_init</variables>
				<inductives>count, i, size_args_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[count == 6 && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="28">
				<variables>count, i, j, size_args_init</variables>
				<inductives>count, i, j, size_args_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[count == 6 && i >= 1 && size_args_init == 1 && count >=i && count >j && i >=size_args_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="29">
				<variables>count, i, j, size___r0, size_args_init</variables>
				<inductives>count, i, j, size___r0, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[count == 6 && i >= 1 && size___r0 == 4 && size_args_init == 1 && count >=i && count >j && i >=size_args_init && j <size___r0]]>
				</constraints>
				<binding>$t.size_args_init == size___r0</binding>
			 </call-site>
		</method>
	</class>
</spec>
