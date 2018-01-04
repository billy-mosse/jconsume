<?xml version="1.0" encoding="UTF-8"?>
<spec>
	 <class decl="ar.uba.dc.jolden.mst.Hashtable">
		 <method decl="int hashMap(java.lang.Object)">
			<relevant-parameters>this_init__f__size, key_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="3201">
				<variables>this__f__size, key, this_init__f__size, key_init</variables>
				<inductives>this__f__size, key, this_init__f__size, key_init</inductives>
				<callee>java.lang.Object: int hashCode()</callee>
				<constraints>
					<![CDATA[this == this_init && this__f__array == this_init__f__array && this__f__array == this_init__f__array && this__f__size == this_init__f__size && this__f__size >= 2]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="java.lang.Object get(java.lang.Object)">
			<relevant-parameters>this_init__f__array, key_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="4100">
				<variables>key, this__f__array, this_init__f__array, key_init</variables>
				<inductives>key, this__f__array, this_init__f__array, key_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: int hashMap(java.lang.Object)</callee>
				<constraints>
					<![CDATA[this == this_init && this__f__array == this_init__f__array && this__f__array == this_init__f__array && this__f__size == this_init__f__size && this__f__size >= 2]]>
				</constraints>
				<binding>$t.this_init__f__size == this__f__size and $t.key_init == key</binding>
			 </call-site>
			 <call-site offset="2" srccode-offset="4700">
				<variables>key, ent#2, this_init__f__array, key_init, this__f__array</variables>
				<inductives>key, ent#2, this_init__f__array, key_init, this__f__array</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: ar.uba.dc.jolden.mst.HashEntry next()</callee>
				<constraints>
					<![CDATA[this_init == this && this_init__f__array == this__f__array && this_init__f__array == this__f__array && this_init__f__size == this__f__size && this_init__f__size >= 2]]>
				</constraints>
			 </call-site>
			 <call-site offset="1" srccode-offset="4801">
				<variables>ent#2, key, this_init__f__array, key_init, this__f__array</variables>
				<inductives>ent#2, key, this_init__f__array, key_init, this__f__array</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: java.lang.Object key()</callee>
				<constraints>
					<![CDATA[this_init == this && this_init__f__array == this__f__array && this_init__f__array == this__f__array && this_init__f__size == this__f__size && this_init__f__size >= 2]]>
				</constraints>
			 </call-site>
			 <call-site offset="3" srccode-offset="4802">
				<variables>ent#2, this_init__f__array, key_init, this__f__array, key</variables>
				<inductives>ent#2, this_init__f__array, key_init, this__f__array, key</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: java.lang.Object entry()</callee>
				<constraints>
					<![CDATA[this_init == this && this_init__f__array == this__f__array && this_init__f__array == this__f__array && this_init__f__size == this__f__size && this_init__f__size >= 2]]>
				</constraints>
			 </call-site>
		</method>
		 <method decl="void &lt;init&gt;(int)">
			<relevant-parameters>sz_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="1900">
				<variables>sz, this, sz_init</variables>
				<inductives>sz, this, sz_init</inductives>
				<callee>java.lang.Object: void &lt;init&gt;()</callee>
				<constraints>
					<![CDATA[sz == sz_init]]>
				</constraints>
			 </call-site>
			 <creation-site offset="0" srccode-offset="2100">
				<variables>this__f__size, __i0, sz_init, sz, aux_1</variables>
				<inductives>this__f__size, __i0, sz_init, sz, aux_1, aux_1</inductives>
				<constraints>
					<![CDATA[this__f__size == __i0 && __i0 == sz_init && __i0 == sz && 1<=aux_1 && aux_1<=__i0]]>
				</constraints>
			 </creation-site>
		</method>
		 <method decl="void put(java.lang.Object,java.lang.Object)">
			<relevant-parameters>this_init__f__array, key_init, value_init</relevant-parameters>
			 <call-site offset="0" srccode-offset="5700">
				<variables>this__f__array, key, value, this_init__f__array, key_init, value_init</variables>
				<inductives>this__f__array, key, value, this_init__f__array, key_init, value_init</inductives>
				<callee>ar.uba.dc.jolden.mst.Hashtable: int hashMap(java.lang.Object)</callee>
				<constraints>
					<![CDATA[this == this_init && this__f__array == this_init__f__array && this__f__array == this_init__f__array && this__f__size == this_init__f__size && this__f__size >= 2]]>
				</constraints>
				<binding>$t.this_init__f__size == this__f__size and $t.key_init == key</binding>
			 </call-site>
			 <creation-site offset="0" srccode-offset="5800">
				<variables>__i0, this__f__array, key, value, this_init__f__array, key_init, value_init</variables>
				<inductives>__i0, this__f__array, key, value, this_init__f__array, key_init, value_init</inductives>
				<constraints>
					<![CDATA[this == this_init && this__f__array == this_init__f__array && this__f__array == this_init__f__array && this__f__size == this_init__f__size && __i0 >= 0 && this__f__size >= 2 && __i0 < this__f__size]]>
				</constraints>
			 </creation-site>
			 <call-site offset="1" srccode-offset="5801">
				<variables>__i0, this__f__array, key, value, __r2, __r0, this_init__f__array, key_init, value_init</variables>
				<inductives>__i0, this__f__array, key, value, __r2, __r0, this_init__f__array, key_init, value_init</inductives>
				<callee>ar.uba.dc.jolden.mst.HashEntry: void &lt;init&gt;(java.lang.Object,java.lang.Object,ar.uba.dc.jolden.mst.HashEntry)</callee>
				<constraints>
					<![CDATA[this == this_init && this__f__array == this_init__f__array && this__f__array == this_init__f__array && this__f__size == this_init__f__size && __i0 >= 0 && this__f__size >= 2 && __i0 < this__f__size]]>
				</constraints>
				<binding>$t.key_init == key and $t.entry_init == value and $t.next_init == __r2</binding>
			 </call-site>
		</method>
	</class>
</spec>
