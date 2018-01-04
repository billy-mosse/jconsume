<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.basic.SubstractWithFolds">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>args.size</relevant-parameters>			
			<creation-site offset="2" srccode-offset="">
      			<constraints><![CDATA[1 <= i <= args.size]]></constraints>
    		</creation-site>
		</method>

		<method decl="java.lang.Integer f(int)">
			<relevant-parameters>n,j</relevant-parameters>
			
    		<invariant id="loop_invariant">
				<constraints><![CDATA[0 <= i < n]]></constraints>
			</invariant>

			<call-site offset="3" srccode-offset="">			
				<annotations>class_called_changed_during_loop</annotations>
      			<constraints>@loop_invariant and $t.n == i</constraints>
    		</call-site>
		</method>
	</class>
</spec>