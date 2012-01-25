<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="bh.MathVector">
		 <method decl="double absolute()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="169">
				<variables>size_f_this_init_data</variables>
				<inductives>size_f_this_init_data</inductives>
				<callee>java.lang.Math: double sqrt(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="double distance(bh.MathVector)">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="178">
				<variables>size_f_this_init_data, size_f_v_init_data</variables>
				<inductives>size_f_this_init_data, size_f_v_init_data</inductives>
				<callee>java.lang.Math: double sqrt(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.lang.Object clone()">
			<relevant-parameters>size_f_this_init_data</relevant-parameters>
			 <call-site offset="0" srccode-offset="37">
				<variables>size_f_this_init_data</variables>
				<inductives>size_f_this_init_data</inductives>
				<callee>java.io.PrintStream: void print(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_f_this_init_data == 3]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="39">
				<variables>size_f_this_init_data</variables>
				<inductives>size_f_this_init_data</inductives>
				<callee>java.lang.Object: java.lang.Object clone()</callee>
				<constraints>
					<![CDATA[size_f_this_init_data == 3]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="40">
				<variables>__i1, size_f_this_init_data</variables>
				<inductives>__i1, size_f_this_init_data</inductives>
				<constraints>
					<![CDATA[__i1 == size_f_this_init_data &&  __i1 == 3]]>
				</constraints>
			 </creation-site>
			 <creation-site offset="1" srccode-offset="46">
				<variables>size_f_this_init_data</variables>
				<inductives>size_f_this_init_data</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="46">
				<variables>size_f_this_init_data</variables>
				<inductives>size_f_this_init_data</inductives>
				<callee>java.lang.Error: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.lang.String toString()">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="235">
				<variables>size_f_this_init_data</variables>
				<inductives>size_f_this_init_data</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="235">
				<variables>size_f_this_init_data</variables>
				<inductives>size_f_this_init_data</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="237">
				<variables>i, size_f_this_init_data</variables>
				<inductives>i, size_f_this_init_data</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="237">
				<variables>i, size_f_this_init_data</variables>
				<inductives>i, size_f_this_init_data</inductives>
				<callee>java.lang.String: java.lang.String valueOf(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="237">
				<variables>i, size_f_this_init_data</variables>
				<inductives>i, size_f_this_init_data</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="237">
				<variables>i, size_f_this_init_data</variables>
				<inductives>i, size_f_this_init_data</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="237">
				<variables>i, size_f_this_init_data</variables>
				<inductives>i, size_f_this_init_data</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="237">
				<variables>i, size_f_this_init_data</variables>
				<inductives>i, size_f_this_init_data</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="239">
				<variables>size_f_this_init_data</variables>
				<inductives>size_f_this_init_data</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="22">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="25">
				<variables>__i0</variables>
				<inductives>__i0</inductives>
				<constraints>
					<![CDATA[__i0 == 3]]>
				</constraints>
			 </creation-site>
		</method>
	</class>
</spec>
