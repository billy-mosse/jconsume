package ar.uba.dc.paper;

import java.util.Iterator;
import java.util.LinkedList;

import java.util.List;

import ar.uba.dc.annotations.InstrumentationSiteInvariant;
import ar.uba.dc.annotations.InstrumentationSiteInvariantList;
import ar.uba.dc.util.ListE;


public class Program4
{
	
	
	public static void main(String[] args) {
		
		
		
		Op op = new Op();

		int r1 = Integer.parseInt(args[0]);
		int r2 = Integer.parseInt(args[0]);
		List another_list = new LinkedList(); //vamos a fingir que es una lista de listas
		
		for (int j = 0; j < r1; j++)
		{
			List l = new LinkedList();
			for (int i = 0; i < r2+j; i++) //r2+j es para que tengan distintos tamaños
			{				
				l.add(i);
			}
			another_list.add(l);
		}
		
		if (args[0]== "use Op2")
			op = new Op2();
		else
			op = new Op();
		
		
		//this functions acts as mainParameters
		test(another_list, op);
	}
	
	public static ListE copy(ListE list) {
		ListE res = new ListE();
		Iterator it = list.iterator();
		while (it.hasNext()) //TODO: tengo que agregar el hasNext() a los invariantes!
			res.add(it.next());//el .add es call #3 porque next es #2
		
		
		int k = 0;
		for (int i = 0; i < list.size(); i ++)
			k+=1;
		
		return res;
	}
	
	
	public static ListE safeMap(ListE l, Op op) {
		ListE cp = copy(l);
		return Program3.map(cp, op); // ML: 5 L + 4. Esc: 5 L + 1
	}

	@InstrumentationSiteInvariantList(invariants={
			@InstrumentationSiteInvariant(
			isCallSite=true,
			index=3,
			constraints={"l.size == maxSize", "l.size >= 0"},
		    newRelevantParameters={"maxSize"}, newInductives = {  }, newVariables = {  })}
	)
	public static ListE test(List ls, Op op){
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