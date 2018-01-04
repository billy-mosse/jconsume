<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins11">
		 <method decl="void iterate(java.util.List)">
			<relevant-parameters>size_l_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="42">
				<variables>size_l, size___r0, size_l_init</variables>
				<inductives>size_l, size___r0, size_l_init</inductives>
				<callee>java.util.List: java.util.Iterator iterator()</callee>
				<constraints>
					<![CDATA[size_l ==size___r0 && size_l ==size_l_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="44">
				<variables>size_l, size_l_init</variables>
				<inductives>size_l, size_l_init</inductives>
				<callee>java.util.Iterator: boolean hasNext()</callee>
				<constraints>
					<![CDATA[size_l ==size_l_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="46">
				<variables>size_l, size_l_init</variables>
				<inductives>size_l, size_l_init</inductives>
				<callee>java.util.Iterator: java.lang.Object next()</callee>
				<constraints>
					<![CDATA[size_l ==size_l_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="49">
				<variables>size___r0, size___r1, size_l_init, cont___r2</variables>
				<inductives>size___r0, size___r1, size_l_init, cont___r2</inductives>
				<callee>java.util.List: boolean addAll(java.util.Collection)</callee>
				<constraints>
					<![CDATA[size___r0 ==size___r1 && size___r0 ==size_l_init && cont___r2 == 0 && size___r0 >cont___r2]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void iterate2(java.util.List)">
			<relevant-parameters>size_l_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="54">
				<variables>size___r0, size_l_init</variables>
				<inductives>size___r0, size_l_init</inductives>
				<callee>java.util.List: java.util.Iterator iterator()</callee>
				<constraints>
					<![CDATA[size___r0 ==size_l_init && size___r0 % 2 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="55">
				<variables>size_l_init</variables>
				<inductives>size_l_init</inductives>
				<callee>java.util.Iterator: boolean hasNext()</callee>
				<constraints>
					<![CDATA[size_l_init % 2 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="57">
				<variables>size_l_init</variables>
				<inductives>size_l_init</inductives>
				<callee>java.util.Iterator: java.lang.Object next()</callee>
				<constraints>
					<![CDATA[size_l_init % 2 == 0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="17">
				<variables>size___r1, size_args_init</variables>
				<inductives>size___r1, size_args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args_init == 1 && size___r1 >=size_args_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="19">
				<variables>__i1, size_args_init</variables>
				<inductives>__i1, size_args_init, </inductives>
				<callee>ar.uba.dc.daikon.Ins11: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[size_args_init == 1 && __i1 >=size_args_init]]>
				</constraints>
				<binding>$t.n_init == __i1</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>n_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="24">
				<variables>n, n_init</variables>
				<inductives>n, n_init</inductives>
				<constraints>
					<![CDATA[n ==n_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="24">
				<variables>n, size___r0, size___r1, n_init</variables>
				<inductives>n, size___r0, size___r1, n_init</inductives>
				<callee>java.util.ArrayList: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[n ==n_init && size___r0 ==size___r1 && size___r0 == 0 && n >size___r0]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="28">
				<variables>size_l, n, i, size___r0, n_init</variables>
				<inductives>size_l, n, i, size___r0, n_init</inductives>
				<constraints>
					<![CDATA[size_l ==i && size_l ==size___r0 && n ==n_init && size_l >= 0 && size_l <n]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="28">
				<variables>size_l, n, i, size___r0, size___r3, size___r2, __i0, n_init</variables>
				<inductives>size_l, n, i, size___r0, size___r3, size___r2, __i0, n_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[size_l ==i && size_l ==size___r0 && size_l ==size___r3 && size_l ==size___r2 && size_l ==__i0 && n ==n_init && size_l >= 0 && size_l <n]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="28">
				<variables>size_l, n, i, size___r0, size___r3, n_init</variables>
				<inductives>size_l, n, i, size___r0, size___r3, n_init</inductives>
				<callee>java.util.List: boolean add(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_l ==i && size_l ==size___r0 && size_l ==size___r3 && n ==n_init && size_l >= 0 && size_l <n]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="33">
				<variables>size_l, size___r0, n_init</variables>
				<inductives>size_l, size___r0, n_init, </inductives>
				<callee>ar.uba.dc.daikon.Ins11: void iterate(java.util.List)</callee>
				<constraints>
					<![CDATA[size_l ==size___r0 && size_l ==n_init]]>
				</constraints>
				<binding>$t.size_l_init == size___r0</binding>
			 </call-site>
			 <call-site offset="4" srccode-offset="36">
				<variables>size___r0, n_init</variables>
				<inductives>size___r0, n_init, </inductives>
				<callee>ar.uba.dc.daikon.Ins11: void iterate2(java.util.List)</callee>
				<constraints>
					<![CDATA[size___r0 % 2 == 0 && size___r0 - 2 *n_init == 0 && size___r0 >n_init]]>
				</constraints>
				<binding>$t.size_l_init == size___r0</binding>
			 </call-site>
		</method>
	</class>
</spec>
