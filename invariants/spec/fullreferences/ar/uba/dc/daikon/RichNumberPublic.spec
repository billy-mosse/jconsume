<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.RichNumberPublic">
		 <method decl="void &lt;init&gt;(int)">
			<relevant-parameters>num_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="7">
				<variables>num, this, num_init</variables>
				<inductives>num, this, num_init</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[num ==num_init && this__number == 0 && num >this__number]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
