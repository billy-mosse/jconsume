<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.util.ListC">
		 <method decl="boolean add(java.lang.Object)">
			<relevant-parameters>size_this_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="18">
				<variables>size_this, size___r0, size_this_init</variables>
				<inductives>size_this, size___r0, size_this_init</inductives>
				<constraints>
					<![CDATA[size_this ==size___r0 && size_this ==size_this_init && size_this >= 0]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="18">
				<variables>size_this, size___r0, size_this_init</variables>
				<inductives>size_this, size___r0, size_this_init</inductives>
				<callee>ar.uba.dc.util.Cell: void &lt;init&gt;(java.lang.Object,ar.uba.dc.util.Cell)</callee>
				<constraints>
					<![CDATA[size_this ==size___r0 && size_this ==size_this_init && size_this >= 0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.util.Iterator iterator()">
			<relevant-parameters>size_this_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="30">
				<variables>size_this, size_this_init</variables>
				<inductives>size_this, size_this_init</inductives>
				<constraints>
					<![CDATA[size_this ==size_this_init && size_this >= 0]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="30">
				<variables>size_this_init</variables>
				<inductives>size_this_init</inductives>
				<callee>ar.uba.dc.util.ListItr: void &lt;init&gt;(ar.uba.dc.util.Cell)</callee>
				<constraints>
					<![CDATA[size_this_init >= 0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="6">
				<variables>size_this, size___r0</variables>
				<inductives>size_this, size___r0</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[size_this ==size___r0 && size_this == 0]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
