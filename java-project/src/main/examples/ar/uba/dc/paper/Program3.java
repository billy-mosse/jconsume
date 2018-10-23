package ar.uba.dc.paper;

import java.util.Iterator;
import java.util.List;

import ar.uba.dc.util.ListE;


public class Program3
{
	
	public static void main(String[] args) {
		
		
		int k = Integer.parseInt(args[0]);
		mainParameters(k, args[0]);

	}
	//no deberia necesitar partir esto en dos, no?
	public static void mainParameters(int k, String u)
	{
		ListE list = new ListE();
		for(int j = 0; j < k; j++)
		{
			list.add(new Integer(j));
		}
		
		//Op op = new Op();
		Op op;

		if (u== "use Op2")
			op = new Op2();
		else
			op = new Op();
		
		op = new Op();
		
		ListE new_ListE = map(list, op);
	}

	
	public static ListE map(ListE list, Op op) {
		ListE res = new ListE();

		Iterator it = list.iterator();
		// sum max (OP, OP2) , no lo sabe hacer y hace sum OP + OP2
		// si te das cuenta que op no cambia podrias hacer max (sum OP, sum OP2) 
		//int i = 0;
		while(it.hasNext())
		//for(int i = 0; i< 5; i++)
		{
		//	i+=1;
			Object li = it.next();
			Object o = op.apply(li);
			res.add(o);
		}
		
		return res;
	}


	/*public static ListE map2(ListE list, Op op2) {
		ListE res = new List();


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