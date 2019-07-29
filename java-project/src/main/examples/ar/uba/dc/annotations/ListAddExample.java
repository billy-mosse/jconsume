package ar.uba.dc.annotations;

import java.util.ArrayList;
import java.util.List;

public class ListAddExample {
	public static void main(String[] args)
	{
		A a = new A();
		B bObj = new B();
		bObj.b = 4;
		a.bb = bObj;
		List<A> l = new ArrayList<A>();
		l.add(a);
		
		B bObj2 = new B();
		bObj2.b = 5;
		a.bb = bObj2;
		System.out.println(l.get(0).bb.b);	
	}
}
