<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.em3d.BiGraph">
		 <method decl="ar.uba.dc.jolden.em3d.BiGraph create(int,int,boolean)">
			<relevant-parameters>numNodes_init, numDegree_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="43">
				<variables>l___l0, numNodes_init, numDegree_init</variables>
				<inductives>l___l0, numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void initSeed(long)</callee>
				<constraints>
					<![CDATA[l___l0 == 783 && l___l0 >numNodes_init && l___l0 >numDegree_init && numNodes_init >numDegree_init]]>
				</constraints>
				<binding>$t.l_seed_init == l___l0</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="48">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="49">
				<variables>__i1, __i2, numNodes_init, numDegree_init</variables>
				<inductives>__i1, __i2, numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: ar.uba.dc.jolden.em3d.Node[] fillTable(int,int)</callee>
				<constraints>
					<![CDATA[__i1 ==numNodes_init && __i2 ==numDegree_init && __i1 >__i2]]>
				</constraints>
				<binding>$t.size_init == __i1 and $t.degree_init == __i2</binding>
			 </call-site>
			 <call-site offset="3" srccode-offset="50">
				<variables>__i1, __i2, numNodes_init, numDegree_init</variables>
				<inductives>__i1, __i2, numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: ar.uba.dc.jolden.em3d.Node[] fillTable(int,int)</callee>
				<constraints>
					<![CDATA[__i1 ==numNodes_init && __i2 ==numDegree_init && __i1 >__i2]]>
				</constraints>
				<binding>$t.size_init == __i1 and $t.degree_init == __i2</binding>
			 </call-site>
			 <call-site offset="4" srccode-offset="56">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="57">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[numNodes_init >numDegree_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="58">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="59">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[cont___r6 >= 1 && numNodes_init >numDegree_init && numNodes_init >=cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="60">
				<variables>size_f___r5_toNodes, size___r8, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>size___r8, numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void makeUniqueNeighbors(ar.uba.dc.jolden.em3d.Node[])</callee>
				<constraints>
					<![CDATA[size_f___r5_toNodes ==numDegree_init && size___r8 ==numNodes_init && cont___r6 >= 1 && size_f___r5_toNodes <size___r8 && size___r8 >=cont___r6]]>
				</constraints>
				<binding>$t.size_f_this_init_toNodes == size_f___r5_toNodes and $t.size_nodeTable_init == size___r8</binding>
			 </call-site>
			 <call-site offset="9" srccode-offset="62">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="63">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="64">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[cont___r6 >= 1 && numNodes_init >numDegree_init && numNodes_init >=cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="65">
				<variables>size_f___r5_toNodes, size___r8, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>size___r8, numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void makeUniqueNeighbors(ar.uba.dc.jolden.em3d.Node[])</callee>
				<constraints>
					<![CDATA[size_f___r5_toNodes ==numDegree_init && size___r8 ==numNodes_init && cont___r6 >= 1 && size_f___r5_toNodes <size___r8 && size___r8 >=cont___r6]]>
				</constraints>
				<binding>$t.size_f_this_init_toNodes == size_f___r5_toNodes and $t.size_nodeTable_init == size___r8</binding>
			 </call-site>
			 <call-site offset="13" srccode-offset="69">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="70">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="71">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="72">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[cont___r6 >= 1 && numNodes_init >numDegree_init && numNodes_init >=cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="73">
				<variables>_f___r5_fromCount, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init, cont___r6</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void makeFromNodes()</callee>
				<constraints>
					<![CDATA[cont___r6 >= 1 && numNodes_init >=cont___r6 && _f___r5_fromCount ==numDegree_init && _f___r5_fromCount >= 0]]>
				</constraints>
				<binding>$t._f_this_init_fromCount == _f___r5_fromCount</binding>
			 </call-site>
			 <call-site offset="18" srccode-offset="75">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="21" srccode-offset="76">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="19" srccode-offset="77">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[cont___r6 >= 1 && numNodes_init >numDegree_init && numNodes_init >=cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="78">
				<variables>_f___r5_fromCount, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init, cont___r6</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void makeFromNodes()</callee>
				<constraints>
					<![CDATA[cont___r6 >= 1 && numNodes_init >=cont___r6 && _f___r5_fromCount >= 0 && _f___r5_fromCount ==numDegree_init]]>
				</constraints>
				<binding>$t._f_this_init_fromCount == _f___r5_fromCount</binding>
			 </call-site>
			 <call-site offset="22" srccode-offset="82">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="25" srccode-offset="83">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="23" srccode-offset="84">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[cont___r6 >= 1 && numNodes_init >numDegree_init && numNodes_init >=cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="24" srccode-offset="85">
				<variables>size_f___r5_coeffs, size_f___r5_fromNodes, _f___r5_fromLength, size_f___r5_toNodes, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void updateFromNodes()</callee>
				<constraints>
					<![CDATA[size_f___r5_coeffs ==size_f___r5_fromNodes && size_f___r5_toNodes ==numDegree_init && size_f___r5_coeffs >= 0 && _f___r5_fromLength == 0 && cont___r6 >= 1 && size_f___r5_coeffs >=_f___r5_fromLength && size_f___r5_coeffs <numNodes_init && _f___r5_fromLength <size_f___r5_toNodes && _f___r5_fromLength <numNodes_init && size_f___r5_toNodes <numNodes_init && numNodes_init >=cont___r6]]>
				</constraints>
				<binding>$t.size_f_this_init_coeffs == size_f___r5_coeffs and $t.size_f_this_init_fromNodes == size_f___r5_fromNodes and $t._f_this_init_fromLength == _f___r5_fromLength and $t.size_f_this_init_toNodes == size_f___r5_toNodes</binding>
			 </call-site>
			 <call-site offset="26" srccode-offset="87">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="29" srccode-offset="88">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="27" srccode-offset="89">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[cont___r6 >= 1 && numNodes_init >numDegree_init && numNodes_init >=cont___r6]]>
				</constraints>
			 </call-site>
			 <call-site offset="28" srccode-offset="90">
				<variables>size_f___r5_coeffs, size_f___r5_fromNodes, _f___r5_fromLength, size_f___r5_toNodes, numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void updateFromNodes()</callee>
				<constraints>
					<![CDATA[size_f___r5_coeffs ==size_f___r5_fromNodes && size_f___r5_coeffs ==_f___r5_fromLength && size_f___r5_toNodes ==numDegree_init && size_f___r5_coeffs >= 0 && cont___r6 >= 1 && size_f___r5_coeffs <numNodes_init && size_f___r5_toNodes <numNodes_init && numNodes_init >=cont___r6]]>
				</constraints>
				<binding>$t.size_f_this_init_coeffs == size_f___r5_coeffs and $t.size_f_this_init_fromNodes == size_f___r5_fromNodes and $t._f_this_init_fromLength == _f___r5_fromLength and $t.size_f_this_init_toNodes == size_f___r5_toNodes</binding>
			 </call-site>
			 <creation-site offset="0" srccode-offset="94">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </creation-site>
			 <call-site offset="30" srccode-offset="94">
				<variables>numNodes_init, numDegree_init, cont___r6</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.BiGraph: void &lt;init&gt;(ar.uba.dc.jolden.em3d.Node,ar.uba.dc.jolden.em3d.Node)</callee>
				<constraints>
					<![CDATA[cont___r6 == 0 && numNodes_init >numDegree_init && numNodes_init >cont___r6 && numDegree_init >cont___r6]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.lang.String toString()">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="120">
				<variables></variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="120">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="121">
				<variables></variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="121">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="122">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="123">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="3" srccode-offset="123">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="123">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="123">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="123">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="123">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="126">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="126">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="127">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="128">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="11" srccode-offset="128">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="128">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="128">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="128">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="128">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="130">
				<variables>cont___r6</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;(ar.uba.dc.jolden.em3d.Node,ar.uba.dc.jolden.em3d.Node)">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="27">
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
			 <call-site offset="0" srccode-offset="104">
				<variables></variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="104">
				<variables>cont___r2</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[cont___r2 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="105">
				<variables>cont___r2</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[cont___r2 == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="106">
				<variables>d_f___r1_value, size_f___r1_fromNodes, size_f___r1_coeffs, _f___r1_fromCount, cont___r2</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void computeNewValue()</callee>
				<constraints>
					<![CDATA[size_f___r1_fromNodes ==size_f___r1_coeffs && size_f___r1_fromNodes ==_f___r1_fromCount && d_f___r1_value == 0 && size_f___r1_fromNodes >= 0 && cont___r2 == 1 && d_f___r1_value <=size_f___r1_fromNodes]]>
				</constraints>
				<binding>$t.d_f_this_init_value == d_f___r1_value and $t.size_f_this_init_fromNodes == size_f___r1_fromNodes and $t.size_f_this_init_coeffs == size_f___r1_coeffs and $t._f_this_init_fromCount == _f___r1_fromCount</binding>
			 </call-site>
			 <call-site offset="4" srccode-offset="108">
				<variables>cont___r2</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[cont___r2 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="108">
				<variables>cont___r2</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[cont___r2 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="109">
				<variables>cont___r2</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[cont___r2 == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="110">
				<variables>d_f___r1_value, size_f___r1_fromNodes, size_f___r1_coeffs, _f___r1_fromCount, cont___r2</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void computeNewValue()</callee>
				<constraints>
					<![CDATA[size_f___r1_fromNodes ==size_f___r1_coeffs && size_f___r1_fromNodes ==_f___r1_fromCount && d_f___r1_value == 0 && size_f___r1_fromNodes >= 0 && cont___r2 == 1 && d_f___r1_value <=size_f___r1_fromNodes]]>
				</constraints>
				<binding>$t.d_f_this_init_value == d_f___r1_value and $t.size_f_this_init_fromNodes == size_f___r1_fromNodes and $t.size_f_this_init_coeffs == size_f___r1_coeffs and $t._f_this_init_fromCount == _f___r1_fromCount</binding>
			 </call-site>
		</method>
	</class>
</spec>
