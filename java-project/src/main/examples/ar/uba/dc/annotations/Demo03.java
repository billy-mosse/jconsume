package ar.uba.dc.annotations;


import ar.uba.dc.util.ListE;

public class Demo03 {

	public static void main(String[] args)
	{
		MyInteger var = new MyInteger(1);
		MyInteger var2 = new MyInteger(2);
		var.f = Integer.parseInt(args[0]);
		var2.f = Integer.parseInt(args[1]);
		var.g = new MyInteger(3);
		var2.g = new MyInteger(5);
		var.g.f = 4;
		var2.g.f = 4;
		mainParameters(var, var2, Integer.parseInt(args[2]));
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
	public static MyInteger mainParameters(MyInteger k, MyInteger l, int n)
	{
		MyInteger r = new MyInteger(4);
		r.f = 4;
		r.g = new MyInteger(5);
		MyInteger ret = doSomething(r);
		
		return ret;
	}
	
	
	/**
	 * "maxLive": "[kk_init__f__f,kk__f__g__f__f,ll_init__f__f,ll_init__f__g__f__f] -> {2 + kk_init__f__f + ll_init__f__g__f__f: kk_init__f__f >= 2; 3: kk_init__f__f <= 1}",
       "escape": "[kk_init__f__f,kk_init__f__g__f__f,ll_init__f__f,ll_init__f__g__f__f] -> {1 + kk_init__f__f + ll_init__f__g__f__f: kk_init__f__f >= 2; 3: kk_init__f__f <= 1}",
	 * @param kk
	 * @param ll
	 * @return
	 */
	
	//TODO: cambiar ejemplo a uno mas simple...donde escapa siempre el mismo parametro.
	public static MyInteger doSomething(MyInteger r)
	{
		//creo que hay un bug: si no uso el field relevante no me lo marca?
		for(int i = 1; i <= r.f; i++)
		{
			Integer tmp = new Integer(4);
		}
		return new MyInteger(5);	
	}
}