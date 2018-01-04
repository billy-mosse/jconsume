<?xml version="1.0" encoding="UTF-8"?>
<spec>
  <class decl="javazoom.jl.player.jlp">
    <method decl="void main(java.lang.String[])">
      <relevant-parameters>MAX_SUBBANDS,MAX_BUFFER_SIZE,inputStream.headerSize</relevant-parameters>
      <call-site offset="1">
	<constraints><![CDATA[ MAX_SUBBANDS == $t.MAX_SUBBANDS and MAX_SUBBANDS >= 0 and $t.MAX_BUFFER_SIZE == MAX_BUFFER_SIZE and $t.inputStream.headerSize == inputStream.headerSize and inputStream.headerSize >= 0]]></constraints>
      </call-site>
      </method>
      <method decl="void play()">
	<relevant-parameters>MAX_SUBBANDS,MAX_BUFFER_SIZE,inputStream.headerSize</relevant-parameters>
	<call-site offset="4" srccode-offset="119">
	  <constraints><![CDATA[ b == $t.MAX_SUBBANDS and b >= 0 and MAX_SUBBANDS >= b and MAX_SUBBANDS >= 0 and $t.MAX_BUFFER_SIZE == MAX_BUFFER_SIZE and $t.inputStream.headerSize == inputStream.headerSize and MAX_BUFFER_SIZE >= 0 and MAX_SUBBANDS >= 0]]></constraints>
	</call-site>
	<call-site offset="4" op="sum">
	  <constraints><![CDATA[ MAX_SUBBANDS == $t.MAX_SUBBANDS and $t.MAX_BUFFER_SIZE == MAX_BUFFER_SIZE and $t.inputStream.headerSize == inputStream.headerSize and MAX_BUFFER_SIZE >= 0 and MAX_SUBBANDS >= 0]]></constraints>
	</call-site>
    </method>
  </class>
</spec>