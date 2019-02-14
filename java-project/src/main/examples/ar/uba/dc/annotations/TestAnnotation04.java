package ar.uba.dc.annotations;

public class TestAnnotation04 {

	public static void main(String[] args)
	{
		Integer var = mainParameters(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	}	
	
	public static Integer mainParameters(Integer k, Integer l)
	{
		Integer result = 0;
		for(int i = 1; i < k; i++)
		{
			result += max(k, l);
		}
		return result;
	}
	
	public static java.lang.Integer max(Integer k, Integer l)
	{
		if(k > l)
			return k;
		else
			return l;
	}
}