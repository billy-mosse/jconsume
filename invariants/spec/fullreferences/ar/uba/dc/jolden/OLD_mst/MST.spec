<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.mst.MST">
		 <method decl="int computeMST(ar.uba.dc.jolden.mst.Graph,int)">
			<relevant-parameters>numvert_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="81">
				<variables>cost, numvert, numvert_init</variables>
				<inductives>cost, numvert, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: ar.uba.dc.jolden.mst.Vertex firstNode()</callee>
				<constraints>
					<![CDATA[numvert ==numvert_init && cost == 0 && cost <numvert]]>
				</constraints>
				<binding>$t.size_f_this_init_nodes == size_f___r0_nodes</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="82">
				<variables>cost, numvert, numvert_init</variables>
				<inductives>cost, numvert, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[numvert ==numvert_init && cost == 0 && cost <numvert]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="89">
				<variables>cost, numvert, numvert_init</variables>
				<inductives>cost, numvert, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: ar.uba.dc.jolden.mst.BlueReturn doAllBlueRule(ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[cost >= 0 && numvert >= 1 && numvert <numvert_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="90">
				<variables>cost, numvert, numvert_init</variables>
				<inductives>cost, numvert, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: ar.uba.dc.jolden.mst.Vertex vert()</callee>
				<constraints>
					<![CDATA[cost >= 0 && numvert >= 1 && numvert <numvert_init]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="91">
				<variables>cost, numvert, numvert_init</variables>
				<inductives>cost, numvert, numvert_init</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: int dist()</callee>
				<constraints>
					<![CDATA[cost >= 0 && numvert >= 1 && numvert <numvert_init]]>
				</constraints>
				<binding>$t._f_this_init_dist == _f___r2_dist</binding>
			 </call-site>
		</method>
		 <method decl="ar.uba.dc.jolden.mst.BlueReturn BlueRule(ar.uba.dc.jolden.mst.Vertex,ar.uba.dc.jolden.mst.Vertex)">
			<relevant-parameters>_f_vlist_init_mindist</relevant-parameters>
			 <creation-site offset="0" srccode-offset="100">
				<variables>_f_vlist_mindist, size_f___r4_array, _f___r4_size, _f_vlist_init_mindist</variables>
				<inductives>_f_vlist_mindist, size_f___r4_array, _f___r4_size, _f_vlist_init_mindist</inductives>
				<constraints>
					<![CDATA[_f_vlist_mindist ==_f_vlist_init_mindist && size_f___r4_array ==_f___r4_size && size_f___r4_array == 0 && _f_vlist_mindist >size_f___r4_array]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="100">
				<variables>_f_vlist_mindist, size_f___r4_array, _f___r4_size, _f_vlist_init_mindist</variables>
				<inductives>_f_vlist_mindist, size_f___r4_array, _f___r4_size, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[_f_vlist_mindist ==_f_vlist_init_mindist && size_f___r4_array ==_f___r4_size && size_f___r4_array == 0 && _f_vlist_mindist >size_f___r4_array]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="103">
				<variables>__i0, _f_vlist_init_mindist</variables>
				<inductives>__i0, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setDist(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.d_init == __i0</binding>
			 </call-site>
			 <call-site offset="2" srccode-offset="108">
				<variables>_f_vlist_mindist, size_f___r4_array, _f___r4_size, _f_vlist_init_mindist</variables>
				<inductives>_f_vlist_mindist, size_f___r4_array, _f___r4_size, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setVert(ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[_f_vlist_mindist ==_f_vlist_init_mindist && size_f___r4_array ==_f___r4_size && size_f___r4_array == 0 && _f_vlist_mindist >size_f___r4_array]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="109">
				<variables>_f_vlist_mindist, size_f___r4_array, _f___r4_size, _f___r3_mindist, _f_vlist_init_mindist</variables>
				<inductives>_f_vlist_mindist, size_f___r4_array, _f___r4_size, _f___r3_mindist, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: int mindist()</callee>
				<constraints>
					<![CDATA[_f_vlist_mindist ==_f___r3_mindist && _f_vlist_mindist ==_f_vlist_init_mindist && size_f___r4_array ==_f___r4_size && size_f___r4_array == 0 && _f_vlist_mindist >size_f___r4_array]]>
				</constraints>
				<binding>$t._f_this_init_mindist == _f___r3_mindist</binding>
			 </call-site>
			 <call-site offset="4" srccode-offset="109">
				<variables>_f_vlist_mindist, size_f___r4_array, _f___r4_size, __i0, _f_vlist_init_mindist</variables>
				<inductives>_f_vlist_mindist, size_f___r4_array, _f___r4_size, __i0, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setDist(int)</callee>
				<constraints>
					<![CDATA[_f_vlist_mindist ==__i0 && _f_vlist_mindist ==_f_vlist_init_mindist && size_f___r4_array ==_f___r4_size && size_f___r4_array == 0 && _f_vlist_mindist >size_f___r4_array]]>
				</constraints>
				<binding>$t.d_init == __i0</binding>
			 </call-site>
			 <call-site offset="5" srccode-offset="110">
				<variables>_f_vlist_mindist, size_f___r4_array, _f___r4_size, _f_vlist_init_mindist</variables>
				<inductives>_f_vlist_mindist, size_f___r4_array, _f___r4_size, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Hashtable neighbors()</callee>
				<constraints>
					<![CDATA[_f_vlist_mindist ==_f_vlist_init_mindist && size_f___r4_array ==_f___r4_size && size_f___r4_array == 0 && _f_vlist_mindist >size_f___r4_array]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="111">
				<variables>size_f___r4_array, _f___r4_size, _f_vlist_mindist, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, _f_vlist_mindist, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: java.lang.Object get(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && _f_vlist_mindist ==_f_vlist_init_mindist && size_f___r4_array <_f_vlist_mindist]]>
				</constraints>
				<binding>$t.size_f_this_init_array == size_f___r4_array and $t._f_this_init_size == _f___r4_size</binding>
			 </call-site>
			 <call-site offset="7" srccode-offset="113">
				<variables>size_f___r4_array, _f___r4_size, _f_vlist_mindist, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, _f_vlist_mindist, _f_vlist_init_mindist</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && _f_vlist_mindist ==_f_vlist_init_mindist && size_f___r4_array <_f_vlist_mindist]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="114">
				<variables>size_f___r4_array, _f___r4_size, _f_vlist_mindist, dist, __i1, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, _f_vlist_mindist, dist, __i1, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: int dist()</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && _f_vlist_mindist ==_f_vlist_init_mindist && dist ==__i1 && size_f___r4_array <_f_vlist_mindist && size_f___r4_array <dist]]>
				</constraints>
				<binding>$t._f_this_init_dist == _f___r1_dist</binding>
			 </call-site>
			 <call-site offset="9" srccode-offset="115">
				<variables>size_f___r4_array, _f___r4_size, _f_vlist_mindist, dist, __i0, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, _f_vlist_mindist, dist, __i0, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void setMindist(int)</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && _f_vlist_mindist ==_f_vlist_init_mindist && dist ==__i0 && size_f___r4_array <_f_vlist_mindist && size_f___r4_array <dist && _f_vlist_mindist >dist]]>
				</constraints>
				<binding>$t.m_init == __i0</binding>
			 </call-site>
			 <call-site offset="10" srccode-offset="116">
				<variables>size_f___r4_array, _f___r4_size, _f_vlist_mindist, __i0, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, _f_vlist_mindist, __i0, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setDist(int)</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && _f_vlist_mindist ==__i0 && size_f___r4_array <_f_vlist_mindist && size_f___r4_array <_f_vlist_init_mindist && _f_vlist_mindist <_f_vlist_init_mindist]]>
				</constraints>
				<binding>$t.d_init == __i0</binding>
			 </call-site>
			 <call-site offset="11" srccode-offset="119">
				<variables>size_f___r4_array, _f___r4_size, _f_vlist_mindist, size___r8, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, _f_vlist_mindist, size___r8, _f_vlist_init_mindist</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="123">
				<variables>size_f___r4_array, _f___r4_size, count, _f___r2_mindist, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, count, _f___r2_mindist, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && count == 0 && size_f___r4_array >count && size_f___r4_array <_f___r2_mindist && size_f___r4_array <_f_vlist_init_mindist && count <_f___r2_mindist && count <_f_vlist_init_mindist && _f___r2_mindist <=_f_vlist_init_mindist]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="123">
				<variables>size_f___r4_array, _f___r4_size, count, _f___r2_mindist, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, count, _f___r2_mindist, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && size_f___r4_array >= 2 && count >= 1 && size_f___r4_array <_f_vlist_init_mindist && count <_f_vlist_init_mindist]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="126">
				<variables>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && count >= 1 && size_f___r4_array <_f_vlist_init_mindist && count <_f_vlist_init_mindist && _f_tmp_mindist <_f_vlist_init_mindist]]>
				</constraints>
			 </call-site>
			 <call-site offset="15" srccode-offset="127">
				<variables>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void setNext(ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && count >= 1 && size_f___r4_array <_f_vlist_init_mindist && count <_f_vlist_init_mindist && _f_tmp_mindist <_f_vlist_init_mindist]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="129">
				<variables>count, _f_tmp_mindist, size_f___r4_array, _f___r4_size, _f_vlist_init_mindist</variables>
				<inductives>count, _f_tmp_mindist, size_f___r4_array, _f___r4_size, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Hashtable neighbors()</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && count >= 1 && count <_f_tmp_mindist && count <_f_vlist_init_mindist && _f_tmp_mindist >size_f___r4_array && size_f___r4_array <_f_vlist_init_mindist]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="130">
				<variables>count, _f_tmp_mindist, size_f_hash_array, _f_hash_size, _f___r2_mindist, _f_vlist_init_mindist</variables>
				<inductives>count, _f_tmp_mindist, size_f_hash_array, _f_hash_size, _f___r2_mindist, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: int mindist()</callee>
				<constraints>
					<![CDATA[_f_tmp_mindist ==_f___r2_mindist && size_f_hash_array ==_f_hash_size && count >= 1 && count <_f_tmp_mindist && count <_f_vlist_init_mindist && _f_tmp_mindist >size_f_hash_array && size_f_hash_array <_f_vlist_init_mindist]]>
				</constraints>
				<binding>$t._f_this_init_mindist == _f___r2_mindist</binding>
			 </call-site>
			 <call-site offset="18" srccode-offset="131">
				<variables>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, dist2, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, dist2, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: java.lang.Object get(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && _f_tmp_mindist ==dist2 && count >= 1 && size_f___r4_array <_f_tmp_mindist && size_f___r4_array <_f_vlist_init_mindist && count <_f_tmp_mindist && count <_f_vlist_init_mindist]]>
				</constraints>
				<binding>$t.size_f_this_init_array == size_f___r4_array and $t._f_this_init_size == _f___r4_size</binding>
			 </call-site>
			 <call-site offset="19" srccode-offset="133">
				<variables>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, dist2, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, dist2, _f_vlist_init_mindist</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && _f_tmp_mindist ==dist2 && count >= 1 && size_f___r4_array <_f_tmp_mindist && size_f___r4_array <_f_vlist_init_mindist && count <_f_tmp_mindist && count <_f_vlist_init_mindist]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="135">
				<variables>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, dist, __i0, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, dist, __i0, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: void setMindist(int)</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && dist ==__i0 && count >= 1 && size_f___r4_array <_f_tmp_mindist && size_f___r4_array <_f_vlist_init_mindist && count <_f_tmp_mindist && count <_f_vlist_init_mindist && _f_tmp_mindist >dist]]>
				</constraints>
				<binding>$t.m_init == __i0</binding>
			 </call-site>
			 <call-site offset="21" srccode-offset="139">
				<variables>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, dist2, size___r8, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, dist2, size___r8, _f_vlist_init_mindist</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="22" srccode-offset="141">
				<variables>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, dist2, __i1, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, dist2, __i1, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: int dist()</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && _f_tmp_mindist ==dist2 && _f_tmp_mindist ==__i1 && count >= 1 && size_f___r4_array <_f_vlist_init_mindist && count <_f_vlist_init_mindist]]>
				</constraints>
				<binding>$t._f_this_init_dist == _f___r1_dist</binding>
			 </call-site>
			 <call-site offset="23" srccode-offset="142">
				<variables>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, dist2, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, dist2, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setVert(ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && _f_tmp_mindist ==dist2 && count >= 1 && size_f___r4_array <_f_vlist_init_mindist && count <_f_vlist_init_mindist && _f_tmp_mindist <_f_vlist_init_mindist]]>
				</constraints>
			 </call-site>
			 <call-site offset="24" srccode-offset="143">
				<variables>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, __i0, _f_vlist_init_mindist</variables>
				<inductives>size_f___r4_array, _f___r4_size, count, _f_tmp_mindist, __i0, _f_vlist_init_mindist</inductives>
				<callee>ar.uba.dc.jolden.mst.BlueReturn: void setDist(int)</callee>
				<constraints>
					<![CDATA[size_f___r4_array ==_f___r4_size && _f_tmp_mindist ==__i0 && count >= 1 && size_f___r4_array <_f_vlist_init_mindist && count <_f_vlist_init_mindist && _f_tmp_mindist <_f_vlist_init_mindist]]>
				</constraints>
				<binding>$t.d_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="ar.uba.dc.jolden.mst.BlueReturn doAllBlueRule(ar.uba.dc.jolden.mst.Vertex)">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="155">
				<variables></variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.mst.Vertex: ar.uba.dc.jolden.mst.Vertex next()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="156">
				<variables></variables>
				<inductives></inductives>
				<callee>ar.uba.dc.jolden.mst.MST: ar.uba.dc.jolden.mst.BlueReturn BlueRule(ar.uba.dc.jolden.mst.Vertex,ar.uba.dc.jolden.mst.Vertex)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t._f_vlist_init_mindist == _f___r1_mindist</binding>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="18">
				<variables></variables>
				<inductives></inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void main(java.lang.String[])">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="35">
				<variables>size___r0, size_args_init</variables>
				<inductives>size___r0, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void mainOrig(java.lang.String[])</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t.size_args_init == size___r0</binding>
			 </call-site>
		</method>
		 <method decl="void mainOrig(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="40">
				<variables>size___r0, size_args_init</variables>
				<inductives>size___r0, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void parseCmdLine(java.lang.String[])</callee>
				<constraints>
					<![CDATA[size___r0 ==size_args_init && size___r0 == 2]]>
				</constraints>
				<binding>$t.size_args_init == size___r0</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="41">
				<variables>__i0, size_args_init</variables>
				<inductives>__i0, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void mainParameters(int,boolean,boolean)</callee>
				<constraints>
					<![CDATA[size_args_init == 2 && __i0 >size_args_init]]>
				</constraints>
				<binding>$t.pVertices_init == __i0</binding>
			 </call-site>
		</method>
		 <method decl="void mainParameters(int,boolean,boolean)">
			<relevant-parameters>pVertices_init</relevant-parameters>
			 <creation-site offset="0" srccode-offset="46">
				<variables>pVertices, pVertices_init</variables>
				<inductives>pVertices, pVertices_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="0" srccode-offset="46">
				<variables>pVertices, size___r2, pVertices_init</variables>
				<inductives>pVertices, size___r2, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="46">
				<variables>pVertices, __i1, pVertices_init</variables>
				<inductives>pVertices, __i1, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="46">
				<variables>pVertices, pVertices_init</variables>
				<inductives>pVertices, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="46">
				<variables>pVertices, size___r4, pVertices_init</variables>
				<inductives>pVertices, size___r4, pVertices_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="48">
				<variables>pVertices, pVertices_init</variables>
				<inductives>pVertices, pVertices_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[pVertices ==pVertices_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="49">
				<variables>l_start0, pVertices, pVertices_init</variables>
				<inductives>l_start0, pVertices, pVertices_init</inductives>
				<constraints>
					<![CDATA[pVertices ==pVertices_init && l_start0 <pVertices]]>
				</constraints>
			 </creation-site>
			 <call-site offset="5" srccode-offset="49">
				<variables>l_start0, pVertices, __i1, pVertices_init</variables>
				<inductives>l_start0, pVertices, __i1, pVertices_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Graph: void &lt;init&gt;(int)</callee>
				<constraints>
					<![CDATA[pVertices ==__i1 && pVertices ==pVertices_init && l_start0 <pVertices]]>
				</constraints>
				<binding>$t.numvert_init == __i1</binding>
			 </call-site>
			 <call-site offset="6" srccode-offset="50">
				<variables>l_start0, pVertices, pVertices_init</variables>
				<inductives>l_start0, pVertices, pVertices_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[pVertices ==pVertices_init && l_start0 <pVertices]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="53">
				<variables>l_start0, l_end0, pVertices, size___r4, pVertices_init</variables>
				<inductives>l_start0, l_end0, pVertices, size___r4, pVertices_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="54">
				<variables>l_start0, l_end0, pVertices, pVertices_init</variables>
				<inductives>l_start0, l_end0, pVertices, pVertices_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[pVertices ==pVertices_init && l_start0 <l_end0 && l_start0 <pVertices && l_end0 <pVertices]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="55">
				<variables>l_start0, l_start1, l_end0, __i3, pVertices_init</variables>
				<inductives>l_start0, l_start1, l_end0, __i3, pVertices_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: int computeMST(ar.uba.dc.jolden.mst.Graph,int)</callee>
				<constraints>
					<![CDATA[l_start1 ==l_end0 && __i3 ==pVertices_init && l_start0 <l_start1 && l_start0 <__i3 && l_start1 <__i3]]>
				</constraints>
				<binding>$t.numvert_init == __i3</binding>
			 </call-site>
			 <call-site offset="10" srccode-offset="56">
				<variables>l_start0, l_start1, l_end0, dist, pVertices_init</variables>
				<inductives>l_start0, l_start1, l_end0, dist, pVertices_init</inductives>
				<callee>java.lang.System: long currentTimeMillis()</callee>
				<constraints>
					<![CDATA[l_start1 ==l_end0 && l_start0 <l_start1 && l_start0 <dist && l_start0 <pVertices_init && l_start1 <dist && l_start1 <pVertices_init && dist >pVertices_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="2" srccode-offset="59">
				<variables>l_start0, l_end1, l_start1, l_end0, dist, pVertices_init</variables>
				<inductives>l_start0, l_end1, l_start1, l_end0, dist, pVertices_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="11" srccode-offset="59">
				<variables>l_start0, l_end1, l_start1, l_end0, dist, size___r2, pVertices_init</variables>
				<inductives>l_start0, l_end1, l_start1, l_end0, dist, size___r2, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="12" srccode-offset="59">
				<variables>l_start0, l_end1, l_start1, l_end0, __i1, pVertices_init</variables>
				<inductives>l_start0, l_end1, l_start1, l_end0, __i1, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="13" srccode-offset="59">
				<variables>l_start0, l_end1, l_start1, l_end0, pVertices_init</variables>
				<inductives>l_start0, l_end1, l_start1, l_end0, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="14" srccode-offset="59">
				<variables>l_start0, l_end1, l_start1, l_end0, size___r4, pVertices_init</variables>
				<inductives>l_start0, l_end1, l_start1, l_end0, size___r4, pVertices_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="3" srccode-offset="62">
				<variables>l_start0, l_end1, l_start1, l_end0, pVertices_init</variables>
				<inductives>l_start0, l_end1, l_start1, l_end0, pVertices_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="15" srccode-offset="62">
				<variables>l_start0, l_end1, l_start1, l_end0, size___r2, pVertices_init</variables>
				<inductives>l_start0, l_end1, l_start1, l_end0, size___r2, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="16" srccode-offset="62">
				<variables>l_start0, l_end1, l_start1, d___d0, pVertices_init</variables>
				<inductives>l_start0, l_end1, l_start1, d___d0, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="17" srccode-offset="62">
				<variables>l_start0, l_end1, l_start1, pVertices_init</variables>
				<inductives>l_start0, l_end1, l_start1, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="18" srccode-offset="62">
				<variables>l_start0, l_end1, l_start1, size___r4, pVertices_init</variables>
				<inductives>l_start0, l_end1, l_start1, size___r4, pVertices_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="4" srccode-offset="63">
				<variables>l_start0, l_end1, l_start1, pVertices_init</variables>
				<inductives>l_start0, l_end1, l_start1, pVertices_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="19" srccode-offset="63">
				<variables>l_start0, l_end1, l_start1, size___r2, pVertices_init</variables>
				<inductives>l_start0, l_end1, l_start1, size___r2, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="20" srccode-offset="63">
				<variables>l_start0, l_end1, d___d0, pVertices_init</variables>
				<inductives>l_start0, l_end1, d___d0, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="21" srccode-offset="63">
				<variables>l_start0, l_end1, pVertices_init</variables>
				<inductives>l_start0, l_end1, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="22" srccode-offset="63">
				<variables>l_start0, l_end1, size___r4, pVertices_init</variables>
				<inductives>l_start0, l_end1, size___r4, pVertices_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <creation-site offset="5" srccode-offset="64">
				<variables>l_start0, l_end1, pVertices_init</variables>
				<inductives>l_start0, l_end1, pVertices_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="23" srccode-offset="64">
				<variables>l_start0, l_end1, size___r2, pVertices_init</variables>
				<inductives>l_start0, l_end1, size___r2, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="24" srccode-offset="64">
				<variables>d___d0, pVertices_init</variables>
				<inductives>d___d0, pVertices_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.StringBuffer append(double)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="25" srccode-offset="64">
				<variables>pVertices_init</variables>
				<inductives>pVertices_init</inductives>
				<callee>java.lang.StringBuffer: java.lang.String toString()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="26" srccode-offset="64">
				<variables>size___r4, pVertices_init</variables>
				<inductives>size___r4, pVertices_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="27" srccode-offset="67">
				<variables>size___r4, pVertices_init</variables>
				<inductives>size___r4, pVertices_init</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[size___r4 == 5 && size___r4 <pVertices_init]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void parseCmdLine(java.lang.String[])">
			<relevant-parameters>size_args_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="168">
				<variables>size_args, i, size___r2, size___r3, size_args_init</variables>
				<inductives>size_args, i, size___r2, size___r3, size_args_init</inductives>
				<callee>java.lang.String: boolean startsWith(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args ==size___r2 && size_args ==size_args_init && size_args == 2 && i == 0 && size___r3 == 1]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="171">
				<variables>size_args, i, size_arg, size___r2, size___r3, size_args_init</variables>
				<inductives>size_args, i, size_arg, size___r2, size___r3, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_args ==size_arg && size_args ==size___r2 && size_args ==size___r3 && size_args ==size_args_init && size_args == 2 && i == 1]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="173">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<constraints>
					<![CDATA[size_args ==size_args_init && size_args == 2 && i == 1]]>
				</constraints>
			 </creation-site>
			 <call-site offset="2" srccode-offset="173">
				<variables>size_args, i, size___r7, size_args_init</variables>
				<inductives>size_args, i, size___r7, size_args_init</inductives>
				<callee>java.lang.Integer: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[size_args ==i && size_args ==size___r7 && size_args ==size_args_init && size_args == 2]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="173">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>java.lang.Integer: int intValue()</callee>
				<constraints>
					<![CDATA[size_args ==i && size_args ==size_args_init && size_args == 2]]>
				</constraints>
			 </call-site>
			 <creation-site offset="1" srccode-offset="174">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </creation-site>
			 <call-site offset="4" srccode-offset="174">
				<variables>size___r7, size_args_init</variables>
				<inductives>size___r7, size_args_init</inductives>
				<callee>java.lang.RuntimeException: void &lt;init&gt;(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="175">
				<variables>size_args, i, size_arg, size___r2, size___r3, size_args_init</variables>
				<inductives>size_args, i, size_arg, size___r2, size___r3, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="177">
				<variables>size_args, i, size_arg, size___r2, size___r3, size_args_init</variables>
				<inductives>size_args, i, size_arg, size___r2, size___r3, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="179">
				<variables>size_args, i, size___r2, size___r3, size_args_init</variables>
				<inductives>size_args, i, size___r2, size___r3, size_args_init</inductives>
				<callee>java.lang.String: boolean equals(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="8" srccode-offset="180">
				<variables>size_args, i, size_args_init</variables>
				<inductives>size_args, i, size_args_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="9" srccode-offset="183">
				<variables>size_args_init</variables>
				<inductives>size_args_init</inductives>
				<callee>ar.uba.dc.jolden.mst.MST: void usage()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void usage()">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="191">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="192">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="193">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="194">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="195">
				<variables>size___r1</variables>
				<inductives>size___r1</inductives>
				<callee>java.io.PrintStream: void println(java.lang.String)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="196">
				<variables>__i0</variables>
				<inductives>__i0</inductives>
				<callee>java.lang.System: void exit(int)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
