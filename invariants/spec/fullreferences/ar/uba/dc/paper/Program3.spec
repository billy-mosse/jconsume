<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.paper.Program3">
		 <method decl="ar.uba.dc.util.List map(ar.uba.dc.util.List,ar.uba.dc.paper.Op)">
			<relevant-parameters>list_init, op_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="41">
				<variables>op, list, list_init, op_init</variables>
				<inductives>op, list, list_init, op_init</inductives>
				<constraints>
					<![CDATA[op ==op_init && list ==list_init && list__size ==list_init__size]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="43">
				<variables>__r0, op, list, list_init, op_init, __r1</variables>
				<inductives>__r0, op, list, list_init, op_init, __r1, </inductives>
				<callee>ar.uba.dc.util.List: java.util.Iterator iterator()</callee>
				<constraints>
					<![CDATA[__r0__size ==cont___r1 && op ==op_init && list ==list_init && list__size ==list_init__size && cont___r1 == 0 && list__size >=cont___r1]]>
				</constraints>
				<binding>$t.this_init == list</binding>
			 </call-site>
			 <call-site offset="2" srccode-offset="47">
				<variables>__r0, __r1, op, list_init, op_init, list, __r1</variables>
				<inductives>__r0, __r1, op, list_init, op_init, list, __r1</inductives>
				<callee>java.util.Iterator: boolean hasNext()</callee>
				<constraints>
					<![CDATA[__r0__size ==cont___r1 && op ==op_init && list_init ==list && list_init__size ==list__size && __r1__getClass______getName____ == ar__uba__dc__util__ListItr__class && cont___r1 >= 0 && list_init__size >=cont___r1]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="51">
				<variables>__r0, __r1, op, list_init, op_init, list, __r1</variables>
				<inductives>__r0, __r1, op, list_init, op_init, list, __r1</inductives>
				<callee>java.util.Iterator: java.lang.Object next()</callee>
				<constraints>
					<![CDATA[op ==op_init && list_init ==list && list_init__size ==list__size && __r0__size >= 0 && __r1__getClass______getName____ == ar__uba__dc__util__ListItr__class && cont___r1 >= 1 && __r0__size <list_init__size && __r0__size -cont___r1 + 1 == 0 && list_init__size >=cont___r1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="52">
				<variables>__r0, __r1, __r2, op, list_init, op_init, list, __r1</variables>
				<inductives>__r0, __r1, __r2, op, list_init, op_init, list, __r1, </inductives>
				<callee>ar.uba.dc.paper.Op: java.lang.Object apply(java.lang.Object)</callee>
				<constraints>
					<![CDATA[op ==op_init && list_init ==list && list_init__size ==list__size && __r0__size >= 0 && __r1__getClass______getName____ == ar__uba__dc__util__ListItr__class && __r2__getClass______getName____ == java__lang__Integer__class && cont___r1 >= 1 && __r0__size <list_init__size && __r0__size -cont___r1 + 1 == 0 && list_init__size >=cont___r1]]>
				</constraints>
				<binding>$t.o_init == __r2</binding>
			 </call-site>
			 <call-site offset="5" srccode-offset="53">
				<variables>__r0, __r1, op, __r3, list_init, op_init, list, __r1</variables>
				<inductives>__r0, __r1, op, __r3, list_init, op_init, list, __r1, </inductives>
				<callee>ar.uba.dc.util.List: void add(java.lang.Object)</callee>
				<constraints>
					<![CDATA[op ==op_init && list_init ==list && list_init__size ==list__size && __r0__size >= 0 && __r1__getClass______getName____ == ar__uba__dc__util__ListItr__class && __r3__getClass______getName____ == java__lang__Integer__class && cont___r1 >= 1 && __r0__size <list_init__size && __r0__size -cont___r1 + 1 == 0 && list_init__size >=cont___r1]]>
				</constraints>
				<binding>$t.this_init == __r0 and $t.e_init == __r3</binding>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="13">
				<variables>args, __r0, args_init</variables>
				<inductives>args, __r0, args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args ==args_init && args____ ==args_init____ && args__getClass______getName____ == java__lang__String______class && size__args______ == 2 && args__getClass______getName____ ==args_init__getClass______getName____ && __r0 inargs____ && __r0__toString inargs______toString]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="14">
				<variables>__i0, __r1, args_init, args</variables>
				<inductives>__i0, __r1, args_init, args, </inductives>
				<callee>ar.uba.dc.paper.Program3: void mainParameters(int,java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init ==args && args_init____ ==args____ && args_init__getClass______getName____ == java__lang__String______class && size__args_init______ == 2 && __r1 inargs_init____ && __r1__toString inargs_init______toString && args_init__getClass______getName____ ==args__getClass______getName____]]>
				</constraints>
				<binding>$t.k_init == __i0 and $t.u_init == __r1</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int,java.lang.String)">
			<relevant-parameters>k_init, u_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="20">
				<variables>u, k, k_init, u_init</variables>
				<inductives>u, k, k_init, u_init</inductives>
				<constraints>
					<![CDATA[u ==u_init && k ==k_init && u__toString ==u_init__toString]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="23">
				<variables>__r0, u, k, j, k_init, u_init</variables>
				<inductives>__r0, u, k, j, k_init, u_init</inductives>
				<constraints>
					<![CDATA[__r0__size ==j && u ==u_init && k ==k_init && j >= 0 && u__toString ==u_init__toString && k >j]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="2" srccode-offset="30">
				<variables>__r0, k_init, u_init, k, u</variables>
				<inductives>__r0, k_init, u_init, k, u</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <creation-site offset="3" srccode-offset="32">
				<variables>__r0, k_init, u_init, k, u</variables>
				<inductives>__r0, k_init, u_init, k, u</inductives>
				<constraints>
					<![CDATA[__r0__size ==k_init && k_init ==k && u_init ==u && u_init__toString ==u__toString]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="4" srccode-offset="34">
				<variables>__r0, k_init, u_init, k, u</variables>
				<inductives>__r0, k_init, u_init, k, u</inductives>
				<constraints>
					<![CDATA[__r0__size ==k_init && k_init ==k && u_init ==u && u_init__toString ==u__toString]]>
				</constraints>
			 </creation-site>
			 <call-site offset="6" srccode-offset="36">
				<variables>__r0, __r2, k_init, u_init, k, u</variables>
				<inductives>__r0, __r2, k_init, u_init, k, u, </inductives>
				<callee>ar.uba.dc.paper.Program3: ar.uba.dc.util.List map(ar.uba.dc.util.List,ar.uba.dc.paper.Op)</callee>
				<constraints>
					<![CDATA[__r0__size ==k_init && k_init ==k && u_init ==u && u_init__toString ==u__toString]]>
				</constraints>
				<binding>$t.list_init == __r0 and $t.op_init == __r2</binding>
			 </call-site>
		</method>
	</class>
</spec>
