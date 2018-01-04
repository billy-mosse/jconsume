<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.em3d.BiGraph">
		 <method decl="ar.uba.dc.jolden.em3d.BiGraph create(int,int,boolean)">
			<relevant-parameters>numNodes_init, numDegree_init</relevant-parameters>
			 <call-site offset="28" srccode-offset="10000">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7, cont___r8, cont___r9</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numNodes_init == cont___r7 && numNodes_init == cont___r8 && numDegree_init == numDegree && cont___r9 >= 1 && numNodes_init > numDegree_init && numNodes_init >= cont___r9]]>
				</constraints>
			 </call-site>
			 <call-site offset="29" srccode-offset="10100">
				<variables>__r31, __r29, n1, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7, cont___r8, cont___r9</variables>
				<inductives>n1__f__toNodes</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void updateFromNodes()</callee>
				<constraints>
					<![CDATA[n1__f__next__f__fromCount == n1__f__next__f__fromLength && n1__f__fromCount == n1__f__fromLength && numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numNodes_init == cont___r7 && numNodes_init == cont___r8 && numDegree_init == numDegree && cont___r9 >= 1 && n1__f__next__f__fromCount < numNodes_init && n1__f__fromCount < numNodes_init && numNodes_init > numDegree_init && numNodes_init >= cont___r9]]>
				</constraints>
				<binding>$t.this_init__f__toNodes == n1__f__toNodes</binding>
			 </call-site>
			 <creation-site offset="0" srccode-offset="10501">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7, cont___r8, cont___r9</variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numNodes_init == cont___r7 && numNodes_init == cont___r8 && numNodes_init == cont___r9 && numDegree_init == numDegree && numNodes_init > numDegree_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="30" srccode-offset="10502">
				<variables>__r31, __r29, __r10, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7, cont___r8, cont___r9</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.BiGraph: void &lt;init&gt;(ar.uba.dc.jolden.em3d.Node,ar.uba.dc.jolden.em3d.Node)</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numNodes_init == cont___r7 && numNodes_init == cont___r8 && numNodes_init == cont___r9 && numDegree_init == numDegree && numNodes_init > numDegree_init]]>
				</constraints>
				<binding>$t.e_init == __r31 and $t.h_init == __r29</binding>
			 </call-site>
			 <call-site offset="0" srccode-offset="5600">
				<variables>numNodes, numDegree, aux_25, numNodes_init, numDegree_init</variables>
				<inductives>numNodes, numDegree</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void initSeed(long)</callee>
				<constraints>
					<![CDATA[numNodes == numNodes_init && numDegree == numDegree_init && l_aux_25 == 783 && numNodes > numDegree && numNodes < l_aux_25 && numDegree < l_aux_25]]>
				</constraints>
				<binding>$t.seed_init == aux_25</binding>
			 </call-site>
			 <call-site offset="2" srccode-offset="6000">
				<variables>numNodes, numDegree, numNodes_init, numDegree_init</variables>
				<inductives>numNodes, numDegree</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: ar.uba.dc.jolden.em3d.Node[] fillTable(int,int)</callee>
				<constraints>
					<![CDATA[numNodes == numNodes_init && numDegree == numDegree_init && numNodes > numDegree]]>
				</constraints>
				<binding>$t.size_init == numNodes and $t.degree_init == numDegree</binding>
			 </call-site>
			 <call-site offset="3" srccode-offset="6100">
				<variables>__r0, numNodes, numDegree, numNodes_init, numDegree_init</variables>
				<inductives>__r0, numNodes, numDegree</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: ar.uba.dc.jolden.em3d.Node[] fillTable(int,int)</callee>
				<constraints>
					<![CDATA[__r0__f__fromCount == __r0__f__fromLength && numNodes == numNodes_init && numDegree == numDegree_init && numNodes > numDegree]]>
				</constraints>
				<binding>$t.size_init == numNodes and $t.degree_init == numDegree</binding>
			 </call-site>
			 <call-site offset="5" srccode-offset="6800">
				<variables>__r31, __r29, __r0, __r1, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2</variables>
				<inductives>__r0, __r1</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[__r0__f__fromNodes == __r1__f__fromNodes && __r0__f__coeffs == __r1__f__coeffs && __r0__f__fromCount == __r0__f__fromLength && __r0__f__fromCount == __r1__f__fromCount && __r0__f__fromCount == __r1__f__fromLength && numNodes_init == numNodes && numDegree_init == numDegree && cont___r2 == 0 && numNodes_init > numDegree_init && numNodes_init > cont___r2 && numDegree_init > cont___r2]]>
				</constraints>
				<binding>$t.this_init == __r29</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="6900">
				<variables>numNodes, numDegree, __r27, aux_26, numNodes_init, numDegree_init</variables>
				<inductives>numNodes, numDegree</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="6901">
				<variables>__r31, __r29, __r0, __r1, __r25, aux_27, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r0, __r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="6902">
				<variables>__r31, __r29, __r0, __r1, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2</variables>
				<inductives>__r0, __r1</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[__r0__f__fromNodes == __r1__f__fromNodes && __r0__f__coeffs == __r1__f__coeffs && __r0__f__fromCount == __r0__f__fromLength && __r0__f__fromCount == __r1__f__fromLength && numNodes_init == numNodes && numDegree_init == numDegree && cont___r2 >= 0 && numNodes_init > numDegree_init && numNodes_init >= cont___r2]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="7000">
				<variables>__r31, __r29, __r0, __r1, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2</variables>
				<inductives>__r0, __r1</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[__r0__f__fromNodes == __r1__f__fromNodes && __r0__f__coeffs == __r1__f__coeffs && __r0__f__fromCount == __r0__f__fromLength && __r0__f__fromCount == __r1__f__fromLength && numNodes_init == numNodes && numDegree_init == numDegree && cont___r2 >= 1 && numNodes_init > numDegree_init && numNodes_init >= cont___r2]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="7100">
				<variables>__r31, __r29, __r0, __r1, n1, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2</variables>
				<inductives>__r0, __r1, n1__f__toNodes</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void makeUniqueNeighbors(ar.uba.dc.jolden.em3d.Node[])</callee>
				<constraints>
					<![CDATA[__r0__f__fromNodes == __r1__f__fromNodes && __r0__f__coeffs == __r1__f__coeffs && __r0__f__fromCount == __r0__f__fromLength && __r0__f__fromCount == __r1__f__fromLength && n1__f__next__f__fromNodes == n1__f__fromNodes && n1__f__next__f__coeffs == n1__f__coeffs && n1__f__next__f__fromCount == n1__f__next__f__fromLength && n1__f__next__f__fromCount == n1__f__fromCount && n1__f__fromCount == n1__f__fromLength && numNodes_init == numNodes && numDegree_init == numDegree && n1__f__next__f__fromCount == 0 && n1__f__fromCount == 0 && cont___r2 >= 1 && n1__f__next__f__fromCount < numNodes_init && n1__f__next__f__fromCount < numDegree_init && n1__f__next__f__fromCount < cont___r2 && n1__f__fromCount < numNodes_init && n1__f__fromCount < numDegree_init && n1__f__fromCount < cont___r2 && numNodes_init > numDegree_init && numNodes_init >= cont___r2]]>
				</constraints>
				<binding>$t.this_init__f__toNodes == n1__f__toNodes and $t.size_nodeTable_init == size___r1</binding>
			 </call-site>
			 <call-site offset="9" srccode-offset="7300">
				<variables>__r31, __r29, __r0, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4</variables>
				<inductives>__r0</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[__r0__f__fromCount == __r0__f__fromLength && numNodes_init == numNodes && numNodes_init == cont___r2 && numDegree_init == numDegree && cont___r4 == 0 && numNodes_init > numDegree_init && numNodes_init > cont___r4 && numDegree_init > cont___r4]]>
				</constraints>
				<binding>$t.this_init == __r31</binding>
			 </call-site>
			 <call-site offset="10" srccode-offset="7400">
				<variables>__r31, __r29, __r0, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4</variables>
				<inductives>__r0</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numDegree_init == numDegree && cont___r4 >= 0 && numNodes_init > numDegree_init && numNodes_init >= cont___r4]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="7500">
				<variables>__r31, __r29, __r0, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4</variables>
				<inductives>__r0</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numDegree_init == numDegree && cont___r4 >= 1 && numNodes_init > numDegree_init && numNodes_init >= cont___r4]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="7600">
				<variables>__r31, __r29, __r0, n1, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4</variables>
				<inductives>__r0, n1__f__toNodes</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void makeUniqueNeighbors(ar.uba.dc.jolden.em3d.Node[])</callee>
				<constraints>
					<![CDATA[n1__f__next__f__fromNodes == n1__f__fromNodes && n1__f__next__f__coeffs == n1__f__coeffs && n1__f__next__f__fromLength == n1__f__fromLength && numNodes_init == numNodes && numNodes_init == cont___r2 && numDegree_init == numDegree && n1__f__next__f__fromLength == 0 && n1__f__fromLength == 0 && cont___r4 >= 1 && n1__f__next__f__fromCount >= n1__f__next__f__fromLength && n1__f__next__f__fromCount >= n1__f__fromLength && n1__f__next__f__fromCount < numNodes_init && n1__f__next__f__fromLength <= n1__f__fromCount && n1__f__next__f__fromLength < numNodes_init && n1__f__next__f__fromLength < numDegree_init && n1__f__next__f__fromLength < cont___r4 && n1__f__fromCount >= n1__f__fromLength && n1__f__fromCount < numNodes_init && n1__f__fromLength < numNodes_init && n1__f__fromLength < numDegree_init && n1__f__fromLength < cont___r4 && numNodes_init > numDegree_init && numNodes_init >= cont___r4]]>
				</constraints>
				<binding>$t.this_init__f__toNodes == n1__f__toNodes and $t.size_nodeTable_init == size___r0</binding>
			 </call-site>
			 <call-site offset="14" srccode-offset="8100">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numDegree_init == numDegree && cont___r5 == 0 && numNodes_init > numDegree_init && numNodes_init > cont___r5 && numDegree_init > cont___r5]]>
				</constraints>
				<binding>$t.this_init == __r29</binding>
			 </call-site>
			 <call-site offset="13" srccode-offset="8200">
				<variables>__r31, __r29, __r19, aux_28, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="8201">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numDegree_init == numDegree && cont___r5 >= 0 && numNodes_init > numDegree_init && numNodes_init >= cont___r5]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="8300">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numDegree_init == numDegree && cont___r5 >= 1 && numNodes_init > numDegree_init && numNodes_init >= cont___r5]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="8400">
				<variables>__r31, __r29, n1, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5</variables>
				<inductives>n1__f__fromCount</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void makeFromNodes()</callee>
				<constraints>
					<![CDATA[n1__f__next__f__fromNodes == n1__f__fromNodes && n1__f__next__f__coeffs == n1__f__coeffs && n1__f__next__f__fromLength == n1__f__fromLength && numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numDegree_init == numDegree && n1__f__next__f__fromLength == 0 && n1__f__fromLength == 0 && cont___r5 >= 1 && n1__f__next__f__fromCount >= n1__f__next__f__fromLength && n1__f__next__f__fromCount >= n1__f__fromLength && n1__f__next__f__fromCount < numNodes_init && n1__f__next__f__fromLength <= n1__f__fromCount && n1__f__next__f__fromLength < numNodes_init && n1__f__next__f__fromLength < numDegree_init && n1__f__next__f__fromLength < cont___r5 && n1__f__fromCount >= n1__f__fromLength && n1__f__fromCount < numNodes_init && n1__f__fromLength < numNodes_init && n1__f__fromLength < numDegree_init && n1__f__fromLength < cont___r5 && numNodes_init > numDegree_init && numNodes_init >= cont___r5]]>
				</constraints>
				<binding>$t.this_init__f__fromCount == n1__f__fromCount</binding>
			 </call-site>
			 <call-site offset="18" srccode-offset="8600">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numDegree_init == numDegree && cont___r7 == 0 && numNodes_init > numDegree_init && numNodes_init > cont___r7 && numDegree_init > cont___r7]]>
				</constraints>
				<binding>$t.this_init == __r31</binding>
			 </call-site>
			 <call-site offset="19" srccode-offset="8700">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numDegree_init == numDegree && cont___r7 >= 0 && numNodes_init > numDegree_init && numNodes_init >= cont___r7]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="8800">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numDegree_init == numDegree && cont___r7 >= 1 && numNodes_init > numDegree_init && numNodes_init >= cont___r7]]>
				</constraints>
			 </call-site>
			 <call-site offset="21" srccode-offset="8900">
				<variables>__r31, __r29, n1, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7</variables>
				<inductives>n1__f__fromCount</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void makeFromNodes()</callee>
				<constraints>
					<![CDATA[n1__f__next__f__fromNodes == n1__f__fromNodes && n1__f__next__f__coeffs == n1__f__coeffs && n1__f__next__f__fromLength == n1__f__fromLength && numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numDegree_init == numDegree && n1__f__next__f__fromLength == 0 && n1__f__fromLength == 0 && cont___r7 >= 1 && n1__f__next__f__fromCount >= n1__f__next__f__fromLength && n1__f__next__f__fromCount >= n1__f__fromLength && n1__f__next__f__fromCount < numNodes_init && n1__f__next__f__fromLength <= n1__f__fromCount && n1__f__next__f__fromLength < numNodes_init && n1__f__next__f__fromLength < numDegree_init && n1__f__next__f__fromLength < cont___r7 && n1__f__fromCount >= n1__f__fromLength && n1__f__fromCount < numNodes_init && n1__f__fromLength < numNodes_init && n1__f__fromLength < numDegree_init && n1__f__fromLength < cont___r7 && numNodes_init > numDegree_init && numNodes_init >= cont___r7]]>
				</constraints>
				<binding>$t.this_init__f__fromCount == n1__f__fromCount</binding>
			 </call-site>
			 <call-site offset="22" srccode-offset="9300">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7, cont___r8</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numNodes_init == cont___r7 && numDegree_init == numDegree && cont___r8 == 0 && numNodes_init > numDegree_init && numNodes_init > cont___r8 && numDegree_init > cont___r8]]>
				</constraints>
				<binding>$t.this_init == __r29</binding>
			 </call-site>
			 <call-site offset="23" srccode-offset="9400">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7, cont___r8</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numNodes_init == cont___r7 && numDegree_init == numDegree && cont___r8 >= 0 && numNodes_init > numDegree_init && numNodes_init >= cont___r8]]>
				</constraints>
			 </call-site>
			 <call-site offset="24" srccode-offset="9500">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7, cont___r8</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numNodes_init == cont___r7 && numDegree_init == numDegree && cont___r8 >= 1 && numNodes_init > numDegree_init && numNodes_init >= cont___r8]]>
				</constraints>
			 </call-site>
			 <call-site offset="25" srccode-offset="9600">
				<variables>__r31, __r29, n1, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7, cont___r8</variables>
				<inductives>n1__f__toNodes</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void updateFromNodes()</callee>
				<constraints>
					<![CDATA[n1__f__next__f__fromNodes == n1__f__next__f__fromNodes__f__next && n1__f__next__f__fromNodes__f__value == n1__f__next__f__coeffs && n1__f__next__f__fromNodes__f__toNodes == n1__f__next__f__fromNodes__f__fromNodes && n1__f__next__f__fromNodes__f__fromCount == n1__f__next__f__fromNodes__f__fromLength && n1__f__next__f__fromLength == n1__f__fromLength && n1__f__fromNodes == n1__f__fromNodes__f__next && n1__f__fromNodes__f__value == n1__f__coeffs && n1__f__fromNodes__f__toNodes == n1__f__fromNodes__f__fromNodes && n1__f__fromNodes__f__fromCount == n1__f__fromNodes__f__fromLength && numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numNodes_init == cont___r7 && numDegree_init == numDegree && n1__f__next__f__fromLength == 0 && n1__f__fromLength == 0 && cont___r8 >= 1 && n1__f__next__f__fromCount >= n1__f__next__f__fromLength && n1__f__next__f__fromCount >= n1__f__fromLength && n1__f__next__f__fromCount < numNodes_init && n1__f__next__f__fromLength <= n1__f__fromCount && n1__f__next__f__fromLength < numNodes_init && n1__f__next__f__fromLength < numDegree_init && n1__f__next__f__fromLength < cont___r8 && n1__f__fromCount >= n1__f__fromLength && n1__f__fromCount < numNodes_init && n1__f__fromLength < numNodes_init && n1__f__fromLength < numDegree_init && n1__f__fromLength < cont___r8 && numNodes_init > numDegree_init && numNodes_init >= cont___r8]]>
				</constraints>
				<binding>$t.this_init__f__toNodes == n1__f__toNodes</binding>
			 </call-site>
			 <call-site offset="26" srccode-offset="9800">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7, cont___r8, cont___r9</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numNodes_init == cont___r7 && numNodes_init == cont___r8 && numDegree_init == numDegree && cont___r9 == 0 && numNodes_init > numDegree_init && numNodes_init > cont___r9 && numDegree_init > cont___r9]]>
				</constraints>
				<binding>$t.this_init == __r31</binding>
			 </call-site>
			 <call-site offset="27" srccode-offset="9900">
				<variables>__r31, __r29, numNodes_init, numDegree_init, numNodes, numDegree, cont___r2, cont___r4, cont___r5, cont___r7, cont___r8, cont___r9</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numNodes_init == cont___r2 && numNodes_init == cont___r4 && numNodes_init == cont___r5 && numNodes_init == cont___r7 && numNodes_init == cont___r8 && numDegree_init == numDegree && cont___r9 >= 0 && numNodes_init > numDegree_init && numNodes_init >= cont___r9]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;(ar.uba.dc.jolden.em3d.Node,ar.uba.dc.jolden.em3d.Node)">
			<relevant-parameters>e_init, h_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="3100">
				<variables>h, e, this, e_init, h_init</variables>
				<inductives></inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void compute()">
			<relevant-parameters>this_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="11901">
				<variables>this, __r0, this_init, cont___r1</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[cont___r1 == 0]]>
				</constraints>
				<binding>$t.this_init == __r0</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="11902">
				<variables>this, this_init, cont___r1</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[cont___r1 >= 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="12000">
				<variables>this, this_init, cont___r1</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[cont___r1 >= 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="12100">
				<variables>this, n, this_init, cont___r1</variables>
				<inductives>n__f__fromCount, n__f__fromNodes, n__f__coeffs, n__f__value</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void computeNewValue()</callee>
				<constraints>
					<![CDATA[n__f__next__f__toNodes__f__fromCount == n__f__next__f__toNodes__f__fromLength && n__f__next__f__fromNodes__f__fromCount == n__f__next__f__fromNodes__f__fromLength && n__f__next__f__fromCount == n__f__next__f__fromLength && n__f__toNodes__f__fromCount == n__f__toNodes__f__fromLength && n__f__fromNodes__f__fromCount == n__f__fromNodes__f__fromLength && n__f__fromCount == n__f__fromLength && cont___r1 >= 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="12301">
				<variables>__r2, this_init, this, cont___r1, cont___r3</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[cont___r3 == 0 && cont___r1 > cont___r3]]>
				</constraints>
				<binding>$t.this_init == __r2</binding>
			 </call-site>
			 <call-site offset="5" srccode-offset="12302">
				<variables>this_init, this, cont___r1, cont___r3</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[cont___r3 >= 0 && cont___r1 >= cont___r3]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="12400">
				<variables>this_init, this, cont___r1, cont___r3</variables>
				<inductives></inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[cont___r3 >= 1 && cont___r1 >= cont___r3]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="12500">
				<variables>n, this_init, this, cont___r1, cont___r3</variables>
				<inductives>n__f__fromCount, n__f__fromNodes, n__f__coeffs, n__f__value</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void computeNewValue()</callee>
				<constraints>
					<![CDATA[n__f__next__f__toNodes__f__fromCount == n__f__next__f__toNodes__f__fromLength && n__f__next__f__fromNodes__f__fromCount == n__f__next__f__fromNodes__f__fromLength && n__f__next__f__fromCount == n__f__next__f__fromLength && n__f__toNodes__f__fromCount == n__f__toNodes__f__fromLength && n__f__fromNodes__f__fromCount == n__f__fromNodes__f__fromLength && n__f__fromCount == n__f__fromLength && cont___r3 >= 1 && n__f__next__f__fromCount < cont___r1 && n__f__fromCount < cont___r1 && cont___r1 >= cont___r3]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
