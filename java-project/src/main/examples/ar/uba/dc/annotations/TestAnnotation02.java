package ar.uba.dc.annotations;

public class TestAnnotation02 {

	public static void main(String[] args)
	{
		mainParameters(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		//Integer a = doSomethingRecursive(10);
	
	}	
	
	
	public static void mainParameters(Integer k, Integer l)
	{
		addOne(k, l);
	}
	
	public static void addOne(Integer k, Integer l)
	{
		k = k+1;
	}
}