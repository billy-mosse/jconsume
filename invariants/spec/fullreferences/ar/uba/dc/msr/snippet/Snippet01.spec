<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.msr.snippet.Snippet01">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>size</relevant-parameters>
			
			<call-site offset="2" >
      			<constraints><![CDATA[$t.size == size]]></constraints>
    		</call-site>
    		
    		<call-site offset="3" >
      			<constraints><![CDATA[$t.vector.length == size]]></constraints>
    		</call-site>
    		
    		<call-site offset="4" >
      			<constraints><![CDATA[$t.vector.length == size]]></constraints>
    		</call-site>
    	
    	</method>
    	
    	<method decl="java.lang.Integer[] randomVector(int)">
			<relevant-parameters>size</relevant-parameters>
			
			<creation-site offset="2" >
      			<constraints><![CDATA[1 <= k <= size]]></constraints>
    		</creation-site>
			
			<creation-site offset="3" >
      			<constraints><![CDATA[0 <= i < size]]></constraints>
    		</creation-site>
    		
    		<call-site offset="3-4" >
      			<constraints><![CDATA[0 <= i < size]]></constraints>
    		</call-site>
    	</method>
    	
    	<method decl="java.lang.Integer[] copy(java.lang.Integer[])">
			<relevant-parameters>vector.length</relevant-parameters>
			
			<creation-site offset="0" >
      			<constraints><![CDATA[1 <= k <= vector.length]]></constraints>
    		</creation-site>
    	</method>
    	
    	<method decl="java.lang.Integer[] deepCopy(java.lang.Integer[])">
			<relevant-parameters>vector.length</relevant-parameters>
			
			<creation-site offset="0" >
      			<constraints><![CDATA[1 <= k <= vector.length]]></constraints>
    		</creation-site>
			
			<creation-site offset="1" >
      			<constraints><![CDATA[0 <= i < vector.length]]></constraints>
    		</creation-site>
    		
    		<call-site offset="0" >
      			<constraints><![CDATA[0 <= i < vector.length]]></constraints>
    		</call-site>
    	</method>
    	
    </class>
</spec>