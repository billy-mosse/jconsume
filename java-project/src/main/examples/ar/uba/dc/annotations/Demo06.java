package ar.uba.dc.annotations;

import java.util.ArrayList;

import ar.uba.dc.util.List;

public class Demo06 {

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
	
	//si l es writable, termina apuntando a var.
	public static MyInteger mainParameters(MyInteger k, MyInteger l, int n)
	{
		MyInteger var = f(k,l,n);
		return var.g;		
	}
	
	
	//Si l es writable, entonces...
	public static MyInteger f(MyInteger k, MyInteger l, int n)
	{
		MyInteger r = new MyInteger(4);
		r.f = 4;
		r.g = new MyInteger(5);

		MyInteger s = new MyInteger(4);
		s.f = 4;
		s.g = new MyInteger(5);
		
		
		MyInteger return_value = doSomething(r, l);
		
		return return_value;
	}
	
	//sin anotaciones, los parametros r y l no van a escapar del metodo padre
	//con anotaciones, si.
	public static MyInteger doSomething(MyInteger r, MyInteger l)
	{
		return new MyInteger(5);	
	}
}