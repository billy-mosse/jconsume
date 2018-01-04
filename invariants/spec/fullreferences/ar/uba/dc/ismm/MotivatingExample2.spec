<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.ismm.MotivatingExample2">

	      <method decl="void main(java.lang.String[])">
		  <relevant-parameters>size, maxElementSize</relevant-parameters>
		  <requires><![CDATA[size > 0]]></requires>
		  <requires><![CDATA[maxElementSize > 0]]></requires>

		  <call-site offset="3">
		    <constraints><![CDATA[$t.list.size == size and $t.list.elements.maxSize == maxElementSize]]></constraints>
		  </call-site>
		</method>
	
		<method decl="ar.uba.dc.ismm.Transform getTransform(boolean)">
		</method>
		
		<method decl="java.lang.Integer[] toArray(ar.uba.dc.util.List)">
			<relevant-parameters>list.size</relevant-parameters>
			<requires><![CDATA[list.size > 0]]></requires>
   		
   			<call-site offset="2-5">
      			<constraints><![CDATA[1 <= k <= list.size]]></constraints>
    		</call-site>
    		
			<creation-site offset="0-1">
      			<constraints><![CDATA[1 <= k <= list.size]]></constraints>
    		</creation-site>	
		</method>
		
		<method decl="ar.uba.dc.util.List duplicate(ar.uba.dc.util.List)">
			<relevant-parameters>list.size</relevant-parameters>
			<requires><![CDATA[list.size > 0]]></requires>
			
    		<creation-site offset="1">
      			<constraints><![CDATA[1 <= k <= list.size]]></constraints>
    		</creation-site>

    		<call-site offset="2-6">
      			<constraints><![CDATA[1 <= k <= list.size]]></constraints>
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
		
		<method decl="ar.uba.dc.util.List map(ar.uba.dc.util.List,ar.uba.dc.ismm.Transform)">
			<relevant-parameters>list.size</relevant-parameters>
			<requires><![CDATA[list.size > 0]]></requires>

			<call-site offset="3" loop-invariant="true">
      			<constraints><![CDATA[1 <= k <= list.size]]></constraints>
    		</call-site>
    		
    		<call-site offset="2,4-5">
      			<constraints><![CDATA[1 <= k <= list.size]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.util.List copy(ar.uba.dc.util.List)">
			<relevant-parameters>list.size</relevant-parameters>
			<requires><![CDATA[list.size > 0]]></requires>
			
    		<call-site offset="2-4">
      			<constraints><![CDATA[1 <= k <= list.size]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.util.List safeMap(ar.uba.dc.util.List,ar.uba.dc.ismm.Transform)">
			<relevant-parameters>list.size</relevant-parameters>
			<requires><![CDATA[list.size > 0]]></requires>
		
    		<call-site offset="0-1">
      			<constraints><![CDATA[$t.list.size == list.size]]></constraints>
    		</call-site>
		</method>
		
		<method decl="ar.uba.dc.util.List test(ar.uba.dc.util.List,ar.uba.dc.ismm.Transform)">
			<relevant-parameters>list.size, list.elements.maxSize</relevant-parameters>
			<requires><![CDATA[list.size > 0]]></requires>
			<requires><![CDATA[list.elements.maxSize > 0]]></requires>
			
    		<call-site offset="4">
      			<constraints><![CDATA[1 <= k <= list.size and $t.list.size == list.elements.maxSize]]></constraints>
    		</call-site>
    		
    		<call-site offset="2,3,5">
      			<constraints><![CDATA[1 <= k <= list.size]]></constraints>
    		</call-site>
		</method>
	</class>
</spec>
