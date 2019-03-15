package ar.uba.dc.annotations;

import java.util.ArrayList;

import ar.uba.dc.util.List;

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
		mainParameters(var, var2, 10);
		f();
	}
	
	
	public static void f()
	{
		
	}
	
	/**
	 * 
	 * 
	 * @param k
	 * @param l
	 * @return
	 */
	/*public static MyInteger mainParameters(MyInteger k, MyInteger l)
	{
		MyInteger result = new MyInteger();
		for(int i = 1; i < 10; i++)
		{			
			//2 + kk_init__f__f:
			result.f += max(k, l).f;
		}
		return result;
	}*/
	
	
	//
	public static Integer mainParameters(MyInteger k, MyInteger l, int n)
	{
		//esto consume 1
		Integer my_integer = new Integer(10);
		for(int i = 1; i < n; i++)
		{			
			//el maximo de esto es 2 + kk_init__f__f + ll_init__f__g__f__f
			List tmp = listOfSize(k, l);
		}
		return my_integer;
	}
	
	
	/**
	 * "maxLive": "[kk_init__f__f,kk__f__g__f__f,ll_init__f__f,ll_init__f__g__f__f] -> {2 + kk_init__f__f + ll_init__f__g__f__f: kk_init__f__f >= 2; 3: kk_init__f__f <= 1}",
       "escape": "[kk_init__f__f,kk_init__f__g__f__f,ll_init__f__f,ll_init__f__g__f__f] -> {1 + kk_init__f__f + ll_init__f__g__f__f: kk_init__f__f >= 2; 3: kk_init__f__f <= 1}",
	 * @param kk
	 * @param ll
	 * @return
	 */
	
	//TODO: cambiar ejemplo a uno mas simple...donde escapa siempre el mismo parametro.
	public static List listOfSize(MyInteger kk, MyInteger ll)
	{
		Integer temp = new Integer(4);
		List my_list = new List();
		for(int i = 0; i < kk.f + ll.g.f; i++)
			my_list.add(new Integer(i));
		
		return my_list;
		
	}
}