<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="ar.uba.dc.rinard.BasicTest">
		<method decl="void main(java.lang.String[])">
			<relevant-parameters>n</relevant-parameters>
			
			<call-site offset="2, 4">
      			<constraints><![CDATA[$t.n == n]]></constraints>
    		</call-site>
    		
    		<call-site offset="3">
      			<constraints><![CDATA[$t.upTo == n]]></constraints>
    		</call-site>
		</method>
	
		<method decl="void sumNumbersUpToParameterUsingStaticList(java.lang.Integer)">
			<relevant-parameters>upTo</relevant-parameters>
			
			<creation-site offset="1">
      			<constraints><![CDATA[1 <= i <= upTo]]></constraints>
    		</creation-site>
    		
    		<call-site offset="2-8">
      			<constraints><![CDATA[1 <= i <= upTo]]></constraints>
    		</call-site>
		</method>
		
		<method decl="void sumNumbersUpToParameter(java.lang.Integer)">
			<relevant-parameters>n</relevant-parameters>
			
			<creation-site offset="1">
      			<constraints><![CDATA[1 <= i <= n]]></constraints>
    		</creation-site>
    		
    		<call-site offset="2-8">
      			<constraints><![CDATA[1 <= i <= n]]></constraints>
    		</call-site>
		</method>
		
		<method decl="java.lang.Integer sumOperator(java.lang.Integer)">
			<relevant-parameters>n</relevant-parameters>
			
			<call-site offset="3-9">
      			<constraints><![CDATA[1 <= i <= n]]></constraints>
    		</call-site>
    		
    		<call-site offset="11-17">
      			<constraints><![CDATA[1 <= i < n]]></constraints>
    		</call-site>
    	</method>
    	
    	<method decl="void constantParameterCall()">
			
			<call-site offset="1">
      			<constraints><![CDATA[$t.n == 0]]></constraints>
    		</call-site>
    		
    		<call-site offset="3">
      			<constraints><![CDATA[$t.n == 1]]></constraints>
    		</call-site>
    		
    		<call-site offset="5">
      			<constraints><![CDATA[$t.n == 7]]></constraints>
    		</call-site>
    	</method>
	</class>
</spec>