<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.mst.MST">
		 <method decl="ar.uba.dc.jolden.mst.BlueReturn BlueRule(ar.uba.dc.jolden.mst.Vertex,ar.uba.dc.jolden.mst.Vertex)">
			<relevant-parameters>inserted_init, vlist_init, vlist_init__f__mindist</relevant-parameters>
			 <creation-site offset="0" srccode-offset="12700">
				<variables>inserted, __r5, __r20, __r17, vlist, __r1, inserted_init, vlist_init</variables>
				<inductives></inductives> <!-- REMOVE __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r1__f__size, __r17__f__mindist, __r1__f__array__f__size, vlist__f__mindist -->
				<constraints>
					<![CDATA[__r5 == __r1 && __r20 == __r17 && vlist == vlist_init && vlist__f__mindist == vlist_init__f__mindist && vlist__f__next == vlist_init__f__next && vlist__f__next__f__mindist == vlist_init__f__next__f__mindist && vlist__f__next__f__next == vlist_init__f__next__f__next && vlist__f__next__f__neighbors == vlist_init__f__next__f__neighbors && vlist__f__neighbors == vlist_init__f__neighbors && vlist__f__neighbors__f__array == vlist_init__f__neighbors__f__array && vlist__f__neighbors__f__size == vlist_init__f__neighbors__f__size && vlist__f__neighbors__f__size == vlist__f__neighbors__f__array__f__size && vlist__f__neighbors__f__size == vlist_init__f__neighbors__f__array__f__size && vlist__f__mindist >= vlist__f__neighbors__f__array__f__size-1 && vlist__f__next__f__mindist >= vlist__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="12701">
				<variables>inserted, __r5, __r20, __r17, vlist, __r1, __r0, inserted_init, vlist_init</variables>
				<inductives>__r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r17__f__mindist, __r1__f__array__f__size, __r1__f__size, vlist__f__mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[__r5 == __r1 && __r20 == __r17 && vlist == vlist_init && vlist__f__mindist == vlist_init__f__mindist && vlist__f__next == vlist_init__f__next && vlist__f__next__f__mindist == vlist_init__f__next__f__mindist && vlist__f__next__f__next == vlist_init__f__next__f__next && vlist__f__next__f__neighbors == vlist_init__f__next__f__neighbors && vlist__f__neighbors == vlist_init__f__neighbors && vlist__f__neighbors__f__array == vlist_init__f__neighbors__f__array && vlist__f__neighbors__f__size == vlist_init__f__neighbors__f__size && vlist__f__neighbors__f__size == vlist__f__neighbors__f__array__f__size && vlist__f__neighbors__f__size == vlist_init__f__neighbors__f__array__f__size && vlist__f__mindist >= vlist__f__neighbors__f__array__f__size-1 && vlist__f__next__f__mindist >= vlist__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="13000">
				<variables>__r0, aux_7, inserted_init, vlist_init, inserted, vlist</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setDist(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="13500">
				<variables>__r0, inserted, __r5, __r20, prev, __r17, vlist, __r1, inserted_init, vlist_init</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r17__f__mindist, __r1__f__array__f__size, __r1__f__size, vlist__f__mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setVert(ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[__r0__f__vert == __r20 && __r5 == __r1 && __r20 == __r17 && vlist == vlist_init && vlist__f__mindist == vlist_init__f__mindist && vlist__f__next == vlist_init__f__next && vlist__f__next__f__mindist == vlist_init__f__next__f__mindist && vlist__f__next__f__next == vlist_init__f__next__f__next && vlist__f__next__f__neighbors == vlist_init__f__next__f__neighbors && vlist__f__neighbors == vlist_init__f__neighbors && vlist__f__neighbors__f__array == vlist_init__f__neighbors__f__array && vlist__f__neighbors__f__size == vlist_init__f__neighbors__f__size && vlist__f__neighbors__f__size == vlist__f__neighbors__f__array__f__size && vlist__f__neighbors__f__size == vlist_init__f__neighbors__f__array__f__size && __r0__f__dist == 0 && __r0__f__dist < vlist__f__mindist && __r0__f__dist < vlist__f__next__f__mindist && __r0__f__dist < vlist__f__neighbors__f__array__f__size-1 && vlist__f__mindist >= vlist__f__neighbors__f__array__f__size-1 && vlist__f__next__f__mindist >= vlist__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="13600">
				<variables>__r0, inserted, __r5, __r20, prev, __r17, vlist, __r1, inserted_init, vlist_init</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r17__f__mindist, __r1__f__array__f__size, __r1__f__size, vlist__f__mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: int mindist()</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist && __r0__f__vert__f__mindist == vlist__f__mindist && __r0__f__vert__f__next == vlist__f__next && __r0__f__vert__f__neighbors == vlist__f__neighbors && __r5 == __r1 && __r20 == __r17 && vlist == vlist_init && vlist__f__mindist == vlist_init__f__mindist && vlist__f__next == vlist_init__f__next && vlist__f__next__f__mindist == vlist_init__f__next__f__mindist && vlist__f__next__f__next == vlist_init__f__next__f__next && vlist__f__next__f__neighbors == vlist_init__f__next__f__neighbors && vlist__f__neighbors == vlist_init__f__neighbors && vlist__f__neighbors__f__array == vlist_init__f__neighbors__f__array && vlist__f__neighbors__f__size == vlist_init__f__neighbors__f__size && vlist__f__neighbors__f__size == vlist__f__neighbors__f__array__f__size && vlist__f__neighbors__f__size == vlist_init__f__neighbors__f__array__f__size && __r0__f__dist == 0 && __r0__f__dist < vlist__f__mindist && __r0__f__dist < vlist__f__next__f__mindist && __r0__f__dist < vlist__f__neighbors__f__array__f__size-1 && vlist__f__mindist >= vlist__f__neighbors__f__array__f__size-1 && vlist__f__next__f__mindist >= vlist__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="13601">
				<variables>__r0, inserted, __r5, __r20, prev, __r17, vlist, __r1, __i0, inserted_init, vlist_init</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r17__f__mindist, __r1__f__array__f__size, __r1__f__size, __i0</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setDist(int)</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __i0 && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r5 == __r1 && __r20 == __r17 && __i0 == vlist_init__f__mindist && vlist_init__f__neighbors__f__size == vlist_init__f__neighbors__f__array__f__size && __r0__f__dist == 0 && __r0__f__dist < __i0 && __r0__f__dist < vlist_init__f__next__f__mindist && __r0__f__dist < vlist_init__f__neighbors__f__array__f__size-1 && __i0 >= vlist_init__f__neighbors__f__array__f__size-1 && vlist_init__f__next__f__mindist >= vlist_init__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="13700">
				<variables>__r0, inserted, __r5, __r20, prev, __r17, vlist, __r1, inserted_init, vlist_init</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r17__f__mindist, __r1__f__array__f__size, __r1__f__size</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Hashtable neighbors()</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __r0__f__dist && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r0__f__dist == vlist_init__f__mindist && __r5 == __r1 && __r20 == __r17 && vlist_init__f__neighbors__f__size == vlist_init__f__neighbors__f__array__f__size && __r0__f__dist >= vlist_init__f__neighbors__f__array__f__size-1 && vlist_init__f__next__f__mindist >= vlist_init__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="13800">
				<variables>__r0, inserted, __r5, __r20, prev, __r17, vlist, __r1, inserted_init, vlist_init</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r17__f__mindist, __r1__f__array__f__size, __r1__f__size</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: java.lang.Object get(java.lang.Object)</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __r0__f__dist && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == __r1 && __r0__f__dist == vlist_init__f__mindist && __r20 == __r17 && __r1 == vlist_init__f__neighbors && __r1__f__array == vlist_init__f__neighbors__f__array && __r1__f__size == vlist_init__f__neighbors__f__size && __r1__f__size == __r1__f__array__f__size && __r1__f__size == vlist_init__f__neighbors__f__array__f__size && __r0__f__dist >= __r1__f__array__f__size-1 && vlist_init__f__next__f__mindist >= __r1__f__array__f__size-1]]>
				</constraints>
				<binding>$t.this_init == __r1 and $t.this_init__f__array == __r1__f__array and $t.this_init__f__array__f__size == __r1__f__array__f__size and $t.key_init == inserted</binding>
			 </call-site>
			 <call-site offset="7" srccode-offset="14000">
				<variables>__r0, inserted, __r5, __r20, prev, __r17, vlist, __r13, inserted_init, vlist_init</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r17__f__mindist, __r13__f__value</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __r0__f__dist && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r0__f__dist == vlist_init__f__mindist && __r20 == __r17 && vlist_init__f__neighbors__f__size == vlist_init__f__neighbors__f__array__f__size && __r0__f__dist >= vlist_init__f__neighbors__f__array__f__size-1 && vlist_init__f__next__f__mindist >= vlist_init__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="14100">
				<variables>__r0, inserted, __r5, __r20, prev, __r17, vlist, __i4, inserted_init, vlist_init</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r17__f__mindist, __i4</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: int dist()</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __r0__f__dist && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r0__f__dist == vlist_init__f__mindist && __r20 == __r17 && vlist_init__f__neighbors__f__size == vlist_init__f__neighbors__f__array__f__size && __r0__f__dist >= vlist_init__f__neighbors__f__array__f__size-1 && __i4 > vlist_init__f__neighbors__f__size && vlist_init__f__next__f__mindist >= vlist_init__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="14200">
				<variables>__r0, inserted, __r5, __r20, prev, __r17, vlist, __i4, inserted_init, vlist_init</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r17__f__mindist, __i4</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void setMindist(int)</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __r0__f__dist && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r0__f__dist == vlist_init__f__mindist && __r20 == __r17 && vlist_init__f__neighbors__f__size == vlist_init__f__neighbors__f__array__f__size && vlist_init__f__neighbors__f__size >= 2 && __r0__f__dist > __i4 && __r0__f__dist > vlist_init__f__neighbors__f__size && __i4 > vlist_init__f__neighbors__f__size && vlist_init__f__next__f__mindist >= vlist_init__f__neighbors__f__size]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="14300">
				<variables>__r0, inserted, __r5, __r20, prev, __r17, vlist, __i4, inserted_init, vlist_init</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r17__f__mindist, __i4</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setDist(int)</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __i4 && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r20 == __r17 && __i4 == vlist_init__f__mindist && vlist_init__f__neighbors__f__size == vlist_init__f__neighbors__f__array__f__size && vlist_init__f__neighbors__f__size >= 2 && __r0__f__dist > __i4 && __r0__f__dist > vlist_init__f__neighbors__f__size && __i4 > vlist_init__f__neighbors__f__size && vlist_init__f__next__f__mindist >= vlist_init__f__neighbors__f__size]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="14600">
				<variables>__r0, inserted, __r5, __r20, prev, __r17, vlist, __r3, aux_8, inserted_init, vlist_init</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r17__f__mindist</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="15000">
				<variables>__r0, inserted, __r5, __r20, prev, __r17, vlist, inserted_init, vlist_init</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __r17__f__mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __r0__f__dist && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r0__f__dist == vlist_init__f__mindist && __r20 == __r17 && vlist_init__f__neighbors__f__size == vlist_init__f__neighbors__f__array__f__size && __r0__f__dist >= vlist_init__f__neighbors__f__array__f__size-1 && vlist_init__f__next__f__mindist >= vlist_init__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="23" srccode-offset="15001">
				<variables>__r0, prev, inserted, __r5, __r20, tmp, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __r0__f__dist && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r0__f__dist == vlist_init__f__mindist && __r0__f__dist == vlist__f__mindist && __r5 == __r20__f__neighbors && __r5__f__array == __r20__f__neighbors__f__array && __r5__f__size == vlist_init__f__neighbors__f__size && __r5__f__size == vlist__f__neighbors__f__size && __r5__f__size == __r5__f__array__f__size && __r5__f__size == vlist_init__f__neighbors__f__array__f__size && __r5__f__size == vlist__f__neighbors__f__array__f__size && __r20__f__neighbors__f__size == __r20__f__neighbors__f__array__f__size && vlist_init == vlist && vlist_init__f__next == vlist__f__next && vlist_init__f__next__f__mindist == vlist__f__next__f__mindist && vlist_init__f__next__f__next == vlist__f__next__f__next && vlist_init__f__next__f__neighbors == vlist__f__next__f__neighbors && vlist_init__f__neighbors == vlist__f__neighbors && vlist_init__f__neighbors__f__array == vlist__f__neighbors__f__array && __r0__f__dist >= __r20__f__neighbors__f__size && __r0__f__dist >= __r5__f__array__f__size-1 && __r20__f__mindist >= __r5__f__array__f__size-1 && __r20__f__mindist >= __r20__f__neighbors__f__array__f__size-1 && __r20__f__next__f__mindist >= __r5__f__array__f__size-1 && __r20__f__next__f__mindist >= __r20__f__neighbors__f__array__f__size-1 && vlist_init__f__next__f__mindist >= __r5__f__array__f__size-1 && vlist_init__f__next__f__mindist >= __r20__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="15300">
				<variables>__r0, inserted, __r5, __r20, tmp, prev, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="15400">
				<variables>__r0, inserted, __r5, __r20, tmp, __r12, prev, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void setNext(ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="15600">
				<variables>__r0, inserted, __r5, __r20, tmp, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, tmp__f__mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Hashtable neighbors()</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __r0__f__dist && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r0__f__dist == vlist_init__f__mindist && __r0__f__dist == vlist__f__mindist && __r5__f__size == __r20__f__neighbors__f__size && __r5__f__size == __r5__f__array__f__size && __r5__f__size == __r20__f__neighbors__f__array__f__size && __r20__f__mindist == tmp__f__mindist && __r20__f__next == tmp__f__next && __r20__f__next__f__mindist == tmp__f__next__f__mindist && __r20__f__next__f__next == tmp__f__next__f__next && __r20__f__next__f__neighbors == tmp__f__next__f__neighbors && __r20__f__neighbors == tmp__f__neighbors && __r20__f__neighbors__f__array == tmp__f__neighbors__f__array && tmp__f__neighbors__f__size == vlist_init__f__neighbors__f__size && tmp__f__neighbors__f__size == vlist__f__neighbors__f__size && tmp__f__neighbors__f__size == tmp__f__neighbors__f__array__f__size && tmp__f__neighbors__f__size == vlist_init__f__neighbors__f__array__f__size && tmp__f__neighbors__f__size == vlist__f__neighbors__f__array__f__size && vlist_init == vlist && vlist_init__f__next == vlist__f__next && vlist_init__f__next__f__mindist == vlist__f__next__f__mindist && vlist_init__f__next__f__next == vlist__f__next__f__next && vlist_init__f__next__f__neighbors == vlist__f__next__f__neighbors && vlist_init__f__neighbors == vlist__f__neighbors && vlist_init__f__neighbors__f__array == vlist__f__neighbors__f__array && __r0__f__dist >= __r5__f__size && __r0__f__dist >= tmp__f__neighbors__f__array__f__size-1 && __r20__f__mindist >= __r5__f__array__f__size-1 && __r20__f__mindist >= tmp__f__neighbors__f__array__f__size-1 && __r20__f__next__f__mindist >= __r5__f__array__f__size-1 && __r20__f__next__f__mindist >= tmp__f__neighbors__f__array__f__size-1 && tmp__f__mindist >= __r5__f__array__f__size-1 && tmp__f__mindist >= tmp__f__neighbors__f__array__f__size-1 && tmp__f__next__f__mindist >= __r5__f__array__f__size-1 && tmp__f__next__f__mindist >= tmp__f__neighbors__f__array__f__size-1 && vlist_init__f__next__f__mindist >= __r5__f__array__f__size-1 && vlist_init__f__next__f__mindist >= tmp__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="15700">
				<variables>__r0, inserted, __r5, __r20, tmp, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, tmp__f__mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: int mindist()</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __r0__f__dist && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r0__f__dist == vlist_init__f__mindist && __r0__f__dist == vlist__f__mindist && __r5 == __r20__f__neighbors && __r5 == tmp__f__neighbors && __r5__f__array == __r20__f__neighbors__f__array && __r5__f__array == tmp__f__neighbors__f__array && __r5__f__size == tmp__f__neighbors__f__size && __r5__f__size == vlist_init__f__neighbors__f__size && __r5__f__size == vlist__f__neighbors__f__size && __r5__f__size == __r5__f__array__f__size && __r5__f__size == tmp__f__neighbors__f__array__f__size && __r5__f__size == vlist_init__f__neighbors__f__array__f__size && __r5__f__size == vlist__f__neighbors__f__array__f__size && __r20__f__mindist == tmp__f__mindist && __r20__f__next == tmp__f__next && __r20__f__next__f__mindist == tmp__f__next__f__mindist && __r20__f__next__f__next == tmp__f__next__f__next && __r20__f__next__f__neighbors == tmp__f__next__f__neighbors && __r20__f__neighbors__f__size == __r20__f__neighbors__f__array__f__size && vlist_init == vlist && vlist_init__f__next == vlist__f__next && vlist_init__f__next__f__mindist == vlist__f__next__f__mindist && vlist_init__f__next__f__next == vlist__f__next__f__next && vlist_init__f__next__f__neighbors == vlist__f__next__f__neighbors && vlist_init__f__neighbors == vlist__f__neighbors && vlist_init__f__neighbors__f__array == vlist__f__neighbors__f__array && __r0__f__dist >= __r20__f__neighbors__f__size && __r0__f__dist >= __r5__f__array__f__size-1 && __r20__f__mindist >= __r5__f__array__f__size-1 && __r20__f__mindist >= __r20__f__neighbors__f__array__f__size-1 && __r20__f__next__f__mindist >= __r5__f__array__f__size-1 && __r20__f__next__f__mindist >= __r20__f__neighbors__f__array__f__size-1 && tmp__f__mindist >= __r5__f__array__f__size-1 && tmp__f__mindist >= __r20__f__neighbors__f__array__f__size-1 && tmp__f__next__f__mindist >= __r5__f__array__f__size-1 && tmp__f__next__f__mindist >= __r20__f__neighbors__f__array__f__size-1 && vlist_init__f__next__f__mindist >= __r5__f__array__f__size-1 && vlist_init__f__next__f__mindist >= __r20__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="15800">
				<variables>__r0, inserted, __r5, __r20, tmp, __i1, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __i1</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: java.lang.Object get(java.lang.Object)</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __r0__f__dist && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r0__f__dist == vlist_init__f__mindist && __r0__f__dist == vlist__f__mindist && __r5 == __r20__f__neighbors && __r5__f__array == __r20__f__neighbors__f__array && __r5__f__size == vlist_init__f__neighbors__f__size && __r5__f__size == vlist__f__neighbors__f__size && __r5__f__size == __r5__f__array__f__size && __r5__f__size == vlist_init__f__neighbors__f__array__f__size && __r5__f__size == vlist__f__neighbors__f__array__f__size && __r20__f__mindist == __i1 && __r20__f__neighbors__f__size == __r20__f__neighbors__f__array__f__size && vlist_init == vlist && vlist_init__f__next == vlist__f__next && vlist_init__f__next__f__mindist == vlist__f__next__f__mindist && vlist_init__f__next__f__next == vlist__f__next__f__next && vlist_init__f__next__f__neighbors == vlist__f__next__f__neighbors && vlist_init__f__neighbors == vlist__f__neighbors && vlist_init__f__neighbors__f__array == vlist__f__neighbors__f__array && __r0__f__dist >= __r20__f__neighbors__f__size && __r0__f__dist >= __r5__f__array__f__size-1 && __r20__f__mindist >= __r5__f__array__f__size-1 && __r20__f__mindist >= __r20__f__neighbors__f__array__f__size-1 && __r20__f__next__f__mindist >= __r5__f__array__f__size-1 && __r20__f__next__f__mindist >= __r20__f__neighbors__f__array__f__size-1 && __i1 >= __r5__f__array__f__size-1 && __i1 >= __r20__f__neighbors__f__array__f__size-1 && vlist_init__f__next__f__mindist >= __r5__f__array__f__size-1 && vlist_init__f__next__f__mindist >= __r20__f__neighbors__f__array__f__size-1]]>
				</constraints>
				<binding>$t.this_init == __r5 and $t.this_init__f__array == __r5__f__array and $t.this_init__f__array__f__size == __r5__f__array__f__size and $t.key_init == inserted</binding>
			 </call-site>
			 <call-site offset="18" srccode-offset="16000">
				<variables>__r0, inserted, __r5, __r20, tmp, __i1, __r11, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __i1, __r11__f__value</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __r0__f__dist && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r0__f__dist == vlist_init__f__mindist && __r0__f__dist == vlist__f__mindist && __r5 == __r20__f__neighbors && __r5__f__array == __r20__f__neighbors__f__array && __r5__f__size == vlist_init__f__neighbors__f__size && __r5__f__size == vlist__f__neighbors__f__size && __r5__f__size == __r5__f__array__f__size && __r5__f__size == vlist_init__f__neighbors__f__array__f__size && __r5__f__size == vlist__f__neighbors__f__array__f__size && __r20__f__mindist == __i1 && __r20__f__neighbors__f__size == __r20__f__neighbors__f__array__f__size && vlist_init == vlist && vlist_init__f__next == vlist__f__next && vlist_init__f__next__f__mindist == vlist__f__next__f__mindist && vlist_init__f__next__f__next == vlist__f__next__f__next && vlist_init__f__next__f__neighbors == vlist__f__next__f__neighbors && vlist_init__f__neighbors == vlist__f__neighbors && vlist_init__f__neighbors__f__array == vlist__f__neighbors__f__array && __r0__f__dist >= __r20__f__neighbors__f__size && __r0__f__dist >= __r5__f__array__f__size-1 && __r20__f__mindist >= __r5__f__array__f__size-1 && __r20__f__mindist >= __r20__f__neighbors__f__array__f__size-1 && __r20__f__next__f__mindist >= __r5__f__array__f__size-1 && __r20__f__next__f__mindist >= __r20__f__neighbors__f__array__f__size-1 && __i1 >= __r5__f__array__f__size-1 && __i1 >= __r20__f__neighbors__f__array__f__size-1 && vlist_init__f__next__f__mindist >= __r5__f__array__f__size-1 && vlist_init__f__next__f__mindist >= __r20__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="19" srccode-offset="16200">
				<variables>__r0, inserted, __r5, __r20, tmp, __i3, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __i3</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void setMindist(int)</callee>
				<constraints>
					<![CDATA[__r0__f__vert == vlist_init && __r0__f__vert__f__mindist == __r0__f__dist && __r0__f__vert__f__next == vlist_init__f__next && __r0__f__vert__f__neighbors == vlist_init__f__neighbors && __r0__f__dist == vlist_init__f__mindist && __r0__f__dist == vlist__f__mindist && __r5 == __r20__f__neighbors && __r5__f__array == __r20__f__neighbors__f__array && __r5__f__size == vlist_init__f__neighbors__f__size && __r5__f__size == vlist__f__neighbors__f__size && __r5__f__size == __r5__f__array__f__size && __r5__f__size == vlist_init__f__neighbors__f__array__f__size && __r5__f__size == vlist__f__neighbors__f__array__f__size && __r20__f__neighbors__f__size == __r20__f__neighbors__f__array__f__size && vlist_init == vlist && vlist_init__f__next == vlist__f__next && vlist_init__f__next__f__mindist == vlist__f__next__f__mindist && vlist_init__f__next__f__next == vlist__f__next__f__next && vlist_init__f__next__f__neighbors == vlist__f__next__f__neighbors && vlist_init__f__neighbors == vlist__f__neighbors && vlist_init__f__neighbors__f__array == vlist__f__neighbors__f__array && __r0__f__dist >= __r5__f__size && __r0__f__dist >= __r20__f__neighbors__f__size && __r5__f__size < __r20__f__mindist && __r5__f__size <= vlist_init__f__next__f__mindist && __r20__f__mindist > __r20__f__neighbors__f__size && __r20__f__mindist > __i3 && __r20__f__next__f__mindist >= __r5__f__array__f__size-1 && __r20__f__next__f__mindist >= __r20__f__neighbors__f__array__f__size-1 && __r20__f__neighbors__f__size <= vlist_init__f__next__f__mindist && __i3 >= __r5__f__array__f__size-1 && __i3 >= __r20__f__neighbors__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="16800">
				<variables>__r0, inserted, __r5, __r20, tmp, __i1, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __i1</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: int dist()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="21" srccode-offset="16900">
				<variables>__r0, inserted, __r5, __r20, tmp, __i1, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __i1</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setVert(ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="22" srccode-offset="17000">
				<variables>__r0, inserted, __r5, __r20, tmp, __i1, inserted_init, vlist_init, vlist</variables>
				<inductives>__r0__f__dist, __r5__f__array__f__size, __r5__f__size, __r20__f__mindist, __i1</inductives>
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
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="18800">
				<variables>inserted, __r1, inserted_init</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.mst.MST: ar.uba.dc.jolden.mst.BlueReturn BlueRule(ar.uba.dc.jolden.mst.Vertex,ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.inserted_init == inserted and $t.vlist_init == __r1 and $t.vlist_init__f__mindist == __r1__f__mindist</binding>
			 </call-site>
		</method>
		 <method decl="int computeMST(ar.uba.dc.jolden.mst.Graph,int)">
			<relevant-parameters>graph_init, graph_init__f__nodes, graph_init__f__nodes__f__size, numvert_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="10300">
				<variables>cost, __r2, numvert, graph, graph_init, numvert_init</variables>
				<inductives>cost, numvert, graph__f__nodes__f__size</inductives> <!-- remove __r2__f__dist , __r0-->
				<callee>ar.uba.dc.jolden.mst.Graph: ar.uba.dc.jolden.mst.Vertex firstNode()</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == graph__f__nodes__f__size && numvert == graph_init__f__nodes__f__size && graph == graph_init && graph__f__nodes == graph_init__f__nodes && cost == 0 && cost < graph__f__nodes__f__size-1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="10400">
				<variables>cost, __r2, inserted, numvert, __r0, graph_init, numvert_init, graph</variables>
				<inductives>cost, __r2__f__dist, numvert</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == graph_init__f__nodes__f__size && numvert == graph__f__nodes__f__size && graph_init == graph && graph_init__f__nodes == graph__f__nodes && cost == 0 && cost < graph_init__f__nodes__f__size-1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="11100">
				<variables>cost, numvert_4, __r2, inserted, graph_init, numvert_init, graph, numvert</variables>
				<inductives>numvert_4</inductives> <!-- REMOVE cost, __r2__f__dist -->
				<callee>ar.uba.dc.jolden.mst.MST: ar.uba.dc.jolden.mst.BlueReturn doAllBlueRule(ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[__r2__f__vert__f__mindist == __r2__f__dist && graph_init == graph && graph_init__f__nodes == graph__f__nodes && numvert_init == numvert && numvert_init == graph_init__f__nodes__f__size && numvert_init == graph__f__nodes__f__size && cost >= 0 && numvert_4 >= 1 && cost >= __r2__f__dist && cost < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && cost < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert_4 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert_4 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert_4 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && numvert_4 <= graph_init__f__nodes__f__size-1 && __r2__f__dist < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __r2__f__dist < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __r2__f__dist < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > numvert_init && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > numvert_init && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > numvert_init]]>
				</constraints>
				<binding>$t.inserted_init == inserted</binding>
			 </call-site>
			 <call-site offset="3" srccode-offset="11200">
				<variables>cost, numvert_4, __r2, graph_init, numvert_init, graph, numvert</variables>
				<inductives>cost, numvert_4, __r2__f__dist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: ar.uba.dc.jolden.mst.Vertex vert()</callee>
				<constraints>
					<![CDATA[__r2__f__vert__f__mindist == __r2__f__dist && graph_init == graph && graph_init__f__nodes == graph__f__nodes && numvert_init == numvert && numvert_init == graph_init__f__nodes__f__size && numvert_init == graph__f__nodes__f__size && cost >= 0 && numvert_4 >= 1 && cost < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && cost < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert_4 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert_4 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert_4 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && numvert_4 <= graph_init__f__nodes__f__size-1 && __r2__f__dist < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __r2__f__dist < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __r2__f__dist < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > numvert_init && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > numvert_init && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > numvert_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="11300">
				<variables>__r2, inserted, cost, numvert_4, graph_init, numvert_init, graph, numvert</variables>
				<inductives>__r2__f__dist, cost, numvert_4</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: int dist()</callee>
				<constraints>
					<![CDATA[__r2__f__vert__f__mindist == __r2__f__dist && graph_init == graph && graph_init__f__nodes == graph__f__nodes && numvert_init == numvert && numvert_init == graph_init__f__nodes__f__size && numvert_init == graph__f__nodes__f__size && cost >= 0 && numvert_4 >= 1 && __r2__f__dist < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __r2__f__dist < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __r2__f__dist < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && cost < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && cost < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert_4 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert_4 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert_4 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && numvert_4 <= graph_init__f__nodes__f__size-1 && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > numvert_init && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > numvert_init && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > numvert_init]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="3700">
				<variables>args, args_init</variables>
				<inductives></inductives><!-- args -->
				<callee>ar.uba.dc.jolden.mst.MST: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.args_init == args and $t.args_init == args and $t.args_init == args</binding>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>args_init, args_init, args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="4800">
				<variables>args, args_init</variables>
				<inductives>args</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void parseCmdLine(java.lang.String[])</callee>
				<constraints>
					<![CDATA[args == args_init && args__f__size == 2]]>
				</constraints>
				<binding>$t.args_init == args and $t.args_init == args</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="4900">
				<variables>__i0, args_init, args</variables>
				<inductives></inductives><!-- __i0 -->
				<callee>ar.uba.dc.jolden.mst.MST: void mainParameters(int,boolean,boolean)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init__f__size == 2 && __i0 > args_init__f__size]]>
				</constraints>
				<binding>$t.pVertices_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int,boolean,boolean)">
			<relevant-parameters>pVertices_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="6500">
				<variables>__r16, __r12, __r8, __r3, pVertices, __r24, __r25, pVertices_init</variables>
				<inductives></inductives> <!-- REMOVE __r16__f__count, __r16__f__value__f__size, __r12__f__count, __r12__f__value__f__size, __r8__f__count, __r8__f__value__f__size, __r3__f__count, __r3__f__value__f__size, pVertices, __r25__f__count, __r25__f__value__f__size -->
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="6501">
				<variables>__r16, __r12, __r8, __r3, pVertices, __r24, __r25, __r23, aux_0, pVertices_init</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __r12__f__count, __r12__f__value__f__size, __r8__f__count, __r8__f__value__f__size, __r3__f__count, __r3__f__value__f__size, pVertices, __r25__f__count, __r25__f__value__f__size</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="6502">
				<variables>__r16, __r12, __r8, __r3, pVertices, __r24, __r25, __r23, pVertices_init</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __r12__f__count, __r12__f__value__f__size, __r8__f__count, __r8__f__value__f__size, __r3__f__count, __r3__f__value__f__size, pVertices, __r25__f__count, __r25__f__value__f__size</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="6503">
				<variables>__r16, __r12, __r8, __r3, pVertices, __r24, __r25, pVertices_init</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __r12__f__count, __r12__f__value__f__size, __r8__f__count, __r8__f__value__f__size, __r3__f__count, __r3__f__value__f__size, pVertices, __r25__f__count, __r25__f__value__f__size</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="6504">
				<variables>__r16, __r12, __r8, __r3, pVertices, __r26, __r24, pVertices_init</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __r12__f__count, __r12__f__value__f__size, __r8__f__count, __r8__f__value__f__size, __r3__f__count, __r3__f__value__f__size, pVertices</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="6700">
				<variables>__r16, __r12, __r8, __r3, pVertices, pVertices_init</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __r12__f__count, __r12__f__value__f__size, __r8__f__count, __r8__f__value__f__size, __r3__f__count, __r3__f__value__f__size, pVertices</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r16 == __r12 && __r16 == __r8 && __r16 == __r3 && pVertices == pVertices_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="6800">
				<variables>__r16, __l0, __r12, __r8, __r3, pVertices, pVertices_init</variables>
				<inductives>pVertices</inductives> <!-- __r16__f__count, __r16__f__value__f__size, __l0, __r12__f__count, __r12__f__value__f__size, __r8__f__count, __r8__f__value__f__size, __r3__f__count, __r3__f__value__f__size -->
				<constraints>
					<![CDATA[__r16 == __r12 && __r16 == __r8 && __r16 == __r3 && pVertices == pVertices_init && __l0 > pVertices]]>
				</constraints>
			 </creation-site>
			 <call-site offset="5" srccode-offset="6801">
				<variables>__r16, __l0, __r12, __r8, __r3, pVertices, __i1, __r0, pVertices_init</variables>
				<inductives>__i1, pVertices</inductives><!-- REMOVE __r16__f__count, __r16__f__value__f__size, __l0, __r12__f__count, __r12__f__value__f__size, __r8__f__count, __r8__f__value__f__size, __r3__f__count, __r3__f__value__f__size -->
				<callee>ar.uba.dc.jolden.mst.Graph: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[__r16 == __r12 && __r16 == __r8 && __r16 == __r3 && pVertices == __i1 && pVertices == pVertices_init && __l0 > pVertices]]>
				</constraints>
				<binding>$t.numvert_init == __i1</binding>
			 </call-site>
			 <call-site offset="6" srccode-offset="6900">
				<variables>__r16, __l0, __r12, __r8, __r3, __r0, pVertices, pVertices_init</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l0, __r12__f__count, __r12__f__value__f__size, __r8__f__count, __r8__f__value__f__size, __r3__f__count, __r3__f__value__f__size, pVertices, __r0__f__nodes__f__size</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r16 == __r12 && __r16 == __r8 && __r16 == __r3 && pVertices == pVertices_init && pVertices == __r0__f__nodes__f__size && __l0 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __l0 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __l0 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __l0 > pVertices && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > pVertices && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > pVertices && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > pVertices]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="7200">
				<variables>__r16, __l0, __r12, __r8, __l2, __r3, __r0, pVertices, __r21, aux_1, pVertices_init</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l0, __r12__f__count, __r12__f__value__f__size, __r8__f__count, __r8__f__value__f__size, __l2, __r3__f__count, __r3__f__value__f__size, pVertices, __r0__f__nodes__f__size</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="7300">
				<variables>__r16, __l0, __r12, __r8, __l2, __r3, __r0, pVertices, pVertices_init</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l0, __r12__f__count, __r12__f__value__f__size, __r8__f__count, __r8__f__value__f__size, __l2, __r3__f__count, __r3__f__value__f__size, pVertices, __r0__f__nodes__f__size</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r16 == __r12 && __r16 == __r8 && __r16 == __r3 && pVertices == pVertices_init && pVertices == __r0__f__nodes__f__size && __l0 < __l2 && __l0 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __l0 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __l0 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __l0 > pVertices && __l2 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __l2 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __l2 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __l2 > pVertices && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > pVertices && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > pVertices && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > pVertices]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="7400">
				<variables>__r16, __l0, __r12, __l3, __r8, __l2, __r3, __r0, pVertices, pVertices_init</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l0, __r12__f__count, __r12__f__value__f__size, __l3, __r8__f__count, __r8__f__value__f__size, __l2, __r3__f__count, __r3__f__value__f__size, pVertices, __r0__f__nodes__f__size</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: int computeMST(ar.uba.dc.jolden.mst.Graph,int)</callee>
				<constraints>
					<![CDATA[__r16 == __r12 && __r16 == __r8 && __r16 == __r3 && __l3 == __l2 && pVertices == pVertices_init && pVertices == __r0__f__nodes__f__size && __l0 < __l3 && __l0 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __l0 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __l0 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __l0 > pVertices && __l3 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __l3 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __l3 > ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __l3 > pVertices && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > pVertices && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > pVertices && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > pVertices]]>
				</constraints>
				<binding>$t.graph_init == __r0 and $t.graph_init__f__nodes == __r0__f__nodes and $t.graph_init__f__nodes__f__size == __r0__f__nodes__f__size and $t.numvert_init == pVertices</binding>
			 </call-site>
			 <call-site offset="10" srccode-offset="7500">
				<variables>__r16, __l0, __r12, __l3, __r8, __l2, __r3, __i4, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l0, __r12__f__count, __r12__f__value__f__size, __l3, __r8__f__count, __r8__f__value__f__size, __l2, __r3__f__count, __r3__f__value__f__size, __i4</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r16 == __r12 && __r16 == __r8 && __r16 == __r3 && __l3 == __l2 && pVertices_init == pVertices && __l0 < __l3 && __l0 > __i4 && __l0 > pVertices_init && __l3 > __i4 && __l3 > pVertices_init && __i4 > pVertices_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="7800">
				<variables>__r16, __l5, __l0, __r12, __l3, __r8, __l2, __r2, __r3, __i4, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __l3, __r8__f__count, __r8__f__value__f__size, __l2, __r3__f__count, __r3__f__value__f__size, __i4</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="11" srccode-offset="7801">
				<variables>__r16, __l5, __l0, __r12, __l3, __r8, __l2, __r2, __r3, __i4, __r1, aux_2, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __l3, __r8__f__count, __r8__f__value__f__size, __l2, __r3__f__count, __r3__f__value__f__size, __i4</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="7802">
				<variables>__r16, __l5, __l0, __r12, __l3, __r8, __l2, __r2, __r3, __i4, __r1, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __l3, __r8__f__count, __r8__f__value__f__size, __l2, __r3__f__count, __r3__f__value__f__size, __i4</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="7803">
				<variables>__r16, __l5, __l0, __r12, __l3, __r8, __l2, __r2, __r3, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __l3, __r8__f__count, __r8__f__value__f__size, __l2, __r3__f__count, __r3__f__value__f__size</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="7804">
				<variables>__r16, __l5, __l0, __r12, __l3, __r8, __l2, __r4, __r2, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __l3, __r8__f__count, __r8__f__value__f__size, __l2</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="8100">
				<variables>__r16, __l5, __l0, __r12, __l3, __r7, __r8, __l2, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __l3, __r8__f__count, __r8__f__value__f__size, __l2</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="15" srccode-offset="8101">
				<variables>__r16, __l5, __l0, __r12, __l3, __r7, __r8, __l2, __r6, aux_3, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __l3, __r8__f__count, __r8__f__value__f__size, __l2</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="8102">
				<variables>__r16, __l5, __l0, __r12, __l3, __r7, __r8, __d1, __r6, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __l3, __r8__f__count, __r8__f__value__f__size, __d1</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="8103">
				<variables>__r16, __l5, __l0, __r12, __l3, __r7, __r8, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __l3, __r8__f__count, __r8__f__value__f__size</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="18" srccode-offset="8104">
				<variables>__r16, __l5, __l0, __r12, __l3, __r9, __r7, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __l3</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="4" srccode-offset="8200">
				<variables>__r16, __l5, __l0, __r11, __r12, __l3, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __l3</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="19" srccode-offset="8201">
				<variables>__r16, __l5, __l0, __r11, __r12, __l3, __r10, aux_4, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __l3</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="8202">
				<variables>__r16, __l5, __l0, __r11, __r12, __d3, __r10, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size, __d3</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="21" srccode-offset="8203">
				<variables>__r16, __l5, __l0, __r11, __r12, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0, __r12__f__count, __r12__f__value__f__size</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="22" srccode-offset="8204">
				<variables>__r16, __l5, __l0, __r13, __r11, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="5" srccode-offset="8300">
				<variables>__r15, __r16, __l5, __l0, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="23" srccode-offset="8301">
				<variables>__r15, __r16, __l5, __l0, __r14, aux_5, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __l5, __l0</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="24" srccode-offset="8302">
				<variables>__r15, __r16, __d5, __r14, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size, __d5</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="25" srccode-offset="8303">
				<variables>__r15, __r16, pVertices_init, pVertices</variables>
				<inductives>__r16__f__count, __r16__f__value__f__size</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="26" srccode-offset="8304">
				<variables>__r17, __r15, pVertices_init, pVertices</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="27" srccode-offset="8600">
				<variables>__r5, aux_6, pVertices_init, pVertices</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[pVertices_init == pVertices && aux_6__f__toString__f__length == 5 && pVertices_init > aux_6__f__toString__f__length]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void parseCmdLine(java.lang.String[])">
			<relevant-parameters>args_init, args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="20700">
				<variables>i, args, arg, aux_9, args_init</variables>
				<inductives>i, args, arg__f__value__f__size</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[i == args__f__size-1 && i == args_init__f__size-1 && args == args_init && arg == aux_9 && args__f__size == arg__f__toString__f__length && args__f__size == aux_9__f__toString__f__length && args__f__size == 2 && arg__f__toString == aux_9__f__toString]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="20800">
				<variables>args_init, args</variables>
				<inductives></inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="3" srccode-offset="20801">
				<variables>__r4, aux_10, args_init, args</variables>
				<inductives></inductives>
				<callee>java.lang.RuntimeException: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="20900">
				<variables>args, arg, i, args_init</variables>
				<inductives>args, i</inductives> <!-- arg__f__value__f__size -->
				<constraints>
					<![CDATA[args == args_init && i == args__f__size-1 && i == args_init__f__size-1 && args__f__size == arg__f__toString__f__length && args__f__size == 2]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="20901">
				<variables>args, arg, i, __r6, __r5, args_init</variables>
				<inductives>args</inductives> <!-- REMOVE arg__f__value__f__size, i -->
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[args == args_init && i == args__f__size && i == arg__f__toString__f__length && i == __r6__f__toString__f__length && i == args_init__f__size && i == 2 && arg__f__toString < __r6__f__toString]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="20902">
				<variables>args, arg, i, __r5, args_init</variables>
				<inductives>args, arg__f__value__f__size, i, __r5__f__value</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[args == args_init && i == args__f__size && i == arg__f__toString__f__length && i == args_init__f__size && i == 2]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="21100">
				<variables>args, arg, i, aux_11, args_init</variables>
				<inductives>args, arg__f__value__f__size, i</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="21101">
				<variables>args, arg, i, aux_12, args_init</variables>
				<inductives>args, arg__f__value__f__size, i</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="21102">
				<variables>args, arg, i, aux_13, args_init</variables>
				<inductives>args, arg__f__value__f__size, i</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="21600">
				<variables>args, arg, i, args_init</variables>
				<inductives>args, arg__f__value__f__size, i</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="22000">
				<variables>args, arg, i, __r1, aux_14, args_init</variables>
				<inductives>args, arg__f__value__f__size, i</inductives>
				<callee>java.lang.String: boolean startsWith(java.lang.String)</callee>
				<constraints>
					<![CDATA[args == args_init && args__f__size == __r1__f__toString__f__length && args__f__size-1 == aux_14__f__toString__f__length && aux_14__f__toString__f__length__f__size == args_init-1 && i == 0 && args__f__size == 2 && __r1__f__toString > aux_14__f__toString]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="22001">
				<variables>args_init, args</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void usage()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="23000">
				<variables>__r0, aux_15</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="23100">
				<variables>__r1, aux_16</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="23200">
				<variables>__r2, aux_17</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="23300">
				<variables>__r3, aux_18</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="23400">
				<variables>__r4, aux_19</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="23500">
				<variables>aux_20</variables>
				<inductives></inductives>
				<callee>java.lang.System: void exit(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
