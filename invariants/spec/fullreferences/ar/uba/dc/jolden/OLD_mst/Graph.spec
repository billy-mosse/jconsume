<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.mst.Graph">
		 <method decl="int computeDist(int,int,int)">
			<relevant-parameters>i_init, j_init, numvert_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="94">
				<variables>__i0, i_init, j_init, numvert_init</variables>
				<inductives>__i0, i_init, j_init, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: int random(int)</callee>
				<constraints>
					<![CDATA[__i0 >= 1 && i_init >= 0 && j_init >= 0 && __i0 >=i_init && __i0 >=j_init && i_init <numvert_init && j_init <numvert_init]]>
				</constraints>
				<binding>$t.seed_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="int random(int)">
			<relevant-parameters>seed_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="108">
				<variables>__i0, __i1, seed_init</variables>
				<inductives>__i0, __i1, seed_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: int mult(int,int)</callee>
				<constraints>
					<![CDATA[__i0 ==seed_init && __i0 >= 1 && __i1 == 31415821 && __i0 <__i1]]>
				</constraints>
				<binding>$t.p_init == __i0 and $t.q_init == __i1</binding>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;(int)">
			<relevant-parameters>numvert_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="22">
				<variables>numvert, size_f_this_nodes, numvert_init</variables>
				<inductives>numvert, size_f_this_nodes, numvert_init</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[numvert ==numvert_init && size_f_this_nodes == 0 && numvert >size_f_this_nodes]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="24">
				<variables>numvert, size_f_this_nodes, __i0, numvert_init, aux_1</variables>
				<inductives>numvert, size_f_this_nodes, __i0, numvert_init, aux_1</inductives>
				<constraints>
					<![CDATA[numvert ==__i0 && numvert ==numvert_init && size_f_this_nodes == 0 && numvert >size_f_this_nodes && 1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="28">
				<variables>numvert, size_f_this_nodes, i, size___r5, __i0, numvert_init</variables>
				<inductives>numvert, size_f_this_nodes, i, size___r5, __i0, numvert_init</inductives>
				<constraints>
					<![CDATA[numvert ==size_f_this_nodes && numvert ==size___r5 && numvert ==numvert_init && i ==__i0 && i >= 0 && numvert >i]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="28">
				<variables>numvert, size_f_this_nodes, i, size___r5, __i0, __i1, numvert_init</variables>
				<inductives>numvert, size_f_this_nodes, i, size___r5, __i0, __i1, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void &lt;init&gt;(ar.uba.dc.jolden.mst.Vertex,int)</callee>
				<constraints>
					<![CDATA[numvert ==size_f_this_nodes && numvert ==size___r5 && numvert ==__i1 && numvert ==numvert_init && i ==__i0 && i >= 0 && numvert >i]]>
				</constraints>
				<binding>$t.numvert_init == __i1</binding>
			 </call-site>
			 <call-site offset="2" srccode-offset="31">
				<variables>size_f___r0_nodes, __i0, numvert_init</variables>
				<inductives>size_f___r0_nodes, __i0, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: void addEdges(int)</callee>
				<constraints>
					<![CDATA[size_f___r0_nodes ==__i0 && size_f___r0_nodes ==numvert_init  ]]>
				</constraints>
				<binding>$t.size_f_this_init_nodes == size_f___r0_nodes and $t.numvert_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="void addEdges(int)">
			<relevant-parameters>size_f_this_init_nodes, numvert_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="70">
				<variables>count1, size_f_this_nodes, numvert, size_f___r3_array, _f___r3_size, size_f_this_init_nodes, numvert_init</variables>
				<inductives>count1, size_f_this_nodes, numvert, size_f___r3_array, _f___r3_size, size_f_this_init_nodes, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[size_f_this_nodes ==numvert && size_f_this_nodes ==size_f_this_init_nodes && size_f_this_nodes ==numvert_init && size_f___r3_array ==_f___r3_size && count1 >= 1 && count1 <=size_f_this_nodes && size_f_this_nodes >size_f___r3_array]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="71">
				<variables>size_f_this_nodes, numvert, size_f___r3_array, _f___r3_size, count1, size_f_this_init_nodes, numvert_init</variables>
				<inductives>size_f_this_nodes, numvert, size_f___r3_array, _f___r3_size, count1, size_f_this_init_nodes, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Hashtable neighbors()</callee>
				<constraints>
					<![CDATA[size_f_this_nodes ==numvert && size_f_this_nodes ==size_f_this_init_nodes && size_f_this_nodes ==numvert_init && size_f___r3_array ==_f___r3_size && count1 >= 0 && size_f_this_nodes >size_f___r3_array && size_f_this_nodes >count1]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="74">
				<variables>size_f_this_nodes, numvert, count1, size_f_hash_array, _f_hash_size, i, __i1, __i2, __i3, size_f_this_init_nodes, numvert_init</variables>
				<inductives>size_f_this_nodes, numvert, count1, size_f_hash_array, _f_hash_size, i, __i1, __i2, __i3, size_f_this_init_nodes, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: int computeDist(int,int,int)</callee>
				<constraints>
					<![CDATA[size_f_this_nodes ==numvert && size_f_this_nodes ==__i3 && size_f_this_nodes ==size_f_this_init_nodes && size_f_this_nodes ==numvert_init && count1 ==__i2 && size_f_hash_array ==_f_hash_size && i ==__i1 && count1 >= 0 && size_f_hash_array >= 2 && i >= 0 && size_f_this_nodes >count1 && size_f_this_nodes >size_f_hash_array && size_f_this_nodes >i ]]>
				</constraints>
				<binding>$t.i_init == __i1 and $t.j_init == __i2 and $t.numvert_init == __i3</binding>
			 </call-site>
			 <creation-site offset="0" srccode-offset="75">
				<variables>size_f_this_nodes, numvert, size_f___r3_array, _f___r3_size, count1, size_f_hash_array, _f_hash_size, i, dist, size_f_this_init_nodes, numvert_init</variables>
				<inductives>size_f_this_nodes, numvert, size_f___r3_array, _f___r3_size, count1, size_f_hash_array, _f_hash_size, i, dist, size_f_this_init_nodes, numvert_init</inductives>
				<constraints>
					<![CDATA[size_f_this_nodes ==numvert && size_f_this_nodes ==size_f_this_init_nodes && size_f_this_nodes ==numvert_init && size_f___r3_array ==_f___r3_size && size_f___r3_array ==size_f_hash_array && size_f___r3_array ==_f_hash_size && size_f___r3_array >= 2 && count1 >= 0 && i >= 0 && size_f_this_nodes >size_f___r3_array && size_f_this_nodes >count1 && size_f_this_nodes >i]]>
				</constraints>
			 </creation-site>
			 <call-site offset="3" srccode-offset="75">
				<variables>size_f_this_nodes, numvert, size_f___r3_array, _f___r3_size, count1, size_f_hash_array, _f_hash_size, i, __i4, size_f_this_init_nodes, numvert_init</variables>
				<inductives>size_f_this_nodes, numvert, size_f___r3_array, _f___r3_size, count1, size_f_hash_array, _f_hash_size, i, __i4, size_f_this_init_nodes, numvert_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[size_f_this_nodes ==numvert && size_f_this_nodes ==size_f_this_init_nodes && size_f_this_nodes ==numvert_init && size_f___r3_array ==_f___r3_size && size_f___r3_array ==size_f_hash_array && size_f___r3_array ==_f_hash_size && size_f___r3_array >= 2 && count1 >= 0 && i >= 0 && size_f_this_nodes >size_f___r3_array && size_f_this_nodes >count1 && size_f_this_nodes >i]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="75">
				<variables>size_f_this_nodes, numvert, size_f___r3_array, _f___r3_size, count1, size_f_hash_array, _f_hash_size, i, size_f_this_init_nodes, numvert_init</variables>
				<inductives>size_f_this_nodes, numvert, size_f___r3_array, _f___r3_size, count1, size_f_hash_array, _f_hash_size, i, size_f_this_init_nodes, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: void put(java.lang.Object,java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_f_this_nodes ==numvert && size_f_this_nodes ==size_f_this_init_nodes && size_f_this_nodes ==numvert_init && size_f___r3_array ==_f___r3_size && size_f___r3_array ==size_f_hash_array && size_f___r3_array ==_f_hash_size && size_f___r3_array >= 2 && count1 >= 0 && i >= 0 && size_f_this_nodes >size_f___r3_array && size_f_this_nodes >count1 && size_f_this_nodes >i]]>
				</constraints>
				<binding>$t.size_f_this_init_array == size_f___r3_array</binding>
			 </call-site>
		</method>
		<method decl="void createGraph(int)">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="41">
				<variables>numvert, size_f_this_nodes, __i0, size_f_this_init_nodes, numvert_init, aux_1</variables>
				<inductives>numvert, size_f_this_nodes, __i0, size_f_this_init_nodes, numvert_init, aux_1</inductives>
				<constraints>
					<![CDATA[1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="45">
				<variables>numvert, size_f_this_nodes, i, size___r5, __i0, size_f_this_init_nodes, numvert_init</variables>
				<inductives>numvert, size_f_this_nodes, i, size___r5, __i0, size_f_this_init_nodes, numvert_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="45">
				<variables>numvert, size_f_this_nodes, i, size___r5, __i0, __i1, size_f_this_init_nodes, numvert_init</variables>
				<inductives>numvert, size_f_this_nodes, i, size___r5, __i0, __i1, size_f_this_init_nodes, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void &lt;init&gt;(ar.uba.dc.jolden.mst.Vertex,int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.numvert_init == __i1</binding>
			</call-site>			
			 <call-site offset="1" srccode-offset="49">
				<variables>size_f___r0_nodes, __i0, size_f_this_init_nodes, numvert_init</variables>
				<inductives>size_f___r0_nodes, __i0, size_f_this_init_nodes, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: void addEdges(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.numvert_init == __i0 and $t.size_f_this_init_nodes == size_f___r0_nodes</binding>
			 </call-site>
		</method>
	</class>
</spec>
