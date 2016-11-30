package ar.uba.dc.simple;

public class TwoCallsToSameMethod {

	
	public static void main(String[] args) {

		a1();
		
		
	}
	
	public static void a1()
	{
		Integer i = a0();
		Integer j = a0();
		Integer k = a0();
	}


	public static Integer a0()
	{
		return new Integer(4);
	}
}