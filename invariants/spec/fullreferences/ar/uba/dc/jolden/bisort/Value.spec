<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.bisort.Value">
		 <method decl="ar.uba.dc.jolden.bisort.Value createTree(int,int)">
			<relevant-parameters>tree_size_init, seed_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="48">
				<variables>tree_size, seed, tree_size_init, seed_init</variables>
				<inductives>tree_size, seed, tree_size_init, seed_init, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: int random(int)</callee>
				<constraints>
					<![CDATA[tree_size == tree_size_init && seed == seed_init && tree_size < seed]]>
				</constraints>
				<binding>$t.seed_init == seed</binding>
			 </call-site>
			 <creation-site offset="0" srccode-offset="51">
				<variables>__i0, tree_size, next_val, tree_size_init, seed_init, seed</variables>
				<inductives>__i0, tree_size, next_val, tree_size_init, seed_init, seed</inductives>
				<constraints>
					<![CDATA[tree_size == tree_size_init && seed_init == seed && next_val >= 0 && __i0 > tree_size && __i0 > next_val && tree_size < seed_init && next_val < seed_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="52">
				<variables>__r0, __i0, tree_size, __i1, tree_size_init, seed_init, seed</variables>
				<inductives>__r0, __i0, tree_size, __i1, tree_size_init, seed_init, seed, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: ar.uba.dc.jolden.bisort.Value createTree(int,int)</callee>
				<constraints>
					<![CDATA[tree_size == tree_size_init && seed_init == seed && __i1 >= 1 && __i0 > tree_size && __i0 > __i1 && tree_size > __i1 && tree_size < seed_init && __i1 < seed_init]]>
				</constraints>
				<binding>$t.tree_size_init == __i1 and $t.seed_init == __i0</binding>
			 </call-site>
			 <call-site offset="3" srccode-offset="53">
				<variables>__r0, __i3, __i0, __i2, tree_size_init, seed_init, tree_size, seed</variables>
				<inductives>__r0, __i3, __i0, __i2, tree_size_init, seed_init, tree_size, seed, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: ar.uba.dc.jolden.bisort.Value createTree(int,int)</callee>
				<constraints>
					<![CDATA[tree_size_init == tree_size && seed_init == seed && __i3 >= 1 && __i3 < __i0 && __i3 < __i2 && __i3 < tree_size_init && __i3 < seed_init && __i0 > __i2 && __i0 > tree_size_init && __i2 - tree_size_init - 1 == 0 && __i2 < seed_init && tree_size_init < seed_init]]>
				</constraints>
				<binding>$t.tree_size_init == __i3 and $t.seed_init == __i4</binding>
			 </call-site>
		</method>
		 <method decl="int bimerge(int,boolean)">
			<relevant-parameters>this_init, spr_val_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="123">
				<variables>spr_val, __r2, this, __r1, prl, pll, prr, plr, pr, pl, this_init, spr_val_init</variables>
				<inductives>spr_val, __r2, this, __r1, prl, pll, prr, plr, pr, pl, this_init, spr_val_init</inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: void swapValRight(ar.uba.dc.jolden.bisort.Value)</callee>
				<constraints>
					<![CDATA[spr_val >= 0 && spr_val_init >= 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="132">
				<variables>spr_val, __r2, this, __r1, prl, pll, prr, plr, pr, pl, this_init, spr_val_init</variables>
				<inductives>spr_val, __r2, this, __r1, prl, pll, prr, plr, pr, pl, this_init, spr_val_init</inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: void swapValLeft(ar.uba.dc.jolden.bisort.Value)</callee>
				<constraints>
					<![CDATA[spr_val == spr_val_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="143">
				<variables>spr_val, __r2, this, __i0, __r1, this_init, spr_val_init</variables>
				<inductives>spr_val, __r2, this, __i0, __r1, this_init, spr_val_init, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: int bimerge(int,boolean)</callee>
				<constraints>
					<![CDATA[spr_val >= 0 && __i0 >= 0 && spr_val_init >= 0]]>
				</constraints>
				<binding>$t.this_init == __r1 and $t.spr_val_init == __i0</binding>
			 </call-site>
			 <call-site offset="3" srccode-offset="144">
				<variables>spr_val, __r2, this_init, spr_val_init, this</variables>
				<inductives>spr_val, __r2, this_init, spr_val_init, this, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: int bimerge(int,boolean)</callee>
				<constraints>
					<![CDATA[spr_val >= 0 && spr_val_init >= 0]]>
				</constraints>
				<binding>$t.this_init == __r2 and $t.spr_val_init == spr_val</binding>
			 </call-site>
		</method>
		 <method decl="int bisort(int,boolean)">
			<relevant-parameters>this_init, spr_val_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="81">
				<variables>this, spr_val, __r2, tmpval, __r1, this_init, spr_val_init</variables>
				<inductives>this, spr_val, __r2, tmpval, __r1, this_init, spr_val_init, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: int bisort(int,boolean)</callee>
				<constraints>
					<![CDATA[spr_val == spr_val_init]]>
				</constraints>
				<binding>$t.this_init == __r1 and $t.spr_val_init == tmpval</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="83">
				<variables>this, spr_val, __r2, this_init, spr_val_init</variables>
				<inductives>this, spr_val, __r2, this_init, spr_val_init, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: int bisort(int,boolean)</callee>
				<constraints>
					<![CDATA[spr_val == spr_val_init]]>
				</constraints>
				<binding>$t.this_init == __r2 and $t.spr_val_init == spr_val</binding>
			 </call-site>
			 <call-site offset="2" srccode-offset="84">
				<variables>__i1, this, this_init, spr_val_init, spr_val</variables>
				<inductives>__i1, this, this_init, spr_val_init, spr_val, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: int bimerge(int,boolean)</callee>
				<constraints>
					<![CDATA[spr_val_init == spr_val && __i1 >= 0]]>
				</constraints>
				<binding>$t.this_init == this and $t.spr_val_init == __i1</binding>
			 </call-site>
		</method>
		 <method decl="int random(int)">
			<relevant-parameters>seed_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="244">
				<variables>seed, aux_25, seed_init</variables>
				<inductives>seed, aux_25, seed_init</inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: int mult(int,int)</callee>
				<constraints>
					<![CDATA[seed == seed_init && aux_25 == 31415821]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="int skiprand(int,int)">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="231">
				<variables>n, seed, seed_init, n_init</variables>
				<inductives>n, seed, seed_init, n_init, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: int random(int)</callee>
				<constraints>
					<![CDATA[n == n_init && seed == seed_init && n >= 1 && n < seed]]>
				</constraints>
				<binding>$t.seed_init == seed</binding>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;(int)">
			<relevant-parameters>v_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="29">
				<variables>v, this, v_init</variables>
				<inductives>v, this, v_init</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[v == v_init && v >= 0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void inOrder()">
			<relevant-parameters>this_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="196">
				<variables>__r8, this, __r5__f__count, __r5__f__value, __r9, this_init</variables>
				<inductives>__r8, this, __r5__f__count, __r5__f__value, __r9, this_init, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: void inOrder()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.this_init == __r9</binding>
			 </call-site>
			 <creation-site offset="0" srccode-offset="197">
				<variables>__r8, this, __r2, __r5__f__count, __r5__f__value, this_init</variables>
				<inductives>__r8, this, __r2, __r5__f__count, __r5__f__value, this_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="8" srccode-offset="199">
				<variables>__r8, this_init, this</variables>
				<inductives>__r8, this_init, this, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: void inOrder()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.this_init == __r8</binding>
			 </call-site>
		</method>
	</class>
</spec>
