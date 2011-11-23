<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="javazoom.jl.decoder.Bitstream">
		<method decl="void &lt;init&gt;(java.io.InputStream)">
			<relevant-parameters>in.size</relevant-parameters>
      		<creation-site offset="0">
       			<constraints>433</constraints>
      		</creation-site>
      		<creation-site offset="1">
       			<constraints>1732</constraints>
      		</creation-site>
      		<creation-site offset="2">
       			<constraints>18</constraints>
      		</creation-site>
      		<creation-site offset="4">
       			<constraints>4</constraints>
      		</creation-site>
      		<creation-site offset="5">
       			<constraints>1</constraints>
      		</creation-site>
      		<call-site offset="4">
				<constraints><![CDATA[$t.in.size == in.size and in.size >= 0]]></constraints>
			</call-site>
    	</method>
	
    	<method decl="int readID3v2Header(java.io.InputStream)">
      		<creation-site offset="0">
       			<constraints>4</constraints>
      		</creation-site>
    	</method>

	<method decl="void loadID3v2(java.io.InputStream)">
	<relevant-parameters>in.size</relevant-parameters>
		<creation-site offset="0">
       		<constraints><![CDATA[0 < k <= in.size]]> </constraints>
      	</creation-site>
    	</method>
  	</class>  
</spec>