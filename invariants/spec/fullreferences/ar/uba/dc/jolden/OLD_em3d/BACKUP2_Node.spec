<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.em3d.Node">
		 <method decl="ar.uba.dc.jolden.em3d.Node[] fillTable(int,int)">
			<relevant-parameters>size_init, degree_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="100">
				<variables>size, degree, __i0, size_init, degree_init, aux_1</variables>
				<inductives>size, degree, __i0, size_init, degree_init, aux_1</inductives>
				<constraints>
					<![CDATA[size ==__i0 && size ==size_init && degree ==degree_init && size == 20 && degree == 5 && 1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="102">
				<variables>size, size_table, degree, size_init, degree_init</variables>
				<inductives>size, size_table, degree, size_init, degree_init</inductives>
				<constraints>
					<![CDATA[size ==size_table && size ==size_init && degree ==degree_init && size == 20 && degree == 5]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="102">
				<variables>degree, size, size_table, __i1, size_init, degree_init</variables>
				<inductives>degree, size, size_table, __i1, size_init, degree_init, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[degree ==__i1 && degree ==degree_init && size ==size_table && size ==size_init && degree == 5 && size == 20]]>
				</constraints>
				<binding>$t.degree_init == __i1</binding>
			 </call-site>
			 <creation-site offset="2" srccode-offset="105">
				<variables>size_table, degree, size, i, size_init, degree_init</variables>
				<inductives>size_table, degree, size, i, size_init, degree_init</inductives>
				<constraints>
					<![CDATA[size_table ==size && size_table ==size_init && degree ==degree_init && size_table == 20 && degree == 5 && size_table >i && i >= 1 ]]> <!-- Agregue un i >= 1; daikon no se dio cuenta -->
				</constraints> <!-- specInvariantsWriter me devuelve size_table ==size,size_table ==size_init,degree ==degree_init,size_table == 200,degree == 100,size_table >i, asi que no hay un bug con mi cambio -->
			 </creation-site>
			 <call-site offset="1" srccode-offset="105">
				<variables>size_table, degree, size, i, __i1, size_init, degree_init</variables>
				<inductives>size_table, degree, size, i, __i1, size_init, degree_init, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[size_table ==size && size_table ==size_init && degree ==__i1 && degree ==degree_init && size_table == 20 && degree == 5 && size_table >i && i>=1]]> <!-- aca tambien agregue i>= 1 -->
				</constraints>
				<binding>$t.degree_init == __i1</binding>
			 </call-site>
		</method>
		 <method decl="java.util.Enumeration elements()">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="238">
				<variables></variables>
				<inductives></inductives>
			 </creation-site>
			 <call-site offset="0" srccode-offset="238">
				<variables></variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node$1Enumerate: void &lt;init&gt;(ar.uba.dc.jolden.em3d.Node)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;(int)">
			<relevant-parameters>degree_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="69">
				<variables>degree, degree_init</variables>
				<inductives>degree, degree_init</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[degree ==degree_init && degree == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="71">
				<variables>degree, degree_init</variables>
				<inductives>degree, degree_init</inductives>
				<callee>java.util.Random: double nextDouble()</callee>
				<constraints>
					<![CDATA[degree ==degree_init && degree == 5]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="73">
				<variables>__i0, degree_init, aux_1</variables>
				<inductives>__i0, degree_init, aux_1</inductives>
				<constraints>
					<![CDATA[__i0 ==degree_init && __i0 == 5 && 1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void initSeed(long)">
			<relevant-parameters>l_seed_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="54">
				<variables>l_seed, l_seed_init</variables>
				<inductives>l_seed, l_seed_init</inductives>
				<constraints>
					<![CDATA[l_seed ==l_seed_init && l_seed == 783]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="54">
				<variables>l___l0, l_seed_init</variables>
				<inductives>l___l0, l_seed_init</inductives>
				<callee>java.util.Random: void &lt;init&gt;(long)</callee>
				<constraints>
					<![CDATA[l___l0 ==l_seed_init && l___l0 == 783]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void makeFromNodes()">
			<relevant-parameters>_f_this_init_fromCount</relevant-parameters>
			 <creation-site offset="0" srccode-offset="182">
				<variables>_f_this_fromCount, __i0, _f_this_init_fromCount, aux_1</variables>
				<inductives>_f_this_fromCount, __i0, _f_this_init_fromCount, aux_1</inductives>
				<constraints>
					<![CDATA[_f_this_fromCount ==__i0 && _f_this_fromCount ==_f_this_init_fromCount && 1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="183">
				<variables>__i0, _f_this_init_fromCount, aux_1</variables>
				<inductives>__i0, _f_this_init_fromCount, aux_1</inductives>
				<constraints>
					<![CDATA[__i0 ==_f_this_init_fromCount && 1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void makeUniqueNeighbors(ar.uba.dc.jolden.em3d.Node[])">
			<relevant-parameters>size_f_this_init_toNodes, size_nodeTable_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="145">
				<variables>size_f_this_toNodes, filled, size_nodeTable, _f___r3_fromCount, size_f_this_init_toNodes, size_nodeTable_init</variables>
				<inductives>size_f_this_toNodes, filled, size_nodeTable, _f___r3_fromCount, size_f_this_init_toNodes, size_nodeTable_init</inductives>
				<callee>java.util.Random: int nextInt()</callee>
				<constraints>
					<![CDATA[size_f_this_toNodes ==size_f_this_init_toNodes && size_nodeTable ==size_nodeTable_init && size_f_this_toNodes == 5 && filled >= 0 && size_nodeTable == 20 && _f___r3_fromCount >= 0 && size_f_this_toNodes >filled && filled <size_nodeTable && size_nodeTable >_f___r3_fromCount]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void updateFromNodes()">
			<relevant-parameters>size_f_this_init_coeffs, size_f_this_init_fromNodes, _f_this_init_fromLength, size_f_this_init_toNodes</relevant-parameters>
			 <call-site offset="0" srccode-offset="199">
				<variables>size_f_this_coeffs, size_f_this_fromNodes, _f_this_fromLength, size_f_this_toNodes, i, size___r4, __i1, size_f_this_init_coeffs, size_f_this_init_fromNodes, _f_this_init_fromLength, size_f_this_init_toNodes</variables>
				<inductives>size_f_this_coeffs, size_f_this_fromNodes, _f_this_fromLength, size_f_this_toNodes, i, size___r4, __i1, size_f_this_init_coeffs, size_f_this_init_fromNodes, _f_this_init_fromLength, size_f_this_init_toNodes</inductives>
				<callee>java.util.Random: double nextDouble()</callee>
				<constraints>
					<![CDATA[size_f_this_coeffs ==size_f_this_fromNodes && size_f_this_coeffs ==size_f_this_init_coeffs && size_f_this_coeffs ==size_f_this_init_fromNodes && _f_this_fromLength ==_f_this_init_fromLength && size_f_this_toNodes ==size_f_this_init_toNodes && size_f_this_coeffs >= 1 && _f_this_fromLength >= 0 && size_f_this_toNodes == 5 && i >= 0 && __i1 >= 0 && size_f_this_coeffs >=_f_this_fromLength && size_f_this_toNodes >i && size___r4 >__i1]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
