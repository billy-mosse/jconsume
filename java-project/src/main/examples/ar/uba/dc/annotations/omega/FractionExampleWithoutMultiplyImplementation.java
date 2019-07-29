package ar.uba.dc.annotations.omega;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;



public class FractionExampleWithoutMultiplyImplementation {

	public static void main(String[] args)
	{
		Integer i = Integer.parseInt(args[0]);
		Integer j = Integer.parseInt(args[1]);
		String s = args[2];
		try {
			Class multiplierClass = Class.forName(s);
			Constructor constructor = multiplierClass.getConstructor();
			IMultiplier multiplier = (IMultiplier) constructor.newInstance();
			
			Integer result = mainParameters(new FractionWithoutMultiply(i,j), multiplier);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	


	static IMultiplier m;
	public static Integer mainParameters(FractionWithoutMultiply aa, IMultiplier multiplier)
	{
		Integer b1 = new Integer(3);
		Integer b2 = new Integer(4);
		FractionWithoutMultiply bb = new FractionWithoutMultiply(b1,b2);				
		
		FractionWithoutMultiply new_fraction = multiplier.multiply(aa,bb);
		Integer ret2 = new_fraction.getNumeratorIfGreaterThanDenominator();
		return ret2;
	}
}

