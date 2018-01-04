<?xml version="1.0" encoding="UTF-8"?>
<spec>
 <class decl="javazoom.jl.player.Player">
 	<method decl="void &lt;init&gt;(java.io.InputStream, javazoom.jl.player.AudioDevice)">
      <relevant-parameters>inputStream.headerSize</relevant-parameters>
      <requires><![CDATA[inputStream.headerSize >= 0 ]]></requires>
      <call-site offset="1" srccode-offset="104">
      	 <constraints>$t.in.size == inputStream.headerSize</constraints>
       </call-site>
    </method>
 
  	<method decl="boolean play()">
      <relevant-parameters>MAX_SUBBANDS,MAX_BUFFER_SIZE,layerType,framesMode</relevant-parameters>
      <call-site offset="0" srccode-offset="104">
       <constraints>subbands.max == $t.input.subbands.max and MAX_SUBBANDS >= subbands.max and MAX_SUBBANDS >= 0 and $t.frames == 9999999 and $t.layerType == layerType and $t.framesMode == framesMode and $t.MAX_BUFFER_SIZE == MAX_BUFFER_SIZE</constraints>
       </call-site>
       <call-site offset="0" op="sum">
       <constraints>MAX_SUBBANDS == ms and ms == $t.input.subbands.max and $t.frames == 999999 and $t.layerType == layerType and $t.framesMode == framesMode and $t.MAX_BUFFER_SIZE == MAX_BUFFER_SIZE</constraints>
       </call-site>
    </method>
    <method decl="boolean play(int)">
      <relevant-parameters>MAX_BUFFER_SIZE,input.subbands.max,frames,layerType,framesMode</relevant-parameters>
		<requires><![CDATA[frames >= 0 and input.subbands.max >= 0 and 0 <= framesMode <= 3]]></requires>
      <call-site offset="0" srccode-offset="120">
        <constraints>framesMode == $t.frame.mode and intensity == $t.frame.intensity and subbands == $t.frame.subbands and input.subbands.max >= intensity and input.subbands.max >= subbands and $t.this.decoder.initialized == 0 and $t.frame.layer == layerType and MAX_BUFFER_SIZE > $t.frame.len </constraints>
      </call-site>
      <call-site offset="0" op="sum">
        <!-- <constraints>mode == $t.frame.mode and intensity == $t.frame.intensity and subbands == $t.frame.subbands and intensity >= 0 and intensity &lt; 1 and mode > 0 and mode &lt; 4 and subbands >= 1 and subbands &lt; 2 and $t.this.decoder.initialized == i and i >= 0 and $t.frame.layer == layerType</constraints> -->
        <constraints>framesMode == $t.frame.mode and intensity == $t.frame.intensity and subbands == $t.frame.subbands and intensity >= 0 and intensity &lt; 1 and subbands >= 1 and subbands &lt; 2 and $t.this.decoder.initialized == 0 and $t.frame.layer == layerType and $t.frame.len == MAX_BUFFER_SIZE</constraints>
    </call-site>
    </method>
    <method decl="boolean decodeFrame()">
    	<relevant-parameters>frame.mode,frame.intensity,frame.subbands,this.decoder.initialized,frame.layer,frame.len</relevant-parameters>
	    <requires>frame.mode >= 0 and frame.subbands > 0 and frame.intensity >= 0 and frame.subbands >= frame.intensity and frame.mode &lt; 4 and this.decoder.initialized >= 0 and frame.layer > 0  and frame.layer &lt; 4 and frame.len > 0</requires>
	    
	    <!--
	    	El capture es utilizado para capturar los bytes leidos por readFrame. Esto es porque el header
	    	es retornado pero escapa ademas por bitstream.header. Sin embargo, ese valor no es leido luego
	    	del decodeFrame y es potencialmente recolectable.
	    -->
	    <call-site offset="0" capture="true">
	      <constraints>frame.mode >= 0</constraints>
	    </call-site>
	    <call-site offset="1" capture="false">
	      <constraints>$t.header.mode == frame.mode and this.decoder.initialized == 0</constraints>
	    </call-site>
	    <call-site offset="3" capture="false">
	      <constraints>$t.layer == frame.layer and this.decoder.initialized == 0</constraints>
	    </call-site>
	    <call-site offset="4" srccode-offset="208" capture="true">
	      <constraints>$t.header.num_subbands == frame.subbands and $t.header.mode == frame.mode and frame.intensity == $t.header.h_intensity_stereo_bound and frame.layer == $t.layer</constraints>
	    </call-site>
	    <!-- Este callsite no genera un residual porque write asume que siempre tiene un buffer suficientemente grande.
	     Lo modelamos como un temporal, porque se sobreescribe el buffer actual con la diferencia.
	     -->
	    <call-site offset="7" capture="true">
	      <constraints>$t.len == frame.len</constraints>
	    </call-site>
  </method>
  </class>
</spec>