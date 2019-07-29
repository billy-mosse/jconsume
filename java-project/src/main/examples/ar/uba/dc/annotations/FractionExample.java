package ar.uba.dc.annotations;

public class FractionExample {

	public static void main(String[] args)
	{
		Integer i = Integer.parseInt(args[0]);
		Integer j = Integer.parseInt(args[1]);
		Integer result = mainParameters(new Fraction(i,j));
		//Integer a = doSomethingRecursive(10);
	
	}
	
	public static Integer doSomethingRecursive(Integer b)
	{
		if(b == 0) return 0;
		else return doSomethingRecursive(b-1);
	}
	
	
	public static Integer mainParameters(Fraction aa)
	{
		//int b = Integer.parseUnsignedInt("Hola");
		Integer b1 = new Integer(3);
		Integer b2 = new Integer(4);
		Fraction bb = new Fraction(b1,b2);
		Fraction new_fraction = aa.multiply(bb);
		Integer ret2 = new_fraction.getNumeratorIfGreaterThanDenominator();
		return ret2;
	}
}