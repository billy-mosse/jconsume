package ar.uba.dc.ismm;

import java.util.Iterator;
import java.util.LinkedList;

import ar.uba.dc.rinard.List;


@SuppressWarnings("unused")
public class MotivatingExample {

	/** 
	 * Cha - Lazy
      * @temporal max (10 + (19 + 6*maxElementSize)*size,15 + maxElementSize + (14 + 6*maxElementSize)*size) : size >=1 && maxElementSize >=1   
	  * @residual 0
	 * 
	 *  Cha - Add
      * @temporal 21 + maxElementSize + (21 + 6*maxElementSize)*size : size >=1 && maxElementSize >=1   
	  * @residual 0
	  */
	/**
	 * Para el paper de ismm solo evaluamos el metodo test.
	 * @param args
	 */

	public static void main(String[] args) {
		List list = generateList(args[0].replaceAll("\"", ""));
		boolean firstTransformer = Boolean.parseBoolean(args[1].trim());
		Transform transform = getTransform(firstTransformer);
		test(list,transform);
	}

	
	private static List generateList(String integerList) {
		List list = new List();
		String[] sizes = integerList.split(" ");
		for (String stringSize : sizes) {
			int size = Integer.parseInt(stringSize);
			List newList = new List();
			for(int j=0;j<size;j++) {
				newList.add(new Integer(j));
			}
			list.add(newList);
		}
		
		return list;
	}

	/**
      * @temporal 0
	  * @residual 2 
	  */
	public static Transform getTransform(boolean b) {
		return b ? new Transform() : new Transform2();
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
	 *  Paper dice
	 *  @temporal 3 : list.size >= 1 
	  * @residual 5*list.size + 1 : list.size >= 1
	  * 
	  * Nos da
      * @temporal 3 : list.size >= 1 
	  * @residual 6*list.size + 1 : list.size >= 1 
	  */
	@SuppressWarnings("unchecked")
	public static List map(List list, Transform transform) {
	  List res =  new List(); //residual = 1
	  for (Iterator it = list.iterator(); it.hasNext();) { //tempCall = 1
		  Object tmp = it.next();
		  Object o = transform.apply(tmp); //residual = 5*list.size (4 de Transform2 y 1 de Transform) //maxCall = 2 (2 de transfor vs 1 de transform2)
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
	 * Paper Dice
	 * @temporal 4 + list.size : list.size >=1
	 * @residual 1 + 5*list.size : list.size >=1
	 * 
	 * Cha-Lazy
     * @temporal 4 + list.size : list.size >=1
	 * @residual 1 + 6*list.size : list.size >=1
	 * 
	 * Cha-Add
     * @temporal 5 + list.size : list.size >=1
	 * @residual 1 + 6*list.size : list.size >=1
	 */
	public static List safeMap(List list,Transform transform) {
	  List cp = MotivatingExample.copyList(list); //tempCall = list.size + 1, maxCall = 1
	  List tmp =  map(cp, transform); //residual = 1 + 6*list.size , tempCall = 0 , maxCall = 4 
	  return tmp;
	}
	

	/**
	  * Paper Dice
	  * @temporal 5 + list.elements.maxSize : list.size >=1 && list.elements.maxSize >=1
	  * @residual 1 + (2 + 5*list.elements.maxSize) * list.size: list.size >=1 && list.elements.maxSize >=1
	  * 
	  * Cha - Lazy 
      * @temporal 5 + list.elements.maxSize : list.size >=1 && list.elements.maxSize >=1
	  * @residual 1 + (2 + 6*list.elements.maxSize) * list.size: list.size >=1 && list.elements.maxSize >=1
	  * 
	  * Cha - Add 
      * @temporal 6 + list.elements.maxSize : list.size >=1 && list.elements.maxSize >=1
	  * @residual 1 + (2 + 6*list.elements.maxSize) * list.size: list.size >=1 && list.elements.maxSize >=1
	  * 
	  */
	@SuppressWarnings("unchecked")
	public static List test(List ls, Transform transform){
	  List res = new List();  //residual = 1
	  for(Iterator it = ls.iterator(); it.hasNext();) { //tempCall = 1
		  List cp = safeMap((List) it.next(), transform); 
		  res.add(cp);
	  }
	  return res;
	}
	
}
