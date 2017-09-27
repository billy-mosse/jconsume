package ar.uba.dc.daikon;

public class Ins13Test {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		for(int i=1;i<=count;i++)
		{
			String[] argsTest=new String[i];
			
			for(int j = 0; j < i; j++)
			{
				argsTest[j]=Integer.toString(j);
			}			
			Ins13.main(argsTest);
		}
	}
}
