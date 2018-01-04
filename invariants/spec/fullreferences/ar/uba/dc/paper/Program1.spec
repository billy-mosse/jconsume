<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.paper.Program1">
		 <method decl="void line(ar.uba.dc.paper.A[][],int)">
			<relevant-parameters>size_a_init, m_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="56">
				<variables>m, size_a, j, size___r1, __i1, size_a_init, m_init</variables>
				<inductives>m, size_a, j, size___r1, __i1, size_a_init, m_init</inductives>
				<constraints>
					<![CDATA[m == m_init && a == a_init && a == a_init && size_a == size___r1 && j >= 1 && __i1 >= 0 && m >= j && m > __i1 && m <= size_a && j - __i1 - 1 == 0 && j <= size_a && __i1 <= size_a-1]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="14">
				<variables>size_args, size_args_init</variables>
				<inductives>size_args, size_args_init, </inductives>
				<callee>ar.uba.dc.paper.Program1: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[args == args_init && args == args_init && size_args == 1]]>
				</constraints>
				<binding>$t.size_args_init == size_args</binding>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="24">
				<variables>size___r0, size_args_init, size_args</variables>
				<inductives>size___r0, size_args_init, size_args</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="25">
				<variables>__i0, size_args_init, size_args</variables>
				<inductives>__i0, size_args_init, size_args, </inductives>
				<callee>ar.uba.dc.paper.Program1: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && size_args_init == 1 && __i0 >= size_args_init]]>
				</constraints>
				<binding>$t.r_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>r_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="30">
				<variables>r, r_init, aux_1, aux_2</variables>
				<inductives>r, r_init, aux_2, aux_1</inductives>
				<constraints>
					<![CDATA[r == r_init && 1<=aux_1 && aux_1<=r && 1<=aux_2 && aux_2<=r]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="31">
				<variables>size_b, r, r_init</variables>
				<inductives>size_b, r, r_init, </inductives>
				<callee>ar.uba.dc.paper.Program1: void triangle(ar.uba.dc.paper.A[][],int)</callee>
				<constraints>
					<![CDATA[r == r_init && r == size_b]]>
				</constraints>
				<binding>$t.size_a_init == size_b and $t.n_init == r</binding>
			 </call-site>
		</method>
		 <method decl="void triangle(ar.uba.dc.paper.A[][],int)">
			<relevant-parameters>size_a_init, n_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="43">
				<variables>n, i, size_a, size_a_init, n_init</variables>
				<inductives>n, i, size_a, size_a_init, n_init, </inductives>
				<callee>ar.uba.dc.paper.Program1: void line(ar.uba.dc.paper.A[][],int)</callee>
				<constraints>
					<![CDATA[n == n_init && n == size_a && n == size_a_init && a == a_init && a == a_init && i >= 1 && n >= i]]>
				</constraints>
				<binding>$t.size_a_init == size_a and $t.m_init == i</binding>
			 </call-site>
		</method>
	</class>
</spec>
