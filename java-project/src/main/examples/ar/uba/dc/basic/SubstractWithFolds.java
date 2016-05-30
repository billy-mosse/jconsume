package ar.uba.dc.basic;

import ar.uba.dc.paper.*;

/**
 * @author billy
 * 
 * Test de substract con uno o mas folds
 */
public class SubstractWithFolds {	
	
	public static Integer f(int n){
		IntContainer op;
		if(n==4)
		{
			op = new IntContainer();
		}
		else
		{
			op = new IntContainerSon();
		}

		Integer j = new Integer(4);
		Object o2 = op.apply(n);
		return j;
	}

	public static void main(String[] args) {
		Integer i = new Integer(4);		
		Integer j = f(i);
	}
}