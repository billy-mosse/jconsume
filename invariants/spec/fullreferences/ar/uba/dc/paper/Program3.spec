<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.paper.Program3">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>list.size</relevant-parameters>

			<call-site offset="3" srccode-offset="">
				<constraints>$t.list.size == list.size</constraints>
			</call-site>

		</method>

		<method decl="ar.uba.dc.util.List map(ar.uba.dc.util.List,ar.uba.dc.paper.Op)">
			<relevant-parameters>list.size</relevant-parameters>

			<invariant id="loop_invariant">
				<constraints><![CDATA[1 <= i <= list.size]]></constraints>
			</invariant>

			<call-site offset="2" srccode-offset="">
				<constraints>@loop_invariant</constraints>
			</call-site>

			<call-site offset="3" srccode-offset="">
				<constraints>@loop_invariant</constraints>
			</call-site>

			<call-site offset="4" srccode-offset="">
				<constraints>@loop_invariant</constraints>
			</call-site>
		</method>
	</class>
</spec>
