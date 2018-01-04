<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.bisort.BiSort">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="37">
				<variables>size_args, size_args_init</variables>
				<inductives>size_args, size_args_init, </inductives>
				<callee>ar.uba.dc.jolden.bisort.BiSort: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.size_args_init == size_args</binding>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="42">
				<variables>size_args, size_args_init</variables>
				<inductives>size_args, size_args_init, </inductives>
				<callee>ar.uba.dc.jolden.bisort.BiSort: void parseCmdLine(java.lang.String[])</callee>
				<constraints>
					<![CDATA[args == args_init && args == args_init && size_args == 2]]>
				</constraints>
				<binding>$t.size_args_init == size_args</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="43">
				<variables>__i0, size_args_init, size_args</variables>
				<inductives>__i0, size_args_init, size_args, </inductives>
				<callee>ar.uba.dc.jolden.bisort.BiSort: void mainParameters(int,boolean,boolean)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && size_args_init == 2 && __i0 > size_args_init]]>
				</constraints>
				<binding>$t.size_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int,boolean,boolean)">
			<relevant-parameters>size_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="57">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __r0, size, __r30, __r33__f__count, __r33__f__value, size_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __r0, size, __r30, __r33__f__count, __r33__f__value, size_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="6" srccode-offset="59">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __r0, size, size_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __r0, size, size_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r5 && size == size_init && __r20 == null]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="60">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l0, __r0, size, aux_2, size_init</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l0, __r0, size, aux_2, size_init, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: ar.uba.dc.jolden.bisort.Value createTree(int,int)</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r5 && size == size_init && __r20 == null && aux_2 == 12345768 && l___l0 < size && l___l0 < aux_2 && size < aux_2]]>
				</constraints>
				<binding>$t.tree_size_init == size and $t.seed_init == aux_2</binding>
			 </call-site>
			 <call-site offset="8" srccode-offset="61">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l0, __r0, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l0, __r0, size_init, size</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r5 && size_init == size && __r20 == null && l___l0 < size_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="62">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, aux_3, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, aux_3, size_init, size, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: int random(int)</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r5 && size_init == size && __r20 == null && aux_3 == 245867 && l___l1 > l___l0 && l___l1 < aux_3 && l___l1 < size_init && l___l0 < aux_3 && l___l0 < size_init && aux_3 > size_init && 69 * l___l1 - l___l0 - 2289 * size_init + 1__f__07755286477E11 == 0]]>
				</constraints>
				<binding>$t.seed_init == aux_3</binding>
			 </call-site>
			 <call-site offset="10" srccode-offset="65">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i19, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i19, size_init, size, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: void inOrder()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.this_init == __r0</binding>
			 </call-site>
			 <call-site offset="11" srccode-offset="66">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i19, __r28, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i19, __r28, size_init, size</inductives>
				<callee>java.io.PrintStream: void println(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="70">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i19, __r26, aux_4, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i19, __r26, aux_4, size_init, size</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="72">
				<variables>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i19, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i19, size_init, size</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r5 && size_init == size && __r20 == null && __i19 == 8 && l___l1 > l___l0 && l___l1 < __i19 && l___l1 < size_init && l___l0 < __i19 && l___l0 < size_init && __i19 < size_init && 69 * l___l1 - l___l0 - 2289 * size_init + 1__f__07755286477E11 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="73">
				<variables>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i19, aux_5, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i19, aux_5, size_init, size, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: int bisort(int,boolean)</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r5 && l___l3 == l___l1 && size_init == size && __r20 == null && __i19 == 8 && aux_5 == 0 && l___l3 > l___l0 && l___l3 < __i19 && l___l3 < aux_5 && l___l3 < size_init && l___l0 < __i19 && l___l0 < aux_5 && l___l0 < size_init && __i19 < size_init && aux_5 < size_init && 69 * l___l3 - l___l0 - 2289 * size_init + 1__f__07755286477E11 == 0]]>
				</constraints>
				<binding>$t.this_init == __r0 and $t.spr_val_init == __i19</binding>
			 </call-site>
			 <call-site offset="15" srccode-offset="74">
				<variables>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i4, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i4, size_init, size</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r5 && l___l3 == l___l1 && size_init == size && __r20 == null && l___l3 > l___l0 && l___l3 < __i4 && l___l3 < size_init && l___l0 < __i4 && l___l0 < size_init && __i4 < size_init && 57 * l___l3 - 34 * l___l0 + 763 * __i4 + 3__f__6446484992E10 == 0 && 69 * l___l3 - l___l0 - 2289 * size_init + 1__f__07755286477E11 == 0 && 11 * l___l3 + 15 * __i4 - 360 * size_init + 1__f__7430999698E10 == 0 && 11 * l___l0 + 35 * __i4 - 411 * size_init + 1__f__7431003415E10 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="77">
				<variables>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i4, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i4, size_init, size, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: void inOrder()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.this_init == __r0</binding>
			 </call-site>
			 <call-site offset="17" srccode-offset="78">
				<variables>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i4, __r24, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i4, __r24, size_init, size</inductives>
				<callee>java.io.PrintStream: void println(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="18" srccode-offset="81">
				<variables>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i4, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i4, size_init, size</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r5 && l___l3 == l___l1 && size_init == size && __r20 == null && l___l3 < l___l5 && l___l3 > l___l0 && l___l3 < __i4 && l___l3 < size_init && l___l5 > l___l0 && l___l5 < __i4 && l___l5 < size_init && l___l0 < __i4 && l___l0 < size_init && __i4 < size_init && 89 * l___l3 - 90 * l___l5 + 15 * __i4 - 1584634078 == 0 && l___l3 + l___l5 - 67 * size_init + 3__f__169273094E9 == 0 && 57 * l___l3 - 34 * l___l0 + 763 * __i4 + 3__f__6446484992E10 == 0 && 69 * l___l3 - l___l0 - 2289 * size_init + 1__f__07755286477E11 == 0 && 11 * l___l3 + 15 * __i4 - 360 * size_init + 1__f__7430999698E10 == 0 && 57 * l___l5 - 33 * l___l0 + 778 * __i4 + 3__f__8031116343E10 == 0 && 69 * l___l5 + l___l0 - 2334 * size_init + 1__f__10924557009E11 == 0 && 11 * l___l5 + 13 * __i4 - 356 * size_init + 1__f__7430999422E10 == 0 && 11 * l___l0 + 35 * __i4 - 411 * size_init + 1__f__7431003415E10 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="19" srccode-offset="82">
				<variables>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __l6, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i4, aux_6, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __l6, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __r0, __i4, aux_6, size_init, size, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: int bisort(int,boolean)</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r5 && l___l3 == l___l1 && l___l6 == l___l5 && size_init == size && __r20 == null && aux_6 == 1 && l___l3 < l___l6 && l___l3 > l___l0 && l___l3 < __i4 && l___l3 < aux_6 && l___l3 < size_init && l___l6 > l___l0 && l___l6 < __i4 && l___l6 < aux_6 && l___l6 < size_init && l___l0 < __i4 && l___l0 < aux_6 && l___l0 < size_init && __i4 > aux_6 && __i4 < size_init && aux_6 < size_init && 89 * l___l3 - 90 * l___l6 + 15 * __i4 - 1584634078 == 0 && l___l3 + l___l6 - 67 * size_init + 3__f__169273094E9 == 0 && 57 * l___l3 - 34 * l___l0 + 763 * __i4 + 3__f__6446484992E10 == 0 && 69 * l___l3 - l___l0 - 2289 * size_init + 1__f__07755286477E11 == 0 && 11 * l___l3 + 15 * __i4 - 360 * size_init + 1__f__7430999698E10 == 0 && 57 * l___l6 - 33 * l___l0 + 778 * __i4 + 3__f__8031116343E10 == 0 && 69 * l___l6 + l___l0 - 2334 * size_init + 1__f__10924557009E11 == 0 && 11 * l___l6 + 13 * __i4 - 356 * size_init + 1__f__7430999422E10 == 0 && 11 * l___l0 + 35 * __i4 - 411 * size_init + 1__f__7431003415E10 == 0]]>
				</constraints>
				<binding>$t.this_init == __r0 and $t.spr_val_init == __i4</binding>
			 </call-site>
			 <call-site offset="20" srccode-offset="83">
				<variables>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __l6, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __i7, __r0, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __l3, __r15__f__count, __r15__f__value, __l6, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __i7, __r0, size_init, size</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r20 == __r15 && __r20 == __r10 && __r20 == __r5 && l___l3 == l___l1 && l___l6 == l___l5 && size_init == size && __r20 == null && __i7 >= 0 && l___l3 < l___l6 && l___l3 > l___l0 && l___l3 < __i7 && l___l3 < size_init && l___l6 > l___l0 && l___l6 < __i7 && l___l6 < size_init && l___l0 < __i7 && l___l0 < size_init && __i7 < size_init && 66 * l___l3 - 68 * l___l6 - 67 * __i7 - 3__f__169265724E9 == 0 && l___l3 + l___l6 - 67 * size_init + 3__f__169273094E9 == 0 && 114 * l___l3 - 68 * l___l0 + 2289 * __i7 + 7__f__289311648E10 == 0 && 69 * l___l3 - l___l0 - 2289 * size_init + 1__f__07755286477E11 == 0 && 2 * l___l3 - __i7 - 68 * size_init + 3__f__169273204E9 == 0 && 19 * l___l6 - 11 * l___l0 + 389 * __i7 + 1__f__2677063677E10 == 0 && 69 * l___l6 + l___l0 - 2334 * size_init + 1__f__10924557009E11 == 0 && 2 * l___l6 + __i7 - 66 * size_init + 3__f__169272984E9 == 0 && 2 * l___l0 - 69 * __i7 - 114 * size_init + 3__f__169278122E9 == 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="21" srccode-offset="86">
				<variables>__r20__f__count, __r20__f__value, __l8, __l3, __r15__f__count, __r15__f__value, __l6, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __i7, __r0, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __l8, __l3, __r15__f__count, __r15__f__value, __l6, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __i7, __r0, size_init, size, </inductives>
				<callee>ar.uba.dc.jolden.bisort.Value: void inOrder()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.this_init == __r0</binding>
			 </call-site>
			 <call-site offset="22" srccode-offset="87">
				<variables>__r20__f__count, __r20__f__value, __l8, __l3, __r15__f__count, __r15__f__value, __l6, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __i7, __r23, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __l8, __l3, __r15__f__count, __r15__f__value, __l6, __r10__f__count, __r10__f__value, __l5, __r5__f__count, __r5__f__value, __l1, __l0, __i7, __r23, size_init, size</inductives>
				<callee>java.io.PrintStream: void println(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="91">
				<variables>__r20__f__count, __r20__f__value, __l8, __l3, __r15__f__count, __r15__f__value, __l6, __r10__f__count, __r10__f__value, __l5, __r3, __r5__f__count, __r5__f__value, __l1, __l0, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __l8, __l3, __r15__f__count, __r15__f__value, __l6, __r10__f__count, __r10__f__value, __l5, __r3, __r5__f__count, __r5__f__value, __l1, __l0, size_init, size</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <creation-site offset="2" srccode-offset="92">
				<variables>__r20__f__count, __r20__f__value, __l8, __l3, __r15__f__count, __r15__f__value, __l6, __r8, __r10__f__count, __r10__f__value, __l5, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __l8, __l3, __r15__f__count, __r15__f__value, __l6, __r8, __r10__f__count, __r10__f__value, __l5, size_init, size</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <creation-site offset="3" srccode-offset="93">
				<variables>__r20__f__count, __r20__f__value, __l8, __l3, __r13, __r15__f__count, __r15__f__value, __l6, size_init, size</variables>
				<inductives>__r20__f__count, __r20__f__value, __l8, __l3, __r13, __r15__f__count, __r15__f__value, __l6, size_init, size</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <creation-site offset="4" srccode-offset="94">
				<variables>__r18, __r20__f__count, __r20__f__value, __l8, __l3, size_init, size</variables>
				<inductives>__r18, __r20__f__count, __r20__f__value, __l8, __l3, size_init, size</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="43" srccode-offset="96">
				<variables>__r1, aux_11, size_init, size</variables>
				<inductives>__r1, aux_11, size_init, size</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_init == size]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void parseCmdLine(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="1" srccode-offset="115">
				<variables>i, size_args, size_arg, aux_13, size_args_init</variables>
				<inductives>i, size_args, size_arg, aux_13, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[i == size_args-1 && i == size_args_init-1 && args == args_init && args == args_init && arg == aux_13 && size_args == 2 && arg__f__toString == aux_13__f__toString]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="117">
				<variables>size_args, size_arg, i, size_args_init</variables>
				<inductives>size_args, size_arg, i, size_args_init</inductives>
				<constraints>
					<![CDATA[args == args_init && args == args_init && i == size_args-1 && i == size_args_init-1 && size_args == 2]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="119">
				<variables>size_args_init, size_args</variables>
				<inductives>size_args_init, size_args</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="5" srccode-offset="121">
				<variables>size_args, size_arg, i, aux_15, size_args_init</variables>
				<inductives>size_args, size_arg, i, aux_15, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="123">
				<variables>size_args, size_arg, i, aux_16, size_args_init</variables>
				<inductives>size_args, size_arg, i, aux_16, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="125">
				<variables>size_args, size_arg, i, aux_17, size_args_init</variables>
				<inductives>size_args, size_arg, i, aux_17, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="126">
				<variables>size_args, size_arg, i, size_args_init</variables>
				<inductives>size_args, size_arg, i, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.bisort.BiSort: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="0" srccode-offset="130">
				<variables>size_args, size_arg, i, size___r1, aux_12, size_args_init</variables>
				<inductives>size_args, size_arg, i, size___r1, aux_12, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.bisort.BiSort: void usage()</callee>
				<constraints>
					<![CDATA[args == args_init && args == args_init && arg == null && i == 0 && size_args == 2 && __r1__f__toString > aux_12__f__toString]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void usage()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="140">
				<variables>__r0, aux_18</variables>
				<inductives>__r0, aux_18</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="141">
				<variables>__r1, aux_19</variables>
				<inductives>__r1, aux_19</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="142">
				<variables>__r2, aux_20</variables>
				<inductives>__r2, aux_20</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="143">
				<variables>__r3, aux_21</variables>
				<inductives>__r3, aux_21</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="144">
				<variables>__r4, aux_22</variables>
				<inductives>__r4, aux_22</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="145">
				<variables>aux_23</variables>
				<inductives>aux_23</inductives>
				<callee>java.lang.System: void exit(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
