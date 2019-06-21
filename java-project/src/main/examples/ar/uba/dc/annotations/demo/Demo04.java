package ar.uba.dc.annotations.demo;

import java.util.Iterator;

import ar.uba.dc.annotations.MyInteger;
import ar.uba.dc.util.ListE;

public class Demo04 {

	public static void main(String[] args)
	{
		MyInteger ret = mainParameters();
	}

	public static MyInteger mainParameters()
	{
		MyInteger i = new MyInteger();
		MyInteger j = new MyInteger();
		
		//this es anotado como writable, asi que apunta potencialmente a j
		i.doSomething(j);
		return j;
	}
}