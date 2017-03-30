package ar.uba.dc.paper;

import java.util.Random;

public class Program4Test {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		Random randomno = new Random();
		//int count = 5;
		System.out.println(String.valueOf(count));
		   
		for(int i=0;i<=count;i++)
		{
			for(int j=0;j<=count;j++)
			{
				String[] argsTest=new String[3];
				argsTest[0]=Integer.toString(i);
				argsTest[1]=Integer.toString(j);
				boolean value = randomno.nextBoolean();
				argsTest[2] = value ? "use Op" : "use Op2";
				Program4.main(argsTest);
			}
		}
	}
}
