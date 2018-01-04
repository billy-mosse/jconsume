<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.util.ListC">
		 <method decl="boolean add(java.lang.Object)">
			<relevant-parameters>size_this_init, e_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="18">
				<variables>size_this, e, size_this_init, e_init</variables>
				<inductives>size_this, e, size_this_init, e_init</inductives>
				<constraints>
					<![CDATA[this == this_init]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="java.util.Iterator iterator()">
			<relevant-parameters>size_this_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="30">
				<variables>size_this, size_this_init</variables>
				<inductives>size_this, size_this_init</inductives>
				<constraints>
					<![CDATA[this == this_init]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="100000">
				<variables>size_this</variables>
				<inductives>size_this</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
