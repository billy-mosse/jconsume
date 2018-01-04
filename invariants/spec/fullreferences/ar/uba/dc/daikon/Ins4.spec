<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins4">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="15">
				<variables>size_args, size___r0, size_args_init</variables>
				<inductives>size_args, size___r0, size_args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args == args_init && args == args_init && args__f__toString == [1] && size_args == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="16">
				<variables>__i0, size_args, size_args_init</variables>
				<inductives>__i0, size_args, size_args_init</inductives>
				<callee>ar.uba.dc.daikon.InstrumentedMethod: void a4(int,java.lang.String[])</callee>
				<constraints>
					<![CDATA[__i0 == size_args && __i0 == size_args_init && args == args_init && args == args_init && __i0 == 1 && args__f__toString == [1]]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="17">
				<variables>__i0, size_args_init, size_args</variables>
				<inductives>__i0, size_args_init, size_args, </inductives>
				<callee>ar.uba.dc.daikon.Ins4: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.n_init == size_args</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>n_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="22">
				<variables>n, n_init</variables>
				<inductives>n, n_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="25">
				<variables>size___r0, n, i, n_init</variables>
				<inductives>size___r0, n, i, n_init</inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="3" srccode-offset="26">
				<variables>size___r0, n, i, n_init</variables>
				<inductives>size___r0, n, i, n_init</inductives>
				<callee>ar.uba.dc.daikon.InstrumentedMethod: void a5(int,int,ar.uba.dc.util.ListC)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="30">
				<variables>size___r0, n, n_init, cont___r1</variables>
				<inductives>size___r0, n, n_init, cont___r1, </inductives>
				<callee>ar.uba.dc.util.ListC: java.util.Iterator iterator()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.size_this_init == n</binding>
			 </call-site>
			 <call-site offset="5" srccode-offset="34">
				<variables>it, size___r0, n, k, n_init, cont___r1</variables>
				<inductives>it, size___r0, n, k, n_init, cont___r1</inductives>
				<callee>ar.uba.dc.util.ListItr: boolean hasNext()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="36">
				<variables>it, size___r0, n, k, n_init, cont___r1</variables>
				<inductives>it, size___r0, n, k, n_init, cont___r1</inductives>
				<callee>ar.uba.dc.util.ListItr: java.lang.Object next()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="40">
				<variables>it, j, size___r0, n, k, n_init, cont___r1</variables>
				<inductives>it, j, size___r0, n, k, n_init, cont___r1</inductives>
				<callee>ar.uba.dc.daikon.InstrumentedMethod: void a6(java.lang.Integer,java.util.Iterator,ar.uba.dc.util.ListC,int,int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="41">
				<variables>it, size___r0, n, k, j, n_init, cont___r1</variables>
				<inductives>it, size___r0, n, k, j, n_init, cont___r1</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
