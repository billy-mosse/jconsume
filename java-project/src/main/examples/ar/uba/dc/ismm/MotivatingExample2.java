package ar.uba.dc.ismm;

import java.util.Iterator;
import java.util.LinkedList;

import ar.uba.dc.util.ListE;



@SuppressWarnings("unused")
public class MotivatingExample2 {


	public static void main(String[] args) {
		ListE list = generateList(args[0]);
		boolean firstTransformer = Boolean.parseBoolean(args[1]);
		Transform transform = getTransform(firstTransformer);
		test(list,transform);
	}

	
	private static ListE generateList(String integerList) {
		ListE list = new ListE();
		String[] sizes = integerList.split(",");
		for (String stringSize : sizes) {
			int size = Integer.parseInt(stringSize);
			ListE newList = new ListE();
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
      * @temporal v.length : v.length >= 1
	  * @residual 2*v.length  : v.length >= 1
	  */
	public static Integer[] prod(Integer[] v, int f) {
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
	public static void testProd(Integer[] v1, Integer[] v2) {
		prod(v1, 2); //tempCall = 2*v1.length, maxCall = v1.length
		prod(v2, 2); //tempCall = 2*v2.length, maxCall = v2.length + v1.length
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
	public static ListE map(ListE list, Transform transform) {
	  ListE res =  new ListE(); //residual = 1
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
	public static ListE copy(ListE ls) {
	  ListE res = new ListE(); //residual = 1
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
	public static ListE safeMap(ListE list,Transform transform) {
	  ListE cp = copy(list); //tempCall = list.size + 1, maxCall = 1
	  return map(cp, transform); //residual = 1 + 6*list.size , tempCall = 0 , maxCall = 4 
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
	public static ListE test(ListE ls, Transform transform){
	  ListE res = new ListE();  //residual = 1
	  for(Iterator it = ls.iterator(); it.hasNext();) //tempCall = 1
	     res.add(safeMap((ListE) it.next(), transform)); 
	  return res;
	}
	
}
