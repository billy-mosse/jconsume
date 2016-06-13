package ar.uba.dc.basic;


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
		for(int i = 1; i < n; i++)
		{
			Object o2 = op.apply(i);
		}		
		return j;
	}

	public static void main(String[] args) {
		Integer i = new Integer(4);		
		Integer j = f(i);
	}
}