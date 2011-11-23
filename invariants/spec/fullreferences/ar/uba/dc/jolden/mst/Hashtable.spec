<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.jolden.mst.Hashtable">
		<method decl="void &lt;init&gt;(int)">
			<relevant-parameters>size</relevant-parameters>
			<requires><![CDATA[size > 0]]></requires>
			
			<creation-site offset="0" srccode-offset="">
      			<constraints><![CDATA[1 <= k <= size]]></constraints>
    		</creation-site>
		</method>
		
		<method decl="int hashMap(java.lang.Object)">
			<relevant-parameters>key</relevant-parameters>
		</method>
		
		<method decl="java.lang.Object get(java.lang.Object)">
			<relevant-parameters>this.array.elements.maxLength</relevant-parameters>
			<requires><![CDATA[this.array.elements.maxLength > 0]]></requires>
    		
			<call-site offset="1,2" srccode-offset="">
      			<constraints><![CDATA[0 <= i <= this.array.elements.maxLength]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void put(java.lang.Object,java.lang.Object)">
			<relevant-parameters>key, value</relevant-parameters>
		</method>
		
		<method decl="void remove(java.lang.Object)">
			<relevant-parameters>this.array.elements.maxLength</relevant-parameters>
			<requires><![CDATA[this.array.elements.maxLength > 0]]></requires>
    		
			<call-site offset="3-5" srccode-offset="">
      			<constraints><![CDATA[0 <= i <= this.array.elements.maxLength]]></constraints>
    		</call-site>
		</method>
	</class>
</spec>