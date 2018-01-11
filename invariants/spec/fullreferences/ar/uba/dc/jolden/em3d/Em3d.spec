<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.em3d.Em3d">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="4700">
				<variables>args, args_init</variables>
				<inductives>args</inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.args_init == args</binding>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="5700">
				<variables>args, args_init</variables>
				<inductives>args</inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void parseCmdLine(java.lang.String[])</callee>
				<constraints>
					<![CDATA[args == args_init]]>
				</constraints>
				<binding>$t.args_init == args</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="5800">
				<variables>__i1, __i0, args_init, args</variables>
				<inductives>__i1, __i0</inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void mainParameters(int,int,boolean,boolean)</callee>
				<constraints>
					<![CDATA[args_init == args && __i1 > __i0 && __i1 > args_init__f__size && __i0 >= args_init__f__size-1]]>
				</constraints>
				<binding>$t.numNodes_init == __i1 and $t.numDegree_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int,int,boolean,boolean)">
			<relevant-parameters>numNodes_init, numDegree_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="10400">
				<variables>__r16, __r11, __r6, __r0, __r25, numNodes, numDegree, __r27, aux_0, numNodes_init, numDegree_init</variables>
				<inductives>__r16__f__count, __r16__f__value, __r11__f__count, __r11__f__value, __r6__f__count, __r6__f__value, __r25__f__count, __r25__f__value, numNodes, numDegree</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="10500">
				<variables>__r16, __r11, __r6, __r0, __r25, numNodes, numDegree, numNodes_init, numDegree_init</variables>
				<inductives>__r16__f__count, __r16__f__value, __r11__f__count, __r11__f__value, __r6__f__count, __r6__f__value, __r25__f__count, __r25__f__value, numNodes, numDegree</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r16 == __r11 && __r16 == __r6 && __r16 == __r25 && numNodes == numNodes_init && numDegree == numDegree_init && numNodes > numDegree]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="10600">
				<variables>__r16, __l0, __r11, __r6, __r0, __r25, numNodes, numDegree, numNodes_init, numDegree_init</variables>
				<inductives>__r16__f__count, __r16__f__value, __l0, __r11__f__count, __r11__f__value, __r6__f__count, __r6__f__value, __r25__f__count, __r25__f__value, numNodes, numDegree</inductives>
				<callee>ar.uba.dc.jolden.em3d.BiGraph: ar.uba.dc.jolden.em3d.BiGraph create(int,int,boolean)</callee>
				<constraints>
					<![CDATA[__r16 == __r11 && __r16 == __r6 && __r16 == __r25 && numNodes == numNodes_init && numDegree == numDegree_init && __l0 > numNodes && __l0 > numDegree && numNodes > numDegree]]>
				</constraints>
				<binding>$t.numNodes_init == numNodes and $t.numDegree_init == numDegree</binding>
			 </call-site>
			 <call-site offset="3" srccode-offset="10800">
				<variables>__r16, __l0, __r11, __r6, __r0, __r25, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l0, __r11__f__count, __r11__f__value, __r6__f__count, __r6__f__value, __r25__f__count, __r25__f__value</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r16 == __r11 && __r16 == __r6 && __r16 == __r25 && numNodes_init == numNodes && numDegree_init == numDegree && __l0 > numNodes_init && __l0 > numDegree_init && numNodes_init > numDegree_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="11300">
				<variables>__r16, __l0, __r11, __r6, __l1, __r0, __r22, __r25, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l0, __r11__f__count, __r11__f__value, __r6__f__count, __r6__f__value, __l1, __r25__f__count, __r25__f__value</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="4" srccode-offset="11301">
				<variables>__r16, __l0, __r11, __r6, __l1, __r0, __r22, __r25, __r21, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l0, __r11__f__count, __r11__f__value, __r6__f__count, __r6__f__value, __l1, __r25__f__count, __r25__f__value</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="11302">
				<variables>__r16, __l0, __r11, __r6, __l1, __r0, __r22, __r25, __r21, aux_1, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l0, __r11__f__count, __r11__f__value, __r6__f__count, __r6__f__value, __l1, __r25__f__count, __r25__f__value</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="11303">
				<variables>__r16, __l0, __r11, __r6, __l1, __r0, __r22, __r25, __i7, __r23, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l0, __r11__f__count, __r11__f__value, __r6__f__count, __r6__f__value, __l1, __r25__f__count, __r25__f__value, __i7</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="11304">
				<variables>__r16, __l0, __r11, __r6, __l1, __r0, __r22, __r25, __r24, aux_2, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l0, __r11__f__count, __r11__f__value, __r6__f__count, __r6__f__value, __l1, __r25__f__count, __r25__f__value</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="11305">
				<variables>__r16, __l0, __r11, __r6, __l1, __r0, __r22, __r25, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l0, __r11__f__count, __r11__f__value, __r6__f__count, __r6__f__value, __l1, __r25__f__count, __r25__f__value</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="11306">
				<variables>__r16, __l0, __r11, __r6, __l1, __r0, __r26, __r22, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l0, __r11__f__count, __r11__f__value, __r6__f__count, __r6__f__value, __l1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="11500">
				<variables>__r16, __l0, __r11, __r6, __l1, __r0, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l0, __r11__f__count, __r11__f__value, __r6__f__count, __r6__f__value, __l1</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r16 == __r11 && __r16 == __r6 && numNodes_init == numNodes && numDegree_init == numDegree && __l0 < __l1 && __l0 > numNodes_init && __l0 > numDegree_init && __l1 > numNodes_init && __l1 > numDegree_init && numNodes_init > numDegree_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="11700">
				<variables>__r16, __l0, __r11, __l2, __r6, __l1, __r0, i, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l0, __r11__f__count, __r11__f__value, __l2, __r6__f__count, __r6__f__value, __l1, i</inductives>
				<callee>ar.uba.dc.jolden.em3d.BiGraph: void compute()</callee>
				<constraints>
					<![CDATA[__r16 == __r11 && __r16 == __r6 && numNodes_init == numNodes && numDegree_init == numDegree && i >= 0 && __l0 < __l2 && __l0 < __l1 && __l0 > i && __l0 > numNodes_init && __l0 > numDegree_init && __l2 >= __l1 && __l2 > i && __l2 > numNodes_init && __l2 > numDegree_init && __l1 > i && __l1 > numNodes_init && __l1 > numDegree_init && i < numNodes_init && i < numDegree_init && numNodes_init > numDegree_init]]>
				</constraints>
				<binding>$t.this_init == __r0</binding>
			 </call-site>
			 <call-site offset="12" srccode-offset="11900">
				<variables>__r16, __l0, __r11, __l2, __r6, __l1, __r0, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l0, __r11__f__count, __r11__f__value, __l2, __r6__f__count, __r6__f__value, __l1</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[__r16 == __r11 && __r16 == __r6 && numNodes_init == numNodes && numDegree_init == numDegree && __l0 < __l2 && __l0 < __l1 && __l0 > numNodes_init && __l0 > numDegree_init && __l2 >= __l1 && __l2 > numNodes_init && __l2 > numDegree_init && __l1 > numNodes_init && __l1 > numDegree_init && numNodes_init > numDegree_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="12300">
				<variables>__r16, __l3, __l0, __r11, __l2, __r6, __l1, __r0, __r19, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0, __r11__f__count, __r11__f__value, __l2, __r6__f__count, __r6__f__value, __l1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="12600">
				<variables>__r16, __l3, __l0, __r11, __l2, __r4, __r6, __l1, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0, __r11__f__count, __r11__f__value, __l2, __r6__f__count, __r6__f__value, __l1</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="14" srccode-offset="12601">
				<variables>__r16, __l3, __l0, __r11, __l2, __r4, __r6, __l1, __r3, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0, __r11__f__count, __r11__f__value, __l2, __r6__f__count, __r6__f__value, __l1</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="12602">
				<variables>__r16, __l3, __l0, __r11, __l2, __r4, __r6, __l1, __r3, aux_3, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0, __r11__f__count, __r11__f__value, __l2, __r6__f__count, __r6__f__value, __l1</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="12603">
				<variables>__r16, __l3, __l0, __r11, __l2, __r4, __r6, __d1, __r5, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0, __r11__f__count, __r11__f__value, __l2, __r6__f__count, __r6__f__value, __d1</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="12604">
				<variables>__r16, __l3, __l0, __r11, __l2, __r4, __r6, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0, __r11__f__count, __r11__f__value, __l2, __r6__f__count, __r6__f__value</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="18" srccode-offset="12605">
				<variables>__r16, __l3, __l0, __r11, __l2, __r7, __r4, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0, __r11__f__count, __r11__f__value, __l2</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="12700">
				<variables>__r16, __l3, __l0, __r9, __r11, __l2, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0, __r11__f__count, __r11__f__value, __l2</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="19" srccode-offset="12701">
				<variables>__r16, __l3, __l0, __r9, __r11, __l2, __r8, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0, __r11__f__count, __r11__f__value, __l2</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="12702">
				<variables>__r16, __l3, __l0, __r9, __r11, __l2, __r8, aux_4, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0, __r11__f__count, __r11__f__value, __l2</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="21" srccode-offset="12703">
				<variables>__r16, __l3, __l0, __r9, __r11, __d3, __r10, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0, __r11__f__count, __r11__f__value, __d3</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="22" srccode-offset="12704">
				<variables>__r16, __l3, __l0, __r9, __r11, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0, __r11__f__count, __r11__f__value</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="23" srccode-offset="12705">
				<variables>__r16, __l3, __l0, __r12, __r9, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="12800">
				<variables>__r14, __r16, __l3, __l0, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="24" srccode-offset="12801">
				<variables>__r14, __r16, __l3, __l0, __r13, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0</inductives>
				<callee>java.lang.StringBuilder: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="25" srccode-offset="12802">
				<variables>__r14, __r16, __l3, __l0, __r13, aux_5, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __l3, __l0</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="26" srccode-offset="12803">
				<variables>__r14, __r16, __d5, __r15, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value, __d5</inductives>
				<callee>java.lang.StringBuilder: java.lang.StringBuilder append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="27" srccode-offset="12804">
				<variables>__r14, __r16, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives>__r16__f__count, __r16__f__value</inductives>
				<callee>java.lang.StringBuilder: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="28" srccode-offset="12805">
				<variables>__r17, __r14, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="29" srccode-offset="13100">
				<variables>__r2, aux_6, numNodes_init, numDegree_init, numNodes, numDegree</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[numNodes_init == numNodes && numDegree_init == numDegree && numNodes_init > numDegree_init && numNodes_init > aux_6__f__toString__f__length]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void parseCmdLine(java.lang.String[])">
			<relevant-parameters>args_init</relevant-parameters>
			 <call-site offset="1" srccode-offset="15300">
				<variables>i, args, arg, aux_8, args_init</variables>
				<inductives>i, args, arg__f__value</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[args == args_init && arg__f__toString__f__length == aux_8__f__toString__f__length && i <= args__f__size-1 && arg__f__toString <= aux_8__f__toString && args__f__size-1 > arg__f__toString__f__length]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="15500">
				<variables>args, arg, i, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<constraints>
					<![CDATA[args == args_init && i < args__f__size-1 && i < arg__f__toString__f__length && args__f__size-1 > arg__f__toString__f__length]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="15501">
				<variables>args, arg, i, __r12, __r11, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[args == args_init && i == arg__f__toString__f__length && arg__f__toString < __r12__f__toString && i < args__f__size-1 && i <= __r12__f__toString__f__length && args__f__size-1 >= __r12__f__toString__f__length]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="15502">
				<variables>args, arg, i, __r11, args_init</variables>
				<inductives>args, arg__f__value, i, __r11__f__value</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[args == args_init && i == arg__f__toString__f__length && i < args__f__size-1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="15600">
				<variables>args_init, args</variables>
				<inductives></inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="4" srccode-offset="15601">
				<variables>__r10, aux_9, args_init, args</variables>
				<inductives></inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="15700">
				<variables>args, arg, i, aux_10, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[args == args_init && arg == aux_10 && i == args__f__size-1 && i == args_init__f__size-1 && arg__f__toString__f__length == aux_10__f__toString__f__length && arg__f__toString == aux_10__f__toString && i > arg__f__toString__f__length]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="15900">
				<variables>args, arg, i, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<constraints>
					<![CDATA[args == args_init && i == args__f__size-1 && i == args_init__f__size-1 && i > arg__f__toString__f__length]]>
				</constraints>
			 </creation-site>
			 <call-site offset="6" srccode-offset="15901">
				<variables>args, arg, i, __r9, __r8, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[args == args_init && i == args__f__size && i == args_init__f__size && arg__f__toString < __r9__f__toString && args__f__size-1 > arg__f__toString__f__length && args__f__size-1 > __r9__f__toString__f__length && arg__f__toString__f__length > __r9__f__toString__f__length]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="15902">
				<variables>args, arg, i, __r8, args_init</variables>
				<inductives>args, arg__f__value, i, __r8__f__value</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[args == args_init && i == args__f__size && i == args_init__f__size && args__f__size-1 > arg__f__toString__f__length]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="16000">
				<variables>args_init, args</variables>
				<inductives></inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="8" srccode-offset="16001">
				<variables>__r7, aux_11, args_init, args</variables>
				<inductives></inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="16100">
				<variables>args, arg, i, aux_12, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="4" srccode-offset="16300">
				<variables>args, arg, i, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="10" srccode-offset="16301">
				<variables>args, arg, i, __r6, __r5, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="16302">
				<variables>args, arg, i, __r5, args_init</variables>
				<inductives>args, arg__f__value, i, __r5__f__value</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="5" srccode-offset="16400">
				<variables>args_init, args</variables>
				<inductives></inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="12" srccode-offset="16401">
				<variables>__r4, aux_13, args_init, args</variables>
				<inductives></inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="16500">
				<variables>args, arg, i, aux_14, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="16700">
				<variables>args, arg, i, aux_15, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="16900">
				<variables>args, arg, i, aux_16, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="17000">
				<variables>args, arg, i, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="17300">
				<variables>args_init, args</variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="0" srccode-offset="17400">
				<variables>args, arg, i, __r1, aux_7, args_init</variables>
				<inductives>args, arg__f__value, i</inductives>
				<callee>java.lang.String: boolean startsWith(java.lang.String)</callee>
				<constraints>
					<![CDATA[args == args_init && i == arg__f__toString__f__length && arg__f__toString__f__length == __r1__f__toString__f__length && i >= 0 && arg__f__toString > __r1__f__toString && arg__f__toString > aux_7__f__toString && i < args__f__size-1 && i <= __r1__f__toString__f__length && __r1__f__toString > aux_7__f__toString && args__f__size-1 > arg__f__toString__f__length && args__f__size-1 > __r1__f__toString__f__length && args__f__size-1 > aux_7__f__toString__f__length && arg__f__toString__f__length > aux_7__f__toString__f__length && __r1__f__toString__f__length > aux_7__f__toString__f__length]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void usage()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="18400">
				<variables>__r0, aux_17</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="18500">
				<variables>__r1, aux_18</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="18600">
				<variables>__r2, aux_19</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="18700">
				<variables>__r3, aux_20</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="18800">
				<variables>__r4, aux_21</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="18900">
				<variables>__r5, aux_22</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="19000">
				<variables>__r6, aux_23</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="19100">
				<variables>aux_24</variables>
				<inductives></inductives>
				<callee>java.lang.System: void exit(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
