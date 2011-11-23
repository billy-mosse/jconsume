<?xml version="1.0" encoding="UTF-8"?>
<spec>
	<class decl="javazoom.jl.decoder.LayerIIIDecoder">
		<method decl="void &lt;init&gt;(javazoom.jl.decoder.Bitstream, javazoom.jl.decoder.Header, javazoom.jl.decoder.SynthesisFilter, javazoom.jl.decoder.SynthesisFilter, javazoom.jl.decoder.Obuffer, int)">
			<relevant-parameters></relevant-parameters>
			<creation-site offset="0,1">
				<constraints>32</constraints>
			</creation-site>
			<creation-site offset="2">
				<constraints>4</constraints>
			</creation-site>
			<creation-site offset="7,8,14">
				<constraints>576</constraints>
			</creation-site>
			<creation-site offset="9">
				<constraints>18</constraints>
			</creation-site>
			<creation-site offset="10">
				<constraints>36</constraints>
			</creation-site>
			<creation-site offset="11">
				<constraints>580</constraints>
			</creation-site>
			<creation-site offset="12,13,15,16">
				<constraints>1152</constraints>
			</creation-site>
			<creation-site offset="17,18">
				<constraints>2</constraints>
			</creation-site>
			<creation-site offset="21,49">
				<constraints>9</constraints>
			</creation-site>
			<creation-site offset="22,24,26,28,30,32,34,36,38">
				<constraints>23</constraints>
			</creation-site>
			<creation-site offset="23,25,27,29,31,33,35,37,39">
				<constraints>14</constraints>
			</creation-site>
			<creation-site offset="50">
				<constraints>5</constraints>
			</creation-site>
			<creation-site offset="51">
				<constraints>3</constraints>
			</creation-site>
			<creation-site offset="53">
				<constraints>54</constraints>
			</creation-site>
			<call-site offset="13">
				<constraints><![CDATA[0 <= i < 9]]></constraints>
			</call-site>
		</method>
		<method decl="void &lt;clinit&gt;()">
			<relevant-parameters></relevant-parameters>
			<creation-site offset="0,5">
				<constraints>2</constraints>
			</creation-site>
			<creation-site offset="1,2,8">
				<constraints>16</constraints>
			</creation-site>
			<creation-site offset="3">
				<constraints>22</constraints>
			</creation-site>
			<creation-site offset="4">
				<constraints>64</constraints>
			</creation-site>
			<creation-site offset="6,7">
				<constraints>32</constraints>
			</creation-site>
			<creation-site offset="9,10">
				<constraints>8</constraints>
			</creation-site>
			<creation-site
				offset="11,18,19,20,22,23,24,26,27,28,30,31,32,34,35,36,38,39,40">
				<constraints>4</constraints>
			</creation-site>
			<creation-site offset="17,21,25,29,33,37">
				<constraints>3</constraints>
			</creation-site>
			<creation-site offset="12,13,14,15">
				<constraints>36</constraints>
			</creation-site>
			<creation-site offset="16">
				<constraints>6</constraints>
			</creation-site>
		</method>
		<method decl="int[] reorder(int[])">
			<creation-site offset="0">
				<constraints>576</constraints>
			</creation-site>
		</method>
		<method decl="float[] create_t_43()">
			<creation-site offset="0">
				<constraints>8192</constraints>
			</creation-site>
		</method>
	</class>
</spec>