<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="bisort.BiSortTest">
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
			 <creation-site offset="0" srccode-offset="18">
				<variables>size_args, __i0, size_args_init</variables>
				<inductives></inductives>
				<constraints>
					<![CDATA[size_args == size_args_init &&  size_args == 1 &&  __i0 == 2]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="20">
				<variables>size_argsTest, size___r2, size_args_init</variables>
				<inductives></inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_argsTest == size___r2 &&  size_argsTest == 2 &&  size_args_init == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="23">
				<variables>size_argsTest, count, i, size___r1, __i1, __i3, size_args_init</variables>
				<inductives></inductives>
				<callee>java.lang.Integer: java.lang.String toString(int)</callee>
				<constraints>
					<![CDATA[size_argsTest == size___r1 &&  __i1 == size_args_init &&  size_argsTest == 2 &&  count == 10 &&  __i1 == 1 &&  size_argsTest <= i &&  size_argsTest < __i3 &&  count >= i &&  count < __i3 &&  i > __i1 &&  i - __i3 + 100 == 0 &&  __i1 < __i3]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="24">
				<variables>size_argsTest, count, i, size___r1, size_args_init</variables>
				<inductives></inductives>
				<callee>bisort.BiSort: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[size_argsTest == size___r1 &&  size_argsTest == 2 &&  count == 10 &&  size_args_init == 1 &&  size_argsTest <= i &&  count >= i &&  i > size_args_init &&  $t.size_args_init == size___r1]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
