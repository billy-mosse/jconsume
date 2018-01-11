<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.ar.uba.dc.jolden.mst.Vertex">
		 <method decl="void &lt;init&gt;(ar.uba.dc.jolden.mst.Vertex,int)">
			<relevant-parameters>numvert_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="26">
				<variables>numvert, numvert_init</variables>
				<inductives>numvert, numvert_init</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[numvert ==numvert_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="30">
				<variables>numvert, numvert_init</variables>
				<inductives>numvert, numvert_init</inductives>
				<constraints>
					<![CDATA[numvert ==numvert_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="30">
				<variables>size_f___r1_array, _f___r1_size, __i1, numvert_init</variables>
				<inductives>size_f___r1_array, _f___r1_size, __i1, numvert_init, $t.sz_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[size_f___r1_array ==_f___r1_size && size_f___r1_array ==__i1 && size_f___r1_array <numvert_init]]>
				</constraints>
				<binding>$t.sz_init == __i1</binding>
			 </call-site>
		</method>
	</class>
</spec>
