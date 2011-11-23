package ar.uba.dc.util.collections.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/** A map with sets as values, HashMap implementation.
*
* @author Ondrej Lhotak
*/
public class HashMultiMap<K, V> implements MultiMap<K, V> {
   Map<K, Set<V>> m = new HashMap<K, Set<V>>(0);

   public HashMultiMap() {
	   super();
   }
   
   public HashMultiMap(MultiMap<K, V> m) {
	   this();
	   putAll(m);
   }
   
   public void putAll(MultiMap<K, V> m) {
	   Iterator<K> it = m.keySet().iterator();
	   while(it.hasNext()) {
		   K o = it.next();
		   putAll(o, m.get(o));
	   }
   }
   
   public boolean isEmpty() {
	   return numKeys() == 0;
   }
   
   public int numKeys() {
	   return m.size();
   }
   
   public boolean containsKey(K key) {
	   return m.containsKey(key);
   }
   
   public boolean containsValue(V value) {
	   Iterator<Set<V>> it = m.values().iterator();
	   while(it.hasNext()) {
		   Set<V> s = it.next();
		   if(s.contains(value)) return true;
	   }
	   return false;
   }
   
   protected Set<V> newSet() {
	   return new HashSet<V>(4);
   }
   
   private Set<V> findSet(K key) {
	   Set<V> s = m.get(key);
	   if(s == null) {
		   s = newSet();
		   m.put(key, s);
	   }
	   return s;
   }
   
   public boolean put(K key, V value) {
	   return findSet(key).add(value);
   }
   
   public boolean putAll(K key, Set<V> values) {
	   if (values.isEmpty()) return false;
	   return findSet(key).addAll(values);
   }
   
   public boolean remove(K key, V value) {
	   Set<V> s = m.get(key);
	   if(s == null) return false;
	   boolean ret = s.remove(value);
	   if(s.isEmpty()) {
		   m.remove(key);
	   }
	   return ret;
   }
   
   public boolean remove(K key) {
	   return null != m.remove(key);
   }
   
   public boolean removeAll(K key, Set<V> values) {
       Set<V> s = m.get(key);
       if(s == null) return false;
       boolean ret = s.removeAll(values);
       if(s.isEmpty()) {
           m.remove(key);
       }
       return ret;
   }
   
   public Set<V> get(K o) {
	   Set<V> ret = m.get(o);
	   if(ret == null) return Collections.emptySet();
	   return Collections.unmodifiableSet(ret);
   }
   
   public Set<K> keySet() {
	   return m.keySet();
   }
   
   public Set<V> values() {
	   Set<V> ret = new HashSet<V>(0);
	   Iterator<Set<V>> it = m.values().iterator();
	   while(it.hasNext()) {
		   Set<V> s = it.next();
		   ret.addAll(s);
	   }
	   return ret;
   }
   
   @SuppressWarnings("unchecked")
   public boolean equals(Object o) {
		if(!(o instanceof MultiMap)) return false;
		MultiMap mm = (MultiMap) o;
		if(!keySet().equals(mm.keySet())) return false;
		Iterator it = m.entrySet().iterator();
		while(it.hasNext()) {
		    Map.Entry e = (Map.Entry) it.next();
		    Set s = (Set) e.getValue();
		    if(!s.equals(mm.get(e.getKey()))) return false;
		}
		return true;
   }
   
   public int hashCode() {
	   return m.hashCode();
   }
   
   @Override
   public String toString () {
	   StringBuilder sb = new StringBuilder();
	   for (K key : keySet()) {
		   sb.append(", " + key + " -> " + get(key));
	   }
	   
	   String ret = "[";
	   if (sb.length() > 0) {
		   ret += sb.substring(2);
	   }
	   ret += "]";
	   
	   return ret;
   }

	/* (non-Javadoc)
	 * @see ar.uba.dc.util.collections.map.MultiMap#entrySet()
	 */
	@Override
	public Set<Entry<K, Set<V>>> entrySet() {
		return m.entrySet();
	}
   
}
