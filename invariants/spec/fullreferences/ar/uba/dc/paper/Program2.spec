<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.paper.Program2">
		 <method decl="ar.uba.dc.util.ListC map(ar.uba.dc.util.ListC,ar.uba.dc.paper.Op)">
			<relevant-parameters>size_list_init, op_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="39">
				<variables>op, size_list, size_list_init, op_init</variables>
				<inductives>op, size_list, size_list_init, op_init</inductives>
				<constraints>
					<![CDATA[list == list_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="40">
				<variables>size___r0, op, size_list, size_list_init, op_init, cont___r1</variables>
				<inductives>size___r0, op, size_list, size_list_init, op_init, cont___r1, </inductives>
				<callee>ar.uba.dc.util.ListC: java.util.Iterator iterator()</callee>
				<constraints>
					<![CDATA[list == list_init && cont___r1 == 0]]>
				</constraints>
				<binding>$t.size_this_init == size_list</binding>
			 </call-site>
			 <call-site offset="2" srccode-offset="51">
				<variables>size___r0, op, size_list_init, op_init, size_list, cont___r1</variables>
				<inductives>size___r0, op, size_list_init, op_init, size_list, cont___r1</inductives>
				<callee>java.util.Iterator: boolean hasNext()</callee>
				<constraints>
					<![CDATA[list_init == list && cont___r1 >= 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="53">
				<variables>size___r0, op, size_list_init, op_init, size_list, cont___r1</variables>
				<inductives>size___r0, op, size_list_init, op_init, size_list, cont___r1</inductives>
				<callee>java.util.Iterator: java.lang.Object next()</callee>
				<constraints>
					<![CDATA[list_init == list && cont___r1 >= 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="55">
				<variables>size___r0, __r2, op, size_list_init, op_init, size_list, cont___r1</variables>
				<inductives>size___r0, __r2, op, size_list_init, op_init, size_list, cont___r1, </inductives>
				<callee>ar.uba.dc.paper.Op: java.lang.Object apply(java.lang.Object)</callee>
				<constraints>
					<![CDATA[list_init == list && cont___r1 >= 1]]>
				</constraints>
				<binding>$t.o_init == __r2</binding>
			 </call-site>
			 <call-site offset="5" srccode-offset="56">
				<variables>size___r0, op, __r3, size_list_init, op_init, size_list, cont___r1</variables>
				<inductives>size___r0, op, __r3, size_list_init, op_init, size_list, cont___r1, </inductives>
				<callee>ar.uba.dc.util.ListC: boolean add(java.lang.Object)</callee>
				<constraints>
					<![CDATA[list_init == list && cont___r1 >= 1]]>
				</constraints>
				<binding>$t.size_this_init == size___r0 and $t.e_init == __r3</binding>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="13">
				<variables>size_args, size_args_init</variables>
				<inductives>size_args, size_args_init, </inductives>
				<callee>ar.uba.dc.paper.Program2: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[args == args_init && args == args_init]]>
				</constraints>
				<binding>$t.size_args_init == size_args</binding>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="18">
				<variables>size___r0, size_args_init, size_args</variables>
				<inductives>size___r0, size_args_init, size_args</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="19">
				<variables>__i0, size_args_init, size_args</variables>
				<inductives>__i0, size_args_init, size_args, </inductives>
				<callee>ar.uba.dc.paper.Program2: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args]]>
				</constraints>
				<binding>$t.r_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>r_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="24">
				<variables>r, r_init</variables>
				<inductives>r, r_init</inductives>
				<constraints>
					<![CDATA[r == r_init]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="28">
				<variables>size___r0, r, i, r_init</variables>
				<inductives>size___r0, r, i, r_init</inductives>
				<constraints>
					<![CDATA[r == r_init && i >= 0 && r > i]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="29">
				<variables>size___r0, r, i, __r3, r_init</variables>
				<inductives>size___r0, r, i, __r3, r_init, </inductives>
				<callee>ar.uba.dc.util.ListC: boolean add(java.lang.Object)</callee>
				<constraints>
					<![CDATA[r == r_init && i >= 0 && r > i]]>
				</constraints>
				<binding>$t.size_this_init == size___r0 and $t.e_init == __r3</binding>
			 </call-site>
			 <creation-site offset="2" srccode-offset="32">
				<variables>size___r0, r_init, r</variables>
				<inductives>size___r0, r_init, r</inductives>
				<constraints>
					<![CDATA[r_init == r]]>
				</constraints>
			 </creation-site>
			 <call-site offset="4" srccode-offset="34">
				<variables>size___r0, __r1, r_init, r</variables>
				<inductives>size___r0, __r1, r_init, r, </inductives>
				<callee>ar.uba.dc.paper.Program2: ar.uba.dc.util.ListC map(ar.uba.dc.util.ListC,ar.uba.dc.paper.Op)</callee>
				<constraints>
					<![CDATA[r_init == r]]>
				</constraints>
				<binding>$t.size_list_init == size___r0 and $t.op_init == __r1</binding>
			 </call-site>
		</method>
	</class>
</spec>
