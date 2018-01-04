<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.mst.MST">
		 <method decl="ar.uba.dc.jolden.mst.BlueReturn BlueRule(ar.uba.dc.jolden.mst.Vertex,ar.uba.dc.jolden.mst.Vertex)">
			<relevant-parameters>inserted_init, vlist_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="12700">
				<variables>inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, inserted_init, vlist_init</variables>
				<inductives>inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, inserted_init, vlist_init</inductives>
				<constraints>
					<![CDATA[__r5 == __r1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="12701">
				<variables>inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, __r0, inserted_init, vlist_init</variables>
				<inductives>inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, __r0, inserted_init, vlist_init</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[__r5 == __r1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="13000">
				<variables>__r0, aux_7, inserted_init, vlist_init, inserted, vlist</variables>
				<inductives>__r0, aux_7, inserted_init, vlist_init, inserted, vlist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setDist(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="13500">
				<variables>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, inserted_init, vlist_init</variables>
				<inductives>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, inserted_init, vlist_init</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setVert(ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[__r5 == __r1]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="13600">
				<variables>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, inserted_init, vlist_init</variables>
				<inductives>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, inserted_init, vlist_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: int mindist()</callee>
				<constraints>
					<![CDATA[__r5 == __r1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="13601">
				<variables>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, __i0, inserted_init, vlist_init</variables>
				<inductives>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, __i0, inserted_init, vlist_init</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setDist(int)</callee>
				<constraints>
					<![CDATA[__r5 == __r1]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="13700">
				<variables>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, inserted_init, vlist_init</variables>
				<inductives>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, inserted_init, vlist_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Hashtable neighbors()</callee>
				<constraints>
					<![CDATA[__r5 == __r1]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="13800">
				<variables>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, inserted_init, vlist_init</variables>
				<inductives>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r1__f__array, __r1__f__size, inserted_init, vlist_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: java.lang.Object get(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.this_init__f__array == __r1__f__array and $t.key_init == inserted</binding>
			 </call-site>
			 <call-site offset="7" srccode-offset="14000">
				<variables>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r13, inserted_init, vlist_init</variables>
				<inductives>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r13, inserted_init, vlist_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="14100">
				<variables>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __i4, inserted_init, vlist_init</variables>
				<inductives>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __i4, inserted_init, vlist_init</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: int dist()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="14200">
				<variables>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __i4, inserted_init, vlist_init</variables>
				<inductives>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __i4, inserted_init, vlist_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void setMindist(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="14300">
				<variables>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __i4, inserted_init, vlist_init</variables>
				<inductives>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __i4, inserted_init, vlist_init</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setDist(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="14600">
				<variables>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r3, aux_8, inserted_init, vlist_init</variables>
				<inductives>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, __r3, aux_8, inserted_init, vlist_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="15000">
				<variables>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, inserted_init, vlist_init</variables>
				<inductives>__r0, prev, inserted, __r5__f__array, __r5__f__size, vlist, inserted_init, vlist_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="23" srccode-offset="15001">
				<variables>__r0, prev, inserted, __r5__f__array, __r5__f__size, tmp, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0, prev, inserted, __r5__f__array, __r5__f__size, tmp, inserted_init, vlist_init, vlist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="15300">
				<variables>__r0, inserted, __r5__f__array, __r5__f__size, tmp, prev, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0, inserted, __r5__f__array, __r5__f__size, tmp, prev, inserted_init, vlist_init, vlist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="15400">
				<variables>__r0, inserted, __r5__f__array, __r5__f__size, tmp, __r12, prev, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0, inserted, __r5__f__array, __r5__f__size, tmp, __r12, prev, inserted_init, vlist_init, vlist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void setNext(ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="15600">
				<variables>__r0, tmp, inserted, __r5__f__array, __r5__f__size, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0, tmp, inserted, __r5__f__array, __r5__f__size, inserted_init, vlist_init, vlist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Hashtable neighbors()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="15700">
				<variables>__r0, tmp, inserted, __r5__f__array, __r5__f__size, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0, tmp, inserted, __r5__f__array, __r5__f__size, inserted_init, vlist_init, vlist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: int mindist()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="15800">
				<variables>__r0, tmp, __i1, inserted, __r5__f__array, __r5__f__size, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0, tmp, __i1, inserted, __r5__f__array, __r5__f__size, inserted_init, vlist_init, vlist</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: java.lang.Object get(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.this_init__f__array == __r5__f__array and $t.key_init == inserted</binding>
			 </call-site>
			 <call-site offset="18" srccode-offset="16000">
				<variables>__r0, inserted, __r5__f__array, __r5__f__size, tmp, __i1, __r11, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0, inserted, __r5__f__array, __r5__f__size, tmp, __i1, __r11, inserted_init, vlist_init, vlist</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="19" srccode-offset="16200">
				<variables>__r0, inserted, __r5__f__array, __r5__f__size, tmp, __i3, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0, inserted, __r5__f__array, __r5__f__size, tmp, __i3, inserted_init, vlist_init, vlist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void setMindist(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="16800">
				<variables>__r0, inserted, __r5__f__array, __r5__f__size, tmp, __i1, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0, inserted, __r5__f__array, __r5__f__size, tmp, __i1, inserted_init, vlist_init, vlist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: int dist()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="21" srccode-offset="16900">
				<variables>__r0, inserted, __r5__f__array, __r5__f__size, tmp, __i1, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0, inserted, __r5__f__array, __r5__f__size, tmp, __i1, inserted_init, vlist_init, vlist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setVert(ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="22" srccode-offset="17000">
				<variables>__r0, inserted, __r5__f__array, __r5__f__size, tmp, __i1, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0, inserted, __r5__f__array, __r5__f__size, tmp, __i1, inserted_init, vlist_init, vlist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setDist(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="ar.uba.dc.jolden.mst.BlueReturn doAllBlueRule(ar.uba.dc.jolden.mst.Vertex)">
			<relevant-parameters>inserted_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="18700">
				<variables>inserted, __r3, inserted_init</variables>
				<inductives>inserted, __r3, inserted_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="18800">
				<variables>inserted, __r1, inserted_init</variables>
				<inductives>inserted, __r1, inserted_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: ar.uba.dc.jolden.mst.BlueReturn BlueRule(ar.uba.dc.jolden.mst.Vertex,ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.inserted_init == inserted and $t.vlist_init == __r1</binding>
			 </call-site>
		</method>
		 <method decl="int computeMST(ar.uba.dc.jolden.mst.Graph,int)">
			<relevant-parameters>graph_init, numvert_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="10300">
				<variables>cost, __r2, numvert, __r0, graph, graph_init, numvert_init</variables>
				<inductives>cost, __r2, numvert, __r0, graph, graph_init, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: ar.uba.dc.jolden.mst.Vertex firstNode()</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && cost == 0 && cost < numvert]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="10400">
				<variables>cost, __r2, inserted, numvert, __r0, graph_init, numvert_init, graph</variables>
				<inductives>cost, __r2, inserted, numvert, __r0, graph_init, numvert_init, graph</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && cost == 0 && cost < numvert]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="11100">
				<variables>cost, numvert#4, __r2, inserted, graph_init, numvert_init, graph, numvert</variables>
				<inductives>cost, numvert#4, __r2, inserted, graph_init, numvert_init, graph, numvert</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: ar.uba.dc.jolden.mst.BlueReturn doAllBlueRule(ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[numvert_init == numvert && cost >= 0 && numvert_4 >= 1 && numvert_4 < numvert_init]]>
				</constraints>
				<binding>$t.inserted_init == inserted</binding>
			 </call-site>
			 <call-site offset="3" srccode-offset="11200">
				<variables>cost, numvert#4, __r2, graph_init, numvert_init, graph, numvert</variables>
				<inductives>cost, numvert#4, __r2, graph_init, numvert_init, graph, numvert</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: ar.uba.dc.jolden.mst.Vertex vert()</callee>
				<constraints>
					<![CDATA[numvert_init == numvert && cost >= 0 && numvert_4 >= 1 && numvert_4 < numvert_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="11300">
				<variables>__r2, inserted, cost, numvert#4, graph_init, numvert_init, graph, numvert</variables>
				<inductives>__r2, inserted, cost, numvert#4, graph_init, numvert_init, graph, numvert</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: int dist()</callee>
				<constraints>
					<![CDATA[numvert_init == numvert && cost >= 0 && numvert_4 >= 1 && numvert_4 < numvert_init]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="3700">
				<variables>size_args, size_args_init</variables>
				<inductives>size_args, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.size_args_init == size_args</binding>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="4800">
				<variables>size_args, size_args_init</variables>
				<inductives>size_args, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void parseCmdLine(java.lang.String[])</callee>
				<constraints>
					<![CDATA[args == args_init && args == args_init]]>
				</constraints>
				<binding>$t.size_args_init == size_args</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="4900">
				<variables>__i0, size_args_init, size_args</variables>
				<inductives>__i0, size_args_init, size_args</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void mainParameters(int,boolean,boolean)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args]]>
				</constraints>
				<binding>$t.pVertices_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int,boolean,boolean)">
			<relevant-parameters>pVertices_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="6500">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, __r28, __r30__f__count, __r30__f__value, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, __r28, __r30__f__count, __r30__f__value, pVertices_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="6501">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, __r28, __r30__f__count, __r30__f__value, __r27, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, __r28, __r30__f__count, __r30__f__value, __r27, pVertices_init</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="6502">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, __r28, __r30__f__count, __r30__f__value, __r27, aux_0, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, __r28, __r30__f__count, __r30__f__value, __r27, aux_0, pVertices_init</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="6503">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, __r28, __r30__f__count, __r30__f__value, __r29, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, __r28, __r30__f__count, __r30__f__value, __r29, pVertices_init</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="6504">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, __r28, __r30__f__count, __r30__f__value, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, __r28, __r30__f__count, __r30__f__value, pVertices_init</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="6505">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, size___r31, __r28, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, size___r31, __r28, pVertices_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="6700">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, pVertices_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r4 && pVertices == pVertices_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="6800">
				<variables>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, pVertices_init</inductives>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r4 && pVertices == pVertices_init && l___l0 > pVertices]]>
				</constraints>
			 </creation-site>
			 <call-site offset="6" srccode-offset="6801">
				<variables>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, __i1, __r0, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, pVertices, __i1, __r0, pVertices_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r4 && pVertices == __i1 && pVertices == pVertices_init && l___l0 > pVertices]]>
				</constraints>
				<binding>$t.numvert_init == __i1</binding>
			 </call-site>
			 <call-site offset="7" srccode-offset="6900">
				<variables>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, __r0, pVertices, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r4__f__count, __r4__f__value, __r0, pVertices, pVertices_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r4 && pVertices == pVertices_init && l___l0 > pVertices]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="7200">
				<variables>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __l2, __r4__f__count, __r4__f__value, __r0, pVertices, __r25, aux_1, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __l2, __r4__f__count, __r4__f__value, __r0, pVertices, __r25, aux_1, pVertices_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="7300">
				<variables>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __l2, __r4__f__count, __r4__f__value, __r0, pVertices, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __l2, __r4__f__count, __r4__f__value, __r0, pVertices, pVertices_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r4 && pVertices == pVertices_init && l___l0 < l___l2 && l___l0 > pVertices && l___l2 > pVertices]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="7400">
				<variables>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r4__f__count, __r4__f__value, __r0, pVertices, pVertices_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r4__f__count, __r4__f__value, __r0, pVertices, pVertices_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: int computeMST(ar.uba.dc.jolden.mst.Graph,int)</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r4 && pVertices == pVertices_init && l___l0 < l___l3 && l___l0 < l___l2 && l___l0 > pVertices && l___l3 >= l___l2 && l___l3 > pVertices && l___l2 > pVertices]]>
				</constraints>
				<binding>$t.graph_init == __r0 and $t.numvert_init == pVertices</binding>
			 </call-site>
			 <call-site offset="11" srccode-offset="7500">
				<variables>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r4__f__count, __r4__f__value, __i4, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r4__f__count, __r4__f__value, __i4, pVertices_init, pVertices</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r4 && pVertices_init == pVertices && l___l0 < l___l3 && l___l0 < l___l2 && l___l0 > __i4 && l___l0 > pVertices_init && l___l3 >= l___l2 && l___l3 > __i4 && l___l3 > pVertices_init && l___l2 > __i4 && l___l2 > pVertices_init && __i4 > pVertices_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="7800">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r2, __r4__f__count, __r4__f__value, __i4, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r2, __r4__f__count, __r4__f__value, __i4, pVertices_init, pVertices</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="12" srccode-offset="7801">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r2, __r4__f__count, __r4__f__value, __i4, __r1, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r2, __r4__f__count, __r4__f__value, __i4, __r1, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="7802">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r2, __r4__f__count, __r4__f__value, __i4, __r1, aux_2, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r2, __r4__f__count, __r4__f__value, __i4, __r1, aux_2, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="7803">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r2, __r4__f__count, __r4__f__value, __i4, __r3, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r2, __r4__f__count, __r4__f__value, __i4, __r3, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="7804">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r2, __r4__f__count, __r4__f__value, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, __r2, __r4__f__count, __r4__f__value, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="7805">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, size___r5, __r2, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r10__f__count, __r10__f__value, __l2, size___r5, __r2, pVertices_init, pVertices</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="8100">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r8, __r10__f__count, __r10__f__value, __l2, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r8, __r10__f__count, __r10__f__value, __l2, pVertices_init, pVertices</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="17" srccode-offset="8101">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r8, __r10__f__count, __r10__f__value, __l2, __r7, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r8, __r10__f__count, __r10__f__value, __l2, __r7, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="18" srccode-offset="8102">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r8, __r10__f__count, __r10__f__value, __l2, __r7, aux_3, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r8, __r10__f__count, __r10__f__value, __l2, __r7, aux_3, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="19" srccode-offset="8103">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r8, __r10__f__count, __r10__f__value, __d1, __r9, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r8, __r10__f__count, __r10__f__value, __d1, __r9, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="8104">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r8, __r10__f__count, __r10__f__value, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, __r8, __r10__f__count, __r10__f__value, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="21" srccode-offset="8105">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, size___r11, __r8, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r15__f__count, __r15__f__value, __l3, size___r11, __r8, pVertices_init, pVertices</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="4" srccode-offset="8200">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r13, __r15__f__count, __r15__f__value, __l3, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r13, __r15__f__count, __r15__f__value, __l3, pVertices_init, pVertices</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="22" srccode-offset="8201">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r13, __r15__f__count, __r15__f__value, __l3, __r12, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r13, __r15__f__count, __r15__f__value, __l3, __r12, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="23" srccode-offset="8202">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r13, __r15__f__count, __r15__f__value, __l3, __r12, aux_4, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r13, __r15__f__count, __r15__f__value, __l3, __r12, aux_4, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="24" srccode-offset="8203">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r13, __r15__f__count, __r15__f__value, __d3, __r14, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r13, __r15__f__count, __r15__f__value, __d3, __r14, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="25" srccode-offset="8204">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, __r13, __r15__f__count, __r15__f__value, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, __r13, __r15__f__count, __r15__f__value, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="26" srccode-offset="8205">
				<variables>__r20__f__count, __r20__f__value, __l5, __l0, size___r16, __r13, pVertices_init, pVertices</variables>
				<inductives>__r20__f__count, __r20__f__value, __l5, __l0, size___r16, __r13, pVertices_init, pVertices</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="5" srccode-offset="8300">
				<variables>__r18, __r20__f__count, __r20__f__value, __l5, __l0, pVertices_init, pVertices</variables>
				<inductives>__r18, __r20__f__count, __r20__f__value, __l5, __l0, pVertices_init, pVertices</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="27" srccode-offset="8301">
				<variables>__r18, __r20__f__count, __r20__f__value, __l5, __l0, __r17, pVertices_init, pVertices</variables>
				<inductives>__r18, __r20__f__count, __r20__f__value, __l5, __l0, __r17, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="28" srccode-offset="8302">
				<variables>__r18, __r20__f__count, __r20__f__value, __l5, __l0, __r17, aux_5, pVertices_init, pVertices</variables>
				<inductives>__r18, __r20__f__count, __r20__f__value, __l5, __l0, __r17, aux_5, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="29" srccode-offset="8303">
				<variables>__r18, __r20__f__count, __r20__f__value, __d5, __r19, pVertices_init, pVertices</variables>
				<inductives>__r18, __r20__f__count, __r20__f__value, __d5, __r19, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="30" srccode-offset="8304">
				<variables>__r18, __r20__f__count, __r20__f__value, pVertices_init, pVertices</variables>
				<inductives>__r18, __r20__f__count, __r20__f__value, pVertices_init, pVertices</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="31" srccode-offset="8305">
				<variables>size___r21, __r18, pVertices_init, pVertices</variables>
				<inductives>size___r21, __r18, pVertices_init, pVertices</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="32" srccode-offset="8600">
				<variables>__r6, aux_6, pVertices_init, pVertices</variables>
				<inductives>__r6, aux_6, pVertices_init, pVertices</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[pVertices_init == pVertices]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void parseCmdLine(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="1" srccode-offset="20700">
				<variables>i, size_args, size_arg, aux_10, size_args_init</variables>
				<inductives>i, size_args, size_arg, aux_10, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[args == args_init && args == args_init && arg == aux_10 && i == 1 && arg__f__toString == aux_10__f__toString]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="20900">
				<variables>size_args, size_arg, i, size_args_init</variables>
				<inductives>size_args, size_arg, i, size_args_init</inductives>
				<constraints>
					<![CDATA[args == args_init && args == args_init && i == 1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="20901">
				<variables>size_args, size_arg, i, size___r6, __r5, size_args_init</variables>
				<inductives>size_args, size_arg, i, size___r6, __r5, size_args_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[args == args_init && args == args_init && i == 2 && arg__f__toString < __r6__f__toString]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="20902">
				<variables>size_args, size_arg, i, __r5, size_args_init</variables>
				<inductives>size_args, size_arg, i, __r5, size_args_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[args == args_init && args == args_init && i == 2]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="21000">
				<variables>size_args_init, size_args</variables>
				<inductives>size_args_init, size_args</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="4" srccode-offset="21001">
				<variables>__r4, aux_11, size_args_init, size_args</variables>
				<inductives>__r4, aux_11, size_args_init, size_args</inductives>
				<callee>java.lang.RuntimeException: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="21100">
				<variables>size_args, size_arg, i, aux_12, size_args_init</variables>
				<inductives>size_args, size_arg, i, aux_12, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="21300">
				<variables>size_args, size_arg, i, aux_13, size_args_init</variables>
				<inductives>size_args, size_arg, i, aux_13, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="21500">
				<variables>size_args, size_arg, i, aux_14, size_args_init</variables>
				<inductives>size_args, size_arg, i, aux_14, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="21600">
				<variables>size_args, size_arg, i, size_args_init</variables>
				<inductives>size_args, size_arg, i, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="0" srccode-offset="22000">
				<variables>size_args, size_arg, i, size___r1, aux_9, size_args_init</variables>
				<inductives>size_args, size_arg, i, size___r1, aux_9, size_args_init</inductives>
				<callee>java.lang.String: boolean startsWith(java.lang.String)</callee>
				<constraints>
					<![CDATA[args == args_init && args == args_init && i == 0 && __r1__f__toString > aux_9__f__toString]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="22001">
				<variables>size_args_init, size_args</variables>
				<inductives>size_args_init, size_args</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void usage()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="23000">
				<variables>__r0, aux_16</variables>
				<inductives>__r0, aux_16</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="23100">
				<variables>__r1, aux_17</variables>
				<inductives>__r1, aux_17</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="23200">
				<variables>__r2, aux_18</variables>
				<inductives>__r2, aux_18</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="23300">
				<variables>__r3, aux_19</variables>
				<inductives>__r3, aux_19</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="23400">
				<variables>__r4, aux_20</variables>
				<inductives>__r4, aux_20</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="23500">
				<variables>aux_21</variables>
				<inductives>aux_21</inductives>
				<callee>java.lang.System: void exit(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
