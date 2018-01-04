<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.paper.FieldInvariant">
		 <method decl="void line(ar.uba.dc.paper.A[][],int)">
			<relevant-parameters>size_a_init, m_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="58">
				<variables>m, size_a, j, size___r2, __i1, size_a_init, m_init</variables>
				<inductives>m, size_a, j, size___r2, __i1, size_a_init, m_init</inductives>
				<constraints>
					<![CDATA[m ==m_init && size_a ==size___r2 && size_a ==size_a_init && size_a == 10 && j >= 1 && __i1 >= 0 && m <=size_a && m >=j && m >__i1 && size_a >=j && size_a >__i1 && j -__i1 - 1 == 0]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="58">
				<variables>m, size_a, j, size___r2, __i1, size_a_init, m_init</variables>
				<inductives>m, size_a, j, size___r2, __i1, size_a_init, m_init</inductives>
				<callee>ar.uba.dc.paper.A: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[m ==m_init && size_a ==size___r2 && size_a ==size_a_init && size_a == 10 && j >= 1 && __i1 >= 0 && m <=size_a && m >=j && m >__i1 && size_a >=j && size_a >__i1 && j -__i1 - 1 == 0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="14">
				<variables>size_args, size___r2, size_args_init</variables>
				<inductives>size_args, size___r2, size_args_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 1 && size___r2 == 2]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="15">
				<variables>size___r3, size_args_init</variables>
				<inductives>size___r3, size_args_init, </inductives>
				<callee>ar.uba.dc.paper.FieldInvariant: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[size___r3 ==size_args_init && size___r3 == 1]]>
				</constraints>
				<binding>$t.size_args_init == size___r3</binding>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="25">
				<variables>size___r1, size_args_init</variables>
				<inductives>size___r1, size_args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[size___r1 == 2 && size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="26">
				<variables>__i1, size_args_init</variables>
				<inductives>__i1, size_args_init, </inductives>
				<callee>ar.uba.dc.paper.FieldInvariant: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[__i1 == 10 && size_args_init == 1]]>
				</constraints>
				<binding>$t.r_init == __i1</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>r_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="31">
				<variables>r, __i0, __i1, r_init, aux_1, aux_2</variables>
				<inductives>r, __i0, __i1, r_init, aux_1, aux_2</inductives>
				<constraints>
					<![CDATA[r ==__i0 && r ==__i1 && r ==r_init && r == 10 && 1<=aux_1 && aux_1<=__i0 && 1<=aux_2 && aux_2<=__i1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="32">
				<variables>size___r0, __i1, r_init</variables>
				<inductives>size___r0, __i1, r_init, </inductives>
				<callee>ar.uba.dc.paper.FieldInvariant: void triangle(ar.uba.dc.paper.A[][],int)</callee>
				<constraints>
					<![CDATA[size___r0 ==__i1 && size___r0 ==r_init && size___r0 == 10]]>
				</constraints>
				<binding>$t.size_a_init == size___r0 and $t.n_init == __i1</binding>
			 </call-site>
		</method>
		 <method decl="void triangle(ar.uba.dc.paper.A[][],int)">
			<relevant-parameters>size_a_init, n_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="42">
				<variables>size___r2, size_a, n, size___r4, size_a_init, n_init</variables>
				<inductives>size___r2, size_a, n, size___r4, size_a_init, n_init</inductives>
				<constraints>
					<![CDATA[size___r2 ==size___r4 && size_a ==n && size_a ==size_a_init && size_a ==n_init && size___r2 == 0 && size_a == 10]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="42">
				<variables>size___r2, size_a, n, size___r4, size_a_init, n_init</variables>
				<inductives>size___r2, size_a, n, size___r4, size_a_init, n_init</inductives>
				<callee>ar.uba.dc.paper.Counter: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[size___r2 ==size___r4 && size_a ==n && size_a ==size_a_init && size_a ==n_init && size___r2 == 0 && size_a == 10]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="43">
				<variables>size___r2, size_a, n, size___r4, b___b1, size_a_init, n_init</variables>
				<inductives>size___r2, size_a, n, size___r4, b___b1, size_a_init, n_init</inductives>
				<callee>java.lang.Integer: java.lang.Integer valueOf(int)</callee>
				<constraints>
					<![CDATA[size___r2 ==size___r4 && size_a ==n && size_a ==size_a_init && size_a ==n_init && size___r2 == 0 && size_a == 10 && b___b1 == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="43">
				<variables>size___r2, size_a, n, size___r4, size_a_init, n_init</variables>
				<inductives>size___r2, size_a, n, size___r4, size_a_init, n_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size___r2 ==size___r4 && size_a ==n && size_a ==size_a_init && size_a ==n_init && size_a == 10 && size___r2 <=size_a]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="43">
				<variables>size___r2, size_a, n, size___r4, __i2, size_a_init, n_init</variables>
				<inductives>size___r2, size_a, n, size___r4, __i2, size_a_init, n_init</inductives>
				<callee>java.lang.Integer: java.lang.Integer valueOf(int)</callee>
				<constraints>
					<![CDATA[size___r2 ==size___r4 && size_a ==n && size_a ==size_a_init && size_a ==n_init && size_a == 10 && size___r2 <=size_a && size___r2 -__i2 + 1 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="43">
				<variables>size___r2, size_a, n, size___r4, size_a_init, n_init</variables>
				<inductives>size___r2, size_a, n, size___r4, size_a_init, n_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size___r2 ==size___r4 && size_a ==n && size_a ==size_a_init && size_a ==n_init && size_a == 10]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="45">
				<variables>size___r2, size_a, n, size___r4, size___r3, size_a_init, n_init</variables>
				<inductives>size___r2, size_a, n, size___r4, size___r3, size_a_init, n_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size___r2 ==size___r4 && size_a ==n && size_a ==size___r3 && size_a ==size_a_init && size_a ==n_init && size_a == 10 && size___r2 <=size_a]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="45">
				<variables>size___r2, size_a, n, size___r4, size___r3, __i2, size_a_init, n_init</variables>
				<inductives>size___r2, size_a, n, size___r4, size___r3, __i2, size_a_init, n_init, </inductives>
				<callee>ar.uba.dc.paper.FieldInvariant: void line(ar.uba.dc.paper.A[][],int)</callee>
				<constraints>
					<![CDATA[size___r2 ==size___r4 && size___r2 ==__i2 && size_a ==n && size_a ==size___r3 && size_a ==size_a_init && size_a ==n_init && size_a == 10 && size___r2 <=size_a]]>
				</constraints>
				<binding>$t.size_a_init == size___r3 and $t.m_init == __i2</binding>
			 </call-site>
		</method>
	</class>
</spec>
