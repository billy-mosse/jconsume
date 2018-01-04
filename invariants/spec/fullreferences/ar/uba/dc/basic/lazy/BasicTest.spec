<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.basic.lazy.BasicTest">
		
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>args_0</relevant-parameters>
			
			<call-site offset="2,3">
				<constraints><![CDATA[$t.size == args_0]]></constraints>
			</call-site>
		</method>
		
		<method decl="void showDiffIfTemporalIsConstant(int)">
			<relevant-parameters>size</relevant-parameters>
			
			<creation-site offset="1">
				<constraints><![CDATA[0 <= 2*i < arreglo.size and arreglo.size == size]]></constraints>
			</creation-site>
		</method>
		
		<method decl="void showExampleWithCandidates()">
			<call-site offset="0">
				<constraints><![CDATA[$t.from == 0 and $t.to == 0]]></constraints>
			</call-site>
			
			<call-site offset="1">
				<constraints><![CDATA[$t.from == 0 and $t.to == 4]]></constraints>
			</call-site>
			
			<call-site offset="2">
				<constraints><![CDATA[$t.from == 1 and $t.to == 4]]></constraints>
			</call-site>
			
			<call-site offset="3">
				<constraints><![CDATA[$t.from == 3 and $t.to == 3]]></constraints>
			</call-site>
			
			<call-site offset="4">
				<constraints><![CDATA[$t.from == 3 and $t.to == 4]]></constraints>
			</call-site>
		</method>
		
		<method decl="int sumPositions(int, int)">
			<relevant-parameters>from, to</relevant-parameters>
			
			<call-site offset="0">
				<constraints><![CDATA[from <= i <= to and $t.size == i]]></constraints>
			</call-site>
		</method>
		
		<method decl="ar.uba.dc.basic.lazy.T[] posiblesPares(int)">
			<relevant-parameters>size</relevant-parameters>
			
			<invariant id="loop_invariant">
				<constraints><![CDATA[0 <= i < size and 0 <= j < size]]></constraints>
			</invariant>
			
			<creation-site offset="1">
				<constraints><![CDATA[@loop_invariant]]></constraints>
			</creation-site>
			
			<call-site offset="2-4">
				<constraints><![CDATA[@loop_invariant]]></constraints>
			</call-site>
			
		</method>
	</class>
</spec>