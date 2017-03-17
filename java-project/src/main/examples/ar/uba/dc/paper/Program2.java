package ar.uba.dc.paper;

import java.util.Iterator;

import ar.uba.dc.util.ListC;

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
		ListC list = new ListC();
		
		for(int i = 0; i < r; i ++)
		{
			list.add(new Integer(i));
		}
		
		Op op = new Op();
		
		ListC new_list = map(list, op);

	}

	public static ListC map(ListC list, Op op) {
		ListC res = new ListC();
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