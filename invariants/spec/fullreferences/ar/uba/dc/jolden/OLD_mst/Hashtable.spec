<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.mst.Hashtable">
		 <method decl="int hashMap(java.lang.Object)">
			<relevant-parameters>_f_this_init_size</relevant-parameters>
			 <call-site offset="0" srccode-offset="19">
				<variables>_f_this_size, _f_this_init_size</variables>
				<inductives>_f_this_size, _f_this_init_size</inductives>
				<callee>java.lang.Object: int hashCode()</callee>
				<constraints>
					<![CDATA[_f_this_size ==_f_this_init_size && _f_this_size >= 2]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.lang.Object get(java.lang.Object)">
			<relevant-parameters>size_f_this_init_array, _f_this_init_size</relevant-parameters>
			 <call-site offset="0" srccode-offset="24">
				<variables>size_f_this_array, _f___r0_size, size_f_this_init_array, _f_this_init_size</variables>
				<inductives>size_f_this_array, _f___r0_size, size_f_this_init_array, _f_this_init_size</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: int hashMap(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_f_this_array ==_f___r0_size && size_f_this_array ==size_f_this_init_array && size_f_this_array ==_f_this_init_size && size_f_this_array >= 2]]>
				</constraints>
				<binding>$t._f_this_init_size == _f___r0_size</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="28">
				<variables>size_f_this_init_array, _f_this_init_size</variables>
				<inductives>size_f_this_init_array, _f_this_init_size</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: ar.uba.dc.jolden.mst.HashEntry next()</callee>
				<constraints>
					<![CDATA[size_f_this_init_array ==_f_this_init_size && size_f_this_init_array >= 2]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="28">
				<variables>size_f_this_init_array, _f_this_init_size</variables>
				<inductives>size_f_this_init_array, _f_this_init_size</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: java.lang.Object key()</callee>
				<constraints>
					<![CDATA[size_f_this_init_array ==_f_this_init_size && size_f_this_init_array >= 2]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="30">
				<variables>size_f_this_init_array, _f_this_init_size</variables>
				<inductives>size_f_this_init_array, _f_this_init_size</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: java.lang.Object entry()</callee>
				<constraints>
					<![CDATA[size_f_this_init_array ==_f_this_init_size && size_f_this_init_array >= 2]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;(int)">
			<relevant-parameters>sz_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="9">
				<variables>size_f_this_array, _f_this_size, sz, sz_init</variables>
				<inductives>size_f_this_array, _f_this_size, sz, sz_init</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[size_f_this_array ==_f_this_size && sz ==sz_init && size_f_this_array == 0 && size_f_this_array <sz]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="12">
				<variables>size_f_this_array, _f_this_size, __i0, sz_init, aux_1</variables>
				<inductives>size_f_this_array, _f_this_size, __i0, sz_init, aux_1</inductives>
				<constraints>
					<![CDATA[_f_this_size ==__i0 && _f_this_size ==sz_init && size_f_this_array == 0 && size_f_this_array <_f_this_size && 1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void put(java.lang.Object,java.lang.Object)">
			<relevant-parameters>size_f_this_init_array</relevant-parameters>
			 <call-site offset="0" srccode-offset="36">
				<variables>size_f_this_array, size_f_this_init_array</variables>
				<inductives>size_f_this_array, size_f_this_init_array</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: int hashMap(java.lang.Object)</callee>
				<constraints>
					<![CDATA[size_f_this_array ==size_f_this_init_array && size_f_this_array >= 2]]>
				</constraints>
				<binding>$t._f_this_init_size == _f___r0_size</binding>
			 </call-site>
			 <creation-site offset="0" srccode-offset="37">
				<variables>j, size_f_this_array, size_f_this_init_array</variables>
				<inductives>j, size_f_this_array, size_f_this_init_array</inductives>
				<constraints>
					<![CDATA[size_f_this_array ==size_f_this_init_array && j >= 0 && size_f_this_array >= 2 && j <size_f_this_array]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="37">
				<variables>j, size_f_this_array, size_f_this_init_array</variables>
				<inductives>j, size_f_this_array, size_f_this_init_array</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: void &lt;init&gt;(java.lang.Object,java.lang.Object,ar.uba.dc.jolden.mst.HashEntry)</callee>
				<constraints>
					<![CDATA[size_f_this_array ==size_f_this_init_array && j >= 0 && size_f_this_array >= 2 && j <size_f_this_array]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void remove(java.lang.Object)">
			<relevant-parameters></relevant-parameters>
			 <call-site offset="0" srccode-offset="43">
				<variables>size_f_this_array, size_f_this_init_array</variables>
				<inductives>size_f_this_array, size_f_this_init_array</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: int hashMap(java.lang.Object)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
				<binding>$t._f_this_init_size == _f___r0_size</binding>
			 </call-site>
			 <call-site offset="1" srccode-offset="45">
				<variables>j, size_f_this_array, size_f_this_init_array</variables>
				<inductives>j, size_f_this_array, size_f_this_init_array</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: java.lang.Object key()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="2" srccode-offset="46">
				<variables>size___r3, __i1, size_f_this_init_array</variables>
				<inductives>size___r3, __i1, size_f_this_init_array</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: ar.uba.dc.jolden.mst.HashEntry next()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="49">
				<variables>size_f_this_init_array</variables>
				<inductives>size_f_this_init_array</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: ar.uba.dc.jolden.mst.HashEntry next()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="4" srccode-offset="49">
				<variables>size_f_this_init_array</variables>
				<inductives>size_f_this_init_array</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: java.lang.Object key()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="5" srccode-offset="50">
				<variables>size_f_this_init_array</variables>
				<inductives>size_f_this_init_array</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: ar.uba.dc.jolden.mst.HashEntry next()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="6" srccode-offset="51">
				<variables>size_f_this_init_array</variables>
				<inductives>size_f_this_init_array</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: ar.uba.dc.jolden.mst.HashEntry next()</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
			 <call-site offset="7" srccode-offset="51">
				<variables>size_f_this_init_array</variables>
				<inductives>size_f_this_init_array</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: void setNext(ar.uba.dc.jolden.mst.HashEntry)</callee>
				<constraints>
					<![CDATA[]]>
				</constraints>
			 </call-site>
		</method>
	</class>
</spec>
