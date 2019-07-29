package ar.uba.dc.annotations;

public class TestAnnotation05 {

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
	
	public static Integer max(Integer k, Integer l)
	{
		Integer temp = new Integer(5);
		return new Integer(4);
	}
}