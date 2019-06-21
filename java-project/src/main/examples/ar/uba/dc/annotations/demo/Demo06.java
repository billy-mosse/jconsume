package ar.uba.dc.annotations.demo;


import ar.uba.dc.annotations.MyInteger;
import ar.uba.dc.util.ListE;

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
	
	public static MyInteger mainParameters(MyInteger k, MyInteger l, int n)
	{
		MyInteger temp = new MyInteger(4);
		MyInteger temp2 = new MyInteger(4);
		
		//Como temp es unreachable, ter
		MyInteger var = doSomething(temp, temp2);
		return var;
	}
	
	
	public static MyInteger doSomething(MyInteger t, MyInteger s)
	{
		//experimento: el residual deberia cambiar si algo escapa por un parametro
		t.f = new Integer(4);
		return new MyInteger(5);
	}
} 