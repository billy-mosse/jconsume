<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.util.List">
		 <method decl="java.util.Iterator iterator()">
			<relevant-parameters>this_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="25">
				<variables>this, this_init</variables>
				<inductives>this, this_init</inductives>
				<constraints>
					<![CDATA[this == this_init && this__size == this_init__size]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="100000">
				<variables>this</variables>
				<inductives>this</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[this__size == 0]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void add(java.lang.Object)">
			<relevant-parameters>this_init, e_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="15">
				<variables>this, e, this_init, e_init</variables>
				<inductives>this, e, this_init, e_init</inductives>
				<constraints>
					<![CDATA[this == this_init && this__size == this_init__size && e == e_init && this__size >= 0]]>
				</constraints>
			 </creation-site>
		</method>
	</class>
</spec>
