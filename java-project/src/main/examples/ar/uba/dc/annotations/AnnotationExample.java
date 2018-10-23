package ar.uba.dc.annotations;

import java.awt.geom.Area;


//For annotations
import java.lang.annotation.Documented;

/*@Documented
@interface MemorySummary {
    String[] formalParameters();
    String PiecewiseQuasipolynomial(); //This should be my custom class
}*/

import java.util.Iterator;

import ar.uba.dc.paper.Op;
import ar.uba.dc.paper.Op2;
import ar.uba.dc.util.ListE;



public class AnnotationExample
{
	
	
	public static void main(String[] args) {
		Op2 op = new Op2();

		int r1 = Integer.parseInt(args[0]);
		int r2 = Integer.parseInt(args[0]);
		ListE another_list = new ListE(); //vamos a fingir que es una ListEa de ListEas
		

		
		for (int j = 0; j < r1; j++)
		{
			ListE l = new ListE();
			for (int i = 0; i < r2+j*(j-3); i++) //r2+j es para que tengan distintos tamaños
			{				
				l.add(i);
			}
			another_list.add(l);
		}
		
		ListE short_list = new ListE();
		short_list.add(1); another_list.add(short_list);
		
		
		/*if (args[0]== "use Op2")
			op = new Op2();
		else
			op = new Op();*/
				
		System.out.println(another_list.toString());
		//this functions acts as mainParameters
		test(another_list, op);
	}
	
	public static ListE copy(ListE list) {
		ListE res = new ListE();
		Iterator<Integer> it = list.iterator();
		while (it.hasNext()) 
			res.add(it.next());
		
		return res;
	}
	
	public static ListE safeMap(ListE list, Op op) {
		ListE cp = copy(list);
		return ar.uba.dc.paper.Program3.map(cp, op);
	}
	

	//OJO. En realidad l.size <= maxSize,...pero no quiero que aparezca como inductiva
	//Hay otra manera de arreglarlo?
	@InstrumentationSiteInvariantList(invariants={
			@InstrumentationSiteInvariant(
			isCallSite=true,
			index=3,
			constraints={"l.size == maxSize", "l.size >= 0"},
		    newRelevantParameters={"maxSize"}, newInductives = {  }, newVariables = {  })}
	)
	//ls is a ListE of ListEs
	public static ListE test(ListE ls, Op op){
		ListE res = new ListE();
		Iterator it = ls.iterator();
		while(it.hasNext()){
			ListE l = (ListE)it.next();
			
			l = safeMap(l, op);//Callsite #3
			
			res.add(l);
		}
		return res;
	}
}
	
