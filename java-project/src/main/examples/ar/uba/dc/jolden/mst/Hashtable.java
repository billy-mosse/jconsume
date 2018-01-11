package ar.uba.dc.jolden.mst;


public class Hashtable
{
  protected HashEntry array[];
  protected int size;

  /**
   * ArrayCountSize
   * @temporal 0
   * @residual size : size >= 1
   * 
   * ArrayCountOne
   * @temporal 0
   * @residual 1
   */
  public Hashtable(int sz)
  {
    size = sz;
    array = new HashEntry[size]; //residual = 1  (size : size >= 1ArrayCountSize)
    for (int i=0; i<size; i++)
      array[i] = null;
  }

  /**
   * @temporal 0
   * @residual 0
   */
  private int hashMap(Object key) 
  {
    return ((key.hashCode() >> 3 ) % size); //(Ver q ue hace hashcode)
  }

  /**
   * @temporal 0
   * @residual 0
   */ 
  public Object get(Object key)
  {
    int j = hashMap(key); //maxCall = 0, tempCall = 0 (Ver cuanto devuelve hash map)

    HashEntry ent = null;
    
    for (ent = array[j]; ent != null && ent.key() != key; ent = ent.next()); //maxCall = 0, tempCall = 0, residual = 0 

    if (ent != null) return ent.entry(); //maxCall = 0, tempCall = 0
    return null;
  }

  /**
   * @temporal 0
   * @residual 1
   */
  public void put(Object key, Object value)
  {
    int j = hashMap(key);  //maxCall = 0, tempCall = 0  (Ver q hace hashMap)
    HashEntry ent = new HashEntry(key, value, array[j]); //residual = 1 (esto tengo q especificar?)
    array[j] = ent;
  }

  /**
   * @temporal 0
   * @residual 0
   */
  public void remove(Object key)
  {
    int j = hashMap(key); //maxCall = 0, tempCall = 0 (Ver q hace hasMap)
    HashEntry ent = array[j];  
    if (ent != null && ent.key() == key)
      array[j] = ent.next(); //maxCall = 0, tempCall = 0
    else {
      HashEntry prev = ent;
      for (ent = ent.next(); ent != null && ent.key() != key; //maxCall = 0, tempCall = 0 
	   prev = ent, ent = ent.next());
      prev.setNext(ent.next()); //maxCall = 0, tempCall = 0
    }
  }

}

class HashEntry
{
  private Object key;
  private Object entry;
  private HashEntry next;

  /**
   * @temporal 0
   * @residual 0
   */
  public HashEntry(Object key, Object entry, HashEntry next)
  {
    this.key = key;
    this.entry = entry;
    this.next = next;
  }

  /**
   * @temporal 0
   * @residual 0
   */
  public Object key()
  {
    return key;
  }

  /**
   * @temporal 0
   * @residual 0
   */
  public Object entry()
  {
    return entry;
  }

  /**
   * @temporal 0
   * @residual 0
   */
  public HashEntry next()
  {
    return next;
  }

  /**
   * @temporal 0
   * @residual 0
   */
  public void setNext(HashEntry n)
  {
    next = n;
  }

}
