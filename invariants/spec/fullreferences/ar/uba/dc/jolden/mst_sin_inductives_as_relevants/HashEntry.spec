<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.mst.HashEntry">
		 <method decl="void &lt;init&gt;(java.lang.Object,java.lang.Object,ar.uba.dc.jolden.mst.HashEntry)">
			<relevant-parameters>key_init, entry_init, next_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="9301">
				<variables>next, entry, key, this, key_init, entry_init, next_init</variables>
				<inductives>next, entry, key, this, key_init, entry_init, next_init</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
