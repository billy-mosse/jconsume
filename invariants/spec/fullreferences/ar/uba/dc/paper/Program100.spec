<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.paper.Program100">
		 <method decl="void line(ar.uba.dc.paper.A[][],int)">
			<relevant-parameters>size_a_init, m_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="62">
				<variables>m, size_a, j, size___r2, __i1, size_a_init, m_init</variables>
				<inductives>m, size_a, j, size___r2, __i1, size_a_init, m_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="62">
				<variables>m, size_a, j, size___r2, __i1, size_a_init, m_init</variables>
				<inductives>m, size_a, j, size___r2, __i1, size_a_init, m_init</inductives>
				<callee>ar.uba.dc.paper.A: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="14">
				<variables>size___r0, size_args_init</variables>
				<inductives>size___r0, size_args_init, </inductives>
				<callee>ar.uba.dc.paper.Program100: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.size_args_init == size___r0</binding>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="24">
				<variables>size___r1, size_args_init</variables>
				<inductives>size___r1, size_args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="25">
				<variables>__i1, size_args_init</variables>
				<inductives>__i1, size_args_init, </inductives>
				<callee>ar.uba.dc.paper.Program100: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.r_init == __i1</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>r_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="30">
				<variables>r, __i0, __i1, r_init, aux_1, aux_2</variables>
				<inductives>r, __i0, __i1, r_init, aux_1, aux_2</inductives>
				<constraints>
					<![CDATA[1<=aux_1 && aux_1<=__i0 && 1<=aux_2 && aux_2<=__i1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="31">
				<variables>size___r0, __i1, r_init</variables>
				<inductives>size___r0, __i1, r_init, </inductives>
				<callee>ar.uba.dc.paper.Program100: void triangle(ar.uba.dc.paper.A[][],int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.size_a_init == size___r0 and $t.n_init == __i1</binding>
			 </call-site>
		</method>
		 <method decl="void triangle(ar.uba.dc.paper.A[][],int)">
			<relevant-parameters>size_a_init, n_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="43">
				<variables>size_a, n, i, size___r0, __i1, size_a_init, n_init</variables>
				<inductives>size_a, n, i, size___r0, __i1, size_a_init, n_init, </inductives>
				<callee>ar.uba.dc.paper.Program100: void line(ar.uba.dc.paper.A[][],int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.size_a_init == size___r0 and $t.m_init == __i1</binding>
			 </call-site>
		</method>
	</class>
</spec>
