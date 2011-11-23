package ar.uba.dc.ismm;

import java.util.Iterator;

import ar.uba.dc.rinard.List;

@SuppressWarnings("unused")
public class Snippet02 {

	/** 
	 * Cha - Lazy
      * @temporal max (10 + (19 + 6*maxElementSize)*size,15 + maxElementSize + (14 + 6*maxElementSize)*size) : size >=1 && maxElementSize >=1   
	  * @residual 0
	 * 
	 *  Cha - Add
      * @temporal 21 + maxElementSize + (21 + 6*maxElementSize)*size : size >=1 && maxElementSize >=1   
	  * @residual 0
	  */
	public static void main(String[] args) {
		Integer[] v = new Integer[10];
		duplicate(v,v);
		multiyply(v, 2);
		List list = new List();
		List list2 = new List();
		list2 = duplicate(list);
		Integer[] v2 = toArray(list);
		map(list, getTransform(false));
		test(list,getTransform(false));
	}
	
	/**
      * @temporal 0
	  * @residual 2 
	  */
	public static ITrans getTransform(boolean b) {
		//return b? new TransA() : new TransB();
		return b? new TransA() : new TransB();
	}
	
	/**
	  * @temporal 1
	  * @residual 2*ls.size : ls.size >=1
	  */
	@SuppressWarnings("unchecked")
	public static Integer[] toArray(List ls) {
	  Integer[] res = new Integer[ls.size()]; //residual = ls.size
	  int i=0;
	  for (Iterator it = ls.iterator() ; it.hasNext();) { //tempCall = 1
	    res[i] = new Integer((Integer)it.next());  //residual = ls.size 
	    i++;
	  }
	  return res;
	}
	
	/**
      * @temporal 1
	  * @residual 2*list.size +1 : list.size >=1
	  */
	@SuppressWarnings("unchecked")
	public static List duplicate(List list) {
		List res = new List(); //residual = 1 
		for (Iterator it = list.iterator() ; it.hasNext();) { //tempCall = 1
			int value = ((Integer)it.next()).intValue(); 
		    res.add(new Integer(value * 2));  //residual = 2*list.size
		}
		
		return res;
	}
	
	/**
      * @temporal v.length : v.length >= 1
	  * @residual 2*v.length  : v.length >= 1
	  */
	public static Integer[] multiyply(Integer[] v, int f) {
		Integer[] res = new Integer[v.length]; //residual = v.length
		for (int i=0;i<v.length;i++) {
			int value = v[i].intValue();
			Integer factor = new Integer(f); //tempLocal = v.length
			res[i] = new Integer(value * factor); //residual = v.length  
		}
		return res;
	}
	
	/**
	 * 
	 * Lazy
     * @temporal max(3*v1.length + 2*v2.length,2*v1.length + 3*v2.length)  : v1.length >=1 && v2.length >=1
	 * @residual 0
	 * 
	 * 	Add
     * @temporal 3*v1.length + 3*v2.length : v1.length >=1 && v2.length >=1
	 * @residual 0
	 */
	public static void duplicate(Integer[] v1, Integer[] v2) {
		multiyply(v1, 2); //tempCall = 2*v1.length, maxCall = v1.length
		multiyply(v2, 2); //tempCall = 2*v2.length, maxCall = v2.length + v1.length
	}
	
	/**
      * @temporal 3 : list.size >= 1 
	  * @residual 6*list.size + 1 : list.size >= 1 
	  */
	@SuppressWarnings("unchecked")
	public static List map(List list, ITrans transform) {
	  List res =  new List(); //residual = 1
	  for (Iterator it = list.iterator(); it.hasNext();) { //tempCall = 1
	    Object o = transform.apply(it.next()); //residual = 5*list.size (4 de Transform2 y 1 de Transform) //maxCall = 2 (2 de transfor vs 1 de transform2)
	    res.add(o); //residual = list.size
	  }
	  return res;
	}
	
	/** 
      * @temporal 1
	  * @residual list.size + 1 : list.size >= 1
	  */
	@SuppressWarnings("unchecked")
	public static List copyList(List ls) {
	  List res = new List(); //residual = 1
	  for (Iterator it = ls.iterator(); it.hasNext();) //tempCall = 1
	    res.add(it.next()); //residual = list.size
	  return res;
	}
	
	/**
	 * 
	 * Cha-Lazy
     * @temporal 4 + list.size : list.size >=1
	 * @residual 1 + 6*list.size : list.size >=1
	 * 
	 * Cha-Add
     * @temporal 5 + list.size : list.size >=1
	 * @residual 1 + 6*list.size : list.size >=1
	 */
	public static List safeMap(List list,ITrans transform) {
	  List cp = Snippet02.copyList(list); //tempCall = list.size + 1, maxCall = 1
	  return map(cp, transform); //residual = 1 + 6*list.size , tempCall = 0 , maxCall = 4 
	}

	/**
	  * Cha - Lazy 
      * @temporal 5 + list.elements.maxSize : list.size >=1 && list.elements.maxSize >=1
	  * @residual 1 + (2 + 6*list.elements.maxSize) * list.size: list.size >=1 && list.elements.maxSize >=1
	  * 
	  * Cha - Add 
      * @temporal 6 + list.elements.maxSize : list.size >=1 && list.elements.maxSize >=1
	  * @residual 1 + (2 + 6*list.elements.maxSize) * list.size: list.size >=1 && list.elements.maxSize >=1
	  */
	@SuppressWarnings("unchecked")
	public static List test(List ls, ITrans transform){
	  List res = new List();  //residual = 1
	  for(Iterator it = ls.iterator(); it.hasNext();) //tempCall = 1
	     res.add(safeMap((List) it.next(), transform)); 
	  return res;
	}
	
}
