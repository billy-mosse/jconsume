<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.basic.SubstractWithFolds">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>args.size</relevant-parameters>			
			<creation-site offset="0" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= args.size]]></constraints>
    		</creation-site>
			<call-site offset="2" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= args.size and $t.n == i]]></constraints>
    		</call-site>
		</method>

		<method decl="java.lang.Integer f(int)">
			<relevant-parameters>n</relevant-parameters>
			<creation-site offset="0" srccode-offset="">
				<variables>j</variables>
				<inductives>j</inductives>
      			<constraints><![CDATA[1 <= j <= n]]></constraints>
    		</creation-site>
		</method>
	</class>
</spec>