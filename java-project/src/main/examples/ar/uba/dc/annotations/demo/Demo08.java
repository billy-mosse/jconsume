package ar.uba.dc.annotations.demo;

import java.util.Iterator;

import ar.uba.dc.annotations.MyInteger;
import ar.uba.dc.util.ListE;

public class Demo08 {

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
		
		//como el ret es wFresh, apunta potencialmente a temp; luego temp escapa del metodo
		MyInteger var = doSomething(temp);
		return var;
	}
	
	
	public static MyInteger doSomething(MyInteger t)
	{
		t.g = new MyInteger(10); //escapa
		MyInteger r = new MyInteger(4);
		r.g = t; //t tambien escapa a traves del return
		return r; 
	}
}