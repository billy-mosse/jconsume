<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="bisort.BiSort">
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="16">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="38">
				<variables>size___r0, size_args_init</variables>
				<inductives>size___r0, size_args_init, $t.size_args_init</inductives>
				<callee>bisort.BiSort: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[$t.size_args_init == size___r0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="42">
				<variables>size___r0, size_args_init</variables>
				<inductives>size___r0, size_args_init, $t.size_args_init</inductives>
				<callee>bisort.BiSort: void parseCmdLine(java.lang.String[])</callee>
				<constraints>
					<![CDATA[size___r0 == size_args_init &&  size___r0 == 2 &&  $t.size_args_init == size___r0]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="43">
				<variables>__i0, size_args_init</variables>
				<inductives>__i0, size_args_init, $t.size_init</inductives>
				<callee>bisort.BiSort: void mainParameters(int,boolean,boolean)</callee>
				<constraints>
					<![CDATA[size_args_init == 2 &&  __i0 > size_args_init &&  $t.size_init == __i0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int,boolean,boolean)">
			<relevant-parameters>size_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="51">
				<variables>_f___r5_value, size, size_init</variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="51">
				<variables>_f___r5_value, size, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="51">
				<variables>_f___r5_value, size, __i1, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="51">
				<variables>_f___r5_value, size, size___r3, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="51">
				<variables>_f___r5_value, size, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="51">
				<variables>_f___r5_value, size, size___r4, size_init</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="53">
				<variables>_f___r5_value, size, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[size == size_init &&  f___r5_value == 0 &&  f___r5_value < size]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="54">
				<variables>l_start2, _f___r5_value, __i3, __i4, size_init</variables>
				<inductives>$t.size_init, $t.seed_init</inductives>
				<callee>bisort.Value: bisort.Value createTree(int,int)</callee>
				<constraints>
					<![CDATA[__i3 == size_init &&  f___r5_value == 0 &&  __i4 == 12345768 &&  l_start2 > f___r5_value &&  l_start2 > __i3 &&  l_start2 > __i4 &&  f___r5_value < __i3 &&  __i3 < __i4 &&  $t.size_init == __i3 &&  $t.seed_init == __i4]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="55">
				<variables>l_start2, _f_tree_value, size_init</variables>
				<inductives>l_start2, _f_tree_value, size_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[f_tree_value == 29 &&  l_start2 > f_tree_value &&  l_start2 > size_init &&  f_tree_value < size_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="56">
				<variables>l_start2, l_end2, _f_tree_value, __i3, size_init</variables>
				<inductives>l_start2, l_end2, _f_tree_value, __i3, size_init, $t.seed_init</inductives>
				<callee>bisort.Value: int random(int)</callee>
				<constraints>
					<![CDATA[f_tree_value == 29 &&  __i3 == 245867 &&  l_start2 < l_end2 &&  l_start2 > f_tree_value &&  l_start2 > __i3 &&  l_start2 > size_init &&  l_end2 > f_tree_value &&  l_end2 > __i3 &&  l_end2 > size_init &&  f_tree_value < size_init &&  __i3 > size_init &&  $t.seed_init == __i3]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="59">
				<variables>l_start2, l_end2, _f_tree_value, sval, _f___r5_value, size_init</variables>
				<inductives>$t._f_this_init_value</inductives>
				<callee>bisort.Value: void inOrder()</callee>
				<constraints>
					<![CDATA[$t._f_this_init_value == _f___r5_value]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="60">
				<variables>l_start2, l_end2, _f_tree_value, sval, __i4, size_init</variables>
				<inductives>l_start2, l_end2, _f_tree_value, sval, __i4, size_init</inductives>
				<callee>java.io.PrintStream: void println(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="64">
				<variables>l_start2, l_end2, _f_tree_value, sval, size___r4, size_init</variables>
				<inductives>l_start2, l_end2, _f_tree_value, sval, size___r4, size_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="66">
				<variables>l_start2, l_end2, _f_tree_value, sval, size_init</variables>
				<inductives>l_start2, l_end2, _f_tree_value, sval, size_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[f_tree_value == 29 &&  sval == 8 &&  l_start2 < l_end2 &&  l_start2 > f_tree_value &&  l_start2 > sval &&  l_start2 > size_init &&  l_end2 > f_tree_value &&  l_end2 > sval &&  l_end2 > size_init &&  f_tree_value < size_init &&  sval < size_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="67">
				<variables>l_start0, l_start2, l_end2, _f_tree_value, _f___r5_value, __i4, size_init</variables>
				<inductives>l_start0, l_start2, l_end2, _f_tree_value, _f___r5_value, __i4, size_init, $t.spr_val_init, $t._f_this_init_value</inductives>
				<callee>bisort.Value: int bisort(int,boolean)</callee>
				<constraints>
					<![CDATA[f_tree_value == f___r5_value &&  f_tree_value == 29 &&  __i4 == 8 &&  l_start0 > l_start2 &&  l_start0 >= l_end2 &&  l_start0 > f_tree_value &&  l_start0 > __i4 &&  l_start0 > size_init &&  l_start2 < l_end2 &&  l_start2 > f_tree_value &&  l_start2 > __i4 &&  l_start2 > size_init &&  l_end2 > f_tree_value &&  l_end2 > __i4 &&  l_end2 > size_init &&  f_tree_value < size_init &&  __i4 < size_init &&  $t._f_this_init_value == _f___r5_value &&  $t.spr_val_init == __i4]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="68">
				<variables>l_start0, l_start2, l_end2, _f_tree_value, sval, size_init</variables>
				<inductives>l_start0, l_start2, l_end2, _f_tree_value, sval, size_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[l_start0 > l_start2 &&  l_start0 >= l_end2 &&  l_start0 > f_tree_value &&  l_start0 > sval &&  l_start0 > size_init &&  l_start2 < l_end2 &&  l_start2 > f_tree_value &&  l_start2 > sval &&  l_start2 > size_init &&  l_end2 > f_tree_value &&  l_end2 > sval &&  l_end2 > size_init &&  f_tree_value < sval &&  f_tree_value < size_init &&  sval < size_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="71">
				<variables>l_start0, l_end0, l_start2, l_end2, _f_tree_value, sval, _f___r5_value, size_init</variables>
				<inductives>$t._f_this_init_value</inductives>
				<callee>bisort.Value: void inOrder()</callee>
				<constraints>
					<![CDATA[$t._f_this_init_value == _f___r5_value]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="72">
				<variables>l_start0, l_end0, l_start2, l_end2, _f_tree_value, sval, __i4, size_init</variables>
				<inductives>_f_tree_value, __i4, size_init</inductives>
				<callee>java.io.PrintStream: void println(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="75">
				<variables>l_start0, l_end0, l_start2, l_end2, _f_tree_value, sval, size_init</variables>
				<inductives>l_start0, l_end2, _f_tree_value, sval, size_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[l_start0 < l_end0 &&  l_start0 > l_start2 &&  l_start0 >= l_end2 &&  l_start0 > f_tree_value &&  l_start0 > sval &&  l_start0 > size_init &&  l_end0 > l_start2 &&  l_end0 > l_end2 &&  l_end0 > f_tree_value &&  l_end0 > sval &&  l_end0 > size_init &&  l_start2 < l_end2 &&  l_start2 > f_tree_value &&  l_start2 > sval &&  l_start2 > size_init &&  l_end2 > f_tree_value &&  l_end2 > sval &&  l_end2 > size_init &&  f_tree_value < sval &&  f_tree_value < size_init &&  sval < size_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="18" srccode-offset="76">
				<variables>l_start0, l_start1, l_end0, l_start2, l_end2, _f_tree_value, _f___r5_value, __i4, size_init</variables>
				<inductives>_f_tree_value, _f___r5_value, __i4, size_init, $t.spr_val_init, $t._f_this_init_value</inductives>
				<callee>bisort.Value: int bisort(int,boolean)</callee>
				<constraints>
					<![CDATA[f_tree_value == f___r5_value &&  l_start0 < l_start1 &&  l_start0 < l_end0 &&  l_start0 > l_start2 &&  l_start0 >= l_end2 &&  l_start0 > f_tree_value &&  l_start0 > __i4 &&  l_start0 > size_init &&  l_start1 >= l_end0 &&  l_start1 > l_start2 &&  l_start1 > l_end2 &&  l_start1 > f_tree_value &&  l_start1 > __i4 &&  l_start1 > size_init &&  l_end0 > l_start2 &&  l_end0 > l_end2 &&  l_end0 > f_tree_value &&  l_end0 > __i4 &&  l_end0 > size_init &&  l_start2 < l_end2 &&  l_start2 > f_tree_value &&  l_start2 > __i4 &&  l_start2 > size_init &&  l_end2 > f_tree_value &&  l_end2 > __i4 &&  l_end2 > size_init &&  f_tree_value < __i4 &&  f_tree_value < size_init &&  __i4 < size_init &&  $t._f_this_init_value == _f___r5_value &&  $t.spr_val_init == __i4]]>
				</constraints>
			 </call-site>
			 <call-site offset="19" srccode-offset="77">
				<variables>l_start0, l_start1, l_end0, l_start2, l_end2, sval, _f_tree_value, size_init</variables>
				<inductives>sval, _f_tree_value, size_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[sval >= 0 &&  l_start0 < l_start1 &&  l_start0 < l_end0 &&  l_start0 > l_start2 &&  l_start0 >= l_end2 &&  l_start0 > sval &&  l_start0 > f_tree_value &&  l_start0 > size_init &&  l_start1 >= l_end0 &&  l_start1 > l_start2 &&  l_start1 > l_end2 &&  l_start1 > sval &&  l_start1 > f_tree_value &&  l_start1 > size_init &&  l_end0 > l_start2 &&  l_end0 > l_end2 &&  l_end0 > sval &&  l_end0 > f_tree_value &&  l_end0 > size_init &&  l_start2 < l_end2 &&  l_start2 > sval &&  l_start2 > f_tree_value &&  l_start2 > size_init &&  l_end2 > sval &&  l_end2 > f_tree_value &&  l_end2 > size_init &&  sval < f_tree_value &&  sval < size_init &&  f_tree_value < size_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="80">
				<variables>l_start0, l_end1, l_start1, l_end0, l_start2, l_end2, sval, _f___r5_value, size_init</variables>
				<inductives>_f___r5_value, size_init, $t._f_this_init_value</inductives>
				<callee>bisort.Value: void inOrder()</callee>
				<constraints>
					<![CDATA[$t._f_this_init_value == _f___r5_value]]>
				</constraints>
			 </call-site>
			 <call-site offset="21" srccode-offset="81">
				<variables>l_start0, l_end1, l_start1, l_end0, l_start2, l_end2, __i4, size_init</variables>
				<inductives>__i4, size_init</inductives>
				<callee>java.io.PrintStream: void println(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="85">
				<variables>l_start0, l_end1, l_start1, l_end0, l_start2, l_end2, size_init</variables>
				<inductives>size_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="22" srccode-offset="85">
				<variables>l_start0, l_end1, l_start1, l_end0, l_start2, l_end2, size_init</variables>
				<inductives>size_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="23" srccode-offset="85">
				<variables>l_start0, l_end1, l_start1, l_end0, l_start2, l_end2, size___r3, size_init</variables>
				<inductives>l_start0, l_end1, l_start1, l_end0, l_start2, l_end2, size___r3, size_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="24" srccode-offset="85">
				<variables>l_start0, l_end1, l_start1, l_end0, d___d0, size_init</variables>
				<inductives>d___d0, size_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="25" srccode-offset="85">
				<variables>l_start0, l_end1, l_start1, l_end0, size_init</variables>
				<inductives>size_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="26" srccode-offset="85">
				<variables>l_start0, l_end1, l_start1, l_end0, size___r4, size_init</variables>
				<inductives>size___r4, size_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="86">
				<variables>l_start0, l_end1, l_start1, l_end0, size_init</variables>
				<inductives>size_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="27" srccode-offset="86">
				<variables>l_start0, l_end1, l_start1, l_end0, size_init</variables>
				<inductives>size_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="28" srccode-offset="86">
				<variables>l_start0, l_end1, l_start1, l_end0, size___r3, size_init</variables>
				<inductives>size___r3, size_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="29" srccode-offset="86">
				<variables>l_start0, l_end1, l_start1, d___d0, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="30" srccode-offset="86">
				<variables>l_start0, l_end1, l_start1, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="31" srccode-offset="86">
				<variables>l_start0, l_end1, l_start1, size___r4, size_init</variables>
				<inductives>l_start0, l_end1, l_start1, size___r4, size_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="87">
				<variables>l_start0, l_end1, l_start1, size_init</variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="32" srccode-offset="87">
				<variables>l_start0, l_end1, l_start1, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="33" srccode-offset="87">
				<variables>l_start0, l_end1, l_start1, size___r3, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="34" srccode-offset="87">
				<variables>l_start0, l_end1, d___d0, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="35" srccode-offset="87">
				<variables>l_start0, l_end1, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="36" srccode-offset="87">
				<variables>l_start0, l_end1, size___r4, size_init</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="4" srccode-offset="88">
				<variables>l_start0, l_end1, size_init</variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="37" srccode-offset="88">
				<variables>l_start0, l_end1, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="38" srccode-offset="88">
				<variables>l_start0, l_end1, size___r3, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="39" srccode-offset="88">
				<variables>d___d0, size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="40" srccode-offset="88">
				<variables>size_init</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="41" srccode-offset="88">
				<variables>size___r4, size_init</variables>
				<inductives>size___r4, size_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="42" srccode-offset="90">
				<variables>size___r4, size_init</variables>
				<inductives>size___r4, size_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[size___r4 == 5 &&  size___r4 < size_init]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void parseCmdLine(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="102">
				<variables>size_args, i, size___r3, size___r4, size_args_init</variables>
				<inductives>size_args, i, size___r3, size___r4, size_args_init</inductives>
				<callee>java.lang.String: boolean startsWith(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args == size___r3 &&  size_args == size_args_init &&  size_args == 2 &&  i == 0 &&  size___r4 == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="106">
				<variables>size_args, i, size_arg, size___r3, size___r4, size_args_init</variables>
				<inductives>size_args, i, size_arg, size___r3, size___r4, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_args == size_arg &&  size_args == size___r3 &&  size_args == size___r4 &&  size_args == size_args_init &&  size_args == 2 &&  i == 1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="108">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<constraints>
					<![CDATA[size_args == size_args_init &&  size_args == 2 &&  i == 1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="108">
				<variables>size_args, i, size___r7, size_args_init</variables>
				<inductives>size_args, i, size___r7, size_args_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args == i &&  size_args == size_args_init &&  size_args == 2 &&  size___r7 == 3]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="108">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_args == i &&  size_args == size_args_init &&  size_args == 2]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="110">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="4" srccode-offset="110">
				<variables>size___r7, size_args_init</variables>
				<inductives>size___r7, size_args_init</inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="112">
				<variables>size_args, i, size_arg, size___r3, size___r4, size_args_init</variables>
				<inductives>size_args, i, size_arg, size___r3, size___r4, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="114">
				<variables>size_args, i, size_arg, size___r3, size___r4, size_args_init</variables>
				<inductives>size_args, i, size_arg, size___r3, size___r4, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="116">
				<variables>size_args, i, size___r3, size___r4, size_args_init</variables>
				<inductives>size_args, i, size___r3, size___r4, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="117">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>bisort.BiSort: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="120">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<callee>bisort.BiSort: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void usage()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="128">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="129">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="130">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="131">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="132">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="133">
				<variables>__i0</variables>
				<inductives>__i0</inductives>
				<callee>java.lang.System: void exit(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
