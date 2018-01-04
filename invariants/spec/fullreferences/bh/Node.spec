<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="bh.Node">
		 <method decl="bh.Node$HG gravSub(bh.Node$HG)">
			<relevant-parameters>d_f_this_init_mass, d_f_hg_init_phi0</relevant-parameters>
			 <creation-site offset="0" srccode-offset="60">
				<variables>d_f_this_init_mass, d_f_hg_init_phi0</variables>
				<inductives>d_f_this_init_mass, d_f_hg_init_phi0</inductives>
				<constraints>
					<![CDATA[d_f_this_init_mass == d_f_hg_init_phi0 &&  d_f_this_init_mass == 0]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="60">
				<variables>d_f_this_init_mass, d_f_hg_init_phi0</variables>
				<inductives>d_f_this_init_mass, d_f_hg_init_phi0</inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[d_f_this_init_mass == d_f_hg_init_phi0 &&  d_f_this_init_mass == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="61">
				<variables>size_f___r0_data, size_f___r1_data, size_f___r4_data, d_f_this_init_mass, d_f_hg_init_phi0</variables>
				<inductives>size_f___r0_data, size_f___r1_data, size_f___r4_data, d_f_this_init_mass, d_f_hg_init_phi0, $t.size_f_u_init_data, $t.size_f_v_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void subtraction(bh.MathVector,bh.MathVector)</callee>
				<constraints>
					<![CDATA[size_f___r0_data == size_f___r1_data &&  size_f___r0_data == size_f___r4_data &&  d_f_this_init_mass == d_f_hg_init_phi0 &&  size_f___r0_data == 3 &&  d_f_this_init_mass == 0 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data &&  $t.size_f_v_init_data == size_f___r4_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="63">
				<variables>size_f___r0_data, d_f_this_init_mass, d_f_hg_init_phi0</variables>
				<inductives>size_f___r0_data, d_f_this_init_mass, d_f_hg_init_phi0, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double dotProduct()</callee>
				<constraints>
					<![CDATA[d_f_this_init_mass == d_f_hg_init_phi0 &&  size_f___r0_data == 3 &&  d_f_this_init_mass == 0 &&  $t.size_f_this_init_data == size_f___r0_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="64">
				<variables>d_f_this_init_mass, d_f_hg_init_phi0</variables>
				<inductives>d_f_this_init_mass, d_f_hg_init_phi0</inductives>
				<callee>java.lang.Math: double sqrt(double)</callee>
				<constraints>
					<![CDATA[d_f_this_init_mass == d_f_hg_init_phi0 &&  d_f_this_init_mass == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="69">
				<variables>size_f___r0_data, d___d3, d_f_this_init_mass, d_f_hg_init_phi0</variables>
				<inductives>size_f___r0_data, d___d3, d_f_this_init_mass, d_f_hg_init_phi0, $t.size_f_this_init_data, $t.d_s_init</inductives>
				<callee>bh.MathVector: void multScalar(double)</callee>
				<constraints>
					<![CDATA[d___d3 == d_f_this_init_mass &&  d___d3 == d_f_hg_init_phi0 &&  size_f___r0_data == 3 &&  d___d3 == 0 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.d_s_init == d___d3]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="70">
				<variables>size_f___r0_data, size_f___r1_data, d_f_this_init_mass, d_f_hg_init_phi0</variables>
				<inductives>size_f___r0_data, size_f___r1_data, d_f_this_init_mass, d_f_hg_init_phi0, $t.size_f_u_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void addition(bh.MathVector)</callee>
				<constraints>
					<![CDATA[size_f___r0_data == size_f___r1_data &&  d_f_this_init_mass == d_f_hg_init_phi0 &&  size_f___r0_data == 3 &&  d_f_this_init_mass == 0 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="int oldSubindex(bh.MathVector,int)">
			<relevant-parameters>size_f_ic_init_data, l_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="40">
				<variables>l, k, __i1, size_f_ic_init_data, l_init</variables>
				<inductives>l, k, __i1, size_f_ic_init_data, l_init, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[l == l_init &&  k == __i1 &&  size_f_ic_init_data == 3 &&  l > k &&  l > size_f_ic_init_data &&  k < size_f_ic_init_data &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.lang.String toString()">
			<relevant-parameters>d_f_this_init_mass</relevant-parameters>
			 <creation-site offset="0" srccode-offset="52">
				<variables>d_f_this_init_mass</variables>
				<inductives>d_f_this_init_mass</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="52">
				<variables>d_f_this_init_mass</variables>
				<inductives>d_f_this_init_mass</inductives>
				<callee>java.lang.String: java.lang.String valueOf(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="52">
				<variables>d_f_this_init_mass</variables>
				<inductives>d_f_this_init_mass</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="52">
				<variables>d_f_this_init_mass</variables>
				<inductives>d_f_this_init_mass</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="52">
				<variables>d_f_this_init_mass</variables>
				<inductives>d_f_this_init_mass</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="52">
				<variables>d_f_this_init_mass</variables>
				<inductives>d_f_this_init_mass</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="26">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="29">
				<variables></variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="29">
				<variables></variables>
				<inductives></inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
