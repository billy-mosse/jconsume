<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="bh.Node$HG">
		 <method decl="void &lt;init&gt;(bh.Node,bh.Body,bh.MathVector)">
			<relevant-parameters>size_f_p_init_data</relevant-parameters>
			 <call-site offset="0" srccode-offset="103">
				<variables>size_f_p_init_data</variables>
				<inductives>size_f_p_init_data</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[size_f_p_init_data == 3]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="106">
				<variables>size_f___r4_data, size_f_p_init_data</variables>
				<inductives>size_f___r4_data, size_f_p_init_data, $t.size_f_this_init_data</inductives>
				<callee>bh.MathVector: java.lang.Object clone()</callee>
				<constraints>
					<![CDATA[size_f___r4_data == size_f_p_init_data &&  size_f___r4_data == 3 &&  $t.size_f_this_init_data == size_f___r4_data]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="108">
				<variables>size_f_p_init_data</variables>
				<inductives>size_f_p_init_data</inductives>
				<constraints>
					<![CDATA[size_f_p_init_data == 3]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="108">
				<variables>size_f_p_init_data</variables>
				<inductives>size_f_p_init_data</inductives>
				<callee>bh.MathVector: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[size_f_p_init_data == 3]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
