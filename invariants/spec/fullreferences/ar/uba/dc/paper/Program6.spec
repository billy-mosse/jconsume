<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.paper.Program6">
		 <method decl="void f(ar.uba.dc.util.List)">
			<relevant-parameters>_f_B_param_init_size</relevant-parameters>
			 <creation-site offset="0" srccode-offset="38">
				<variables>s, i, _f_B_param_init_size</variables>
				<inductives>s, i, _f_B_param_init_size</inductives>
				<constraints>
					<![CDATA[s ==_f_B_param_init_size && i >= 1 && s >i]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="38">
				<variables>s, i, size___r1, size___r2, __i1, _f_B_param_init_size</variables>
				<inductives>s, i, size___r1, size___r2, __i1, _f_B_param_init_size</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[s ==_f_B_param_init_size && i ==size___r1 && i ==size___r2 && i ==__i1 && i >= 1 && s >i]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init, r</relevant-parameters>
			 <call-site offset="0" srccode-offset="18">
				<variables>size___r2, size_args_init</variables>
				<inductives>size___r2, size_args_init</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args_init == 2 && size___r2 <=size_args_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="20">
				<variables>r, size_args_init</variables>
				<inductives>r, size_args_init</inductives>
				<constraints>
					<![CDATA[r >= 1 && size_args_init == 2]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="20">
				<variables>r, _f___r3_size, size_args_init</variables>
				<inductives>r, _f___r3_size, size_args_init</inductives>
				<callee>ar.uba.dc.util.List: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[r >= 1 && _f___r3_size == 0 && size_args_init == 2 && r >_f___r3_size]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="24">
				<variables>_f_B_size, r, k, size_args_init</variables>
				<inductives>_f_B_size, r, k, size_args_init</inductives>
				<constraints>
					<![CDATA[_f_B_size >= 0 && k >= 1 && size_args_init == 2 && _f_B_size <r && _f_B_size -k + 1 == 0 && r >=k]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="24">
				<variables>_f_B_size, r, k, size___r5, size___r1, b___b1, size_args_init</variables>
				<inductives>_f_B_size, r, k, size___r5, size___r1, b___b1, size_args_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[size___r5 ==size___r1 && size___r5 ==b___b1 && _f_B_size >= 0 && k >= 1 && size___r5 == 4 && size_args_init == 2 && _f_B_size <r && _f_B_size -k + 1 == 0 && r >=k]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="24">
				<variables>_f_B_size, r, k, size___r5, size_args_init</variables>
				<inductives>_f_B_size, r, k, size___r5, size_args_init, </inductives>
				<callee>ar.uba.dc.util.List: void add(java.lang.Object)</callee>
				<constraints>
					<![CDATA[_f_B_size >= 0 && k >= 1 && size___r5 == 4 && size_args_init == 2 && _f_B_size <r && _f_B_size -k + 1 == 0 && r >=k]]>
				</constraints>
				<binding>$t._f_this_init_size == _f___r3_size</binding>
			 </call-site>
			 <call-site offset="4" srccode-offset="27">
				<variables>_f___r3_size, size_args_init</variables>
				<inductives>_f___r3_size, size_args_init, </inductives>
				<callee>ar.uba.dc.paper.Program6: void f(ar.uba.dc.util.List)</callee>
				<constraints>
					<![CDATA[_f___r3_size >= 1 && size_args_init == 2]]>
				</constraints>
				<binding>$t._f_B_param_init_size == _f___r3_size</binding>
			 </call-site>
		</method>
	</class>
</spec>
