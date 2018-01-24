<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.mst.Vertex">
		 <method decl="void &lt;init&gt;(ar.uba.dc.jolden.mst.Vertex,int)">
			<relevant-parameters>n_init, numvert_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="3400">
				<variables>numvert, n, this, n_init, numvert_init</variables>
				<inductives>numvert</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[numvert == numvert_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="3800">
				<variables>this, numvert, n_init, numvert_init, n</variables>
				<inductives>numvert</inductives>
				<constraints>
					<![CDATA[numvert == numvert_init]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="3801">
				<variables>this, __i0, __r0, n_init, numvert_init, n, numvert</variables>
				<inductives></inductives><!-- __i0 -->
				<callee>ar.uba.dc.jolden.mst.Hashtable: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[numvert_init == numvert && __i0 < numvert_init]]>
				</constraints>
				<binding>$t.sz_init == __i0</binding>
			 </call-site>
		</method>
	</class>
</spec>
