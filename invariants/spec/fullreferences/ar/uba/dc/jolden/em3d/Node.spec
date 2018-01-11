<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.em3d.Node">
		 <method decl="ar.uba.dc.jolden.em3d.Node[] fillTable(int,int)">
			<relevant-parameters>size_init, degree_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="10001">
				<variables>size, degree, size_init, degree_init, aux_1</variables>
				<inductives>size, degree, size, aux_1</inductives>
				<constraints>
					<![CDATA[size == size_init && degree == degree_init && size > degree && 1<=aux_1 && aux_1<=size]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="10200">
				<variables>table, size, degree, size_init, degree_init</variables>
				<inductives>table, size, degree</inductives>
				<constraints>
					<![CDATA[size == size_init && size == table__f__size && degree == degree_init && degree < table__f__size-1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="10201">
				<variables>table, size, degree, __r0, size_init, degree_init</variables>
				<inductives>table, size, degree</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[size == size_init && size == table__f__size && degree == degree_init && degree < table__f__size-1]]>
				</constraints>
				<binding>$t.degree_init == degree</binding>
			 </call-site>
			 <creation-site offset="2" srccode-offset="10503">
				<variables>table, size, i, prevNode, degree, size_init, degree_init</variables>
				<inductives>table, size, i, degree</inductives>
				<constraints>
					<![CDATA[size == size_init && size == table__f__size && degree == degree_init && i >= 1 && i <= table__f__size-1 && degree < table__f__size-1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="10504">
				<variables>table, size, i, prevNode, degree, __r1, size_init, degree_init</variables>
				<inductives>table, size, i, degree</inductives>
				<callee>ar.uba.dc.jolden.em3d.Node: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[size == size_init && size == table__f__size && degree == degree_init && i >= 1 && i <= table__f__size-1 && degree < table__f__size-1]]>
				</constraints>
				<binding>$t.degree_init == degree</binding>
			 </call-site>
		</method>
		 <method decl="java.util.Enumeration elements()">
			<relevant-parameters>this_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="22500">
				<variables>this, this_init</variables>
				<inductives></inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="22501">
				<variables>this, __r0, this_init</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Node$1Enumerate: void &lt;init&gt;(ar.uba.dc.jolden.em3d.Node)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.this__0_init == this</binding>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;(int)">
			<relevant-parameters>degree_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="7001">
				<variables>degree, this, degree_init</variables>
				<inductives>degree</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[degree == degree_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="7101">
				<variables>this, degree, __r0, degree_init</variables>
				<inductives>degree</inductives>
				<callee>java.util.Random: double nextDouble()</callee>
				<constraints>
					<![CDATA[degree == degree_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="7301">
				<variables>this, degree, degree_init, aux_1</variables>
				<inductives>degree, degree, aux_1</inductives>
				<constraints>
					<![CDATA[degree == degree_init && 1<=aux_1 && aux_1<=degree]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void initSeed(long)">
			<relevant-parameters>seed_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="5400">
				<variables>seed, seed_init</variables>
				<inductives>seed</inductives>
				<constraints>
					<![CDATA[seed == seed_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="5401">
				<variables>seed, __r0, seed_init</variables>
				<inductives>seed</inductives>
				<callee>java.util.Random: void &lt;init&gt;(long)</callee>
				<constraints>
					<![CDATA[seed == seed_init]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void makeFromNodes()">
			<relevant-parameters>this_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="16901">
				<variables>this, __i0, this_init, aux_1</variables>
				<inductives>this__f__fromCount, __i0, __i0, aux_1</inductives>
				<constraints>
					<![CDATA[this == this_init && this__f__next == this_init__f__next && this__f__next__f__next == this_init__f__next__f__next && this__f__next__f__toNodes == this_init__f__next__f__toNodes && this__f__next__f__fromNodes == this__f__fromNodes && this__f__next__f__fromNodes == this_init__f__next__f__fromNodes && this__f__next__f__coeffs == this__f__coeffs && this__f__next__f__coeffs == this_init__f__next__f__coeffs && this__f__next__f__fromCount == this_init__f__next__f__fromCount && this__f__next__f__fromLength == this_init__f__next__f__fromLength && this__f__toNodes == this_init__f__toNodes && this__f__fromNodes == this_init__f__fromNodes && this__f__coeffs == this_init__f__coeffs && this__f__fromCount == __i0 && this__f__fromLength == this_init__f__fromLength && __i0 == this_init__f__fromCount && this__f__next__f__fromCount >= 0 && this__f__next__f__fromLength <= 0 && this__f__next__f__fromLength >= 0 && this__f__fromLength <= 0 && this__f__fromLength >= 0 && __i0 >= 0 && this__f__next__f__fromCount >= this__f__next__f__fromLength && this__f__next__f__fromCount >= this__f__fromLength && this__f__next__f__fromLength <= __i0 && this__f__next__f__fromLength < this__f__next__f__toNodes__f__size-1 && this__f__next__f__fromLength < this__f__toNodes__f__size-1 && this__f__fromLength <= __i0 && this__f__fromLength < this__f__next__f__toNodes__f__size-1 && this__f__fromLength < this__f__toNodes__f__size-1 && 1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="17001">
				<variables>this, __i1, this_init, aux_1</variables>
				<inductives>__i1, __i1, aux_1</inductives>
				<constraints>
					<![CDATA[__i1 == this_init__f__fromCount && __i1 == this_init__f__fromNodes__f__size && this_init__f__next__f__coeffs == this_init__f__coeffs && this_init__f__next__f__fromCount >= 0 && this_init__f__next__f__fromLength <= 0 && this_init__f__next__f__fromLength >= 0 && this_init__f__fromLength <= 0 && this_init__f__fromLength >= 0 && __i1 >= this_init__f__next__f__fromLength && __i1 >= this_init__f__fromLength && this_init__f__next__f__fromCount >= this_init__f__next__f__fromLength && this_init__f__next__f__fromCount >= this_init__f__fromLength && this_init__f__next__f__fromLength < this_init__f__next__f__toNodes__f__size-1 && this_init__f__next__f__fromLength < this_init__f__toNodes__f__size-1 && this_init__f__fromLength < this_init__f__next__f__toNodes__f__size-1 && this_init__f__fromLength < this_init__f__toNodes__f__size-1 && 1<=aux_1 && aux_1<=__i1]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void makeUniqueNeighbors(ar.uba.dc.jolden.em3d.Node[])">
			<relevant-parameters>this_init, this_init__f__toNodes, nodeTable_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="13200">
				<variables>this, nodeTable, filled, __r10, __r1, this_init, nodeTable_init</variables>
				<inductives>this__f__toNodes, nodeTable, filled, __r10__f__fromCount</inductives>
				<callee>java.util.Random: int nextInt()</callee>
				<constraints>
					<![CDATA[this == this_init && this__f__next == this_init__f__next && this__f__next__f__next == this_init__f__next__f__next && this__f__next__f__toNodes == this_init__f__next__f__toNodes && this__f__next__f__fromNodes == this_init__f__next__f__fromNodes && this__f__next__f__coeffs == this_init__f__next__f__coeffs && this__f__next__f__fromCount == this_init__f__next__f__fromCount && this__f__next__f__fromLength == this_init__f__next__f__fromLength && this__f__toNodes == this_init__f__toNodes && this__f__fromNodes == this_init__f__fromNodes && this__f__coeffs == this_init__f__coeffs && this__f__fromCount == this_init__f__fromCount && this__f__fromLength == this_init__f__fromLength && nodeTable == nodeTable_init && this__f__next__f__fromCount >= 0 && this__f__next__f__fromLength <= 0 && this__f__next__f__fromLength >= 0 && this__f__fromCount >= 0 && this__f__fromLength <= 0 && this__f__fromLength >= 0 && filled >= 0 && __r10__f__next__f__fromCount >= 0 && __r10__f__next__f__fromLength <= 0 && __r10__f__next__f__fromLength >= 0 && __r10__f__fromCount >= 1 && __r10__f__fromLength <= 0 && __r10__f__fromLength >= 0 && this__f__next__f__fromCount >= this__f__next__f__fromLength && this__f__next__f__fromCount >= this__f__fromLength && this__f__next__f__fromCount >= __r10__f__next__f__fromLength && this__f__next__f__fromCount >= __r10__f__fromLength && this__f__next__f__fromCount < nodeTable__f__size-1 && this__f__next__f__fromLength <= this__f__fromCount && this__f__next__f__fromLength <= filled && this__f__next__f__fromLength <= __r10__f__next__f__fromCount && this__f__next__f__fromLength < __r10__f__fromCount && this__f__next__f__fromLength < this__f__next__f__toNodes__f__size-1 && this__f__next__f__fromLength < this__f__toNodes__f__size-1 && this__f__next__f__fromLength < nodeTable__f__size-1 && this__f__next__f__fromLength < __r10__f__next__f__toNodes__f__size-1 && this__f__next__f__fromLength < __r10__f__toNodes__f__size-1 && this__f__fromCount >= this__f__fromLength && this__f__fromCount >= __r10__f__next__f__fromLength && this__f__fromCount >= __r10__f__fromLength && this__f__fromCount < nodeTable__f__size-1 && this__f__fromLength <= filled && this__f__fromLength <= __r10__f__next__f__fromCount && this__f__fromLength < __r10__f__fromCount && this__f__fromLength < this__f__next__f__toNodes__f__size-1 && this__f__fromLength < this__f__toNodes__f__size-1 && this__f__fromLength < nodeTable__f__size-1 && this__f__fromLength < __r10__f__next__f__toNodes__f__size-1 && this__f__fromLength < __r10__f__toNodes__f__size-1 && filled > __r10__f__next__f__fromLength && filled > __r10__f__fromLength && filled <= this__f__next__f__toNodes__f__size-1 && filled <= this__f__toNodes__f__size-1 && filled < nodeTable__f__size-1 && filled <= __r10__f__next__f__toNodes__f__size-1 && filled <= __r10__f__toNodes__f__size-1 && __r10__f__next__f__fromCount >= __r10__f__next__f__fromLength && __r10__f__next__f__fromCount >= __r10__f__fromLength && __r10__f__next__f__fromCount < nodeTable__f__size-1 && __r10__f__next__f__fromLength < __r10__f__fromCount && __r10__f__next__f__fromLength < this__f__next__f__toNodes__f__size-1 && __r10__f__next__f__fromLength < this__f__toNodes__f__size-1 && __r10__f__next__f__fromLength < nodeTable__f__size-1 && __r10__f__next__f__fromLength < __r10__f__next__f__toNodes__f__size-1 && __r10__f__next__f__fromLength < __r10__f__toNodes__f__size-1 && __r10__f__fromCount > __r10__f__fromLength && __r10__f__fromCount < nodeTable__f__size-1 && __r10__f__fromLength < this__f__next__f__toNodes__f__size-1 && __r10__f__fromLength < this__f__toNodes__f__size-1 && __r10__f__fromLength < nodeTable__f__size-1 && __r10__f__fromLength < __r10__f__next__f__toNodes__f__size-1 && __r10__f__fromLength < __r10__f__toNodes__f__size-1]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void updateFromNodes()">
			<relevant-parameters>this_init, this_init__f__toNodes</relevant-parameters>
			 <call-site offset="0" srccode-offset="18601">
				<variables>this, otherNode, i, __r4, __i1, __r3, this_init</variables>
				<inductives>this__f__toNodes, otherNode__f__coeffs, otherNode__f__fromNodes, otherNode__f__fromLength, i, __r4, __i1</inductives>
				<callee>java.util.Random: double nextDouble()</callee>
				<constraints>
					<![CDATA[this == this_init && this__f__next == this_init__f__next && this__f__next__f__next == this_init__f__next__f__next && this__f__next__f__toNodes == this_init__f__next__f__toNodes && this__f__next__f__fromNodes == this_init__f__next__f__fromNodes && this__f__next__f__coeffs == this_init__f__next__f__coeffs && this__f__next__f__fromCount == this_init__f__next__f__fromCount && this__f__next__f__fromCount == this__f__next__f__fromNodes__f__size && this__f__next__f__fromCount == this__f__next__f__coeffs__f__size && this__f__next__f__fromCount == this_init__f__next__f__fromNodes__f__size && this__f__next__f__fromCount == this_init__f__next__f__coeffs__f__size && this__f__next__f__fromLength == this_init__f__next__f__fromLength && this__f__toNodes == this_init__f__toNodes && this__f__fromNodes == this_init__f__fromNodes && this__f__coeffs == this_init__f__coeffs && this__f__fromCount == this_init__f__fromCount && this__f__fromCount == this__f__fromNodes__f__size && this__f__fromCount == this__f__coeffs__f__size && this__f__fromCount == this_init__f__fromNodes__f__size && this__f__fromCount == this_init__f__coeffs__f__size && this__f__fromLength == this_init__f__fromLength && otherNode__f__next__f__fromCount == otherNode__f__next__f__fromNodes__f__size && otherNode__f__next__f__fromCount == otherNode__f__next__f__coeffs__f__size && otherNode__f__coeffs == __r4 && otherNode__f__fromCount == otherNode__f__fromNodes__f__size && otherNode__f__fromCount == otherNode__f__coeffs__f__size && otherNode__f__fromCount == __r4__f__size && this__f__next__f__fromLength >= 0 && this__f__fromLength >= 0 && otherNode__f__next__f__fromLength >= 0 && otherNode__f__fromCount >= 1 && otherNode__f__fromLength >= 1 && i >= 0 && __i1 >= 0 && this__f__next__f__fromCount >= this__f__next__f__fromLength && this__f__fromCount >= this__f__fromLength && otherNode__f__next__f__fromCount >= otherNode__f__next__f__fromLength && otherNode__f__fromLength - __i1 - 1 == 0 && i <= this__f__next__f__toNodes__f__size-1 && i <= this__f__toNodes__f__size-1 && i <= otherNode__f__next__f__toNodes__f__size-1 && __i1 <= __r4__f__size-1]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
