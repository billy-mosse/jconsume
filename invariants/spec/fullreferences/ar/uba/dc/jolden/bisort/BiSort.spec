<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.jolden.bisort.BiSort">
		<method decl="void &lt;init&gt;()">
    	</method>
		
		<method decl="void main(java.lang.String[])">
		</method>
		
		<method decl="void mainOrig(java.lang.String[])">
		</method>
		
		<method decl="void mainParameters(int,boolean,boolean)">
			<relevant-parameters>s</relevant-parameters>
			
			<call-site offset="6,9,13,15,18,20">
      			<constraints><![CDATA[$t.s == s]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void parseCmdLine(java.lang.String[])">
		</method>
		
		<method decl="void usage()">
		</method>
	</class>
</spec>