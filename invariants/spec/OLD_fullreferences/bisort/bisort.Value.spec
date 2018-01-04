<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="bisort.Value">
		 <method decl="bisort.Value createTree(int,int)">
			<relevant-parameters>size_init, seed_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="42">
				<variables>__i0, __i1, __i2, size_init, seed_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: bisort.Value createTreeRec(int,int,int)</callee>
				<constraints>
					<![CDATA[__i0 == __i2 &&  __i0 == size_init &&  __i1 == seed_init &&  __i1 == 12345768 &&  __i0 < __i1 &&  $t.size_init == __i0 &&  $t.seed_init == __i1 &&  $t.origSize_init == __i2]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="bisort.Value createTreeRec(int,int,int)">
			<relevant-parameters>size_init, seed_init, origSize_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="48">
				<variables>origSize, size, __i0, size_init, seed_init, origSize_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: int random(int)</callee>
				<constraints>
					<![CDATA[origSize == origSize_init &&  size == size_init &&  __i0 == seed_init &&  origSize >= size &&  origSize < __i0 &&  size < __i0 &&  $t.seed_init == __i0]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="51">
				<variables>origSize, size, seed, next_val, size_init, seed_init, origSize_init</variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[origSize == origSize_init &&  size > 1 &&  next_val >= 0 &&  origSize >= size &&  origSize < seed &&  origSize > next_val &&  origSize < seed_init &&  size < seed &&  size < seed_init &&  seed > next_val &&  next_val < seed_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="51">
				<variables>origSize, size, seed, __i2, size_init, seed_init, origSize_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[origSize == origSize_init &&  size > 1 &&  __i2 >= 0 &&  origSize >= size &&  origSize < seed &&  origSize > __i2 &&  origSize < seed_init &&  size < seed &&  size < seed_init &&  seed > __i2 &&  __i2 < seed_init &&  $t.v_init == __i2]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="52">
				<variables>origSize, size, seed, __i1, __i2, __i3, size_init, seed_init, origSize_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: bisort.Value createTreeRec(int,int,int)</callee>
				<constraints>
					<![CDATA[origSize == __i3 &&  origSize == origSize_init &&  size == size_init &&  seed == __i2 &&  __i1 >= 1 &&  origSize >= size &&  origSize < seed &&  origSize > __i1 &&  origSize < seed_init &&  size < seed &&  size > __i1 &&  size < seed_init &&  seed > __i1 &&  __i1 < seed_init &&  $t.size_init == __i1 &&  $t.seed_init == __i2 &&  $t.origSize_init == __i3]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="53">
				<variables>__i1, origSize, __i2, __i3, size_init, seed_init, origSize_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: int skiprand(int,int)</callee>
				<constraints>
					<![CDATA[origSize == origSize_init &&  __i1 >= 1 &&  __i1 < origSize &&  __i1 < __i2 &&  __i1 < __i3 &&  __i1 < size_init &&  __i1 < seed_init &&  origSize < __i2 &&  origSize >= size_init &&  origSize < seed_init &&  __i2 > __i3 &&  __i2 > size_init &&  __i3 - size_init - 1 == 0 &&  __i3 < seed_init &&  size_init < seed_init &&  $t.seed_init == __i2 &&  $t.n_init == __i3]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="53">
				<variables>__i1, __i2, __i3, size_init, seed_init, origSize_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: bisort.Value createTreeRec(int,int,int)</callee>
				<constraints>
					<![CDATA[__i3 == origSize_init &&  __i1 >= 1 &&  __i1 < __i2 &&  __i1 < __i3 &&  __i1 < size_init &&  __i1 < seed_init &&  __i2 > __i3 &&  __i2 > size_init &&  __i3 >= size_init &&  __i3 < seed_init &&  size_init < seed_init &&  $t.size_init == __i1 &&  $t.seed_init == __i2 &&  $t.origSize_init == __i3]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="int bimerge(int,boolean)">
			<relevant-parameters>_f_this_init_value, spr_val_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="115">
				<variables>spr_val, _f_this_value, _f_prl_value, _f_pll_value, _f___r0_value, _f___r2_value, _f_this_init_value, spr_val_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: void swapValRight(bisort.Value)</callee>
				<constraints>
					<![CDATA[spr_val == f_this_init_value &&  f_this_value == spr_val_init &&  spr_val >= 0 &&  f_this_value >= 0 &&  f_prl_value >= 0 &&  f_pll_value >= 0 &&  f___r0_value >= 0 &&  $t._f_this_init_value == _f___r0_value &&  $t._f_n_init_value == _f___r2_value]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="124">
				<variables>spr_val, _f_this_value, _f_prr_value, _f_plr_value, _f___r0_value, _f___r2_value, _f_this_init_value, spr_val_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: void swapValLeft(bisort.Value)</callee>
				<constraints>
					<![CDATA[spr_val == spr_val_init &&  f_this_value == f_this_init_value &&  f_prr_value >= 0 &&  f_plr_value >= 0 &&  $t._f_this_init_value == _f___r0_value &&  $t._f_n_init_value == _f___r2_value]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="135">
				<variables>spr_val, _f_this_value, _f___r2_value, __i2, _f_this_init_value, spr_val_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: int bimerge(int,boolean)</callee>
				<constraints>
					<![CDATA[f_this_value == __i2 &&  spr_val >= 0 &&  f_this_value >= 0 &&  f_this_init_value >= 0 &&  spr_val_init >= 0 &&  $t._f_this_init_value == _f___r2_value &&  $t.spr_val_init == __i2]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="136">
				<variables>_f___r0_value, __i0, _f_this_init_value, spr_val_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: int bimerge(int,boolean)</callee>
				<constraints>
					<![CDATA[f___r0_value >= 0 &&  __i0 >= 0 &&  f_this_init_value >= 0 &&  spr_val_init >= 0 &&  $t._f_this_init_value == _f___r0_value &&  $t.spr_val_init == __i0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="int bisort(int,boolean)">
			<relevant-parameters>_f_this_init_value, spr_val_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="77">
				<variables>_f_this_value, spr_val, _f___r1_value, __i1, _f_this_init_value, spr_val_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: int bisort(int,boolean)</callee>
				<constraints>
					<![CDATA[f_this_value == __i1 &&  f_this_value == f_this_init_value &&  spr_val == spr_val_init &&  f___r1_value >= 0 &&  $t._f_this_init_value == _f___r1_value &&  $t.spr_val_init == __i1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="79">
				<variables>_f_this_value, _f___r0_value, __i0, _f_this_init_value, spr_val_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: int bisort(int,boolean)</callee>
				<constraints>
					<![CDATA[__i0 == spr_val_init &&  f_this_value >= 0 &&  $t._f_this_init_value == _f___r0_value &&  $t.spr_val_init == __i0]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="80">
				<variables>_f___r0_value, __i0, _f_this_init_value, spr_val_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: int bimerge(int,boolean)</callee>
				<constraints>
					<![CDATA[f___r0_value >= 0 &&  __i0 >= 0 &&  $t._f_this_init_value == _f___r0_value &&  $t.spr_val_init == __i0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="int random(int)">
			<relevant-parameters>seed_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="218">
				<variables>__i0, __i1, seed_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: int mult(int,int)</callee>
				<constraints>
					<![CDATA[__i0 == seed_init &&  __i1 == 31415821 &&  $t.p_init == __i0 &&  $t.q_init == __i1]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="int skiprand(int,int)">
			<relevant-parameters>seed_init, n_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="207">
				<variables>n, __i0, seed_init, n_init</variables>
				<inductives></inductives>
				<callee>bisort.Value: int random(int)</callee>
				<constraints>
					<![CDATA[n == n_init &&  __i0 == seed_init &&  n >= 1 &&  n < __i0 &&  $t.seed_init == __i0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;(int)">
			<relevant-parameters>v_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="28">
				<variables>v, v_init</variables>
				<inductives></inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[v == v_init &&  v >= 0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void inOrder()">
			<relevant-parameters>_f_this_init_value</relevant-parameters>
			 <call-site offset="0" srccode-offset="179">
				<variables>_f_this_value, _f___r0_value, _f_this_init_value</variables>
				<inductives></inductives>
				<callee>bisort.Value: void inOrder()</callee>
				<constraints>
					<![CDATA[$t._f_this_init_value == _f___r0_value]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="180">
				<variables>_f_this_value, _f_this_init_value</variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="180">
				<variables>_f_this_value, _f_this_init_value</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="180">
				<variables>_f_this_value, __i0, _f_this_init_value</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="180">
				<variables>_f_this_value, size___r5, _f_this_init_value</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="180">
				<variables>_f_this_value, _f_this_init_value</variables>
				<inductives></inductives>
				<callee>java.lang.Object: int hashCode()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="180">
				<variables>_f_this_value, __i0, _f_this_init_value</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="180">
				<variables>_f_this_value, _f_this_init_value</variables>
				<inductives></inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="180">
				<variables>_f_this_value, size___r6, _f_this_init_value</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="182">
				<variables>_f___r0_value, _f_this_init_value</variables>
				<inductives></inductives>
				<callee>bisort.Value: void inOrder()</callee>
				<constraints>
					<![CDATA[$t._f_this_init_value == _f___r0_value]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
