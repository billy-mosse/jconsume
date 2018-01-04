<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.mst.Graph">
		 <method decl="int computeDist(int,int,int)">
			<relevant-parameters>i_init, j_init, numvert_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="12100">
				<variables>__i1, i_init, j_init, numvert_init, i, j, numvert</variables>
				<inductives>__i1</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: int random(int)</callee>
				<constraints>
					<![CDATA[i_init == i && j_init == j && numvert_init == numvert && __i1 >= 1 && i_init >= 0 && j_init >= 0 && __i1 >= i_init && __i1 >= j_init && i_init < numvert_init && j_init < numvert_init]]>
				</constraints>
				<binding>$t.seed_init == __i1</binding>
			 </call-site>
		</method>
		 <method decl="int random(int)">
			<relevant-parameters>seed_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="14301">
				<variables>seed, aux_21, seed_init</variables>
				<inductives>seed</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: int mult(int,int)</callee>
				<constraints>
					<![CDATA[seed == seed_init && seed >= 1 && seed < aux_21]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;(int)">
			<relevant-parameters>numvert_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="3100">
				<variables>numvert, this, numvert_init</variables>
				<inductives>numvert</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[numvert == numvert_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="3300">
				<variables>numvert, this, numvert_init, aux_1</variables>
				<inductives>numvert, numvert, aux_1</inductives>
				<constraints>
					<![CDATA[numvert == numvert_init && 1<=aux_1 && aux_1<=numvert]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="3701">
				<variables>numvert, this, i, __r2, v, numvert_init</variables>
				<inductives>numvert, this__f__nodes, i, __r2</inductives>
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == __r2__f__size && i >= 0 && i <= __r2__f__size-1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="3702">
				<variables>numvert, this, i, __r2, v, __r1, numvert_init</variables>
				<inductives>numvert, this__f__nodes, i, __r2</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void &lt;init&gt;(ar.uba.dc.jolden.mst.Vertex,int)</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == __r2__f__size && i >= 0 && i <= __r2__f__size-1]]>
				</constraints>
				<binding>$t.n_init == v and $t.numvert_init == numvert</binding>
			 </call-site>
			 <call-site offset="2" srccode-offset="4000">
				<variables>numvert, this, numvert_init</variables>
				<inductives>numvert, this__f__nodes</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: void addEdges(int)</callee>
				<constraints>
					<![CDATA[numvert == numvert_init]]>
				</constraints>
				<binding>$t.this_init == this and $t.numvert_init == numvert</binding>
			 </call-site>
		</method>
		 <method decl="void addEdges(int)">
			<relevant-parameters>this_init, numvert_init</relevant-parameters>
			 <call-site offset="4" srccode-offset="9300">
				<variables>count1, numvert, __r1, this, tmp, this_init, numvert_init</variables>
				<inductives>count1, numvert, __r1__f__array, __r1__f__size, this__f__nodes</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && __r1__f__size == __r1__f__array__f__size && count1 >= 1 && count1 <= numvert && numvert > __r1__f__size]]>
				</constraints>
			 </call-site>
			 <call-site offset="0" srccode-offset="9400">
				<variables>tmp, count1, numvert, __r1, this, this_init, numvert_init</variables>
				<inductives>count1, numvert, __r1__f__array, __r1__f__size, this__f__nodes</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Hashtable neighbors()</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && __r1__f__size == __r1__f__array__f__size && count1 >= 0 && count1 < numvert && numvert > __r1__f__size]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="9700">
				<variables>tmp, count1, numvert, i, __r1, this, this_init, numvert_init</variables>
				<inductives>count1, numvert, i, __r1__f__array, __r1__f__size, this__f__nodes</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: int computeDist(int,int,int)</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && __r1__f__size == __r1__f__array__f__size && count1 >= 0 && i >= 0 && __r1__f__size >= 2 && count1 < numvert && numvert > i && numvert > __r1__f__size]]>
				</constraints>
				<binding>$t.i_init == i and $t.j_init == count1 and $t.numvert_init == numvert</binding>
			 </call-site>
			 <creation-site offset="0" srccode-offset="9800">
				<variables>tmp, count1, numvert, __r1, this, i, __r6, __i0, this_init, numvert_init</variables>
				<inductives>count1, numvert, __r1__f__array, __r1__f__size, this__f__nodes, i, __i0</inductives>
				<constraints>
					<![CDATA[numvert == numvert_init && __r1__f__size == __r1__f__array__f__size && count1 >= 0 && __r1__f__size >= 2 && i >= 0 && count1 < numvert && numvert > __r1__f__size && numvert > i && __i0 >= __r1__f__array__f__size-1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="9801">
				<variables>tmp, count1, numvert, __r1, this, i, __r6, __i0, __r5, this_init, numvert_init</variables>
				<inductives>count1, numvert, __r1__f__array, __r1__f__size, this__f__nodes, i, __i0</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && __r1__f__size == __r1__f__array__f__size && count1 >= 0 && __r1__f__size >= 2 && i >= 0 && count1 < numvert && numvert > __r1__f__size && numvert > i && __i0 >= __r1__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="9802">
				<variables>tmp, count1, numvert, __r1, this, i, __r6, __r5, this_init, numvert_init</variables>
				<inductives>count1, numvert, __r1__f__array, __r1__f__size, this__f__nodes, i</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: void put(java.lang.Object,java.lang.Object)</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && __r1__f__size == __r1__f__array__f__size && count1 >= 0 && __r1__f__size >= 2 && i >= 0 && count1 < numvert && numvert > __r1__f__size && numvert > i]]>
				</constraints>
				<binding>$t.this_init == __r1 and $t.this_init__f__array == __r1__f__array and $t.key_init == __r6 and $t.value_init == __r5</binding>
			 </call-site>
		</method>
	</class>
</spec>
