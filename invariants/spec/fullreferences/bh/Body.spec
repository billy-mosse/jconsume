<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="bh.Body">
		 <method decl="bh.Node loadTree(bh.Body,bh.MathVector,int,bh.Tree)">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="144">
				<variables>l_init</variables>
				<inductives>l_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="144">
				<variables>l_init</variables>
				<inductives>l_init</inductives>
				<callee>bh.Cell: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="145">
				<variables>d_f___r5_rsize, __i0, l_init</variables>
				<inductives>d_f___r5_rsize, __i0, l_init, $t.d_f_tree_init_rsize, $t.l_init</inductives>
				<callee>bh.Body: int subindex(bh.Tree,int)</callee>
				<constraints>
					<![CDATA[__i0 == l_init &&  d_f___r5_rsize == 4 &&  d_f___r5_rsize < __i0 &&  $t.d_f_tree_init_rsize == d_f___r5_rsize &&  $t.l_init == __i0
					&& l_init == 10]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="150">
				<variables>size_f___r8_data, __i4, l_init</variables>
				<inductives>size_f___r8_data, __i4, l_init, $t.size_f_ic_init_data, $t.l_init</inductives>
				<callee>bh.Node: int oldSubindex(bh.MathVector,int)</callee>
				<constraints>
					<![CDATA[__i4 == l_init &&  size_f___r8_data == 3 &&  size_f___r8_data < __i4 &&  $t.size_f_ic_init_data == size_f___r8_data &&  $t.l_init == __i4
					&& l_init == 10]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="153">
				<variables>l_init</variables>
				<inductives>l_init</inductives>
				<callee>bh.Node: bh.Node loadTree(bh.Body,bh.MathVector,int,bh.Tree)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="bh.Node$HG walkSubTree(double,bh.Node$HG)">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="254">
				<variables>d_f___r0_mass, d_f___r1_phi0</variables>
				<inductives>d_f___r0_mass, d_f___r1_phi0, $t.d_f_hg_init_phi0, $t.d_f_this_init_mass</inductives>
				<callee>bh.Node: bh.Node$HG gravSub(bh.Node$HG)</callee>
				<constraints>
					<![CDATA[d_f___r0_mass == d_f___r1_phi0 &&  d_f___r0_mass == 0 &&  $t.d_f_this_init_mass == d_f___r0_mass &&  $t.d_f_hg_init_phi0 == d_f___r1_phi0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="boolean icTest(bh.Tree)">
			<relevant-parameters>d_f_tree_init_rsize</relevant-parameters>
			 <call-site offset="0" srccode-offset="107">
				<variables>size_f___r2_data, __i0, d_f_tree_init_rsize</variables>
				<inductives>size_f___r2_data, __i0, d_f_tree_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[size_f___r2_data == 3 &&  __i0 == 0 &&  d_f_tree_init_rsize == 4 &&  $t.size_f_this_init_data == size_f___r2_data &&  $t.i_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="108">
				<variables>size_f___r2_data, __i0, d_f_tree_init_rsize</variables>
				<inductives>size_f___r2_data, __i0, d_f_tree_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[size_f___r2_data == 3 &&  __i0 == 1 &&  d_f_tree_init_rsize == 4 &&  $t.size_f_this_init_data == size_f___r2_data &&  $t.i_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="109">
				<variables>size_f___r2_data, __i0, d_f_tree_init_rsize</variables>
				<inductives>size_f___r2_data, __i0, d_f_tree_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[size_f___r2_data == 3 &&  __i0 == 2 &&  d_f_tree_init_rsize == 4 &&  $t.size_f_this_init_data == size_f___r2_data &&  $t.i_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="114">
				<variables>size_f___r3_data, __i1, d_f_tree_init_rsize</variables>
				<inductives>size_f___r3_data, __i1, d_f_tree_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[size_f___r3_data == 3 &&  __i1 == 0 &&  d_f_tree_init_rsize == 4 &&  $t.size_f_this_init_data == size_f___r3_data &&  $t.i_init == __i1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="119">
				<variables>size_f___r3_data, __i1, d_f_tree_init_rsize</variables>
				<inductives>size_f___r3_data, __i1, d_f_tree_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[size_f___r3_data == 3 &&  __i1 == 1 &&  d_f_tree_init_rsize == 4 &&  $t.size_f_this_init_data == size_f___r3_data &&  $t.i_init == __i1]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="124">
				<variables>size_f___r3_data, __i1, d_f_tree_init_rsize</variables>
				<inductives>size_f___r3_data, __i1, d_f_tree_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[size_f___r3_data == 3 &&  __i1 == 2 &&  d_f_tree_init_rsize == 4 &&  $t.size_f_this_init_data == size_f___r3_data &&  $t.i_init == __i1]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="int subindex(bh.Tree,int)">
			<relevant-parameters>d_f_tree_init_rsize, l_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="213">
				<variables>l, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, d_f_tree_init_rsize, l_init</inductives>
				<constraints>
					<![CDATA[l == l_init &&  d_f_tree_init_rsize == 4 &&  l > d_f_tree_init_rsize]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="213">
				<variables>l, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, d_f_tree_init_rsize, l_init</inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[l == l_init &&  d_f_tree_init_rsize == 4 &&  l > d_f_tree_init_rsize]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="215">
				<variables>l, size_f___r0_data, __i1, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, size_f___r0_data, __i1, d_f_tree_init_rsize, l_init, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[l == l_init &&  size_f___r0_data == 3 &&  __i1 == 0 &&  d_f_tree_init_rsize == 4 &&  l > size_f___r0_data &&  l > __i1 &&  l > d_f_tree_init_rsize &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="215">
				<variables>l, size_f___r4_data, __i0, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, size_f___r4_data, __i0, d_f_tree_init_rsize, l_init, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[l == l_init &&  size_f___r4_data == 3 &&  __i0 == 0 &&  d_f_tree_init_rsize == 4 &&  l > size_f___r4_data &&  l > __i0 &&  l > d_f_tree_init_rsize &&  $t.size_f_this_init_data == size_f___r4_data &&  $t.i_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="216">
				<variables>l, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, d_f_tree_init_rsize, l_init</inductives>
				<callee>java.lang.Math: double floor(double)</callee>
				<constraints>
					<![CDATA[l == l_init &&  d_f_tree_init_rsize == 4 &&  l > d_f_tree_init_rsize]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="216">
				<variables>l, size_f___r0_data, __i1, d___d2, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, size_f___r0_data, __i1, d___d2, d_f_tree_init_rsize, l_init, $t.i_init, $t.size_f_this_init_data, $t.d_v_init</inductives>
				<callee>bh.MathVector: void value(int,double)</callee>
				<constraints>
					<![CDATA[l == l_init &&  size_f___r0_data == 3 &&  __i1 == 0 &&  d_f_tree_init_rsize == 4 &&  l > size_f___r0_data &&  l > __i1 &&  l > d_f_tree_init_rsize &&  size_f___r0_data < d___d2 &&  __i1 < d___d2 &&  d___d2 > d_f_tree_init_rsize &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1 &&  $t.d_v_init == d___d2]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="218">
				<variables>l, size_f___r0_data, __i1, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, size_f___r0_data, __i1, d_f_tree_init_rsize, l_init, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[l == l_init &&  size_f___r0_data == 3 &&  __i1 == 1 &&  d_f_tree_init_rsize == 4 &&  l > size_f___r0_data &&  l > __i1 &&  l > d_f_tree_init_rsize &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="218">
				<variables>l, size_f___r4_data, __i0, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, size_f___r4_data, __i0, d_f_tree_init_rsize, l_init, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[l == l_init &&  size_f___r4_data == 3 &&  __i0 == 1 &&  d_f_tree_init_rsize == 4 &&  l > size_f___r4_data &&  l > __i0 &&  l > d_f_tree_init_rsize &&  $t.size_f_this_init_data == size_f___r4_data &&  $t.i_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="219">
				<variables>l, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, d_f_tree_init_rsize, l_init</inductives>
				<callee>java.lang.Math: double floor(double)</callee>
				<constraints>
					<![CDATA[l == l_init &&  d_f_tree_init_rsize == 4 &&  l > d_f_tree_init_rsize]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="219">
				<variables>l, size_f___r0_data, __i1, d___d2, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, size_f___r0_data, __i1, d___d2, d_f_tree_init_rsize, l_init, $t.i_init, $t.size_f_this_init_data, $t.d_v_init</inductives>
				<callee>bh.MathVector: void value(int,double)</callee>
				<constraints>
					<![CDATA[l == l_init &&  size_f___r0_data == 3 &&  __i1 == 1 &&  d_f_tree_init_rsize == 4 &&  l > size_f___r0_data &&  l > __i1 &&  l > d_f_tree_init_rsize &&  size_f___r0_data < d___d2 &&  __i1 < d___d2 &&  d___d2 > d_f_tree_init_rsize &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1 &&  $t.d_v_init == d___d2]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="221">
				<variables>l, size_f___r0_data, __i1, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, size_f___r0_data, __i1, d_f_tree_init_rsize, l_init, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[l == l_init &&  size_f___r0_data == 3 &&  __i1 == 2 &&  d_f_tree_init_rsize == 4 &&  l > size_f___r0_data &&  l > __i1 &&  l > d_f_tree_init_rsize &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="221">
				<variables>l, size_f___r4_data, __i0, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, size_f___r4_data, __i0, d_f_tree_init_rsize, l_init, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[l == l_init &&  size_f___r4_data == 3 &&  __i0 == 2 &&  d_f_tree_init_rsize == 4 &&  l > size_f___r4_data &&  l > __i0 &&  l > d_f_tree_init_rsize &&  $t.size_f_this_init_data == size_f___r4_data &&  $t.i_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="222">
				<variables>l, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, d_f_tree_init_rsize, l_init</inductives>
				<callee>java.lang.Math: double floor(double)</callee>
				<constraints>
					<![CDATA[l == l_init &&  d_f_tree_init_rsize == 4 &&  l > d_f_tree_init_rsize]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="222">
				<variables>l, size_f___r0_data, __i1, d___d2, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, size_f___r0_data, __i1, d___d2, d_f_tree_init_rsize, l_init, $t.i_init, $t.size_f_this_init_data, $t.d_v_init</inductives>
				<callee>bh.MathVector: void value(int,double)</callee>
				<constraints>
					<![CDATA[l == l_init &&  size_f___r0_data == 3 &&  __i1 == 2 &&  d_f_tree_init_rsize == 4 &&  l > size_f___r0_data &&  l > __i1 &&  l > d_f_tree_init_rsize &&  size_f___r0_data < d___d2 &&  __i1 < d___d2 &&  d___d2 > d_f_tree_init_rsize &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1 &&  $t.d_v_init == d___d2]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="226">
				<variables>l, k, __i1, d_f_tree_init_rsize, l_init</variables>
				<inductives>l, k, __i1, d_f_tree_init_rsize, l_init, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[l == l_init &&  k == __i1 &&  d_f_tree_init_rsize == 4 &&  l > k &&  l > d_f_tree_init_rsize &&  k < d_f_tree_init_rsize &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.lang.String toString()">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="264">
				<variables></variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="264">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="264">
				<variables>d_f___r3_mass</variables>
				<inductives>d_f___r3_mass, $t.d_f_this_init_mass</inductives>
				<callee>bh.Node: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[$t.d_f_this_init_mass == d_f___r3_mass]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="264">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="264">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.util.Enumeration elements()">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="185">
				<variables></variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="185">
				<variables></variables>
				<inductives></inductives>
				<callee>bh.Body$1$Enumerate: void &lt;init&gt;(bh.Body)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.util.Enumeration elementsRev()">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="202">
				<variables></variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="202">
				<variables></variables>
				<inductives></inductives>
				<callee>bh.Body$2$Enumerate: void &lt;init&gt;(bh.Body)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="22">
				<variables></variables>
				<inductives></inductives>
				<callee>bh.Node: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="24">
				<variables></variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="24">
				<variables></variables>
				<inductives></inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="25">
				<variables></variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="25">
				<variables></variables>
				<inductives></inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="26">
				<variables></variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="3" srccode-offset="26">
				<variables></variables>
				<inductives></inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void expandBox(bh.Tree,int)">
			<relevant-parameters>d_f_tree_init_rsize</relevant-parameters>
			 <creation-site offset="0" srccode-offset="75">
				<variables>d_f_tree_init_rsize</variables>
				<inductives>d_f_tree_init_rsize</inductives>
				<constraints>
					<![CDATA[d_f_tree_init_rsize == 4]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="75">
				<variables>d_f_tree_init_rsize</variables>
				<inductives>d_f_tree_init_rsize</inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[d_f_tree_init_rsize == 4]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="77">
				<variables>d_f___r3_rsize, d_f_tree_init_rsize</variables>
				<inductives>d_f___r3_rsize, d_f_tree_init_rsize, $t.d_f_tree_init_rsize</inductives>
				<callee>bh.Body: boolean icTest(bh.Tree)</callee>
				<constraints>
					<![CDATA[d_f___r3_rsize == d_f_tree_init_rsize &&  d_f___r3_rsize == 4 &&  $t.d_f_tree_init_rsize == d_f___r3_rsize]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="80">
				<variables>size_f___r0_data, size_f___r1_data, d___d0, d_f_tree_init_rsize</variables>
				<inductives>size_f___r0_data, size_f___r1_data, d___d0, d_f_tree_init_rsize, $t.size_f_u_init_data, $t.size_f_this_init_data, $t.d_s_init</inductives>
				<callee>bh.MathVector: void addScalar(bh.MathVector,double)</callee>
				<constraints>
					<![CDATA[$t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data &&  $t.d_s_init == d___d0]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="83">
				<variables>k, size_f___r0_data, __i2, d_f_tree_init_rsize</variables>
				<inductives>k, size_f___r0_data, __i2, d_f_tree_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[$t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i2]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="83">
				<variables>k, size_f___r5_data, __i0, d_f_tree_init_rsize</variables>
				<inductives>k, size_f___r5_data, __i0, d_f_tree_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[$t.size_f_this_init_data == size_f___r5_data &&  $t.i_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="84">
				<variables>k, size_f___r0_data, __i2, d_f_tree_init_rsize</variables>
				<inductives>k, size_f___r0_data, __i2, d_f_tree_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[$t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i2]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="85">
				<variables>k, size_f___r0_data, __i2, d___d0, d_f_tree_init_rsize</variables>
				<inductives>k, size_f___r0_data, __i2, d___d0, d_f_tree_init_rsize, $t.i_init, $t.size_f_this_init_data, $t.d_v_init</inductives>
				<callee>bh.MathVector: void value(int,double)</callee>
				<constraints>
					<![CDATA[$t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i2 &&  $t.d_v_init == d___d0]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="90">
				<variables>d_f_tree_init_rsize</variables>
				<inductives>d_f_tree_init_rsize, $t.d_f_this_init_rsize</inductives>
				<callee>bh.Tree: bh.MathVector intcoord(bh.MathVector)</callee>
				<constraints>
					<![CDATA[$t.d_f_this_init_rsize == d_f___r4_rsize]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="91">
				<variables>d_f_tree_init_rsize</variables>
				<inductives>d_f_tree_init_rsize</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="8" srccode-offset="91">
				<variables>d_f_tree_init_rsize</variables>
				<inductives>d_f_tree_init_rsize</inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="92">
				<variables>size_f___r0_data, __i2, d_f_tree_init_rsize</variables>
				<inductives>size_f___r0_data, __i2, d_f_tree_init_rsize, $t.size_f_ic_init_data, $t.l_init</inductives>
				<callee>bh.Node: int oldSubindex(bh.MathVector,int)</callee>
				<constraints>
					<![CDATA[$t.size_f_ic_init_data == size_f___r0_data &&  $t.l_init == __i2]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="93">
				<variables>d_f_tree_init_rsize</variables>
				<inductives>d_f_tree_init_rsize</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="10" srccode-offset="93">
				<variables>d_f_tree_init_rsize</variables>
				<inductives>d_f_tree_init_rsize</inductives>
				<callee>bh.Cell: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="96">
				<variables>d_f___r3_rsize, d_f_tree_init_rsize</variables>
				<inductives>d_f___r3_rsize, d_f_tree_init_rsize, $t.d_f_tree_init_rsize</inductives>
				<callee>bh.Body: boolean icTest(bh.Tree)</callee>
				<constraints>
					<![CDATA[$t.d_f_tree_init_rsize == d_f___r3_rsize]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void hackGravity(double,bh.Node)">
			<relevant-parameters>d_rsize_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="240">
				<variables>size_f___r5_data, d_rsize_init</variables>
				<inductives>size_f___r5_data, d_rsize_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: java.lang.Object clone()</callee>
				<constraints>
					<![CDATA[size_f___r5_data == 3 &&  d_rsize_init == 4 &&  $t.size_f_this_init_data == size_f___r5_data]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="242">
				<variables>d_rsize_init</variables>
				<inductives>d_rsize_init</inductives>
				<constraints>
					<![CDATA[d_rsize_init == 4]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="242">
				<variables>size_f___r8_data, d_rsize_init</variables>
				<inductives>size_f___r8_data, d_rsize_init, $t.size_f_p_init_data</inductives>
				<callee>bh.Node$HG: void &lt;init&gt;(bh.Node,bh.Body,bh.MathVector)</callee>
				<constraints>
					<![CDATA[size_f___r8_data == 3 &&  d_rsize_init == 4 &&  $t.size_f_p_init_data == size_f___r8_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="243">
				<variables>d_rsize_init</variables>
				<inductives>d_rsize_init</inductives>
				<callee>bh.Node: bh.Node$HG walkSubTree(double,bh.Node$HG)</callee>
				<constraints>
					<![CDATA[d_rsize_init == 4]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
