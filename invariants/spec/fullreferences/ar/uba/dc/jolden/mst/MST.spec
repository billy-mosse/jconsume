<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.jolden.mst.MST">
		<invariant id="vertices_value">
			<constraints><![CDATA[MST.vertices == numvert]]></constraints>
		</invariant>
		<invariant id="printResult_value">
			<constraints><![CDATA[MST.printResult == false]]></constraints>
		</invariant>
		<invariant id="printMsgs_value">
			<constraints><![CDATA[MST.printMsgs == false]]></constraints>
		</invariant>
		<invariant id="MyVertexListSize_value">
			<constraints><![CDATA[MST.MyVertexList.size == numvert]]></constraints>
		</invariant>

		<method decl="void main(java.lang.String[])">
			<relevant-parameters>numvert</relevant-parameters>
			<requires><![CDATA[numvert > 0]]></requires>
			
			<call-site offset="0" >
      			<constraints><![CDATA[$t.numvert == numvert]]></constraints>
    		</call-site>
    	</method>
    	
    	<method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>numvert</relevant-parameters>
			<requires><![CDATA[numvert > 0]]></requires>
    		
    		<call-site offset="1" >
      			<constraints><![CDATA[$t.numvert == numvert]]></constraints>
    		</call-site>
    	</method>
		
		<method decl="void mainParameters(int,boolean,boolean)">
			<relevant-parameters>numvert</relevant-parameters>
			<requires><![CDATA[numvert > 0]]></requires>
			<!-- call-site offset="6,10"-->
      			 <call-site offset="5,9" >
			<constraints><![CDATA[$t.numvert == numvert]]></constraints>
    		</call-site>
		</method>
		
		<method decl="int computeMST(ar.uba.dc.jolden.mst.Graph,int)">
			<relevant-parameters>numvert</relevant-parameters>
			<requires><![CDATA[numvert > 0]]></requires>
			
			<invariant id="loop">
				<constraints><![CDATA[1 <= i <= numvert]]></constraints>
			</invariant>
			
			<call-site offset="2" >
      			<constraints><![CDATA[@loop and $t.numvert == numvert]]></constraints>
    		</call-site>
			
			<call-site offset="3-4" >
      			<constraints><![CDATA[@loop]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.jolden.mst.BlueReturn BlueRule(ar.uba.dc.jolden.mst.Vertex,ar.uba.dc.jolden.mst.Vertex)">
			<relevant-parameters>vlist.size</relevant-parameters>
			<requires><![CDATA[vlist.size > 0]]></requires>			

			<invariant id="loop">
				<constraints><![CDATA[0 <= i <= vlist.size]]></constraints>
			</invariant>
    	
    		<call-site offset="6" >
      			<constraints><![CDATA[$t.this.array.elements.maxLength == vlist.size]]></constraints>
    		</call-site> 
    		
    		<call-site offset="17" >
      			<constraints><![CDATA[@loop and $t.this.array.elements.maxLength == vlist.size]]></constraints>
    		</call-site> 
    		
    		<call-site offset="12-16,18-23" >
      			<constraints><![CDATA[@loop]]></constraints>
    		</call-site>
    		
    		
		</method>
		
		<method decl="ar.uba.dc.jolden.mst.BlueReturn doAllBlueRule(ar.uba.dc.jolden.mst.Vertex)">
			<relevant-parameters>numvert</relevant-parameters>
			<requires><![CDATA[numvert > 0]]></requires>
			
			<call-site offset="1" >
      			<constraints><![CDATA[$t.vlist.size == numvert]]></constraints>
    		</call-site>
		</method>

		<method decl="void parseCmdLine(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
		</method>

		<method decl="void usage()">
		</method>
	</class>
</spec>
