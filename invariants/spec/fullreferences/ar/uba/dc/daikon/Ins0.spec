<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.daikon.Ins0">
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="10">
				<variables>__r0, args_init, args</variables>
				<inductives>__r0, args_init, args</inductives>
				<callee>java.lang.Integer: int parseInt(java.lang.String)</callee>
				<constraints>
					<![CDATA[args_init == args]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="11">
				<variables>__i0, args_init, args</variables>
				<inductives>__i0, args_init, args, </inductives>
				<callee>ar.uba.dc.daikon.Ins0: void mainParameters(int)</callee>
				<constraints>
					<![CDATA[args_init == args]]>
				</constraints>
				<binding>$t.n_init == args</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int)">
			<relevant-parameters>n_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="18">
