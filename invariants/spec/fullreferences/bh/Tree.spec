<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="bh.Tree">
		 <method decl="bh.MathVector intcoord(bh.MathVector)">
			<relevant-parameters>d_f_this_init_rsize</relevant-parameters>
			 <creation-site offset="0" srccode-offset="194">
				<variables>d_f_this_init_rsize</variables>
				<inductives>d_f_this_init_rsize</inductives>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="194">
				<variables>d_f_this_init_rsize</variables>
				<inductives>d_f_this_init_rsize</inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="196">
				<variables>size_f___r0_data, __i1, d_f_this_init_rsize</variables>
				<inductives>size_f___r0_data, __i1, d_f_this_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[size_f___r0_data == 3 &&  __i1 == 0 &&  d_f_this_init_rsize == 4 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="196">
				<variables>size_f___r3_data, __i0, d_f_this_init_rsize</variables>
				<inductives>size_f___r3_data, __i0, d_f_this_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[size_f___r3_data == 3 &&  __i0 == 0 &&  d_f_this_init_rsize == 4 &&  $t.size_f_this_init_data == size_f___r3_data &&  $t.i_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="198">
				<variables>d_f_this_init_rsize</variables>
				<inductives>d_f_this_init_rsize</inductives>
				<callee>java.lang.Math: double floor(double)</callee>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="198">
				<variables>size_f___r0_data, __i1, d___d2, d_f_this_init_rsize</variables>
				<inductives>size_f___r0_data, __i1, d___d2, d_f_this_init_rsize, $t.i_init, $t.size_f_this_init_data, $t.d_v_init</inductives>
				<callee>bh.MathVector: void value(int,double)</callee>
				<constraints>
					<![CDATA[size_f___r0_data == 3 &&  __i1 == 0 &&  d_f_this_init_rsize == 4 &&  size_f___r0_data < d___d2 &&  __i1 < d___d2 &&  d___d2 > d_f_this_init_rsize &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1 &&  $t.d_v_init == d___d2]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="203">
				<variables>size_f___r0_data, __i1, d_f_this_init_rsize</variables>
				<inductives>size_f___r0_data, __i1, d_f_this_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[size_f___r0_data == 3 &&  __i1 == 1 &&  d_f_this_init_rsize == 4 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="203">
				<variables>size_f___r3_data, __i0, d_f_this_init_rsize</variables>
				<inductives>size_f___r3_data, __i0, d_f_this_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[size_f___r3_data == 3 &&  __i0 == 1 &&  d_f_this_init_rsize == 4 &&  $t.size_f_this_init_data == size_f___r3_data &&  $t.i_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="205">
				<variables>d_f_this_init_rsize</variables>
				<inductives>d_f_this_init_rsize</inductives>
				<callee>java.lang.Math: double floor(double)</callee>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="205">
				<variables>size_f___r0_data, __i1, d___d2, d_f_this_init_rsize</variables>
				<inductives>size_f___r0_data, __i1, d___d2, d_f_this_init_rsize, $t.i_init, $t.size_f_this_init_data, $t.d_v_init</inductives>
				<callee>bh.MathVector: void value(int,double)</callee>
				<constraints>
					<![CDATA[size_f___r0_data == 3 &&  __i1 == 1 &&  d_f_this_init_rsize == 4 &&  size_f___r0_data < d___d2 &&  __i1 < d___d2 &&  d___d2 > d_f_this_init_rsize &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1 &&  $t.d_v_init == d___d2]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="210">
				<variables>size_f___r0_data, __i1, d_f_this_init_rsize</variables>
				<inductives>size_f___r0_data, __i1, d_f_this_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[size_f___r0_data == 3 &&  __i1 == 2 &&  d_f_this_init_rsize == 4 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="210">
				<variables>size_f___r3_data, __i0, d_f_this_init_rsize</variables>
				<inductives>size_f___r3_data, __i0, d_f_this_init_rsize, $t.i_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double value(int)</callee>
				<constraints>
					<![CDATA[size_f___r3_data == 3 &&  __i0 == 2 &&  d_f_this_init_rsize == 4 &&  $t.size_f_this_init_data == size_f___r3_data &&  $t.i_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="212">
				<variables>d_f_this_init_rsize</variables>
				<inductives>d_f_this_init_rsize</inductives>
				<callee>java.lang.Math: double floor(double)</callee>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="212">
				<variables>size_f___r0_data, __i1, d___d2, d_f_this_init_rsize</variables>
				<inductives>size_f___r0_data, __i1, d___d2, d_f_this_init_rsize, $t.i_init, $t.size_f_this_init_data, $t.d_v_init</inductives>
				<callee>bh.MathVector: void value(int,double)</callee>
				<constraints>
					<![CDATA[size_f___r0_data == 3 &&  __i1 == 2 &&  d_f_this_init_rsize == 4 &&  size_f___r0_data < d___d2 &&  __i1 < d___d2 &&  d___d2 > d_f_this_init_rsize &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i1 &&  $t.d_v_init == d___d2]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.util.Enumeration bodies()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="50">
				<variables></variables>
				<inductives></inductives>
				<callee>bh.Body: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.util.Enumeration bodiesRev()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="59">
				<variables></variables>
				<inductives></inductives>
				<callee>bh.Body: java.util.Enumeration elementsRev()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="29">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="31">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="32">
				<variables></variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="32">
				<variables></variables>
				<inductives></inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="33">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="39">
				<variables>size_f___r5_data, __i0, d___d1</variables>
				<inductives>size_f___r5_data, __i0, d___d1, $t.i_init, $t.size_f_this_init_data, $t.d_v_init</inductives>
				<callee>bh.MathVector: void value(int,double)</callee>
				<constraints>
					<![CDATA[size_f___r5_data == 3 &&  __i0 == 0 &&  d___d1 == -2 &&  $t.size_f_this_init_data == size_f___r5_data &&  $t.i_init == __i0 &&  $t.d_v_init == d___d1]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="40">
				<variables>size_f___r5_data, __i0, d___d1</variables>
				<inductives>size_f___r5_data, __i0, d___d1, $t.i_init, $t.size_f_this_init_data, $t.d_v_init</inductives>
				<callee>bh.MathVector: void value(int,double)</callee>
				<constraints>
					<![CDATA[size_f___r5_data == 3 &&  __i0 == 1 &&  d___d1 == -2 &&  $t.size_f_this_init_data == size_f___r5_data &&  $t.i_init == __i0 &&  $t.d_v_init == d___d1]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="41">
				<variables>size_f___r5_data, __i0, d___d1</variables>
				<inductives>size_f___r5_data, __i0, d___d1, $t.i_init, $t.size_f_this_init_data, $t.d_v_init</inductives>
				<callee>bh.MathVector: void value(int,double)</callee>
				<constraints>
					<![CDATA[size_f___r5_data == 3 &&  __i0 == 2 &&  d___d1 == -2 &&  $t.size_f_this_init_data == size_f___r5_data &&  $t.i_init == __i0 &&  $t.d_v_init == d___d1]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void createTestData(int)">
			<relevant-parameters>nbody_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="68">
				<variables>nbody, nbody_init</variables>
				<inductives>nbody, nbody_init</inductives>
				<constraints>
					<![CDATA[nbody == nbody_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="68">
				<variables>nbody, nbody_init</variables>
				<inductives>nbody, nbody_init</inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[nbody == nbody_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="69">
				<variables>nbody, nbody_init</variables>
				<inductives>nbody, nbody_init</inductives>
				<constraints>
					<![CDATA[nbody == nbody_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="69">
				<variables>nbody, nbody_init</variables>
				<inductives>nbody, nbody_init</inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[nbody == nbody_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="71">
				<variables>nbody, nbody_init</variables>
				<inductives>nbody, nbody_init</inductives>
				<constraints>
					<![CDATA[nbody == nbody_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="71">
				<variables>nbody, nbody_init</variables>
				<inductives>nbody, nbody_init</inductives>
				<callee>bh.Body: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[nbody == nbody_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="75">
				<variables>nbody, nbody_init</variables>
				<inductives>nbody, nbody_init</inductives>
				<callee>java.lang.Math: double sqrt(double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="79">
				<variables>nbody, i, nbody_init</variables>
				<inductives>nbody, i, nbody_init</inductives>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i]]>
				</constraints>
			 </creation-site>
			 <call-site offset="4" srccode-offset="79">
				<variables>nbody, i, nbody_init</variables>
				<inductives>nbody, i, nbody_init</inductives>
				<callee>bh.Body: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="81">
				<variables>nbody, i, nbody_init</variables>
				<inductives>nbody, i, nbody_init</inductives>
				<callee>bh.Body: void setNext(bh.Body)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="85">
				<variables>nbody, i, d___d4, nbody_init</variables>
				<inductives>nbody, i, d___d4, nbody_init, $t.d_seed_init</inductives>
				<callee>bh.BH: double myRand(double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i &&  nbody < d___d4 &&  i < d___d4 &&  $t.d_seed_init == d___d4]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="86">
				<variables>nbody, i, d___d4, d___d0, d___d1, nbody_init</variables>
				<inductives>nbody, i, d___d4, d___d0, d___d1, nbody_init, $t.d_r_init, $t.d_xh_init, $t.d_xl_init</inductives>
				<callee>bh.BH: double xRand(double,double,double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  d___d4 == d___d0 &&  i >= 0 &&  d___d4 == 0 &&  nbody > i &&  nbody > d___d4 &&  nbody < d___d1 &&  i >= d___d4 &&  i < d___d1 &&  d___d4 < d___d1 &&  $t.d_xl_init == d___d4 &&  $t.d_xh_init == d___d0 &&  $t.d_r_init == d___d1]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="87">
				<variables>nbody, i, nbody_init</variables>
				<inductives>nbody, i, nbody_init</inductives>
				<callee>java.lang.Math: double pow(double,double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="88">
				<variables>nbody, i, nbody_init</variables>
				<inductives>nbody, i, nbody_init</inductives>
				<callee>java.lang.Math: double sqrt(double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="92">
				<variables>nbody, i, k, d___d4, nbody_init</variables>
				<inductives>nbody, i, k, d___d4, nbody_init, $t.d_seed_init</inductives>
				<callee>bh.BH: double myRand(double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i &&  nbody < d___d4 &&  i < d___d4 &&  k < d___d4 &&  $t.d_seed_init == d___d4]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="93">
				<variables>nbody, i, k, d___d4, d___d0, d___d1, nbody_init</variables>
				<inductives>nbody, i, k, d___d4, d___d0, d___d1, nbody_init, $t.d_r_init, $t.d_xh_init, $t.d_xl_init</inductives>
				<callee>bh.BH: double xRand(double,double,double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  d___d4 == d___d0 &&  i >= 0 &&  d___d4 == 0 &&  nbody > i &&  nbody > d___d4 &&  nbody < d___d1 &&  i >= d___d4 &&  i < d___d1 &&  k >= d___d4 &&  k < d___d1 &&  d___d4 < d___d1 &&  $t.d_xl_init == d___d4 &&  $t.d_xh_init == d___d0 &&  $t.d_r_init == d___d1]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="94">
				<variables>nbody, i, k, size_f___r0_data, __i2, d___d0, nbody_init</variables>
				<inductives>nbody, i, k, size_f___r0_data, __i2, d___d0, nbody_init, $t.i_init, $t.size_f_this_init_data, $t.d_v_init</inductives>
				<callee>bh.MathVector: void value(int,double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  k == __i2 &&  i >= 0 &&  size_f___r0_data == 3 &&  d___d0 >= 0 &&  nbody > i &&  k < size_f___r0_data &&  size_f___r0_data >= d___d0 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i2 &&  $t.d_v_init == d___d0]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="97">
				<variables>nbody, i, size_f___r0_data, size_f___r1_data, nbody_init</variables>
				<inductives>nbody, i, size_f___r0_data, size_f___r1_data, nbody_init, $t.size_f_u_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void addition(bh.MathVector)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  size_f___r0_data == size_f___r1_data &&  i >= 0 &&  size_f___r0_data == 3 &&  nbody > i &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="101">
				<variables>nbody, i, d___d4, nbody_init</variables>
				<inductives>nbody, i, d___d4, nbody_init, $t.d_seed_init</inductives>
				<callee>bh.BH: double myRand(double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i &&  nbody < d___d4 &&  i < d___d4 &&  $t.d_seed_init == d___d4]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="102">
				<variables>nbody, i, d___d4, d___d0, d___d1, nbody_init</variables>
				<inductives>nbody, i, d___d4, d___d0, d___d1, nbody_init, $t.d_r_init, $t.d_xh_init, $t.d_xl_init</inductives>
				<callee>bh.BH: double xRand(double,double,double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  d___d4 == 0 &&  d___d0 == 1 &&  nbody > i &&  nbody > d___d4 &&  nbody >= d___d0 &&  nbody < d___d1 &&  i >= d___d4 &&  i < d___d1 &&  d___d4 < d___d1 &&  d___d0 < d___d1 &&  $t.d_xl_init == d___d4 &&  $t.d_xh_init == d___d0 &&  $t.d_r_init == d___d1]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="103">
				<variables>nbody, i, d___d4, nbody_init</variables>
				<inductives>nbody, i, d___d4, nbody_init, $t.d_seed_init</inductives>
				<callee>bh.BH: double myRand(double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i &&  nbody < d___d4 &&  i < d___d4 &&  $t.d_seed_init == d___d4]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="104">
				<variables>nbody, i, d___d4, d___d0, d___d1, nbody_init</variables>
				<inductives>nbody, i, d___d4, d___d0, d___d1, nbody_init, $t.d_r_init, $t.d_xh_init, $t.d_xl_init</inductives>
				<callee>bh.BH: double xRand(double,double,double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  d___d4 == d___d0 &&  i >= 0 &&  d___d4 == 0 &&  nbody > i &&  nbody > d___d4 &&  nbody < d___d1 &&  i >= d___d4 &&  i < d___d1 &&  d___d4 < d___d1 &&  $t.d_xl_init == d___d4 &&  $t.d_xh_init == d___d0 &&  $t.d_r_init == d___d1]]>
				</constraints>
			 </call-site>
			 <call-site offset="18" srccode-offset="105">
				<variables>nbody, i, nbody_init</variables>
				<inductives>nbody, i, nbody_init</inductives>
				<callee>java.lang.Math: double pow(double,double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i]]>
				</constraints>
			 </call-site>
			 <call-site offset="19" srccode-offset="107">
				<variables>nbody, i, nbody_init</variables>
				<inductives>nbody, i, nbody_init</inductives>
				<callee>java.lang.Math: double sqrt(double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="107">
				<variables>nbody, i, nbody_init</variables>
				<inductives>nbody, i, nbody_init</inductives>
				<callee>java.lang.Math: double pow(double,double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i]]>
				</constraints>
			 </call-site>
			 <call-site offset="21" srccode-offset="113">
				<variables>nbody, i, k, d___d4, nbody_init</variables>
				<inductives>nbody, i, k, d___d4, nbody_init, $t.d_seed_init</inductives>
				<callee>bh.BH: double myRand(double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i &&  nbody < d___d4 &&  i < d___d4 &&  k < d___d4 &&  $t.d_seed_init == d___d4]]>
				</constraints>
			 </call-site>
			 <call-site offset="22" srccode-offset="114">
				<variables>nbody, i, k, d___d0, d___d1, d___d2, nbody_init</variables>
				<inductives>nbody, i, k, d___d0, d___d1, d___d2, nbody_init, $t.d_r_init, $t.d_xh_init, $t.d_xl_init</inductives>
				<callee>bh.BH: double xRand(double,double,double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  d___d0 == -1 &&  d___d1 == 1 &&  nbody > i &&  nbody > d___d0 &&  nbody >= d___d1 &&  nbody < d___d2 &&  i > d___d0 &&  i < d___d2 &&  k > d___d0 &&  k < d___d2 &&  d___d0 < d___d2 &&  d___d1 < d___d2 &&  $t.d_xl_init == d___d0 &&  $t.d_xh_init == d___d1 &&  $t.d_r_init == d___d2]]>
				</constraints>
			 </call-site>
			 <call-site offset="23" srccode-offset="114">
				<variables>nbody, i, k, size_f___r0_data, __i2, d___d0, nbody_init</variables>
				<inductives>nbody, i, k, size_f___r0_data, __i2, d___d0, nbody_init, $t.i_init, $t.size_f_this_init_data, $t.d_v_init</inductives>
				<callee>bh.MathVector: void value(int,double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  k == __i2 &&  i >= 0 &&  size_f___r0_data == 3 &&  d___d0 == 0 &&  nbody > i &&  nbody > d___d0 &&  i >= d___d0 &&  k < size_f___r0_data &&  k >= d___d0 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.i_init == __i2 &&  $t.d_v_init == d___d0]]>
				</constraints>
			 </call-site>
			 <call-site offset="24" srccode-offset="116">
				<variables>nbody, i, size_f___r0_data, nbody_init</variables>
				<inductives>nbody, i, size_f___r0_data, nbody_init, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: double dotProduct()</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  size_f___r0_data == 3 &&  nbody > i &&  $t.size_f_this_init_data == size_f___r0_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="25" srccode-offset="118">
				<variables>nbody, i, nbody_init</variables>
				<inductives>nbody, i, nbody_init</inductives>
				<callee>java.lang.Math: double sqrt(double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  nbody > i]]>
				</constraints>
			 </call-site>
			 <call-site offset="26" srccode-offset="119">
				<variables>nbody, i, size_f___r0_data, d___d5, nbody_init</variables>
				<inductives>nbody, i, size_f___r0_data, d___d5, nbody_init, $t.size_f_this_init_data, $t.d_s_init</inductives>
				<callee>bh.MathVector: void multScalar(double)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  i >= 0 &&  size_f___r0_data == 3 &&  nbody > i &&  nbody >= d___d5 &&  size_f___r0_data > d___d5 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.d_s_init == d___d5]]>
				</constraints>
			 </call-site>
			 <call-site offset="27" srccode-offset="120">
				<variables>nbody, i, size_f___r0_data, size_f___r1_data, nbody_init</variables>
				<inductives>nbody, i, size_f___r0_data, size_f___r1_data, nbody_init, $t.size_f_u_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void addition(bh.MathVector)</callee>
				<constraints>
					<![CDATA[nbody == nbody_init &&  size_f___r0_data == size_f___r1_data &&  i >= 0 &&  size_f___r0_data == 3 &&  nbody > i &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="28" srccode-offset="124">
				<variables>nbody_init</variables>
				<inductives>nbody_init</inductives>
				<callee>bh.Body: void setNext(bh.Body)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="29" srccode-offset="126">
				<variables>nbody_init</variables>
				<inductives>nbody_init</inductives>
				<callee>bh.Body: bh.Body getNext()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="30" srccode-offset="128">
				<variables>size_f___r0_data, d___d5, nbody_init</variables>
				<inductives>size_f___r0_data, d___d5, nbody_init, $t.size_f_this_init_data, $t.d_s_init</inductives>
				<callee>bh.MathVector: void divScalar(double)</callee>
				<constraints>
					<![CDATA[d___d5 == nbody_init &&  size_f___r0_data == 3 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.d_s_init == d___d5]]>
				</constraints>
			 </call-site>
			 <call-site offset="31" srccode-offset="129">
				<variables>size_f___r0_data, d___d5, nbody_init</variables>
				<inductives>size_f___r0_data, d___d5, nbody_init, $t.size_f_this_init_data, $t.d_s_init</inductives>
				<callee>bh.MathVector: void divScalar(double)</callee>
				<constraints>
					<![CDATA[d___d5 == nbody_init &&  size_f___r0_data == 3 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.d_s_init == d___d5]]>
				</constraints>
			 </call-site>
			 <call-site offset="32" srccode-offset="133">
				<variables>nbody_init</variables>
				<inductives>nbody_init</inductives>
				<callee>bh.Body: java.util.Enumeration elements()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="37" srccode-offset="133">
				<variables>nbody_init, cont___r5</variables>
				<inductives>nbody_init, cont___r5</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[cont___r5 == 0 &&  nbody_init > cont___r5]]>
				</constraints>
			 </call-site>
			 <call-site offset="33" srccode-offset="134">
				<variables>nbody_init, cont___r5</variables>
				<inductives>nbody_init, cont___r5</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[cont___r5 == 1 &&  nbody_init >= cont___r5]]>
				</constraints>
			 </call-site>
			 <call-site offset="34" srccode-offset="135">
				<variables>size_f___r0_data, size_f___r1_data, nbody_init, cont___r5</variables>
				<inductives>size_f___r0_data, size_f___r1_data, nbody_init, cont___r5, $t.size_f_u_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void subtraction(bh.MathVector)</callee>
				<constraints>
					<![CDATA[size_f___r0_data == size_f___r1_data &&  size_f___r0_data == 3 &&  cont___r5 == 1 &&  nbody_init >= cont___r5 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="35" srccode-offset="136">
				<variables>size_f___r0_data, size_f___r1_data, nbody_init, cont___r5</variables>
				<inductives>size_f___r0_data, size_f___r1_data, nbody_init, cont___r5, $t.size_f_u_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void subtraction(bh.MathVector)</callee>
				<constraints>
					<![CDATA[size_f___r0_data == size_f___r1_data &&  size_f___r0_data == 3 &&  cont___r5 == 1 &&  nbody_init >= cont___r5 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="36" srccode-offset="137">
				<variables>nbody_init, cont___r5</variables>
				<inductives>nbody_init, cont___r5</inductives>
				<callee>bh.Body: void setProcNext(bh.Body)</callee>
				<constraints>
					<![CDATA[cont___r5 == 1 &&  nbody_init >= cont___r5]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void makeTree(int,int)">
			<relevant-parameters>d_f_this_init_rsize, nstep_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="173">
				<variables>d_f_this_init_rsize, nstep_init</variables>
				<inductives>d_f_this_init_rsize, nstep_init</inductives>
				<callee>bh.Tree: java.util.Enumeration bodiesRev()</callee>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4 &&  nstep_init >= 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="173">
				<variables>d_f_this_init_rsize, nstep_init, cont___r4</variables>
				<inductives>d_f_this_init_rsize, nstep_init, cont___r4</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4 &&  nstep_init >= 0 &&  cont___r4 == 0 &&  nstep_init >= cont___r4]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="174">
				<variables>d_f_this_init_rsize, nstep_init, cont___r4</variables>
				<inductives>d_f_this_init_rsize, nstep_init, cont___r4</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4 &&  nstep_init >= 0 &&  cont___r4 == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="176">
				<variables>d_f___r1_rsize, d_f_this_init_rsize, nstep_init, cont___r4</variables>
				<inductives>d_f___r1_rsize, d_f_this_init_rsize, nstep_init, cont___r4, $t.d_f_tree_init_rsize</inductives>
				<callee>bh.Body: void expandBox(bh.Tree,int)</callee>
				<constraints>
					<![CDATA[d_f___r1_rsize == d_f_this_init_rsize &&  d_f___r1_rsize == 4 &&  nstep_init >= 0 &&  cont___r4 == 1 &&  $t.d_f_tree_init_rsize == d_f___r1_rsize]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="177">
				<variables>d_f___r0_rsize, size_f___r8_data, d_f_this_init_rsize, nstep_init, cont___r4</variables>
				<inductives>d_f___r0_rsize, size_f___r8_data, d_f_this_init_rsize, nstep_init, cont___r4, $t.d_f_this_init_rsize</inductives>
				<callee>bh.Tree: bh.MathVector intcoord(bh.MathVector)</callee>
				<constraints>
					<![CDATA[d_f___r0_rsize == d_f_this_init_rsize &&  d_f___r0_rsize == 4 &&  size_f___r8_data == 3 &&  nstep_init >= 0 &&  cont___r4 == 1 &&  $t.d_f_this_init_rsize == d_f___r0_rsize]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="181">
				<variables>d_f_this_init_rsize, nstep_init, cont___r4</variables>
				<inductives>d_f_this_init_rsize, nstep_init, cont___r4</inductives>
				<callee>bh.Node: bh.Node loadTree(bh.Body,bh.MathVector,int,bh.Tree)</callee>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4 &&  nstep_init >= 0 &&  cont___r4 == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="185">
				<variables>d_f_this_init_rsize, nstep_init, cont___r4</variables>
				<inductives>d_f_this_init_rsize, nstep_init, cont___r4</inductives>
				<callee>bh.Node: double hackcofm()</callee>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4 &&  nstep_init >= 0 &&  cont___r4 == 0 &&  nstep_init >= cont___r4]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void stepSystem(int,int)">
			<relevant-parameters>d_f_this_init_rsize, nstep_init, nbody_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="155">
				<variables>d_f___r0_rsize, __i1, d_f_this_init_rsize, nstep_init, nbody_init</variables>
				<inductives>d_f___r0_rsize, __i1, d_f_this_init_rsize, nstep_init, nbody_init, $t.d_f_this_init_rsize, $t.nstep_init</inductives>
				<callee>bh.Tree: void makeTree(int,int)</callee>
				<constraints>
					<![CDATA[d_f___r0_rsize == d_f_this_init_rsize &&  __i1 == nstep_init &&  d_f___r0_rsize == 4 &&  __i1 >= 0 &&  nbody_init >= 1 &&  $t.d_f_this_init_rsize == d_f___r0_rsize &&  $t.nstep_init == __i1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="158">
				<variables>d_f_this_init_rsize, nstep_init, nbody_init</variables>
				<inductives>d_f_this_init_rsize, nstep_init, nbody_init</inductives>
				<callee>bh.Body: java.util.Enumeration elementsRev()</callee>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4 &&  nstep_init >= 0 &&  nbody_init >= 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="158">
				<variables>d_f_this_init_rsize, nstep_init, nbody_init, cont___r3</variables>
				<inductives>d_f_this_init_rsize, nstep_init, nbody_init, cont___r3</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4 &&  nstep_init >= 0 &&  cont___r3 == 0 &&  nstep_init >= cont___r3 &&  nbody_init > cont___r3]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="159">
				<variables>d_f_this_init_rsize, nstep_init, nbody_init, cont___r3</variables>
				<inductives>d_f_this_init_rsize, nstep_init, nbody_init, cont___r3</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[d_f_this_init_rsize == 4 &&  nstep_init >= 0 &&  cont___r3 >= 1 &&  nbody_init >= cont___r3]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="160">
				<variables>d___d0, d_f_this_init_rsize, nstep_init, nbody_init, cont___r3</variables>
				<inductives>d___d0, d_f_this_init_rsize, nstep_init, nbody_init, cont___r3, $t.d_rsize_init</inductives>
				<callee>bh.Body: void hackGravity(double,bh.Node)</callee>
				<constraints>
					<![CDATA[d___d0 == d_f_this_init_rsize &&  d___d0 == 4 &&  nstep_init >= 0 &&  cont___r3 >= 1 &&  nbody_init >= cont___r3 &&  $t.d_rsize_init == d___d0]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="163">
				<variables>__i1, d_f_this_init_rsize, nstep_init, nbody_init, cont___r3</variables>
				<inductives>__i1, d_f_this_init_rsize, nstep_init, nbody_init, cont___r3, $t.nstep_init, $t.nbody_init</inductives>
				<callee>bh.Tree: void vp(bh.Body,int,int)</callee>
				<constraints>
					<![CDATA[__i1 == nstep_init &&  __i1 >= 0 &&  d_f_this_init_rsize == 4 &&  nbody_init >= 1 &&  cont___r3 == 0 &&  __i1 >= cont___r3 &&  nbody_init > cont___r3 &&  $t.nstep_init == __i1 &&  $t.nbody_init == nbody_init]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void vp(bh.Body,int,int)">
			<relevant-parameters>nstep_init, nbody_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="221">
				<variables>nstep, nstep_init</variables>
				<inductives>nstep, nstep_init</inductives>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="221">
				<variables>nstep, nstep_init</variables>
				<inductives>nstep, nstep_init</inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="222">
				<variables>nstep, nstep_init</variables>
				<inductives>nstep, nstep_init</inductives>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="222">
				<variables>nstep, nstep_init</variables>
				<inductives>nstep, nstep_init</inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="225">
				<variables>nstep, nstep_init</variables>
				<inductives>nstep, nstep_init</inductives>
				<callee>bh.Body: java.util.Enumeration elementsRev()</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="19" srccode-offset="225">
				<variables>nstep, nstep_init, cont___r3</variables>
				<inductives>nstep, nstep_init, cont___r3</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0 &&  cont___r3 == 0 &&  nstep >= cont___r3]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="226">
				<variables>nstep, nstep_init, cont___r3</variables>
				<inductives>nstep, nstep_init, cont___r3</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0 &&  cont___r3 >= 1 &&  cont___r3 <= nbody_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="227">
				<variables>nbody_init, nstep, size_f___r0_data, nstep_init, cont___r3</variables>
				<inductives>nbody_init, nstep, size_f___r0_data, nstep_init, cont___r3, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: java.lang.Object clone()</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0 &&  size_f___r0_data == 3 &&  cont___r3 >= 1 &&  cont___r3 <= nbody_init &&  $t.size_f_this_init_data == size_f___r0_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="229">
				<variables>nstep, size_f___r0_data, size_f___r1_data, size_f___r6_data, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r0_data, size_f___r1_data, size_f___r6_data, nstep_init, cont___r3, $t.size_f_u_init_data, $t.size_f_v_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void subtraction(bh.MathVector,bh.MathVector)</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  size_f___r0_data == size_f___r1_data &&  size_f___r0_data == size_f___r6_data &&  nstep >= 1 &&  size_f___r0_data == 3 &&  cont___r3 >= 1 &&  nbody_init >= cont___r3 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data &&  $t.size_f_v_init_data == size_f___r6_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="230">
				<variables>nstep, size_f___r0_data, size_f___r1_data, d___d0, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r0_data, size_f___r1_data, d___d0, nstep_init, cont___r3, $t.size_f_u_init_data, $t.size_f_this_init_data, $t.d_s_init</inductives>
				<callee>bh.MathVector: void multScalar(bh.MathVector,double)</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  size_f___r0_data == size_f___r1_data &&  nstep >= 1 &&  size_f___r0_data == 3 &&  d___d0 == 0 &&  cont___r3 >= 1 &&  nstep > d___d0 &&  nbody_init >= cont___r3 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data &&  $t.d_s_init == d___d0]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="231">
				<variables>nstep, size_f___r0_data, size_f___r1_data, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r0_data, size_f___r1_data, nstep_init, cont___r3, $t.size_f_u_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void addition(bh.MathVector)</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  size_f___r0_data == size_f___r1_data &&  nstep >= 1 &&  size_f___r0_data == 3 &&  cont___r3 >= 1 &&  nbody_init >= cont___r3 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="232">
				<variables>nstep, size_f___r1_data, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r1_data, nstep_init, cont___r3, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: java.lang.Object clone()</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 1 &&  size_f___r1_data == 3 &&  cont___r3 >= 1 &&  nbody_init >= cont___r3 &&  $t.size_f_this_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="234">
				<variables>nstep, size_f___r1_data, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r1_data, nstep_init, cont___r3, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: java.lang.Object clone()</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0 &&  size_f___r1_data == 3 &&  cont___r3 >= 1 &&  cont___r3 <= nbody_init &&  $t.size_f_this_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="235">
				<variables>nstep, size_f___r0_data, size_f___r1_data, d___d0, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r0_data, size_f___r1_data, d___d0, nstep_init, cont___r3, $t.size_f_u_init_data, $t.size_f_this_init_data, $t.d_s_init</inductives>
				<callee>bh.MathVector: void multScalar(bh.MathVector,double)</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  size_f___r0_data == size_f___r1_data &&  nstep >= 0 &&  size_f___r0_data == 3 &&  d___d0 == 0 &&  cont___r3 >= 1 &&  cont___r3 <= nbody_init &&  nstep >= d___d0 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data &&  $t.d_s_init == d___d0]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="237">
				<variables>nstep, size_f___r0_data, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r0_data, nstep_init, cont___r3, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: java.lang.Object clone()</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0 &&  size_f___r0_data == 3 &&  cont___r3 >= 1 &&  cont___r3 <= nbody_init &&  $t.size_f_this_init_data == size_f___r0_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="238">
				<variables>nstep, size_f___r0_data, size_f___r1_data, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r0_data, size_f___r1_data, nstep_init, cont___r3, $t.size_f_u_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void addition(bh.MathVector)</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  size_f___r0_data == size_f___r1_data &&  nstep >= 0 &&  size_f___r0_data == 3 &&  cont___r3 >= 1 &&  cont___r3 <= nbody_init &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="239">
				<variables>nstep, size_f___r0_data, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r0_data, nstep_init, cont___r3, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: java.lang.Object clone()</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0 &&  size_f___r0_data == 3 &&  cont___r3 >= 1 &&  cont___r3 <= nbody_init &&  $t.size_f_this_init_data == size_f___r0_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="240">
				<variables>nstep, size_f___r0_data, d___d2, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r0_data, d___d2, nstep_init, cont___r3, $t.size_f_this_init_data, $t.d_s_init</inductives>
				<callee>bh.MathVector: void multScalar(double)</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0 &&  size_f___r0_data == 3 &&  d___d2 == 0 &&  cont___r3 == 1 &&  nstep >= d___d2 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.d_s_init == d___d2]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="241">
				<variables>nstep, size_f___r0_data, size_f___r1_data, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r0_data, size_f___r1_data, nstep_init, cont___r3, $t.size_f_u_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void addition(bh.MathVector)</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  size_f___r0_data == size_f___r1_data &&  nstep >= 0 &&  size_f___r0_data == 3 &&  cont___r3 == 1 &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="242">
				<variables>nstep, size_f___r1_data, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r1_data, nstep_init, cont___r3, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: java.lang.Object clone()</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0 &&  size_f___r1_data == 3 &&  cont___r3 == 1 &&  $t.size_f_this_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="243">
				<variables>nstep, size_f___r0_data, size_f___r1_data, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r0_data, size_f___r1_data, nstep_init, cont___r3, $t.size_f_u_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: void addition(bh.MathVector)</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  size_f___r0_data == size_f___r1_data &&  nstep >= 0 &&  size_f___r0_data == 3 &&  cont___r3 >= 1 &&  cont___r3 <= nbody_init &&  $t.size_f_this_init_data == size_f___r0_data &&  $t.size_f_u_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
			 <call-site offset="18" srccode-offset="244">
				<variables>nstep, size_f___r1_data, nstep_init, cont___r3</variables>
				<inductives>nstep, size_f___r1_data, nstep_init, cont___r3, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: java.lang.Object clone()</callee>
				<constraints>
					<![CDATA[nstep == nstep_init &&  nstep >= 0 &&  size_f___r1_data == 3 &&  cont___r3 == 1 &&  $t.size_f_this_init_data == size_f___r1_data]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
