package ar.uba.dc.annotations;

public class TestAnnotation03 {

	public static void main(String[] args)
	{
		Integer var = mainParameters(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		//Integer a = doSomethingRecursive(10);	
	}	
	
	public static Integer mainParameters(Integer k, Integer l)
	{
		Integer result = max(k, l);
		return result;
	}
	
	public static java.lang.Integer max(Integer k, Integer l)
	{
		return new Integer(4);
	}
}