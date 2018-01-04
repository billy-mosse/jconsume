<?xml version="1.0" encoding="UTF-8"?>

<spec>
   <class decl="javazoom.jl.decoder.SynthesisFilter">
   <method decl="void &lt;init&gt;(int,float,float[])">
      <relevant-parameters>isdnull</relevant-parameters>
      <creation-site offset="0">
        <constraints>32</constraints>
      </creation-site>
      <creation-site offset="1" srccode-offset="77">
        <constraints>512</constraints>
      </creation-site>
      <creation-site offset="2" srccode-offset="78">
         <constraints>512</constraints>
      </creation-site>
      <creation-site offset="3" srccode-offset="79">
         <constraints>32</constraints>
      </creation-site>
      <creation-site offset="4">
         <constraints>32</constraints>
      </creation-site>
      <call-site offset="2">
      	<constraints>isdnull == 1 and $t.result.size == 32 and $t.blockSize == 16</constraints>
      </call-site>
    </method>
    <method decl="float[][] splitArray(float[],int)">
    	<relevant-parameters>result.size, blockSize</relevant-parameters>
    	<requires>blockSize > 0</requires>
    	<requires>result.size > 0</requires>
    	<creation-site offset="0">
        	<constraints><![CDATA[0 <= i < result.size]]></constraints>
      	</creation-site>
      	<call-site offset="0">
        	<constraints><![CDATA[0 <= i < result.size and $t.len == blockSize]]></constraints>
      	</call-site>
    </method>
    <method decl="float[] subArray(float[],int,int)">
      <relevant-parameters>len</relevant-parameters>
      <requires>len > 0</requires>
      <creation-site offset="0" srccode-offset="96">
         <constraints><![CDATA[0 <= i < len]]></constraints>
      </creation-site>
    </method>
    <method decl="void setEQ(float[])">
      <relevant-parameters/>
      <creation-site offset="0" srccode-offset="96">
         <constraints>32</constraints>
      </creation-site>
    </method>
   </class>
</spec>