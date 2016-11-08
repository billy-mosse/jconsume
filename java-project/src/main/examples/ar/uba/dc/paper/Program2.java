package ar.uba.dc.paper;

import java.util.Iterator;

import ar.uba.dc.util.List;

public class Program2
{
	
	public static void main(String[] args) {
		List list = new List();
		
		Op op = new Op();
		
		List new_list = map(list, op);

	}

	
	public static List map(List list, Op op) {
		List res = new List();
		Iterator it = list.iterator();
		
		// sum max (OP, OP2) , no lo sabe hacer y hace sum OP + OP2
		// si te das cuenta que op no cambia podrias hacer max (sum OP, sum OP2) 
		while(it.hasNext())
		//for(int i = 0; i< 5; i++)
		{
			Object li = it.next();
			Object o = op.apply(li);
			res.add(o);
		}
		
		return res;
	}


	/*public static List map2(List list, Op op2) {
		List res = new List();


		Iterator it = list.iterator();
		
		while(it.hasNext())
		//for(int i = 0; i< 5; i++)
		{
			Object li = it.next();
			Object o = op2.apply(li);
			res.add(o);
		}
		
		return res;
	}	*/
}