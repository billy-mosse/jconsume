package ar.uba.dc.basic;


/**
 * @author billy
 * 
 * Test de substract con uno o mas folds
 */
public class ModifyingParameters {	
	
	public static void main(String[] args) {
		Integer j = a1();
	}

	public static Integer a1()
	{
		Integer b = SuperInt();
		return b;
	}

	public static Integer SuperInt()
	{
		Integer b = new Integer(4);
		String s = b.toString();

		return b;
	}
}