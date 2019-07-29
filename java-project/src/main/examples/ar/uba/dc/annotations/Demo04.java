package ar.uba.dc.annotations;

import java.util.Iterator;

import ar.uba.dc.util.ListE;

public class Demo04 {

	public static void main(String[] args)
	{
		mainParameters(Integer.parseInt(args[0]));
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
	public static void mainParameters(int k)
	{
		ListE l = new ListE();
		for(int i = 0; i < k; i++)
		{
			l.add(new Integer(i));
		}
		ListE l_copy = copy(l);
		ListE l_copy2 = copy2(l);
	}
	
	public static ListE copy(ListE l)
	{
		ListE ret = new ListE();
		Iterator it = l.iterator();
		while(it.hasNext())
		{
			Integer x = (Integer) it.next();
			ret.add(x);
		}
		doSomething(ret, l);
		return ret;
	}
	
	public static ListE copy2(ListE l)
	{
		ListE ret = new ListE();
		Iterator it = l.iterator();
		while(it.hasNext())
		{
			Integer x = (Integer) it.next();
			ret.add(x);
		}
		ListE ret2 = doSomethingElse(ret, l);
		
		//deberia escapar el head o algo asi de l tambien
		return ret2;
	}
	
	/**
	 * "maxLive": "[kk_init__f__f,kk__f__g__f__f,ll_init__f__f,ll_init__f__g__f__f] -> {2 + kk_init__f__f + ll_init__f__g__f__f: kk_init__f__f >= 2; 3: kk_init__f__f <= 1}",
       "escape": "[kk_init__f__f,kk_init__f__g__f__f,ll_init__f__f,ll_init__f__g__f__f] -> {1 + kk_init__f__f + ll_init__f__g__f__f: kk_init__f__f >= 2; 3: kk_init__f__f <= 1}",
	 * @param kk
	 * @param ll
	 * @return
	 */
	
	//TODO: cambiar ejemplo a uno mas simple...donde escapa siempre el mismo parametro.
	public static void doSomething(ListE l1, ListE l2)
	{
		
	}
	
	public static ListE doSomethingElse(ListE l1, ListE l2)
	{
		return null;
	}
}