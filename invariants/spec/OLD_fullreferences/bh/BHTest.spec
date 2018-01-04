<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="bh.BHTest">
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="15">
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
			 <call-site offset="0" srccode-offset="22">
				<variables>size___r2, size_args_init</variables>
				<inductives>size___r2, size_args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[size___r2 == size_args_init &&  size___r2 == 1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="25">
				<variables>__i2, count, i, size_args_init</variables>
				<inductives>__i2, count, i, size_args_init</inductives>
				<constraints>
					<![CDATA[__i2 == 4 &&  count == 6 &&  size_args_init == 1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="27">
				<variables>count, i, size_args_init</variables>
				<inductives>count, i, size_args_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[count == 6 &&  size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="29">
				<variables>count, i, size_args_init</variables>
				<inductives>count, i, size_args_init</inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[count == 6 &&  size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="30">
				<variables>count, i, size_args_init</variables>
				<inductives>count, i, size_args_init</inductives>
				<constraints>
					<![CDATA[count == 6 &&  size_args_init == 1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="3" srccode-offset="30">
				<variables>count, i, size_args_init</variables>
				<inductives>count, i, size_args_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[count == 6 &&  size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="30">
				<variables>count, i, size_args_init</variables>
				<inductives>count, i, size_args_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[count == 6 &&  size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="30">
				<variables>count, i, size_args_init</variables>
				<inductives>count, i, size_args_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[count == 6 &&  size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="30">
				<variables>count, i, size_args_init</variables>
				<inductives>count, i, size_args_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[count == 6 &&  size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="30">
				<variables>count, i, size_args_init</variables>
				<inductives>count, i, size_args_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[count == 6 &&  size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="30">
				<variables>count, i, size_args_init</variables>
				<inductives>count, i, size_args_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[count == 6 &&  size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="31">
				<variables>count, i, size___r0, size_args_init</variables>
				<inductives>count, i, size___r0, size_args_init, $t.size_args_init</inductives>
				<callee>bh.BH: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[count == 6 &&  size___r0 == 4 &&  size_args_init == 1 &&  $t.size_args_init == size___r0]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
