<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.jolden.em3d.Em3d">
		<method decl="void &lt;init&gt;()">
		</method>
		
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>numNodes, numIter, numDegree</relevant-parameters>
			<requires><![CDATA[numNodes > 0]]></requires>
			<requires><![CDATA[numIter > 0]]></requires>
			<requires><![CDATA[numDegree > 0]]></requires>
			
			<call-site offset="0" >
      			<constraints><![CDATA[$t.numNodes == numNodes]]></constraints>
      			<binding>$t.numIter == numIter and $t.numDegree == numDegree</binding>
    		</call-site>
		</method>
				
		<method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>numNodes, numIter, numDegree</relevant-parameters>
			<requires><![CDATA[numNodes > 0]]></requires>
			<requires><![CDATA[numIter > 0]]></requires>
			<requires><![CDATA[numDegree > 0]]></requires>
			
			<call-site offset="1" >
      			<!--<constraints><![CDATA[$t.numNodes == numNodes and $t.numIter == numIter and $t.numDegree == numDegree]]></constraints>-->
      			<binding>$t.numNodes == numNodes and $t.numIter == numIter and $t.numDegree == numDegree</binding>
    		</call-site>
		</method>
		
		<method decl="void mainParameters(int, int, boolean, boolean)">
			<relevant-parameters>numNodes, numIter, numDegree</relevant-parameters>
			<requires><![CDATA[numNodes > 0]]></requires>
			<requires><![CDATA[numIter > 0]]></requires>
			<requires><![CDATA[numDegree > 0]]></requires>
			
			<call-site offset="2" >
      			<!--<constraints><![CDATA[$t.numNodes == numNodes and $t.numDegree == numDegree]]></constraints>-->
      			<binding>$t.numNodes == numNodes and $t.numDegree == numDegree</binding>
    		</call-site>
    		
    		<call-site offset="11" >
				<variables>i</variables>
				<inductives>i</inductives>
      			<constraints><![CDATA[0 <= i < numIter]]></constraints>
      			<binding>$t.numNodes == numNodes</binding>
    		</call-site>
		</method>
		
		<method decl="parseCmdLine(java.lang.String[])">
		</method>
		
		<method decl="void usage()">
		</method>
	</class>
</spec>