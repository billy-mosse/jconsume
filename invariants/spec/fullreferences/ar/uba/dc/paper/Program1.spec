<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.paper.Program1">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>r</relevant-parameters>
			<call-site offset="0" srccode-offset="">			
				<constraints><![CDATA[1 <= n <= 3]]></constraints>	
				<variables>n</variables>
				<inductives>n</inductives>
				<!--<binding>$t.n == r</binding>-->
			</call-site>
		</method>

		<method decl="void triangle(ar.uba.dc.paper.A[][], int)">
			<relevant-parameters>n</relevant-parameters>

			<invariant id="loop_invariant">
				<constraints><![CDATA[1 <= i <= n]]></constraints>
			</invariant>			

			<call-site offset="0" srccode-offset="">
				<constraints>@loop_invariant</constraints>
				<binding>$t.m == i</binding>
			</call-site>
		</method>


		<method decl="void line(ar.uba.dc.paper.A[][], int)">
			<relevant-parameters>m</relevant-parameters>
			
			<invariant id="loop_invariant">
				<constraints><![CDATA[1 <= j <= m]]></constraints>
			</invariant>			

			<creation-site offset="0" srccode-offset="">
				<constraints>@loop_invariant</constraints>
				<variables>j</variables>
				<inductives>j</inductives>
			</creation-site>
		</method>
	</class>
</spec>
