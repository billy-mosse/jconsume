package ar.uba.dc.annotations;

public class TestAnnotation03bis {

	public static void main(String[] args)
	{
		mainParameters(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		//Integer a = doSomethingRecursive(10);
		//Integer r = f();
	
	}	
	
	
	public static void mainParameters(Integer k, Integer l)
	{
		//Integer r = maxx(k, l);
	}
	
	public static Integer maxx(Integer k, Integer l)
	{
		if(k > l)
			return k;
		else
			return l;
	}
	
	public static Integer f()
	{
		return new Integer(4);
	}
}