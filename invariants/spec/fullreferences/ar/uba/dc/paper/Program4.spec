<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.paper.Program4">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>list.size, maxSize</relevant-parameters>

			<call-site offset="5" srccode-offset="">
				<constraints>$t.list.size == list.size</constraints>
			</call-site>

			<call-site offset="6" srccode-offset="">
				<constraints>$t.ls.size == another_list.size and $t.maxSize == maxSize</constraints>
			</call-site>

		</method>


		<method decl="ar.uba.dc.util.List safeMap(ar.uba.dc.util.List,ar.uba.dc.paper.Op)">
			<relevant-parameters>list.size</relevant-parameters>

			<call-site offset="0" srccode-offset="">
				<constraints>$t.list.size == list.size</constraints>
			</call-site>


			<call-site offset="3" srccode-offset="">
				<constraints>$t.list.size == cp.size and cp.size==list.size</constraints>
			</call-site>
		</method>

		<method decl="ar.uba.dc.util.List test(ar.uba.dc.util.List,ar.uba.dc.paper.Op)">
			<relevant-parameters>ls.size,maxSize</relevant-parameters>

			<invariant id="loop_invariant">
				<constraints><![CDATA[1 <= i <= ls.size]]></constraints>
			</invariant>

			<!--<creation-site offset="0" srccode-offset="">
				<variables>i</variables>
				<inductives>i</inductives>
				<constraints>@loop_invariant</constraints>
			</creation-site>-->

			<call-site offset="2" srccode-offset="">
				<variables>i</variables>
				<inductives>i</inductives>
				<constraints>@loop_invariant</constraints>
			</call-site>

			<call-site offset="3" srccode-offset="">
				<constraints>@loop_invariant and $t.list.size == maxSize</constraints>
			</call-site>

			<call-site offset="4" srccode-offset="">
				<variables>i</variables>
				<inductives>i</inductives>
				<constraints>@loop_invariant</constraints>
			</call-site>

			<call-site offset="5" srccode-offset="">
				<variables>i</variables>
				<inductives>i</inductives>
				<constraints>@loop_invariant</constraints>
			</call-site>
		</method>


		<method decl="ar.uba.dc.util.List copy(ar.uba.dc.util.List)">
			<relevant-parameters>list.size</relevant-parameters>

			<invariant id="loop_invariant">
				<constraints><![CDATA[1 <= i <= list.size]]></constraints>
			</invariant>

			<call-site offset="2" srccode-offset="">
				<variables>i</variables>
				<inductives>i</inductives>
				<constraints>@loop_invariant</constraints>
			</call-site>

			<call-site offset="3" srccode-offset="">
				<variables>i</variables>
				<inductives>i</inductives>
				<constraints>@loop_invariant</constraints>
			</call-site>

			<call-site offset="4" srccode-offset="">
				<variables>i</variables>
				<inductives>i</inductives>
				<constraints>@loop_invariant</constraints>
			</call-site>
		</method>
	</class>
</spec>
