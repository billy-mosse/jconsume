<?xml version="1.0" encoding="UTF-8"?>

<spec>
	<class decl="javazoom.jl.player.jlp">
		<method decl="void main(java.lang.String[])">
      		<relevant-parameters>MAX_SUBBANDS,MAX_BUFFER_SIZE,layerType,framesMode,inputStream.headerSize</relevant-parameters>
      		<call-site offset="1">
         		<constraints>MAX_SUBBANDS == $t.MAX_SUBBANDS and MAX_SUBBANDS >= 0 and $t.layerType == layerType and $t.framesMode == framesMode and $t.MAX_BUFFER_SIZE == MAX_BUFFER_SIZE and $t.inputStream.headerSize == inputStream.headerSize</constraints>
      		</call-site>
 		</method>
 		<method decl="void play()">
      		<relevant-parameters>MAX_SUBBANDS,MAX_BUFFER_SIZE,layerType,framesMode,inputStream.headerSize</relevant-parameters>
      			 <requires><![CDATA[inputStream.headerSize >= 0 and MAX_BUFFER_SIZE >= 0 and framesMode >= 0 and framesMode <= 3 and layerType > 0 and layerType <= 3]]></requires>
      			<call-site offset="3" srccode-offset="119">
         			<constraints>$t.inputStream.headerSize == inputStream.headerSize</constraints>
         		</call-site>
      			<call-site offset="4" srccode-offset="119">
         			<constraints>b == $t.MAX_SUBBANDS and b >= 0 and MAX_SUBBANDS >= b and MAX_SUBBANDS >= 0 and $t.layerType == layerType and $t.framesMode == framesMode and $t.MAX_BUFFER_SIZE == MAX_BUFFER_SIZE</constraints>
      			</call-site>
       			<call-site offset="4" op="sum">
         			<constraints>MAX_SUBBANDS == $t.MAX_SUBBANDS and $t.layerType == layerType and $t.framesMode == framesMode and $t.MAX_BUFFER_SIZE == MAX_BUFFER_SIZE</constraints>
      			</call-site>
    	</method>
	</class>
</spec>