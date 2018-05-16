package ar.uba.dc.paper;

import java.util.Iterator;

import ar.uba.dc.util.ListCE;

//TODO me genera invariantes de mas para ListC y eso hace que haya relevantParameters que no aparecen en ningun binding
public class Program2
{
	
	public static final void main(String args[])
	{
	  mainOrig(args);
	}

	public static final void mainOrig(String args[])
  {
	  int r = Integer.parseInt(args[0]);
	  mainParameters(r);
  }
  
  
	public static void mainParameters(int r) {
		ListCE list = new ListCE();
		
		for(int i = 0; i < r; i ++)
		{
			Integer integ = new Integer(i); 
			list.add(integ);
		}
		
		Op op = new Op();
		
		ListCE new_list = map(list, op);

	}

	public static ListCE map(ListCE list, Op op) {
		ListCE res = new ListCE();
		Iterator it = list.iterator();
		
		// sum max (OP, OP2) , no lo sabe hacer y hace sum OP + OP2
		// si te das cuenta que op no cambia podrias hacer max (sum OP, sum OP2) 
		//while(it.hasNext())
		/*for(int i = 0; i< n; i++)
		{
			Integer j = new Integer(4);
			j+=1;
		}*/
		
		while(it.hasNext())
		{
			Object li = (Object) it.next();
			
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