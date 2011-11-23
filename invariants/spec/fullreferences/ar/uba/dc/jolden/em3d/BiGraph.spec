<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.jolden.em3d.BiGraph">
		<method decl="void &lt;init&gt;(ar.uba.dc.jolden.em3d.Node,ar.uba.dc.jolden.em3d.Node)">
			<relevant-parameters>numNodes</relevant-parameters>
    	</method>
		
		<method decl="ar.uba.dc.jolden.em3d.BiGraph create(int,int,boolean)">
			<relevant-parameters>numNodes, numDegree</relevant-parameters>
			<requires><![CDATA[numNodes > 0]]></requires>
			<requires><![CDATA[numDegree > 0]]></requires>
			
			<call-site offset="2-3" >
      			<constraints><![CDATA[$t.size == numNodes and $t.degree == numDegree]]></constraints>
    		</call-site>
    		
    		<call-site offset="6-8,10-12,15,16,19,20,23-25,27-29" >
      			<constraints><![CDATA[1 <= i <= numNodes]]></constraints>
    		</call-site>
    		
    		<call-site offset="17,21" >
      			<constraints><![CDATA[1 <= i <= numNodes and $t.fromCount == numDegree]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void compute()">
			<relevant-parameters>numNodes</relevant-parameters>
			<requires><![CDATA[numNodes > 0]]></requires>
			
			<call-site offset="1-3,5-7" >
      			<constraints><![CDATA[1 <= i <= numNodes]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void addEdges(int)">
		</method>
		
		<method decl="java.lang.String toString()">
		</method>
	</class>
</spec>