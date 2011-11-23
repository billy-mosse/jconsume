package ar.uba.dc.ismm;

import java.util.Iterator;
import java.util.LinkedList;

import ar.uba.dc.rinard.List;


@SuppressWarnings("unused")
public class MotivatingExampleToPrint {

	
	public static List map(List list, Transform transform) {
	  List res =  new List();
	  for (Iterator it = list.iterator(); it.hasNext();) { 
	    Object o = transform.apply(it.next()); 
	    res.add(o);
	  }
	  return res;
	}
	
	
	public static List copy(List ls) {
	  List res = new List(); 
	  for (Iterator it = ls.iterator(); it.hasNext();)
	    res.add(it.next());
	  return res;
	}
	
	
	public static List safeMap(List list,Transform transform) {
	  List cp = MotivatingExample.copyList(list);
	  return map(cp, transform);
	}

	
	public static List test(List ls, Transform transform){
	  List res = new List();
	  for(Iterator it = ls.iterator(); it.hasNext();)
	     res.add(safeMap((List) it.next(), transform)); 
	  return res;
	}
	
}
