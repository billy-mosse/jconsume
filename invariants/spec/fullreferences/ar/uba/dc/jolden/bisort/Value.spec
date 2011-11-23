<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.jolden.bisort.Value">
		<method decl="void &lt;init&gt;(int)">
			<relevant-parameters>s</relevant-parameters>
    	</method>
		
		<method decl="ar.uba.dc.jolden.bisort.Value createTree(int,int)">
			<relevant-parameters>s</relevant-parameters>
			
			<call-site offset="2,4">
      			<constraints><![CDATA[$t.s == s / 2]]></constraints>
    		</call-site>
		</method>
		
		<method decl="int bisort(int,boolean)">
			<relevant-parameters>s</relevant-parameters>
			
			<call-site offset="0,1">
      			<constraints><![CDATA[$t.s == s / 2]]></constraints>
    		</call-site>
    		
    		<call-site offset="2">
      			<constraints><![CDATA[$t.s == s]]></constraints>
    		</call-site>
		</method>
		
		<method decl="int bimerge(int, boolean)">
			<relevant-parameters>s</relevant-parameters>
			
			<call-site offset="2,3">
      			<constraints><![CDATA[$t.s == s / 2]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void swapValRight(ar.uba.dc.jolden.bisort.Value)">
		</method>
		
		<method decl="void swapValLeft(ar.uba.dc.jolden.bisort.Value)">
		</method>
		
		<method decl="void inOrder()">
			<relevant-parameters>s</relevant-parameters>
			
			<call-site offset="0,8">
      			<constraints><![CDATA[$t.s == s / 2]]></constraints>
    		</call-site>
		</method>
		
		<method decl="int mult(int,int)">
		</method>
		
		<method decl="int skiprand(int, int)">
		</method>
		
		<method decl="int random(int)">
		</method>
	</class>
</spec>