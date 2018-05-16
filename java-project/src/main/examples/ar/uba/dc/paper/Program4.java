package ar.uba.dc.paper;

import java.util.Iterator;
import java.util.LinkedList;

import ar.uba.dc.util.ListE;



public class Program4
{
	
	
	public static void main(String[] args) {
		
		
		
		Op op = new Op();

		ListE another_list = new ListE(); //vamos a fingir que es una lista de listas

		if (args[0]== "use Op2")
			op = new Op2();
		else
			op = new Op();
		
		//List new_list = safeMap(list,op);
		
		
		test(another_list, op);
	}
	
	public static ListE copy(ListE list) {
		ListE res = new ListE();
		Iterator it = list.iterator();
		while (it.hasNext()) //TODO: tengo que agregar el hasNex() a los invariantes!
			res.add(it.next());//el .add es call #3 porque next es #2
		return res;
	}
	
	
	public static ListE safeMap(ListE list, Op op) {
		ListE cp = copy(list);
		return Program3.map(cp, op); // ML: 5 L + 4. Esc: 5 L + 1
	}

	public static ListE test(ListE ls, Op op){
		ListE res = new ListE();//0
		Iterator it = ls.iterator();//1
		while(it.hasNext()){
			ListE l = (ListE)it.next();
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