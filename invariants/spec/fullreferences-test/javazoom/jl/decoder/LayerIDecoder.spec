<?xml version="1.0" encoding="UTF-8"?>
<spec>
  <class decl="javazoom.jl.decoder.LayerIDecoder">
    <method decl="void decodeFrame()">
      <relevant-parameters>this.header.h_intensity_stereo_bound,this.mode,this.num_subbands</relevant-parameters>
      <requires>this.mode >= 0 and this.num_subbands >= 0 and this.header.h_intensity_stereo_bound >= 0 and this.num_subbands >=  this.header.h_intensity_stereo_bound and this.mode &lt; 4</requires>
      <call-site offset="2" impl="javazoom.jl.decoder.LayerIDecoder">
        <constraints><![CDATA[this.num_subbands == $t.this.num_subbands and this.mode == $t.this.mode and this.header.h_intensity_stereo_bound == $t.this.header.h_intensity_stereo_bound]]></constraints>
      </call-site>
      <call-site offset="2" impl="javazoom.jl.decoder.LayerIIDecoder">
        <constraints><![CDATA[this.num_subbands == $t.this.num_subbands and this.mode == $t.this.mode and this.header.h_intensity_stereo_bound == $t.this.header.h_intensity_stereo_bound]]></constraints>
      </call-site>
       <creation-site offset="0">
        <constraints><![CDATA[1 <= k <= 32]]></constraints>
      </creation-site> 
    </method>
    <method decl="void createSubbands()">
      <relevant-parameters>this.header.h_intensity_stereo_bound,this.mode,this.num_subbands</relevant-parameters>
      <requires>this.mode >= 0 and this.num_subbands > 0 and this.header.h_intensity_stereo_bound >= 0 and this.num_subbands >=  this.header.h_intensity_stereo_bound and this.mode &lt; 4</requires>
      <creation-site offset="0">
        <constraints><![CDATA[this.mode == 3 and i >= 0 and this.num_subbands > i]]></constraints>
      </creation-site>
      <creation-site offset="1">
        <constraints><![CDATA[this.mode == 1 and i >= 0 and this.header.h_intensity_stereo_bound > i]]></constraints>
      </creation-site>
      <creation-site offset="2">
        <constraints><![CDATA[this.mode == 1 and i >= this.header.h_intensity_stereo_bound and this.num_subbands > i]]></constraints>
      </creation-site>
      <!-- <creation-site offset="3">
        <constraints><![CDATA[this.mode == 2 and i >= 0 and this.num_subbands > i]]></constraints>
      </creation-site> -->
      <creation-site offset="3">
        <constraints><![CDATA[(this.mode == 2 and 0 <= i < this.num_subbands) or (this.mode == 0 and 0 <= i < this.num_subbands)]]></constraints>
      </creation-site>
    </method>
  </class>
</spec>