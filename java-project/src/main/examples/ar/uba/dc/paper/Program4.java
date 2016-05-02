package ar.uba.dc.paper;

import java.util.Iterator;
import java.util.LinkedList;

import ar.uba.dc.util.List;



public class Program4
{
	
	
	public static void main(String[] args) {
		List list = new List();
		Op op = new Op();

		List another_list = new List();

		if (args[0]== "use Op2")
			op = new Op2();
		else
			op = new Op();
		
		List new_list = safeMap(list,op);
		test(another_list, op);
	}
	
	public static List copy(List list) {
		List res = new List();
		Iterator it = list.iterator();
		while (it.hasNext()) //TODO: tengo que agregar el hasNex() a los invariantes!
			res.add(it.next());//el .add es call #3 porque next es #2
		return res;
	}
	
	
	public static List safeMap(List list, Op op) {
		List cp = copy(list);
		return Program3.map(cp, op);
	}

	public static List test(List ls, Op op){
		List res = new List();//0
		Iterator it = ls.iterator();//1
		while(it.hasNext()){
			List l = (List)it.next();
			l = safeMap(l, op);
			res.add(l);
		}
		return res;
	}	

	/*public static List test(List ls, Op op) {
		for (int i = 1; i<= ls.size(); i++)
		{
			Integer tmp = new Integer(i);
		}
		return null;
	}*/
}