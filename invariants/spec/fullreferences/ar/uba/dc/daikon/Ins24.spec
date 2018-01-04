<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins24">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="1200">
				<variables>__r0, args_init, args</variables>
				<inductives></inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && __r0__f__toString__f__length >= size_args_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="1400">
				<variables>__i0, aux_0, args_init, args</variables>
				<inductives>__i0</inductives>
				<callee>ar.uba.dc.daikon.Ins24: void mainParameters(int,java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init == args && args_init == args && __i0 >= size_args_init && aux_0__f__toString__f__length > size_args_init]]>
				</constraints>
				<binding>$t.t_init == aux_0 and $t.t_init__f__toString__f__length == aux_0__f__toString__f__length</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int,java.lang.String)">
			<relevant-parameters>t_init, t_init__f__toString__f__length</relevant-parameters>
			 <call-site offset="1" srccode-offset="3400">
				<variables>t, i, __r1, t_init</variables>
				<inductives>i, __r1__f__value</inductives>
				<callee>java.lang.String: int length()</callee>
				<constraints>
					<![CDATA[t == t_init && t__f__toString__f__length == __r1__f__toString__f__length && t__f__toString__f__length == t_init__f__toString__f__length && i >= 1 && t__f__toString < __r1__f__toString && t__f__toString == t_init__f__toString && i <= t__f__toString__f__length]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="3600">
				<variables>t, i, t_init</variables>
				<inductives>i</inductives>
				<constraints>
					<![CDATA[t == t_init && t__f__toString__f__length == t_init__f__toString__f__length && i >= 1 && t__f__toString == t_init__f__toString && i < t__f__toString__f__length]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="3601">
				<variables>t, i, __r0, t_init</variables>
				<inductives>i</inductives>
				<callee>ar.uba.dc.daikon.A: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[t == t_init && t__f__toString__f__length == t_init__f__toString__f__length && i >= 1 && t__f__toString == t_init__f__toString && i < t__f__toString__f__length]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="4000">
				<variables>t, __r2, t_init</variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[t == t_init && t__f__toString__f__length == t_init__f__toString__f__length && t__f__toString == t_init__f__toString]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
