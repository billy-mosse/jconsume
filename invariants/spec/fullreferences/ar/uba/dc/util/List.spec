<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.util.List">
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="5">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void add(java.lang.Object)">
			<relevant-parameters>_f_this_init_size</relevant-parameters>
			 <creation-site offset="0" srccode-offset="15">
				<variables>_f_this_size, _f_this_init_size</variables>
				<inductives>_f_this_size, _f_this_init_size</inductives>
				<constraints>
					<![CDATA[_f_this_size ==_f_this_init_size && _f_this_size >= 0]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="15">
				<variables>_f_this_size, _f_this_init_size</variables>
				<inductives>_f_this_size, _f_this_init_size</inductives>
				<callee>ar.uba.dc.util.Cell: void &lt;init&gt;(java.lang.Object,ar.uba.dc.util.Cell)</callee>
				<constraints>
					<![CDATA[_f_this_size ==_f_this_init_size && _f_this_size >= 0]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
