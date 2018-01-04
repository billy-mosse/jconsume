<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="javazoom.jl.player.JavaSoundAudioDevice">
	<method decl="void &lt;init&gt;()">
      <relevant-parameters/>
 	    <creation-site offset="0" srccode-offset="50">
        <constraints>4096</constraints>
      </creation-site>
    </method>
    <method decl="byte[] getByteArray(int)">
      <relevant-parameters>length,this.byteBuf.length</relevant-parameters>
      <requires>length > 0 and this.byteBuf.length > 0</requires>
      <creation-site offset="0">
        <!-- <constraints>i > 0 and length  >= i </constraints> -->
        <constraints><![CDATA[0 < i <= length + 1024 and length > this.byteBuf.length and length > 4096 ]]></constraints>
      </creation-site>
    </method>
      <method decl="byte[] toByteArray(short[],int,int)">
        <relevant-parameters>len,bufLen</relevant-parameters>
      <call-site offset="0" srccode-offset="175">
        <!-- <constraints>$t.length == len and j >= 0 and j &lt; 2</constraints> -->
        <constraints>$t.length == 2*len and $t.this.byteBuf.length == bufLen</constraints>
      </call-site>
    </method>
    <method decl="void writeImpl(short[],int,int)">
       <relevant-parameters>len,thisBufLen</relevant-parameters>
        <requires>len > 0 and thisBufLen > 0 </requires>
      <call-site offset="1" srccode-offset="155">
         <constraints>$t.bufLen == thisBufLen and $t.length == len</constraints>
      </call-site>
      
      <!-- <call-site offset="1" srccode-offset="157" capture="true">
         <constraints>$t.len == len and j >= 0 and j &lt; 2</constraints>
      </call-site> -->
      <call-site offset="1" srccode-offset="157" capture="false">
         <constraints>$t.len == len</constraints>
      </call-site>
    </method>
	</class>
</spec>