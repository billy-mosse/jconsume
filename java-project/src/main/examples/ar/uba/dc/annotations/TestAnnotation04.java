package ar.uba.dc.annotations;

public class TestAnnotation04 {

	public static void main(String[] args)
	{
		MyInteger var = new MyInteger();
		MyInteger var2 = new MyInteger();
		var.f = Integer.parseInt(args[0]);
		var2.f = Integer.parseInt(args[1]);
		var.g = new MyInteger();
		var2.g = new MyInteger();
		var.g.f = 4;
		var2.g.f = 4;
		MyInteger v1 = mainParameters(var, var2);
		f();
	}
	
	
	public static void f()
	{
		
	}
	
	public static MyInteger mainParameters(MyInteger k, MyInteger l)
	{
		MyInteger result = new MyInteger();
		for(int i = 1; i < k.f; i++)
		{			
			result.f += max(k, l).f;
		}
		return result;
	}
	
	public static MyInteger max(MyInteger kk, MyInteger ll)
	{
		if(kk.f > ll.f && kk.g.f > ll.g.f)
			return kk;
		else
			return ll;
	}
}