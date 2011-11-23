<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.ismm.Snippet03">
		<method decl="void main(java.lang.String[])">
			<creation-site offset="0">
      			<constraints><![CDATA[0 <= i < 10]]></constraints>
    		</creation-site>
    		
    		<creation-site offset="1">
      			<constraints><![CDATA[0 <= i < 20]]></constraints>
    		</creation-site>
		
			<call-site offset="0">
      			<constraints><![CDATA[0 <= i < 10]]></constraints>
    		</call-site>
    		
    		<call-site offset="1">
      			<constraints><![CDATA[0 <= i < 20]]></constraints>
    		</call-site>
		
			<call-site offset="2">
      			<constraints><![CDATA[$t.v1.length == 10 and $t.v2.length == 20]]></constraints>
    		</call-site>
    		
    		<call-site offset="3">
      			<constraints><![CDATA[$t.v1.length == 20 and $t.v2.length == 10]]></constraints>
    		</call-site>
		</method>

		<method decl="java.lang.Integer[] multiyply(java.lang.Integer[],int)">
			<relevant-parameters>v.length</relevant-parameters>
			<requires><![CDATA[v.length > 0]]></requires>
			
			<creation-site offset="0">
      			<constraints><![CDATA[1 <= k <= v.length]]></constraints>
    		</creation-site>
    		
    		<creation-site offset="1-2">
      			<constraints><![CDATA[1 <= k <= v.length]]></constraints>
    		</creation-site>
    		
    		<call-site offset="0-3">
      			<constraints><![CDATA[1 <= k <= v.length]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void duplicate(java.lang.Integer[],java.lang.Integer[])">
			<relevant-parameters>v1.length,v2.length</relevant-parameters>
			<requires><![CDATA[v1.length > 0]]></requires>
			<requires><![CDATA[v2.length > 0]]></requires>
			
			<call-site offset="0">
      			<constraints><![CDATA[$t.v.length == v1.length]]></constraints>
    		</call-site>
    		
    		<call-site offset="1">
      			<constraints><![CDATA[$t.v.length == v2.length]]></constraints>
    		</call-site>
		</method>
	</class>
</spec>