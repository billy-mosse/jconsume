<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="em3d.Node">
		 <method decl="em3d.Node[] fillTable(int,int)">
			<relevant-parameters>size_init, degree_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="80">
				<variables>__i0, size, size_init, degree_init, aux_1</variables>
				<inductives>__i0, size, size_init, degree_init, aux_1</inductives>
				<constraints>
					<![CDATA[__i0 ==size && __i0 ==size_init && __i0 >degree_init && 1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="82">
				<variables>size, size_init, degree_init</variables>
				<inductives>size, size_init, degree_init</inductives>
				<constraints>
					<![CDATA[size ==size_init && size >degree_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="82">
				<variables>size, __i1, size_init, degree_init</variables>
				<inductives>size, __i1, size_init, degree_init</inductives>
				<callee>em3d.Node: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[size ==size_init && __i1 ==degree_init && size >__i1]]>
				</constraints>
				<binding>$t.degree_init == __i1</binding>
			 </call-site>
			 <creation-site offset="2" srccode-offset="85">
				<variables>size, i, size_init, degree_init</variables>
				<inductives>size, i, size_init, degree_init</inductives>
				<constraints>
					<![CDATA[size ==size_init && i >= 1 && size >i && size >degree_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="85">
				<variables>size, i, __i1, size_init, degree_init</variables>
				<inductives>size, i, __i1, size_init, degree_init</inductives>
				<callee>em3d.Node: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[size ==size_init && __i1 ==degree_init && i >= 1 && size >i && size >__i1]]>
				</constraints>
				<binding>$t.degree_init == __i1</binding>
			 </call-site>
		</method>
		 <method decl="java.lang.String toString()">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="192">
				<variables>_f_this_init_fromCount, d_f_this_init_value</variables>
				<inductives>_f_this_init_fromCount, d_f_this_init_value</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="192">
				<variables>_f_this_init_fromCount, d_f_this_init_value</variables>
				<inductives>_f_this_init_fromCount, d_f_this_init_value</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="192">
				<variables>_f_this_init_fromCount, d_f_this_init_value</variables>
				<inductives>_f_this_init_fromCount, d_f_this_init_value</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="192">
				<variables>_f_this_init_fromCount, d_f_this_init_value</variables>
				<inductives>_f_this_init_fromCount, d_f_this_init_value</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="192">
				<variables>_f_this_init_fromCount, d_f_this_init_value</variables>
				<inductives>_f_this_init_fromCount, d_f_this_init_value</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="192">
				<variables>_f_this_init_fromCount, d_f_this_init_value</variables>
				<inductives>_f_this_init_fromCount, d_f_this_init_value</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.util.Enumeration elements()">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="183">
				<variables></variables>
				<inductives></inductives>
				<constraints>
				<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="183">
				<variables></variables>
				<inductives></inductives>
				<callee>em3d.Node$1$Enumerate: void &lt;init&gt;(em3d.Node)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;(int)">
			<relevant-parameters>degree_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="58">
				<variables>degree, degree_init</variables>
				<inductives>degree, degree_init</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[degree ==degree_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="60">
				<variables>degree, degree_init</variables>
				<inductives>degree, degree_init</inductives>
				<callee>java.util.Random: double nextDouble()</callee>
				<constraints>
					<![CDATA[degree ==degree_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="62">
				<variables>__i0, degree_init, aux_1</variables>
				<inductives>__i0, degree_init, aux_1</inductives>
				<constraints>
					<![CDATA[__i0 ==degree_init && 1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void initSeed(long)">
			<relevant-parameters>l_seed_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="51">
				<variables>l_seed_init</variables>
				<inductives>l_seed_init</inductives>
				<constraints>
					<![CDATA[l_seed_init == 783]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="51">
				<variables>l_seed_init</variables>
				<inductives>l_seed_init</inductives>
				<callee>java.util.Random: void &lt;init&gt;(long)</callee>
				<constraints>
					<![CDATA[l_seed_init == 783]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void makeFromNodes()">
			<relevant-parameters>_f_this_init_fromCount</relevant-parameters>
			 <creation-site offset="0" srccode-offset="137">
				<variables>__i0, _f_this_init_fromCount, aux_1</variables>
				<inductives>__i0, _f_this_init_fromCount, aux_1</inductives>
				<constraints>
					<![CDATA[__i0 ==_f_this_init_fromCount && __i0 >= 0 && 1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="138">
				<variables>__i0, _f_this_init_fromCount, aux_1</variables>
				<inductives>__i0, _f_this_init_fromCount, aux_1</inductives>
				<constraints>
					<![CDATA[__i0 ==_f_this_init_fromCount && __i0 >= 0 && 1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void makeUniqueNeighbors(em3d.Node[])">
			<relevant-parameters>size_f_this_init_toNodes, size_nodeTable_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="108">
				<variables>filled, size_nodeTable, size_f_this_init_toNodes, size_nodeTable_init</variables>
				<inductives>filled, size_nodeTable, size_f_this_init_toNodes, size_nodeTable_init</inductives>
				<callee>java.util.Random: int nextInt()</callee>
				<constraints>
					<![CDATA[size_nodeTable ==size_nodeTable_init && filled >= 0 && filled <size_nodeTable && filled <size_f_this_init_toNodes && size_nodeTable >size_f_this_init_toNodes]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void updateFromNodes()">
			<relevant-parameters>size_f_this_init_coeffs, size_f_this_init_fromNodes, _f_this_init_fromLength, size_f_this_init_toNodes</relevant-parameters>
			 <call-site offset="0" srccode-offset="151">
				<variables>i, size_f_this_init_coeffs, size_f_this_init_fromNodes, _f_this_init_fromLength, size_f_this_init_toNodes</variables>
				<inductives>i, size_f_this_init_coeffs, size_f_this_init_fromNodes, _f_this_init_fromLength, size_f_this_init_toNodes</inductives>
				<callee>java.util.Random: double nextDouble()</callee>
				<constraints>
					<![CDATA[size_f_this_init_coeffs ==size_f_this_init_fromNodes && i >= 0 && size_f_this_init_coeffs >= 0 && _f_this_init_fromLength >= 0 && i <size_f_this_init_toNodes && size_f_this_init_coeffs >=_f_this_init_fromLength]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
