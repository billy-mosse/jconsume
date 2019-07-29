package ar.uba.dc.annotations.omega;

import java.util.ArrayList;
import java.util.List;

public class MutatedParameterExample {
	public static void main(String[] args)
	{
		Integer k = Integer.parseInt(args[0]);
		test(k);
	}
	
	public static void test(Integer param)
	{
		param = (int) (long) (java.lang.System.currentTimeMillis() % 20);
		System.out.println(param);
		
		List l = new ArrayList();
		for(int i = 1; i < param ; i++)
		{
			Object o = new Object();
			l.add(o);
			//System.out.println(i);
		}
		
	}
}
