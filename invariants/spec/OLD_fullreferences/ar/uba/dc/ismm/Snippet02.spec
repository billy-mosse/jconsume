<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.ismm.Snippet02">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>size, maxElementSize</relevant-parameters>
			<creation-site  offset="0-2" >
      			<constraints><![CDATA[size > 0 and maxElementSize > 0]]></constraints>
    		</creation-site>
			
    		<call-site  offset="0" >
      			<constraints><![CDATA[$t.v1.length == size and $t.v2.length == size and size > 0 and maxElementSize > 0]]></constraints>
    		</call-site>
    		
    		<call-site  offset="1" >
      			<constraints><![CDATA[$t.v.length == size  and size > 0 and maxElementSize > 0]]></constraints>
    		</call-site>
    		
    		<call-site  offset="2,3,6,8" >
      			<constraints><![CDATA[size > 0 and maxElementSize > 0]]></constraints>
    		</call-site>
    		
			<call-site  offset="4,5,7" >
      			<constraints><![CDATA[$t.list.size == size and size > 0 and maxElementSize > 0]]></constraints>
    		</call-site>
    		
    		<call-site  offset="9" >
      			<constraints><![CDATA[$t.list.size == size and $t.list.elements.maxSize == maxElementSize and size > 0 and maxElementSize > 0]]></constraints>
    		</call-site>
		</method>
	
		<method decl="ar.uba.dc.ismm.ITrans getTransform(boolean)">
		</method>
		
		<method decl="java.lang.Integer[] toArray(ar.uba.dc.rinard.List)">
			<relevant-parameters>list.size</relevant-parameters>
   		
   			<call-site offset="0-1" >
      			<constraints><![CDATA[list.size > 0]]></constraints>
    		</call-site>
   		
    		<call-site offset="2-5" >
      			<constraints><![CDATA[1 <= k <= list.size and list.size > 0]]></constraints>
    		</call-site>
    		
			<creation-site offset="0" >
      			<constraints><![CDATA[1 <= k <= list.size and list.size > 0]]></constraints>
    		</creation-site>
    		
    		<creation-site offset="1" >
      			<constraints><![CDATA[1 <= k <= list.size and list.size > 0]]></constraints>
    		</creation-site>
    		
    		
		</method>
		
		<method decl="ar.uba.dc.rinard.List duplicate(ar.uba.dc.rinard.List)">
			<relevant-parameters>list.size</relevant-parameters>
			<creation-site offset="0" >
      			<constraints><![CDATA[list.size > 0]]></constraints>
    		</creation-site>
			
    		<creation-site offset="1" >
      			<constraints><![CDATA[1 <= k <= list.size and list.size > 0]]></constraints>
    		</creation-site>
    		
    		<call-site offset="0-1" >
      			<constraints><![CDATA[list.size > 0]]></constraints>
    		</call-site>
    		
    		<call-site offset="2-6" >
      			<constraints><![CDATA[1 <= k <= list.size and list.size > 0]]></constraints>
    		</call-site>
		</method>
		
		<method decl="java.lang.Integer[] multiyply(java.lang.Integer[],int)">
			<relevant-parameters>v.length</relevant-parameters>
			<creation-site  offset="0" >
      			<constraints><![CDATA[1 <= k <= v.length and v.length > 0]]></constraints>
    		</creation-site>
    		
    		<creation-site  offset="1-2" >
      			<constraints><![CDATA[1 <= k <= v.length and v.length > 0]]></constraints>
    		</creation-site>
    		
    		<call-site offset="0-3" >
      			<constraints><![CDATA[1 <= k <= v.length and v.length > 0]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void duplicate(java.lang.Integer[],java.lang.Integer[])">
			<relevant-parameters>v1.length,v2.length</relevant-parameters>
			<call-site  offset="0" >
      			<constraints><![CDATA[$t.v.length == v1.length and v1.length > 0 and v2.length > 0]]></constraints>
    		</call-site>
    		
    		<call-site  offset="1" >
      			<constraints><![CDATA[$t.v.length == v2.length and v1.length > 0 and v2.length > 0]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.rinard.List map(ar.uba.dc.rinard.List,ar.uba.dc.ismm.ITrans)">
			<relevant-parameters>list.size</relevant-parameters>
			
			<creation-site offset="0" >
      			<constraints><![CDATA[list.size > 0]]></constraints>
    		</creation-site>
			
			<call-site offset="0-1" >
      			<constraints><![CDATA[list.size > 0]]></constraints>
    		</call-site>
    		
    		<call-site offset="2-5" >
      			<constraints><![CDATA[1 <= k <= list.size and list.size > 0]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.rinard.List copyList(ar.uba.dc.rinard.List)">
			<relevant-parameters>list.size</relevant-parameters>
			
			<creation-site offset="0" >
      			<constraints><![CDATA[list.size > 0]]></constraints>
    		</creation-site>
			
			<call-site offset="0-1" >
      			<constraints><![CDATA[list.size > 0]]></constraints>
    		</call-site>
			
    		<call-site offset="2-4" >
      			<constraints><![CDATA[1 <= k <= list.size and list.size > 0]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.rinard.List safeMap(ar.uba.dc.rinard.List,ar.uba.dc.ismm.ITrans)">
			<relevant-parameters>list.size</relevant-parameters>
		
    		<call-site offset="0-1" >
      			<constraints><![CDATA[$t.list.size == list.size and list.size > 0]]></constraints>
    		</call-site>

		</method>
		
		<method decl="ar.uba.dc.rinard.List test(ar.uba.dc.rinard.List,ar.uba.dc.ismm.ITrans)">
			<relevant-parameters>list.size, list.elements.maxSize</relevant-parameters>
			<creation-site offset="0" >
      			<constraints><![CDATA[list.size > 0 and list.elements.maxSize > 0]]></constraints>
    		</creation-site>
			
    		<call-site offset="3" >
      			<constraints><![CDATA[1 <= k <= list.size and $t.list.size == list.elements.maxSize and list.size > 0 and list.elements.maxSize > 0]]></constraints>
    		</call-site>
    		
    		<call-site offset="0-1" >
      			<constraints><![CDATA[list.size > 0 and list.elements.maxSize > 0]]></constraints>
    		</call-site>
    		
    		<call-site offset="2,4,5" >
      			<constraints><![CDATA[1 <= k <= list.size and list.size > 0 and list.elements.maxSize > 0]]></constraints>
    		</call-site>
    		
		</method>
	</class>
</spec>