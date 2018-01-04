<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins9">
		 <method decl="int f()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="52">
				<variables>size___r0, size___r1, b___b0</variables>
				<inductives>size___r0, size___r1, b___b0</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[size___r0 ==size___r1 && size___r0 ==b___b0 && size___r0 == 4]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="53">
				<variables>size___r0</variables>
				<inductives>size___r0</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size___r0 == 4]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="100000">
				<variables></variables>
				<inductives></inductives>
				<constraints>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="int search(java.util.List,int)">
			<relevant-parameters>size_l_init, k_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="80">
				<variables>k, size___r0, size_l_init, k_init</variables>
				<inductives>k, size___r0, size_l_init, k_init</inductives>
				<callee>java.util.List: java.util.Iterator iterator()</callee>
				<constraints>
					<![CDATA[k ==k_init && size___r0 ==size_l_init && size___r0 % 2 == 0 && k <size___r0]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="83">
				<variables>k, cont, index, size_l_init, k_init</variables>
				<inductives>k, cont, index, size_l_init, k_init</inductives>
				<callee>java.util.Iterator: boolean hasNext()</callee>
				<constraints>
					<![CDATA[k ==k_init && cont >= 0 && index == 0 && size_l_init % 2 == 0 && k >=cont && k >=index && k <size_l_init && cont >=index && cont <size_l_init && index <size_l_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="88">
				<variables>k, cont, index, size_l_init, k_init</variables>
				<inductives>k, cont, index, size_l_init, k_init</inductives>
				<callee>java.util.Iterator: java.lang.Object next()</callee>
				<constraints>
					<![CDATA[k ==k_init && cont >= 1 && index == 0 && size_l_init % 2 == 0 && k >=index && k <size_l_init && cont >index && cont <size_l_init && index <size_l_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="89">
				<variables>size_i, k, cont, index, size___r3, size_l_init, k_init, cont___r1</variables>
				<inductives>size_i, k, cont, index, size___r3, size_l_init, k_init, cont___r1</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_i ==size___r3 && k ==k_init && size_i >= 0 && cont >= 1 && index == 0 && size_l_init % 2 == 0 && cont___r1 == 1 && size_i <=k && size_i -cont + 1 == 0 && size_i >=index && size_i <size_l_init && k >=index && k <size_l_init && cont >index && cont <size_l_init && cont >=cont___r1 && index <size_l_init && size_l_init >cont___r1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="91">
				<variables>size___r3, size_l_init, k_init, cont___r1</variables>
				<inductives>size___r3, size_l_init, k_init, cont___r1</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size___r3 ==k_init && size_l_init % 2 == 0 && cont___r1 == 1 && size___r3 <size_l_init && size_l_init >cont___r1]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="int search2(java.util.List,int)">
			<relevant-parameters>size_l_init, k_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="58">
				<variables>k, size_l, size___r0, size_l_init, k_init</variables>
				<inductives>k, size_l, size___r0, size_l_init, k_init</inductives>
				<callee>java.util.List: java.util.Iterator iterator()</callee>
				<constraints>
					<![CDATA[k ==k_init && size_l ==size___r0 && size_l ==size_l_init && k <size_l]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="61">
				<variables>k, size_l, cont, index, size_l_init, k_init</variables>
				<inductives>k, size_l, cont, index, size_l_init, k_init</inductives>
				<callee>java.util.Iterator: boolean hasNext()</callee>
				<constraints>
					<![CDATA[k ==k_init && size_l ==size_l_init && cont >= 0 && index == 0 && k <size_l && k >=cont && k >=index && size_l >cont && size_l >index && cont >=index]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="66">
				<variables>size_l, k, cont, index, size_l_init, k_init</variables>
				<inductives>size_l, k, cont, index, size_l_init, k_init</inductives>
				<callee>java.util.Iterator: java.lang.Object next()</callee>
				<constraints>
					<![CDATA[size_l ==size_l_init && k ==k_init && cont >= 1 && index == 0 && size_l >k && size_l >=cont && size_l >index && k >=index && cont >index]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="67">
				<variables>size_l, size_i, k, cont, index, size___r3, size_l_init, k_init, cont___r1</variables>
				<inductives>size_l, size_i, k, cont, index, size___r3, size_l_init, k_init, cont___r1</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_l ==size_l_init && size_i ==size___r3 && k ==k_init && size_i >= 0 && cont >= 1 && index == 0 && cont___r1 == 1 && size_l >size_i && size_l >k && size_l >=cont && size_l >index && size_l >=cont___r1 && size_i <=k && size_i -cont + 1 == 0 && size_i >=index && k >=index && cont >index && cont >=cont___r1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="69">
				<variables>size_l, size___r3, size_l_init, k_init, cont___r1</variables>
				<inductives>size_l, size___r3, size_l_init, k_init, cont___r1</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_l ==size_l_init && size___r3 ==k_init && cont___r1 == 1 && size_l >size___r3 && size_l >=z]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="74">
				<variables>index, size___r0, size___r4, size_l_init, k_init, cont___r1</variables>
				<inductives>index, size___r0, size___r4, size_l_init, k_init, cont___r1</inductives>
				<callee>java.util.List: boolean addAll(java.util.Collection)</callee>
				<constraints>
					<![CDATA[index ==k_init && size___r0 ==size___r4 && size___r0 ==size_l_init && cont___r1 == 1 && index <size___r0 && size___r0 >=cont___r1]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="18">
				<variables>size___r1, size_args_init</variables>
				<inductives>size___r1, size_args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args_init == 1 && size___r1 >=size_args_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="20">
				<variables>__i1, size_args_init</variables>
				<inductives>__i1, size_args_init, </inductives>
				<callee>ar.uba.dc.daikon.Ins9: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[size_args_init == 1 && __i1 >=size_args_init]]>
				</constraints>
				<binding>$t.n_init == __i1</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>n_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="25">
				<variables>n, n_init</variables>
				<inductives>n, n_init</inductives>
				<constraints>
					<![CDATA[n ==n_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="25">
				<variables>n, size___r0, size___r1, n_init</variables>
				<inductives>n, size___r0, size___r1, n_init</inductives>
				<callee>java.util.ArrayList: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[n ==n_init && size___r0 ==size___r1 && size___r0 == 0 && n >size___r0]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="29">
				<variables>size_l, n, i, size___r0, n_init</variables>
				<inductives>size_l, n, i, size___r0, n_init</inductives>
				<constraints>
					<![CDATA[size_l ==i && size_l ==size___r0 && n ==n_init && size_l >= 0 && size_l <n]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="29">
				<variables>size_l, n, i, size___r0, size___r3, size___r2, __i0, n_init</variables>
				<inductives>size_l, n, i, size___r0, size___r3, size___r2, __i0, n_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[size_l ==i && size_l ==size___r0 && size_l ==size___r3 && size_l ==size___r2 && size_l ==__i0 && n ==n_init && size_l >= 0 && size_l <n]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="29">
				<variables>size_l, n, i, size___r0, size___r3, n_init</variables>
				<inductives>size_l, n, i, size___r0, size___r3, n_init</inductives>
				<callee>java.util.List: boolean add(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_l ==i && size_l ==size___r0 && size_l ==size___r3 && n ==n_init && size_l >= 0 && size_l <n]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="34">
				<variables>n, size_l, size___r0, __i3, n_init</variables>
				<inductives>n, size_l, size___r0, __i3, n_init, </inductives>
				<callee>ar.uba.dc.daikon.Ins9: int search2(java.util.List,int)</callee>
				<constraints>
					<![CDATA[n ==size_l && n ==size___r0 && n ==n_init && n >__i3]]>
				</constraints>
				<binding>$t.size_l_init == size___r0 and $t.k_init == __i3</binding>
			 </call-site>
			 <call-site offset="4" srccode-offset="41">
				<variables>size___r0, __i3, n_init</variables>
				<inductives>size___r0, __i3, n_init, </inductives>
				<callee>ar.uba.dc.daikon.Ins9: int search(java.util.List,int)</callee>
				<constraints>
					<![CDATA[size___r0 % 2 == 0 && size___r0 >__i3 && size___r0 - 2 *n_init == 0 && size___r0 >n_init && __i3 <n_init]]>
				</constraints>
				<binding>$t.size_l_init == size___r0 and $t.k_init == __i3</binding>
			 </call-site>
			 <call-site offset="5" srccode-offset="44">
				<variables>n_init</variables>
				<inductives>n_init</inductives>
				<callee>ar.uba.dc.daikon.Ins9: int f()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
