<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.em3d.BiGraph">
		 <method decl="ar.uba.dc.jolden.em3d.BiGraph create(int,int,boolean)">
			<relevant-parameters>numNodes_init, numDegree_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="56">
				<variables>numDegree, numNodes, l___l0, numNodes_init, numDegree_init</variables>
				<inductives>numDegree, numNodes, l___l0, numNodes_init, numDegree_init, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void initSeed(long)</callee>
				<constraints>
					<![CDATA[numDegree ==numDegree_init && numNodes ==numNodes_init && numDegree == 5 && numNodes == 20 && l___l0 == 783]]>
				</constraints>
				<binding>$t.l_seed_init == l___l0</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="59">
				<variables>numDegree, numNodes, size___r0, numNodes_init, numDegree_init</variables>
				<inductives>numDegree, numNodes, size___r0, numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="60">
				<variables>numDegree, numNodes, __i1, __i2, numNodes_init, numDegree_init</variables>
				<inductives>numDegree, numNodes, __i1, __i2, numNodes_init, numDegree_init, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: ar.uba.dc.jolden.em3d.Node[] fillTable(int,int)</callee>
				<constraints>
					<![CDATA[numDegree ==__i2 && numDegree ==numDegree_init && numNodes ==__i1 && numNodes ==numNodes_init && numDegree == 5 && numNodes == 20]]>
				</constraints>
				<binding>$t.size_init == __i1 and $t.degree_init == __i2</binding>
			 </call-site>
			 <call-site offset="3" srccode-offset="61">
				<variables>size_hTable, __i1, __i2, numNodes_init, numDegree_init</variables>
				<inductives>size_hTable, __i1, __i2, numNodes_init, numDegree_init, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: ar.uba.dc.jolden.em3d.Node[] fillTable(int,int)</callee>
				<constraints>
					<![CDATA[size_hTable ==__i1 && size_hTable ==numNodes_init && __i2 ==numDegree_init && size_hTable == 20 && __i2 == 5]]>
				</constraints>
				<binding>$t.size_init == __i1 and $t.degree_init == __i2</binding>
			 </call-site>
			 <call-site offset="4" srccode-offset="67">
				<variables>size_hTable, size_eTable, size___r0, numNodes_init, numDegree_init</variables>
				<inductives>size_hTable, size_eTable, size___r0, numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="68">
				<variables>size_hTable, size_eTable, numNodes_init, numDegree_init</variables>
				<inductives>size_hTable, size_eTable, numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[size_hTable ==size_eTable && size_hTable ==numNodes_init && size_hTable == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="69">
				<variables>size_hTable, size_eTable, numNodes_init, numDegree_init</variables>
				<inductives>size_hTable, size_eTable, numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[size_hTable ==size_eTable && size_hTable ==numNodes_init && size_hTable == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="70">
				<variables>size_hTable, size_eTable, numNodes_init, numDegree_init</variables>
				<inductives>size_hTable, size_eTable, numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[size_hTable ==size_eTable && size_hTable ==numNodes_init && size_hTable == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="71">
				<variables>size_hTable, size_eTable, size_f___r5_toNodes, size___r8, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>size_hTable, size_eTable, size_f___r5_toNodes, size___r8, numNodes_init, numDegree_init, cont___r6, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void makeUniqueNeighbors(ar.uba.dc.jolden.em3d.Node[])</callee>
				<constraints>
					<![CDATA[size_hTable ==size_eTable && size_hTable ==size___r8 && size_hTable ==numNodes_init && size_f___r5_toNodes ==numDegree_init && size_hTable == 20 && size_f___r5_toNodes == 5 && cont___r6 == 1]]>
				</constraints>
				<binding>$t.size_f_this_init_toNodes == size_f___r5_toNodes and $t.size_nodeTable_init == size___r8</binding>
			 </call-site>
			 <call-site offset="9" srccode-offset="73">
				<variables>size_hTable, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>size_hTable, numNodes_init, numDegree_init, cont___r6</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[size_hTable ==numNodes_init && size_hTable == 20 && numDegree_init == 5 && cont___r6 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="74">
				<variables>size_hTable, numNodes_init, numDegree_init</variables>
				<inductives>size_hTable, numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[size_hTable ==numNodes_init && size_hTable == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="75">
				<variables>size_hTable, numNodes_init, numDegree_init</variables>
				<inductives>size_hTable, numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[size_hTable ==numNodes_init && size_hTable == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="76">
				<variables>size_hTable, size_f___r5_toNodes, size___r8, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>size_hTable, size_f___r5_toNodes, size___r8, numNodes_init, numDegree_init, cont___r6, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void makeUniqueNeighbors(ar.uba.dc.jolden.em3d.Node[])</callee>
				<constraints>
					<![CDATA[size_hTable ==size___r8 && size_hTable ==numNodes_init && size_f___r5_toNodes ==numDegree_init && size_hTable == 20 && size_f___r5_toNodes == 5 && cont___r6 == 1]]>
				</constraints>
				<binding>$t.size_f_this_init_toNodes == size_f___r5_toNodes and $t.size_nodeTable_init == size___r8</binding>
			 </call-site>
			 <call-site offset="13" srccode-offset="80">
				<variables>size___r0, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>size___r0, numNodes_init, numDegree_init, cont___r6</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="81">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init, cont___r6</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5 && cont___r6 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="82">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="83">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="84">
				<variables>_f___r5_fromCount, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>_f___r5_fromCount, numNodes_init, numDegree_init, cont___r6, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void makeFromNodes()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5 && cont___r6 == 1 && _f___r5_fromCount <numNodes_init && _f___r5_fromCount >=cont___r6]]>
				</constraints>
				<binding>$t._f_this_init_fromCount == _f___r5_fromCount</binding>
			 </call-site>
			 <call-site offset="18" srccode-offset="86">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init, cont___r6</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5 && cont___r6 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="21" srccode-offset="87">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="19" srccode-offset="88">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="89">
				<variables>_f___r5_fromCount, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>_f___r5_fromCount, numNodes_init, numDegree_init, cont___r6, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void makeFromNodes()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5 && cont___r6 == 1 && _f___r5_fromCount <numNodes_init && _f___r5_fromCount >cont___r6]]>
				</constraints>
				<binding>$t._f_this_init_fromCount == _f___r5_fromCount</binding>
			 </call-site>
			 <call-site offset="22" srccode-offset="93">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init, cont___r6</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5 && cont___r6 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="25" srccode-offset="94">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="23" srccode-offset="95">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="24" srccode-offset="96">
				<variables>size_f___r5_coeffs, size_f___r5_fromNodes, _f___r5_fromLength, size_f___r5_toNodes, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>size_f___r5_coeffs, size_f___r5_fromNodes, _f___r5_fromLength, size_f___r5_toNodes, numNodes_init, numDegree_init, cont___r6, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void updateFromNodes()</callee>
				<constraints>
					<![CDATA[size_f___r5_coeffs ==size_f___r5_fromNodes && size_f___r5_toNodes ==numDegree_init && _f___r5_fromLength == 0 && size_f___r5_toNodes == 5 && numNodes_init == 20 && cont___r6 == 1 && size_f___r5_coeffs >_f___r5_fromLength && size_f___r5_coeffs <numNodes_init && size_f___r5_coeffs >=cont___r6]]>
				</constraints>
				<binding>$t.size_f_this_init_coeffs == size_f___r5_coeffs and $t.size_f_this_init_fromNodes == size_f___r5_fromNodes and $t._f_this_init_fromLength == _f___r5_fromLength and $t.size_f_this_init_toNodes == size_f___r5_toNodes</binding>
			 </call-site>
			 <call-site offset="26" srccode-offset="98">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init, cont___r6</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5 && cont___r6 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="29" srccode-offset="99">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="27" srccode-offset="100">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5]]>
				</constraints>
			 </call-site>
			 <call-site offset="28" srccode-offset="101">
				<variables>size_f___r5_coeffs, size_f___r5_fromNodes, _f___r5_fromLength, size_f___r5_toNodes, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>size_f___r5_coeffs, size_f___r5_fromNodes, _f___r5_fromLength, size_f___r5_toNodes, numNodes_init, numDegree_init, cont___r6, </inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void updateFromNodes()</callee>
				<constraints>
					<![CDATA[size_f___r5_coeffs ==size_f___r5_fromNodes && size_f___r5_coeffs ==_f___r5_fromLength && size_f___r5_toNodes ==numDegree_init && size_f___r5_toNodes == 5 && numNodes_init == 20 && cont___r6 == 1 && size_f___r5_coeffs <numNodes_init && size_f___r5_coeffs >cont___r6]]>
				</constraints>
				<binding>$t.size_f_this_init_coeffs == size_f___r5_coeffs and $t.size_f_this_init_fromNodes == size_f___r5_fromNodes and $t._f_this_init_fromLength == _f___r5_fromLength and $t.size_f_this_init_toNodes == size_f___r5_toNodes</binding>
			 </call-site>
			 <creation-site offset="0" srccode-offset="105">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init, cont___r6</inductives>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5 && cont___r6 == 0]]>
				</constraints>
			 </creation-site>
			 <call-site offset="30" srccode-offset="105">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init, cont___r6</inductives>
				<callee>ar.uba.dc.jolden.em3d.BiGraph: void &lt;init&gt;(ar.uba.dc.jolden.em3d.Node,ar.uba.dc.jolden.em3d.Node)</callee>
				<constraints>
					<![CDATA[numNodes_init == 20 && numDegree_init == 5 && cont___r6 == 0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;(ar.uba.dc.jolden.em3d.Node,ar.uba.dc.jolden.em3d.Node)">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="30">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void compute()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="119">
				<variables></variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="119">
				<variables></variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="120">
				<variables></variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="121">
				<variables>size_f___r1_fromNodes, size_f___r1_coeffs, d_f___r1_value, _f___r1_fromCount, cont___r2</variables>
				<inductives>size_f___r1_fromNodes, size_f___r1_coeffs, d_f___r1_value, _f___r1_fromCount, cont___r2</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void computeNewValue()</callee>
				<constraints>
					<![CDATA[size_f___r1_fromNodes ==size_f___r1_coeffs && size_f___r1_fromNodes ==_f___r1_fromCount && d_f___r1_value == 0 && cont___r2 == 1 && size_f___r1_fromNodes >d_f___r1_value && size_f___r1_fromNodes >cont___r2]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="123">
				<variables>cont___r2</variables>
				<inductives>cont___r2</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[cont___r2 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="123">
				<variables></variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="124">
				<variables></variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="125">
				<variables>size_f___r1_fromNodes, size_f___r1_coeffs, d_f___r1_value, _f___r1_fromCount, cont___r2</variables>
				<inductives>size_f___r1_fromNodes, size_f___r1_coeffs, d_f___r1_value, _f___r1_fromCount, cont___r2</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void computeNewValue()</callee>
				<constraints>
					<![CDATA[size_f___r1_fromNodes ==size_f___r1_coeffs && size_f___r1_fromNodes ==_f___r1_fromCount && d_f___r1_value == 0 && cont___r2 == 1 && size_f___r1_fromNodes >d_f___r1_value && size_f___r1_fromNodes >=cont___r2]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
