<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="bh.BH">
		 <method decl="double myRand(double)">
			<relevant-parameters>d_seed_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="98">
				<variables>d_seed_init</variables>
				<inductives>d_seed_init</inductives>
				<callee>java.lang.Math: double floor(double)</callee>
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
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init, nb, ns</relevant-parameters>
			 <call-site offset="0" srccode-offset="87">
				<variables>size___r0, size_args_init</variables>
				<inductives>size_args_init, $t.size_args_init, $t.__i1, $t.__i0  </inductives>
				<callee>bh.BH: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[$t.size_args_init == size_args_init && $t.__i1 == nb && $t.__i0 == ns]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>size_args_init, __i1, __i0</relevant-parameters>
			 <call-site offset="0" srccode-offset="45">
				<variables>size___r0, size_args_init</variables>
				<inductives>size___r0, size_args_init, $t.size_args_init</inductives>
				<callee>bh.BH: void parseCmdLine(java.lang.String[])</callee>
				<constraints>
					<![CDATA[size___r0 == size_args_init &&  size___r0 == 4 &&  $t.size_args_init == size___r0]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="46">
				<variables>__i1, __i0, size_args_init</variables>
				<inductives>__i1, __i0, size_args_init, $t.nsteps_init, $t.nbody_init</inductives>
				<callee>bh.BH: void mainParameters(int,int,boolean,boolean)</callee>
				<constraints>
					<![CDATA[size_args_init == 4 &&  __i1 - __i0 + 10 == 0 &&  $t.nbody_init == __i1 &&  $t.nsteps_init == __i0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int,int,boolean,boolean)">
			<relevant-parameters>nbody_init, nsteps_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="52">
				<variables>nsteps, nbody_init, nsteps_init</variables>
				<inductives>nsteps, nbody_init, nsteps_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="52">
				<variables>nsteps, nbody_init, nsteps_init</variables>
				<inductives>nsteps, nbody_init, nsteps_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="52">
				<variables>nsteps, nbody_init, nsteps_init</variables>
				<inductives>nsteps, nbody_init, nsteps_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="52">
				<variables>nsteps, nbody_init, nsteps_init</variables>
				<inductives>nsteps, nbody_init, nsteps_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="52">
				<variables>nsteps, nbody_init, nsteps_init</variables>
				<inductives>nsteps, nbody_init, nsteps_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="54">
				<variables>nsteps, nbody_init, nsteps_init</variables>
				<inductives>nsteps, nbody_init, nsteps_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[nsteps == nsteps_init &&  nsteps - nbody_init - 10 == 0]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="55">
				<variables>nsteps, nbody_init, nsteps_init</variables>
				<inductives>nsteps, nbody_init, nsteps_init</inductives>
				<constraints>
					<![CDATA[nsteps == nsteps_init &&  nsteps - nbody_init - 10 == 0]]>
				</constraints>
			 </creation-site>
			 <call-site offset="5" srccode-offset="55">
				<variables>nsteps, nbody_init, nsteps_init</variables>
				<inductives>nsteps, nbody_init, nsteps_init</inductives>
				<callee>bh.Tree: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[nsteps == nsteps_init &&  nsteps - nbody_init - 10 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="56">
				<variables>nsteps, __i2, nbody_init, nsteps_init</variables>
				<inductives>nsteps, __i2, nbody_init, nsteps_init, $t.nbody_init</inductives>
				<callee>bh.Tree: void createTestData(int)</callee>
				<constraints>
					<![CDATA[nsteps == nsteps_init &&  __i2 == nbody_init &&  nsteps - __i2 - 10 == 0 &&  $t.nbody_init == __i2]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="57">
				<variables>nsteps, nbody_init, nsteps_init</variables>
				<inductives>nsteps, nbody_init, nsteps_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[nsteps == nsteps_init &&  nsteps - nbody_init - 10 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="59">
				<variables>nsteps, nbody_init, nsteps_init</variables>
				<inductives>nsteps, nbody_init, nsteps_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="61">
				<variables>nsteps, nbody_init, nsteps_init</variables>
				<inductives>nsteps, nbody_init, nsteps_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[nsteps == nsteps_init &&  nsteps - nbody_init - 10 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="65">
				<variables>nsteps, i, d_f___r5_rsize, __i2, __i0, nbody_init, nsteps_init</variables>
				<inductives>nsteps, i, d_f___r5_rsize, __i2, __i0, nbody_init, nsteps_init, $t.d_f_this_init_rsize, $t.nstep_init, $t.nbody_init</inductives>
				<callee>bh.Tree: void stepSystem(int,int)</callee>
				<constraints>
					<![CDATA[nsteps == nsteps_init &&  __i0 == nbody_init &&  i >= 1 &&  d_f___r5_rsize == 4 &&  __i2 >= 0 &&  __i0 >= 1 &&  nsteps >= i &&  nsteps > d_f___r5_rsize &&  nsteps > __i2 &&  nsteps - __i0 - 10 == 0 &&  i - __i2 - 1 == 0 &&  $t.d_f_this_init_rsize == d_f___r5_rsize &&  $t.nstep_init == __i2 &&  $t.nbody_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="68">
				<variables>nbody_init, nsteps_init</variables>
				<inductives>nbody_init, nsteps_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[nbody_init - nsteps_init + 10 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="72">
				<variables>nbody_init, nsteps_init</variables>
				<inductives>nbody_init, nsteps_init</inductives>
				<callee>bh.Tree: java.util.Enumeration bodies()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="72">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.util.Enumeration: boolean hasMoreElements()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="73">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.util.Enumeration: java.lang.Object nextElement()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="74">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="14" srccode-offset="74">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="74">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="74">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="74">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="18" srccode-offset="74">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="19" srccode-offset="74">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="79">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="21" srccode-offset="79">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="22" srccode-offset="79">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="23" srccode-offset="79">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="24" srccode-offset="79">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="4" srccode-offset="80">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="25" srccode-offset="80">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="26" srccode-offset="80">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="27" srccode-offset="80">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="28" srccode-offset="80">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="5" srccode-offset="81">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="29" srccode-offset="81">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="30" srccode-offset="81">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="31" srccode-offset="81">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="32" srccode-offset="81">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="33" srccode-offset="83">
				<variables>nbody_init, nsteps_init, cont___r7</variables>
				<inductives>nbody_init, nsteps_init, cont___r7</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[cont___r7 == 0 &&  nbody_init - nsteps_init + 10 == 0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void parseCmdLine(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="124">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean startsWith(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args == size_args_init &&  size_args == 4 &&  i % 2 == 0 &&  size_args > i]]>
				</constraints>
			 </call-site>
			 <call-site offset="0" srccode-offset="128">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_args == size_args_init &&  size_args == 4 &&  i % 2 == 1 &&  size_args > i]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="130">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<constraints>
					<![CDATA[size_args == size_args_init &&  size_args == 4 &&  i == 1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="130">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args == size_args_init &&  size_args == 4 &&  i == 2]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="130">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_args == size_args_init &&  size_args == 4 &&  i == 2]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="132">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="3" srccode-offset="132">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="134">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_args == size_args_init &&  size_args == 4 &&  i == 3]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="136">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<constraints>
					<![CDATA[size_args == size_args_init &&  size_args == 4 &&  i == 3]]>
				</constraints>
			 </creation-site>
			 <call-site offset="6" srccode-offset="136">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args == i &&  size_args == size_args_init &&  size_args == 4]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="136">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_args == i &&  size_args == size_args_init &&  size_args == 4]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="138">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="8" srccode-offset="138">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="140">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="142">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="144">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="145">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>bh.BH: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="149">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<callee>bh.BH: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void usage()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="157">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="158">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="159">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="160">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="161">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="162">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="163">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.System: void exit(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
