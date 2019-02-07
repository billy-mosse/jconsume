package ar.uba.dc.annotations;

public class TestAnnotation01 {

	public static void main(String[] args)
	{
		mainParameters(Integer.parseInt(args[0]));
		//Integer a = doSomethingRecursive(10);
	
	}	
	
	//k deberia figurar como mutated, no?
	public static void mainParameters(Integer k)
	{
		addOne(k);
	}
	
	public static void addOne(Integer k)
	{
		k = k+1;
	}
}