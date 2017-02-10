<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.jolden.em3d.Graph">
		<invariant id="CONST_m1_value">
			<constraints><![CDATA[Graph.CONST_m1 == 10000]]></constraints>
		</invariant>
		<invariant id="CONST_b_value">
			<constraints><![CDATA[Graph.CONST_b == 31415821]]></constraints>
		</invariant>
		<invariant id="RANGE_value">
			<constraints><![CDATA[Graph.RANGE == 2048]]></constraints>
		</invariant>
	
		<method decl="void &lt;init&gt;(int)">
			<relevant-parameters>numvert</relevant-parameters>
			
			<creation-site id="loop" offset="1">
      			<constraints><![CDATA[0 <= i < numvert]]></constraints>
    		</creation-site>
			
			<call-site offset="1" >
      			<constraints><![CDATA[@loop]]></constraints>
      			<binding>$t.numvert == numvert</binding>
    		</call-site>
    		
    		<call-site offset="2" >
      			<!--<constraints><![CDATA[$t.numvert == numvert]]></constraints>-->
      			<binding>$t.numvert == numvert</binding>
    		</call-site>
    	</method>
		
		<method decl="void createGraph(int)">
			<relevant-parameters>numvert</relevant-parameters>
			
			<creation-site id="loop" offset="1">
      			<constraints><![CDATA[0 <= i < numvert]]></constraints>
    		</creation-site>
			
			<call-site offset="0" >
      			<constraints><![CDATA[@loop]]></constraints>
      			<binding>$t.numvert == numvert</binding>
    		</call-site>
    		
    		<call-site offset="1" >
      			<!--<constraints><![CDATA[$t.numvert == numvert]]></constraints>-->
      			<binding>$t.numvert == numvert</binding>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.jolden.mst.Vertex firstNode()">
		</method>
		
		<method decl="void addEdges(int)">
			<relevant-parameters>numvert</relevant-parameters>
			
			<invariant id="loop">
				<constraints><![CDATA[1 <= i <= numvert]]></constraints>
			</invariant>
			
			<invariant id="loop2">
				<constraints><![CDATA[@loop and 1 <= j <= numvert]]></constraints>
			</invariant>
			
			<call-site offset="0,4" >
      			<constraints><![CDATA[@loop]]></constraints>
    		</call-site>
			
			<call-site offset="1-3">
      			<constraints><![CDATA[@loop2]]></constraints>
    		</call-site>
    		
    		<creation-site offset="0">
      			<constraints><![CDATA[@loop2]]></constraints>
    		</creation-site>
		</method>
		
		<method decl="int computeDist(int,int,int)">
			<relevant-parameters>i, j, numvert</relevant-parameters>
		</method>
		
		<method decl="int mult(int,int)">
			<relevant-parameters>p, q</relevant-parameters>
		</method>
		
		<method decl="int random(int)">
			<relevant-parameters>seed</relevant-parameters>
		</method>

	</class>
</spec>