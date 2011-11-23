<?xml version="1.0" encoding="UTF-8"?>
<spec>
  <class decl="javazoom.jl.decoder.LayerIIDecoder">
    <method decl="void createSubbands()">
      <relevant-parameters>this.num_subbands,this.mode,this.header.h_intensity_stereo_bound</relevant-parameters>
     <requires>this.mode >= 0 and this.num_subbands > 0 and this.header.h_intensity_stereo_bound >= 0 and this.num_subbands >=  this.header.h_intensity_stereo_bound and this.mode &lt; 4</requires>
     <creation-site offset="0" srccode-offset="48">
        <constraints>this.mode == 3 and i >= 0 and this.num_subbands > i</constraints>
      </creation-site>
      <call-site offset="0" srccode-offset="48">
       <constraints>this.mode == 3 and i >= 0 and this.num_subbands > i</constraints>
      </call-site>
      <creation-site offset="1" srccode-offset="52">
         <constraints>this.mode == 1 and i >= 0 and this.header.h_intensity_stereo_bound > i</constraints>
      </creation-site>
      <call-site offset="1" srccode-offset="52">
        <constraints>this.mode == 1 and i >= 0 and this.header.h_intensity_stereo_bound > i</constraints>
      </call-site>
      <creation-site offset="2" srccode-offset="54">
        <constraints>this.mode == 1 and i >= this.header.h_intensity_stereo_bound and this.num_subbands > i</constraints>
      </creation-site>
       <call-site offset="3" srccode-offset="54">
       <constraints>this.mode == 1 and i >= this.header.h_intensity_stereo_bound and this.num_subbands > i</constraints>
      </call-site>
      <!-- <creation-site offset="3" srccode-offset="59">
        <constraints><![CDATA[this.mode == 2 and 0 <= i < this.num_subbands]]></constraints>
      </creation-site>
      <call-site offset="4" srccode-offset="59">
      	<constraints><![CDATA[this.mode == 2 and 0 <= i < this.num_subbands]]></constraints>
      </call-site> -->
      <creation-site offset="3" srccode-offset="59">
        <constraints><![CDATA[(this.mode == 2 and 0 <= i < this.num_subbands) or (this.mode == 0 and 0 <= i < this.num_subbands)]]></constraints>
      </creation-site>
      <call-site offset="4" srccode-offset="59">
      	<constraints><![CDATA[(this.mode == 2 and 0 <= i < this.num_subbands) or (this.mode == 0 and 0 <= i < this.num_subbands)]]></constraints>
      </call-site> 
    </method>
  </class>  
</spec>