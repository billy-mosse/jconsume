package ar.uba.dc.util.collections.map;

import java.util.Set;
import java.util.Map.Entry;

/** A map with sets as values.
*
* @author Ondrej Lhotak
*/
public interface MultiMap<K,V> {
   public boolean isEmpty();
   public int numKeys();
   public boolean containsKey(K key);
   public boolean containsValue(V value );
   public boolean put(K key, V value);
   public boolean putAll(K key, Set<V> values);
   public void putAll(MultiMap<K, V> m);
   public boolean remove(K key, V value);
   public boolean remove(K key);
   public boolean removeAll(K key, Set<V> values);
   public Set<V> get(K key);
   public Set<K> keySet();
   public Set<V> values();
   public boolean equals(Object o);
   public int hashCode();
   public Set<Entry<K, Set<V>>> entrySet();
}

