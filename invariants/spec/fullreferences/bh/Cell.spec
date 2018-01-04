<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="bh.Cell">
		 <method decl="bh.Node loadTree(bh.Body,bh.MathVector,int,bh.Tree)">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="38">
				<variables>size_f___r0_data, __i0, size_f_this_init_subp, l_init</variables>
				<inductives>size_f___r0_data, __i0, size_f_this_init_subp, l_init, $t.size_f_ic_init_data, $t.l_init</inductives>
				<callee>bh.Node: int oldSubindex(bh.MathVector,int)</callee>
				<constraints>
					<![CDATA[__i0 == l_init &&  size_f___r0_data == 3 &&  __i0 == 536870912 &&  size_f_this_init_subp == 8 &&  $t.size_f_ic_init_data == size_f___r0_data &&  $t.l_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="41">
				<variables>size_f_this_init_subp, l_init</variables>
				<inductives>size_f_this_init_subp, l_init</inductives>
				<callee>bh.Node: bh.Node loadTree(bh.Body,bh.MathVector,int,bh.Tree)</callee>
				<constraints>
					<![CDATA[size_f_this_init_subp == 8 &&  l_init == 536870912]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="bh.Node$HG walkSubTree(double,bh.Node$HG)">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="77">
				<variables>d___d0, size_f_this_init_subp, d_dsq_init</variables>
				<inductives>d___d0, size_f_this_init_subp, d_dsq_init, $t.d_dsq_init</inductives>
				<callee>bh.Cell: boolean subdivp(double,bh.Node$HG)</callee>
				<constraints>
					<![CDATA[d___d0 == d_dsq_init &&  d___d0 % 12 == 4 &&  size_f_this_init_subp == 8 &&  $t.d_dsq_init == d___d0]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="81">
				<variables>k, size_f_this_init_subp, d_dsq_init</variables>
				<inductives>k, size_f_this_init_subp, d_dsq_init</inductives>
				<callee>bh.Node: bh.Node$HG walkSubTree(double,bh.Node$HG)</callee>
				<constraints>
					<![CDATA[k >= 0 &&  size_f_this_init_subp == 8 &&  d_dsq_init % 12 == 4 &&  k < size_f_this_init_subp]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="84">
				<variables>d_f___r0_mass, d_f___r5_phi0, size_f_this_init_subp, d_dsq_init</variables>
				<inductives>d_f___r0_mass, d_f___r5_phi0, size_f_this_init_subp, d_dsq_init, $t.d_f_hg_init_phi0, $t.d_f_this_init_mass</inductives>
				<callee>bh.Node: bh.Node$HG gravSub(bh.Node$HG)</callee>
				<constraints>
					<![CDATA[d_f___r0_mass == d_f___r5_phi0 &&  d_f___r0_mass == 0 &&  size_f_this_init_subp == 8 &&  d_dsq_init == 4 &&  $t.d_f_this_init_mass == d_f___r0_mass &&  $t.d_f_hg_init_phi0 == d_f___r5_phi0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="boolean subdivp(double,bh.Node$HG)">
			<relevant-parameters>d_dsq_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="95">
				<variables>d_dsq_init</variables>
				<inductives>d_dsq_init</inductives>
				<constraints>
					<![CDATA[d_dsq_init % 12 == 4]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="95">
				<variables>d_dsq_init</variables>
				<inductives>d_dsq_init</inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[d_dsq_init % 12 == 4]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="96">
				<variables>size_f___r0_data, size_f___r1_data, size_f___r4_data, d_dsq_init</variables>
				<inductives>size_f___r0_data, size_f___r1_data, size_f___r4_data, d_dsq_init, $t.size_f_u_init_data, $t.size_f_v_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void subtraction(bh.MathVector,bh.MathVector)</callee>
				<constraints>
					<![CDATA[size_f___r0_data == size_f___r1_data &&  size_f___r0_data == size_f___r4_data &&  size_f___r0_data == 3 &&  d_dsq_init % 12 == 4 &&  size_f___r0_data < d_dsq_init &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data &&  $t.size_f_v_init_data == size_f___r4_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="97">
				<variables>size_f___r0_data, d_dsq_init</variables>
				<inductives>size_f___r0_data, d_dsq_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double dotProduct()</callee>
				<constraints>
					<![CDATA[size_f___r0_data == 3 &&  d_dsq_init % 12 == 4 &&  size_f___r0_data < d_dsq_init &&  $t.size_f_this_init_data == size_f___r0_data]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="double hackcofm()">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="54">
				<variables>d_f_this_init_mass, size_f_this_init_subp</variables>
				<inductives>d_f_this_init_mass, size_f_this_init_subp</inductives>
				<constraints>
					<![CDATA[d_f_this_init_mass == 0 &&  size_f_this_init_subp == 8]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="54">
				<variables>d_f_this_init_mass, size_f_this_init_subp</variables>
				<inductives>d_f_this_init_mass, size_f_this_init_subp</inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[d_f_this_init_mass == 0 &&  size_f_this_init_subp == 8]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="55">
				<variables>d_f_this_init_mass, size_f_this_init_subp</variables>
				<inductives>d_f_this_init_mass, size_f_this_init_subp</inductives>
				<constraints>
					<![CDATA[d_f_this_init_mass == 0 &&  size_f_this_init_subp == 8]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="55">
				<variables>d_f_this_init_mass, size_f_this_init_subp</variables>
				<inductives>d_f_this_init_mass, size_f_this_init_subp</inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[d_f_this_init_mass == 0 &&  size_f_this_init_subp == 8]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="59">
				<variables>i, d_f_this_init_mass, size_f_this_init_subp</variables>
				<inductives>i, d_f_this_init_mass, size_f_this_init_subp</inductives>
				<callee>bh.Node: double hackcofm()</callee>
				<constraints>
					<![CDATA[i >= 0 &&  d_f_this_init_mass == 0 &&  size_f_this_init_subp == 8 &&  i >= d_f_this_init_mass &&  i < size_f_this_init_subp]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="61">
				<variables>i, size_f___r1_data, size_f___r0_data, d___d1, d_f_this_init_mass, size_f_this_init_subp</variables>
				<inductives>i, size_f___r1_data, size_f___r0_data, d___d1, d_f_this_init_mass, size_f_this_init_subp, $t.size_f_u_init_data, $t.size_f_this_init_data, $t.d_s_init</inductives>
				<callee>bh.MathVector: void multScalar(bh.MathVector,double)</callee>
				<constraints>
					<![CDATA[size_f___r1_data == size_f___r0_data &&  d___d1 == d_f_this_init_mass &&  i >= 0 &&  size_f___r1_data == 3 &&  d___d1 == 0 &&  size_f_this_init_subp == 8 &&  i >= d___d1 &&  i < size_f_this_init_subp &&  $t.size_f_this_init_data == size_f___r1_data &&  $t.size_f_u_init_data == size_f___r0_data &&  $t.d_s_init == d___d1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="62">
				<variables>i, size_f___r1_data, size_f___r0_data, d_f_this_init_mass, size_f_this_init_subp</variables>
				<inductives>i, size_f___r1_data, size_f___r0_data, d_f_this_init_mass, size_f_this_init_subp, $t.size_f_u_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void addition(bh.MathVector)</callee>
				<constraints>
					<![CDATA[size_f___r1_data == size_f___r0_data &&  i >= 0 &&  size_f___r1_data == 3 &&  d_f_this_init_mass == 0 &&  size_f_this_init_subp == 8 &&  i >= d_f_this_init_mass &&  i < size_f_this_init_subp &&  $t.size_f_this_init_data == size_f___r1_data &&  $t.size_f_u_init_data == size_f___r0_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="67">
				<variables>size_f___r1_data, d___d2, d_f_this_init_mass, size_f_this_init_subp</variables>
				<inductives>size_f___r1_data, d___d2, d_f_this_init_mass, size_f_this_init_subp, $t.size_f_this_init_data, $t.d_s_init</inductives>
				<callee>bh.MathVector: void divScalar(double)</callee>
				<constraints>
					<![CDATA[size_f___r1_data == 3 &&  d_f_this_init_mass == 0 &&  size_f_this_init_subp == 8 &&  size_f___r1_data > d___d2 &&  d___d2 >= d_f_this_init_mass &&  d___d2 < size_f_this_init_subp &&  $t.size_f_this_init_data == size_f___r1_data &&  $t.d_s_init == d___d2]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.lang.String toString()">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="109">
				<variables></variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="109">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="109">
				<variables>d_f___r3_mass</variables>
				<inductives>d_f___r3_mass, $t.d_f_this_init_mass</inductives>
				<callee>bh.Node: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[$t.d_f_this_init_mass == d_f___r3_mass]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="109">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="109">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="19">
				<variables></variables>
				<inductives></inductives>
				<callee>bh.Node: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="22">
				<variables>__i0</variables>
				<inductives>__i0</inductives>
				<constraints>
					<![CDATA[__i0 == 8]]>
				</constraints>
			 </creation-site>
		</method>
	</class>
</spec>
