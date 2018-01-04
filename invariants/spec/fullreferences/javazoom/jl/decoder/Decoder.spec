<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="javazoom.jl.decoder.Decoder">   
    	<method decl="javazoom.jl.decoder.Obuffer decodeFrame(javazoom.jl.decoder.Header,javazoom.jl.decoder.Bitstream,javazoom.jl.decoder.FrameDecoder)">
			<relevant-parameters>header.num_subbands,header.mode,header.h_intensity_stereo_bound,layer</relevant-parameters>
			<requires>layer > 0 and layer &lt; 4 and header.num_subbands >= 0 and header.mode >= 0 and header.mode &lt; 4 and header.h_intensity_stereo_bound >= 0 and header.num_subbands > header.h_intensity_stereo_bound</requires>
			<call-site offset="1">
				<constraints>header.num_subbands == $t.this.num_subbands and $t.this.mode == header.mode and header.h_intensity_stereo_bound == $t.this.header.h_intensity_stereo_bound and layer == $t.layer</constraints>
			</call-site>
		</method>

		<method decl="void initialize(javazoom.jl.decoder.Header)">
			<relevant-parameters>header.mode</relevant-parameters>
			<requires><![CDATA[0 <= header.mode <= 3]]></requires>			
			<call-site offset="4">
				<constraints>$t.isdnull == 1</constraints>
			</call-site>
			<call-site offset="5">
				<constraints><![CDATA[header.mode < 3 and $t.isdnull == 0]]></constraints>
			</call-site>
		</method>

		<method decl="javazoom.jl.decoder.FrameDecoder retrieveDecoder(javazoom.jl.decoder.Header,javazoom.jl.decoder.Bitstream,int)">
			<relevant-parameters>layer</relevant-parameters>
			<requires>layer >= 0</requires>
			<creation-site offset="0" srccode-offset="229">
				<constraints>layer == 3</constraints>
			</creation-site>
			<call-site offset="0" srccode-offset="231">
				<constraints>layer == 3</constraints>
			</call-site>
			<creation-site offset="1" srccode-offset="239">
				<constraints>layer == 2</constraints>
			</creation-site>
			<call-site offset="1" srccode-offset="239">
				<constraints>layer == 2</constraints>
			</call-site>
			<call-site offset="2" srccode-offset="240">
				<constraints>layer == 2</constraints>
			</call-site>
			<creation-site offset="2" srccode-offset="249">
				<constraints>layer == 1</constraints>
			</creation-site>
			<call-site offset="3" srccode-offset="249">
				<constraints>layer == 1</constraints>
			</call-site>
			<call-site offset="4" srccode-offset="250">
				<constraints>layer == 1</constraints>
			</call-site>
			<call-site offset="5" srccode-offset="250">
				<constraints>layer > 3</constraints>
			</call-site>
		</method>
	</class>
</spec>