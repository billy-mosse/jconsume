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
					<![CDATA[seed == seed_init && seed >= 1 && aux_21 == 31415821 && seed < aux_21]]>
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
				<inductives>numvert, aux_1</inductives>
				<constraints>
					<![CDATA[numvert == numvert_init && 1<=aux_1 && aux_1<=numvert]]> <!-- REMOVE numvert__f__size -->
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="3701">
				<variables>numvert, this, i, __r2, v, numvert_init</variables>
				<inductives>numvert, this__f__nodes__f__size, i</inductives><!-- __r2-->
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == this__f__nodes__f__size && numvert == __r2__f__size && this__f__nodes == __r2 && i >= 0 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > i && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > i && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > i && i <= __r2__f__size-1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="3702">
				<variables>numvert, this, i, __r2, v, __r1, numvert_init</variables>
				<inductives>numvert, this__f__nodes__f__size, i, __r2</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void &lt;init&gt;(ar.uba.dc.jolden.mst.Vertex,int)</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == this__f__nodes__f__size && numvert == __r2__f__size && this__f__nodes == __r2 && i >= 0 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > i && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > i && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > i && i <= __r2__f__size-1]]>
				</constraints>
				<binding>$t.n_init == v and $t.numvert_init == numvert</binding>
			 </call-site>
			 <call-site offset="2" srccode-offset="4000">
				<variables>numvert, this, numvert_init</variables>
				<inductives>numvert, this__f__nodes__f__size</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: void addEdges(int)</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == this__f__nodes__f__size && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE]]>
				</constraints>
				<binding>$t.this_init == this and $t.this_init__f__nodes == this__f__nodes and $t.this_init__f__nodes__f__size == this__f__nodes__f__size and $t.numvert_init == numvert</binding>
			 </call-site>
		</method>
		 <method decl="void addEdges(int)">
			<relevant-parameters>this_init, this_init__f__nodes, this_init__f__nodes__f__size, numvert_init</relevant-parameters>
			 <call-site offset="4" srccode-offset="9300">
				<variables>count1, numvert, __r1, this, tmp, this_init, numvert_init</variables>
				<inductives>count1, numvert, __r1__f__array__f__size, __r1__f__size, this__f__nodes__f__size</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == this__f__nodes__f__size && numvert == this_init__f__nodes__f__size && __r1__f__size == __r1__f__array__f__size && this == this_init && this__f__nodes == this_init__f__nodes && count1 >= 1 && count1 <= numvert && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __r1__f__size < this__f__nodes__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="0" srccode-offset="9400">
				<variables>tmp, count1, numvert, __r1, this, this_init, numvert_init</variables>
				<inductives>count1, numvert, __r1__f__array__f__size, __r1__f__size, this__f__nodes__f__size</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Hashtable neighbors()</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == this__f__nodes__f__size && numvert == this_init__f__nodes__f__size && __r1__f__size == __r1__f__array__f__size && this == this_init && this__f__nodes == this_init__f__nodes && count1 >= 0 && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && count1 <= this__f__nodes__f__size-1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __r1__f__size < this__f__nodes__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="9700">
				<variables>tmp, count1, numvert, i, __r1, this, this_init, numvert_init</variables>
				<inductives>count1, numvert, i, __r1__f__array__f__size, __r1__f__size, this__f__nodes__f__size</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: int computeDist(int,int,int)</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == this__f__nodes__f__size && numvert == this_init__f__nodes__f__size && __r1__f__size == __r1__f__array__f__size && this == this_init && this__f__nodes == this_init__f__nodes && count1 >= 0 && i >= 0 && __r1__f__size >= 2 && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && count1 <= this__f__nodes__f__size-1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && i < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && i < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && i < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && i <= this__f__nodes__f__size-1 && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __r1__f__size < this__f__nodes__f__size-1]]>
				</constraints>
				<binding>$t.i_init == i and $t.j_init == count1 and $t.numvert_init == numvert</binding>
			 </call-site>
			 <creation-site offset="0" srccode-offset="9800">
				<variables>tmp, count1, numvert, __r1, this, i, __r6, __i0, this_init, numvert_init</variables>
				<inductives>count1, numvert, i, this__f__nodes__f__size</inductives>
				<!-- una inductiva es i, la otra es  count1. Tuve que sacar __r1__f__array__f__size, __r1__f__size, __i0-->
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == this__f__nodes__f__size && numvert == this_init__f__nodes__f__size && __r1__f__size == __r1__f__array__f__size && this == this_init && this__f__nodes == this_init__f__nodes && count1 >= 0 && __r1__f__size >= 2 && i >= 0 && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && count1 <= this__f__nodes__f__size-1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __r1__f__size < this__f__nodes__f__size-1 && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > i && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > __i0 && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > i && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > __i0 && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > i && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > __i0 && i <= this__f__nodes__f__size-1 && __i0 >= __r1__f__array__f__size-1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="9801">
				<variables>tmp, count1, numvert, __r1, this, i, __r6, __i0, __r5, this_init, numvert_init</variables>
				<inductives>count1, numvert, __r1__f__array__f__size, __r1__f__size, this__f__nodes__f__size, i, __i0</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == this__f__nodes__f__size && numvert == this_init__f__nodes__f__size && __r1__f__size == __r1__f__array__f__size && this == this_init && this__f__nodes == this_init__f__nodes && count1 >= 0 && __r1__f__size >= 2 && i >= 0 && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && count1 <= this__f__nodes__f__size-1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __r1__f__size < this__f__nodes__f__size-1 && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > i && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > __i0 && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > i && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > __i0 && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > i && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > __i0 && i <= this__f__nodes__f__size-1 && __i0 >= __r1__f__array__f__size-1]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="9802">
				<variables>tmp, count1, numvert, __r1, this, i, __r6, __r5, this_init, numvert_init</variables>
				<inductives>count1, numvert, this__f__nodes__f__size, i</inductives>
				<!-- Saco __r1__f__array__f__size, __r1__f__size -->
				<callee>ar.uba.dc.jolden.mst.Hashtable: void put(java.lang.Object,java.lang.Object)</callee>
				<constraints>
					<![CDATA[numvert == numvert_init && numvert == this__f__nodes__f__size && numvert == this_init__f__nodes__f__size && __r1__f__size == __r1__f__array__f__size && this == this_init && this__f__nodes == this_init__f__nodes && count1 >= 0 && __r1__f__size >= 2 && i >= 0 && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && count1 < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && count1 <= this__f__nodes__f__size-1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && numvert < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b && __r1__f__size < ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE && __r1__f__size < this__f__nodes__f__size-1 && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_m1 > i && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__CONST_b > i && ar__f__uba__f__dc__f__jolden__f__mst__f__Graph__f__RANGE > i && i <= this__f__nodes__f__size-1]]>
				</constraints>
				<binding>$t.this_init == __r1 and $t.this_init__f__array == __r1__f__array and $t.this_init__f__array__f__size == this__f__nodes__f__size and $t.key_init == __r6 and $t.value_init == __r5</binding> <!-- __r1__f__array__f__size -->
			 </call-site>
		</method>
	</class>
</spec>
