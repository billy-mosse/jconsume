package ar.uba.dc.paper;

import java.util.Random;

public class Program3Test {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		Random randomno = new Random();
		//int count = 5;
		System.out.println(String.valueOf(count));
		   
		for(int i=0;i<=count;i++)
		{
			String[] argsTest=new String[2];
			argsTest[0]=Integer.toString(i);
			boolean value = randomno.nextBoolean();
			argsTest[1] = value ? "use Op" : "use Op2";
			Program3.main(argsTest);
		}
	}
}
