<?xml version="1.0" encoding="UTF-8"?>
<spec>
 <class decl="javazoom.jl.player.Player">
    <method decl="void &lt;init&gt;(java.io.InputStream, javazoom.jl.player.AudioDevice)">
      <relevant-parameters>inputStream.headerSize</relevant-parameters>
      <requires><![CDATA[inputStream.headerSize >= 0 ]]></requires>
      <call-site offset="1" srccode-offset="104" capture="false">
      	 <constraints>$t.in.size == inputStream.headerSize</constraints>
       </call-site>
    </method>
    <method decl="boolean play()">
      <relevant-parameters>MAX_SUBBANDS,MAX_BUFFER_SIZE</relevant-parameters>
      <requires><![CDATA[MAX_BUFFER_SIZE >= 0 and MAX_SUBBANDS >= 0 ]]></requires>
      <call-site offset="0" srccode-offset="104">
       <constraints>subbands.max == $t.input.subbands.max and MAX_SUBBANDS >= subbands.max and MAX_SUBBANDS >= 0 and $t.frames == 9999999 and $t.MAX_BUFFER_SIZE == MAX_BUFFER_SIZE</constraints>
       </call-site>
       <call-site offset="0" op="sum">
       <constraints>MAX_SUBBANDS == ms and ms == $t.input.subbands.max and $t.frames == 999999 and $t.MAX_BUFFER_SIZE == MAX_BUFFER_SIZE</constraints>
       </call-site>
    </method>
    <method decl="boolean play(int)">
      <relevant-parameters>MAX_BUFFER_SIZE,input.subbands.max,frames</relevant-parameters>
		<requires><![CDATA[frames >= 0 and input.subbands.max >= 0]]></requires>
      <call-site offset="0" srccode-offset="120">
        <constraints><![CDATA[framesMode == $t.frame.mode and 0 <= framesMode <= 3 and intensity == $t.frame.intensity and subbands == $t.frame.subbands and input.subbands.max >= intensity and input.subbands.max >= subbands and $t.this.decoder.initialized == 0 and MAX_BUFFER_SIZE > $t.frame.len ]]></constraints>
      </call-site>
      <call-site offset="0" op="sum">
	  <!--constraints><![CDATA[framesMode == $t.frame.mode and framesMode == 0 and intensity == $t.frame.intensity and subbands == $t.frame.subbands and intensity >= 0 and intensity < 1 and subbands >= 1 and subbands < 2 and $t.this.decoder.initialized == 0 and $t.frame.len == MAX_BUFFER_SIZE]]></constraints-->
    <constraints><![CDATA[framesMode == $t.frame.mode and framesMode == 1 and intensity == $t.frame.intensity and subbands == $t.frame.subbands and intensity >= 0 and intensity < 1 and subbands >= 1 and subbands < 2 and $t.this.decoder.initialized == 0 and $t.frame.len == MAX_BUFFER_SIZE]]></constraints>
    
    </call-site>
    </method>
    <method decl="boolean decodeFrame()">
    	<relevant-parameters>frame.mode,frame.intensity,frame.subbands,this.decoder.initialized,frame.len</relevant-parameters>
	    <requires>frame.mode >= 0 and frame.subbands > 0 and frame.subbands >= frame.intensity and frame.intensity >= 0 and frame.mode &lt; 4 and this.decoder.initialized >= 0 and frame.len > 0</requires>
	    
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
	      <constraints><![CDATA[$t.layer == l and this.decoder.initialized == 0 and  l >= 1 and l <= 3]]></constraints>
	    </call-site>
	    <call-site offset="4" srccode-offset="208" capture="true">
	      <constraints>$t.header.num_subbands == frame.subbands and $t.header.mode == frame.mode and frame.intensity == $t.header.h_intensity_stereo_bound</constraints>
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