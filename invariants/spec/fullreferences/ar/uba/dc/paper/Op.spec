<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.paper.Op">
		 <method decl="java.lang.Object apply(java.lang.Object)">
			<relevant-parameters></relevant-parameters>
			 <creation-site offset="0" srccode-offset="11">
				<variables></variables>
				<inductives></inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="11">
				<variables></variables>
				<inductives></inductives>
				<callee>java.util.Date: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="13">
				<variables></variables>
				<inductives></inductives>
				<constraints>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="13">
				<variables></variables>
				<inductives></inductives>
				<callee>java.util.Date: long getTime()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="13">
				<variables>l___l0</variables>
				<inductives>l___l0</inductives>
				<callee>java.util.Random: void &lt;init&gt;(long)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="14">
				<variables>size___r6</variables>
				<inductives>size___r6</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size___r6 >= 0]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="16">
				<variables>__i1</variables>
				<inductives>__i1</inductives>
				<callee>java.util.Random: int nextInt()</callee>
				<constraints>
					<![CDATA[__i1 >= 0]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="18">
				<variables>result</variables>
				<inductives>result</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="5" srccode-offset="18">
				<variables>size___r6, size___r7, __i3</variables>
				<inductives>size___r6, size___r7, __i3</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[size___r6 ==size___r7 && size___r6 ==__i3]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="7">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
