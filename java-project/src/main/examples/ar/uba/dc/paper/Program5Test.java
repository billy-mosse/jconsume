package ar.uba.dc.paper;

import java.util.Random;

public class Program5Test {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);		   
		for(int i=0;i<=count;i++)
		{
				String[] argsTest=new String[1];
				argsTest[0]=Integer.toString(i);
				Program5.main(argsTest);
			}
		}
	}

