<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.em3d.Em3d">
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="17">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="48">
				<variables>size_args_init,N,D,I</variables>
				<inductives>size_args_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[N == 20 && D == 5 && I == 1]]>
				</constraints>
				<binding>$t.nN == N and $t.nD == D and $t.nI == I</binding>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>nN, nD, nI</relevant-parameters>
			 <call-site offset="0" srccode-offset="52">
				<variables>size___r0, size_args_init</variables>
				<inductives>nN, nD, nI, size___r0, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void parseCmdLine(java.lang.String[])</callee>
				<constraints>
					<![CDATA[size___r0 ==size_args_init && size___r0 == 4]]>
				</constraints>
				<binding>$t.size_args_init == size___r0</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="53">
				<variables>__i1, __i0, size_args_init</variables>
				<inductives>__i1, __i0, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void mainParameters(int,int,boolean,boolean)</callee>
				<constraints>
					<![CDATA[size_args_init == 4 && __i0 == nD && __i1 == nN]]>
					<!-- $t.numIters_init == __i0 && -->
				</constraints>
				<binding>$t.numNodes_init == __i1 and $t.numDegree_init == __i0 and $t.numIters_init == nI</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int,int,boolean,boolean)">
			<relevant-parameters>numNodes_init, numDegree_init, numIters_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="58">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="59">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[numNodes_init >numDegree_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="60">
				<variables>__i2, __i3, numNodes_init, numDegree_init</variables>
				<inductives>__i2, __i3, numNodes_init, numDegree_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.BiGraph: ar.uba.dc.jolden.em3d.BiGraph create(int,int,boolean)</callee>
				<constraints>
					<![CDATA[__i2 ==numNodes_init && __i3 ==numDegree_init && __i2 >__i3]]>
				</constraints>
				<binding>$t.numNodes_init == __i2 and $t.numDegree_init == __i3</binding>
			 </call-site>
			 <call-site offset="3" srccode-offset="61">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[numNodes_init >numDegree_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="66">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="4" srccode-offset="66">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="66">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="66">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="67">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="67">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="68">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[numNodes_init >numDegree_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="10" srccode-offset="70">
				<variables>i, numNodes_init, numDegree_init, numIters_init</variables>
				<inductives>i, numNodes_init, numDegree_init, numIters_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.BiGraph: void compute()</callee>
				<constraints>
					<![CDATA[i >= 0 && i <numIters_init && numNodes_init >numDegree_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="72">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[numNodes_init >numDegree_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="76">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="79">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="13" srccode-offset="79">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="79">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="79">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="79">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="80">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="17" srccode-offset="80">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="18" srccode-offset="80">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="19" srccode-offset="80">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="80">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="81">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="21" srccode-offset="81">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="22" srccode-offset="81">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="23" srccode-offset="81">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="24" srccode-offset="81">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="25" srccode-offset="84">
				<variables>numNodes_init, numDegree_init</variables>
				<inductives>numNodes_init, numDegree_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[numNodes_init >numDegree_init]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void parseCmdLine(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="99">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean startsWith(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 4 && i % 2 == 0 && size_args >i]]>
				</constraints>
			 </call-site>
			 <call-site offset="0" srccode-offset="103">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 4 && i % 2 == 1 && size_args >i]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="105">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 4 && i == 1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="105">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 4 && i == 2]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="105">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 4 && i == 2]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="106">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="3" srccode-offset="106">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="107">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 4 && i == 3]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="109">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 4 && i == 3]]>
				</constraints>
			 </creation-site>
			 <call-site offset="6" srccode-offset="109">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args ==i && size_args ==size_args_init && size_args == 4]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="109">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_args ==i && size_args ==size_args_init && size_args == 4]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="110">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="8" srccode-offset="110">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="111">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="4" srccode-offset="113">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="10" srccode-offset="113">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="11" srccode-offset="113">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="11" srccode-offset="114">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="12" srccode-offset="114">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<callee>java.lang.Error: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="115">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="117">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="119">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="120">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="123">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<callee>ar.uba.dc.jolden.em3d.Em3d: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void usage()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="131">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="132">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="133">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="134">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="135">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="136">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="137">
				<variables></variables>
				<inductives></inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="138">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.System: void exit(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
