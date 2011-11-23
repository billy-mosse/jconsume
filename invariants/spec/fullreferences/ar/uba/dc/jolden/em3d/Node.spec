<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.jolden.em3d.Node">
		<method decl="void initSeed(long)">
		</method>
		
		<method decl="void &lt;init&gt;(int)">
			<relevant-parameters>degree</relevant-parameters>
			<requires><![CDATA[degree > 0]]></requires>
			
			<creation-site offset="0" >
      			<constraints><![CDATA[1 <= k <= degree]]></constraints>
    		</creation-site>
    	</method>
		
		<method decl="ar.uba.dc.jolden.em3d.Node[] fillTable(int,int)">
			<relevant-parameters>size, degree</relevant-parameters>
			<requires><![CDATA[degree > 0]]></requires>
			<requires><![CDATA[size > 0]]></requires>
			
			<creation-site offset="0" >
      			<constraints><![CDATA[1 <= k <= size]]></constraints>
    		</creation-site>
			
			<creation-site offset="2" >
      			<constraints><![CDATA[1 <= i < size]]></constraints>
    		</creation-site>
    		
    		<call-site offset="0" >
      			<constraints><![CDATA[$t.degree == degree]]></constraints>
    		</call-site>
    		
    		<call-site offset="1" >
      			<constraints><![CDATA[1 <= i < size and $t.degree == degree]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void makeUniqueNeighbors(ar.uba.dc.jolden.em3d.Node[])">
		</method>
		
		<method decl="void makeFromNodes()">
			<relevant-parameters>fromCount</relevant-parameters>
			<requires><![CDATA[fromCount > 0]]></requires>
			
			<creation-site offset="0-1" >
      			<constraints><![CDATA[1 <= k <= fromCount]]></constraints>
    		</creation-site>
		</method>
		
		<method decl="void updateFromNodes()">
		</method>
		
		<method decl="void computeNewValue()">
		</method>
		
		<method decl="java.util.Enumeration elements()">
		</method>

		<method decl="java.lang.String toString()">
		</method>
	</class>
</spec>