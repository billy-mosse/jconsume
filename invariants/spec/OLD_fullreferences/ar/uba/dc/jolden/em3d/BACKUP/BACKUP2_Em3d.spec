<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.em3d.Em3d">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="47">
				<variables>size___r0, size_args_init</variables>
				<inductives>size___r0, size_args_init, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[size___r0 ==size_args_init && size___r0 == 6]]>
				</constraints>
				<binding>$t.size_args_init == size___r0</binding>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="57">
				<variables>size___r0, size_args_init</variables>
				<inductives>size___r0, size_args_init, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void parseCmdLine(java.lang.String[])</callee>
				<constraints>
					<![CDATA[size___r0 ==size_args_init && size___r0 == 6]]>
				</constraints>
				<binding>$t.size_args_init == size___r0</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="58">
				<variables>__i1, __i0, size_args_init</variables>
				<inductives>__i1, __i0, size_args_init, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void mainParameters(int,int,boolean,boolean)</callee>
				<constraints>
					<![CDATA[__i1 == 20 && __i0 == 5 && size_args_init == 6]]>
				</constraints>
				<binding>$t.numNodes_init == __i1 and $t.numDegree_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int,int,boolean,boolean)">
			<relevant-parameters>numNodes_init, numDegree_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="104">
				<variables>numDegree, numNodes, size___r0, numNodes_init, numDegree_init</variables>
				<inductives>numDegree, numNodes, size___r0, numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="105">
				<variables>numDegree, numNodes, numNodes_init, numDegree_init</variables>
				<inductives>numDegree, numNodes, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[numDegree ==numDegree_init && numNodes ==numNodes_init && numDegree == 5 && numNodes == 20]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="109">
				<variables>l_start0, __i2, __i3, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, __i2, __i3, numNodes_init, numDegree_init, </inductives>
				<callee>ar.uba.dc.jolden.em3d.BiGraph: ar.uba.dc.jolden.em3d.BiGraph create(int,int,boolean)</callee>
				<constraints>
					<![CDATA[__i2 ==numNodes_init && __i3 ==numDegree_init && l_start0 == 1802631966 && __i2 == 20 && __i3 == 5]]>
				</constraints>
				<binding>$t.numNodes_init == __i2 and $t.numDegree_init == __i3</binding>
			 </call-site>
			 <call-site offset="3" srccode-offset="112">
				<variables>l_start0, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[l_start0 == 1802631966 && numNodes_init == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="117">
				<variables>l_start0, l_end0, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end0, numNodes_init, numDegree_init</inductives>
			 </creation-site>
			 <call-site offset="4" srccode-offset="117">
				<variables>l_start0, l_end0, _f___r4_count, size_f___r4_value, size___r1, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end0, _f___r4_count, size_f___r4_value, size___r1, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="117">
				<variables>l_start0, l_end0, _f___r4_count, size_f___r4_value, __i4, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end0, _f___r4_count, size_f___r4_value, __i4, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="117">
				<variables>l_start0, l_end0, size___r0, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end0, size___r0, numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="118">
				<variables>l_start0, l_end0, _f___r4_count, size_f___r4_value, size___r6, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end0, _f___r4_count, size_f___r4_value, size___r6, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="118">
				<variables>l_start0, l_end0, _f___r4_count, size_f___r4_value, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end0, _f___r4_count, size_f___r4_value, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="121">
				<variables>l_start0, l_end0, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end0, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[l_start0 == 1802631966 && l_end0 == 1802632046 && numNodes_init == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="125">
				<variables>l_start1, l_start0, l_end0, i, numNodes_init, numDegree_init</variables>
				<inductives>l_start1, l_start0, l_end0, i, numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.BiGraph: void compute()</callee>
				<constraints>
					<![CDATA[l_start1 ==l_end0 && l_start1 == 1802632046 && l_start0 == 1802631966 && i == 0 && numNodes_init == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="129">
				<variables>l_start1, l_start0, l_end0, numNodes_init, numDegree_init</variables>
				<inductives>l_start1, l_start0, l_end0, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[l_start1 ==l_end0 && l_start1 == 1802632046 && l_start0 == 1802631966 && numNodes_init == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="133">
				<variables>l_start1, l_end1, l_start0, l_end0, numNodes_init, numDegree_init</variables>
				<inductives>l_start1, l_end1, l_start0, l_end0, numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="136">
				<variables>l_start0, l_end1, l_start1, l_end0, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end1, l_start1, l_end0, numNodes_init, numDegree_init</inductives>

			 </creation-site>
			 <call-site offset="13" srccode-offset="136">
				<variables>l_start0, l_end1, l_start1, _f___r4_count, size_f___r4_value, l_end0, size___r1, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end1, l_start1, _f___r4_count, size_f___r4_value, l_end0, size___r1, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="136">
				<variables>l_start0, l_end1, l_start1, _f___r4_count, size_f___r4_value, d___d0, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end1, l_start1, _f___r4_count, size_f___r4_value, d___d0, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="136">
				<variables>l_start0, l_end1, l_start1, _f___r4_count, size_f___r4_value, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end1, l_start1, _f___r4_count, size_f___r4_value, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="136">
				<variables>l_start0, l_end1, l_start1, size___r0, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end1, l_start1, size___r0, numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="137">
				<variables>l_start0, l_end1, l_start1, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end1, l_start1, numNodes_init, numDegree_init</inductives>
			 </creation-site>
			 <call-site offset="17" srccode-offset="137">
				<variables>l_start0, l_end1, _f___r4_count, size_f___r4_value, l_start1, size___r1, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end1, _f___r4_count, size_f___r4_value, l_start1, size___r1, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="18" srccode-offset="137">
				<variables>l_start0, l_end1, _f___r4_count, size_f___r4_value, d___d0, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end1, _f___r4_count, size_f___r4_value, d___d0, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="19" srccode-offset="137">
				<variables>l_start0, l_end1, _f___r4_count, size_f___r4_value, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end1, _f___r4_count, size_f___r4_value, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="137">
				<variables>l_start0, l_end1, size___r0, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end1, size___r0, numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="138">
				<variables>l_start0, l_end1, numNodes_init, numDegree_init</variables>
				<inductives>l_start0, l_end1, numNodes_init, numDegree_init</inductives>
			 </creation-site>
			 <call-site offset="21" srccode-offset="138">
				<variables>_f___r4_count, size_f___r4_value, l_start0, l_end1, size___r1, numNodes_init, numDegree_init</variables>
				<inductives>_f___r4_count, size_f___r4_value, l_start0, l_end1, size___r1, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="22" srccode-offset="138">
				<variables>_f___r4_count, size_f___r4_value, d___d0, numNodes_init, numDegree_init</variables>
				<inductives>_f___r4_count, size_f___r4_value, d___d0, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="23" srccode-offset="138">
				<variables>_f___r4_count, size_f___r4_value, numNodes_init, numDegree_init</variables>
				<inductives>_f___r4_count, size_f___r4_value, numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="24" srccode-offset="138">
				<variables>size___r0, numNodes_init, numDegree_init</variables>
				<inductives>size___r0, numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="25" srccode-offset="141">
				<variables>size___r0, numNodes_init, numDegree_init</variables>
				<inductives>size___r0, numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[size___r0 ==numDegree_init && size___r0 == 5 && numNodes_init == 20]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void parseCmdLine(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="16" srccode-offset="168">
				<variables>size_args, size___r2, i, size___r3, size_args_init</variables>
				<inductives>size_args, size___r2, i, size___r3, size_args_init</inductives>
				<callee>java.lang.String: boolean startsWith(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 6 && size___r2 == 2 && size___r3 == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="0" srccode-offset="172">
				<variables>size___r2, size_args, i, size_arg, size___r3, size_args_init</variables>
				<inductives>size___r2, size_args, i, size_arg, size___r3, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size___r2 ==size_arg && size___r2 ==size___r3 && size_args ==size_args_init && size___r2 == 2 && size_args == 6]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="174">
				<variables>size_args, size___r2, i, size_args_init</variables>
				<inductives>size_args, size___r2, i, size_args_init</inductives>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 6 && size___r2 == 2 && i == 1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="174">
				<variables>size_args, size___r2, i, size___r5, size___r6, size___r7, size_args_init</variables>
				<inductives>size_args, size___r2, i, size___r5, size___r6, size___r7, size_args_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args ==size_args_init && size___r2 ==i && size___r2 ==size___r7 && size___r5 ==size___r6 && size_args == 6 && size___r2 == 2 && size___r5 == 20]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="174">
				<variables>size_args, size___r2, i, size___r5, size_args_init</variables>
				<inductives>size_args, size___r2, i, size___r5, size_args_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_args ==size_args_init && size___r2 ==i && size_args == 6 && size___r2 == 2 && size___r5 == 20]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="175">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
			 </creation-site>
			 <call-site offset="3" srccode-offset="175">
				<variables>size___r7, size_args_init</variables>
				<inductives>size___r7, size_args_init</inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="176">
				<variables>size___r2, size_args, i, size_arg, size___r3, size_args_init</variables>
				<inductives>size___r2, size_args, i, size_arg, size___r3, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size___r2 ==size_arg && size___r2 ==size___r3 && size_args ==size_args_init && size___r2 == 2 && size_args == 6]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="178">
				<variables>size_args, size___r2, i, size_args_init</variables>
				<inductives>size_args, size___r2, i, size_args_init</inductives>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 6 && size___r2 == 2 && i == 3]]>
				</constraints>
			 </creation-site>
			 <call-site offset="5" srccode-offset="178">
				<variables>size_args, size___r2, i, size___r5, size___r6, size___r7, size_args_init</variables>
				<inductives>size_args, size___r2, i, size___r5, size___r6, size___r7, size_args_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args ==size_args_init && size___r5 ==size___r6 && size_args == 6 && size___r2 == 2 && i == 4 && size___r5 == 5 && size___r7 == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="178">
				<variables>size_args, size___r2, i, size___r5, size_args_init</variables>
				<inductives>size_args, size___r2, i, size___r5, size_args_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 6 && size___r2 == 2 && i == 4 && size___r5 == 5]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="179">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
			 </creation-site>
			 <call-site offset="7" srccode-offset="179">
				<variables>size___r7, size_args_init</variables>
				<inductives>size___r7, size_args_init</inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="180">
				<variables>size___r2, size_args, i, size_arg, size___r3, size_args_init</variables>
				<inductives>size___r2, size_args, i, size_arg, size___r3, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size___r2 ==size_arg && size___r2 ==size___r3 && size_args ==size_args_init && size___r2 == 2 && size_args == 6 && i == 5]]>
				</constraints>
			 </call-site>
			 <creation-site offset="4" srccode-offset="182">
				<variables>size_args, size___r2, i, size_args_init</variables>
				<inductives>size_args, size___r2, i, size_args_init</inductives>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 6 && size___r2 == 2 && i == 5]]>
				</constraints>
			 </creation-site>
			 <call-site offset="9" srccode-offset="182">
				<variables>size_args, size___r2, i, size___r5, size___r6, size___r7, size_args_init</variables>
				<inductives>size_args, size___r2, i, size___r5, size___r6, size___r7, size_args_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args ==i && size_args ==size_args_init && size___r5 ==size___r6 && size___r5 ==size___r7 && size_args == 6 && size___r2 == 2 && size___r5 == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="182">
				<variables>size_args, size___r2, i, size___r5, size_args_init</variables>
				<inductives>size_args, size___r2, i, size___r5, size_args_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_args ==i && size_args ==size_args_init && size_args == 6 && size___r2 == 2 && size___r5 == 1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="5" srccode-offset="183">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
			 </creation-site>
			 <call-site offset="11" srccode-offset="183">
				<variables>size___r7, size_args_init</variables>
				<inductives>size___r7, size_args_init</inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="184">
				<variables>size___r2, i, size_args, size_arg, size___r3, size_args_init</variables>
				<inductives>size___r2, i, size_args, size_arg, size___r3, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="186">
				<variables>size___r2, i, size_args, size_arg, size___r3, size_args_init</variables>
				<inductives>size___r2, i, size_args, size_arg, size___r3, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="188">
				<variables>size___r2, i, size_args, size___r3, size_args_init</variables>
				<inductives>size___r2, i, size_args, size___r3, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="189">
				<variables>size_args, size___r2, i, size_args_init</variables>
				<inductives>size_args, size___r2, i, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="192">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void usage()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="203">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="204">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="205">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="206">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="207">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="208">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="209">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="210">
				<variables>b___b0</variables>
				<inductives>b___b0</inductives>
				<callee>java.lang.System: void exit(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
