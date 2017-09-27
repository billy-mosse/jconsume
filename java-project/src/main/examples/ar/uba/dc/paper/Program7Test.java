package ar.uba.dc.paper;

public class Program7Test {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		for(int i=1;i<=count;i++)
		{
			for(int j=1;j<=count;j++)
			{
				String[] argsTest=new String[2];
				argsTest[0]=Integer.toString(i);
				argsTest[1]=Integer.toString(j);
				Program6.main(argsTest);
			}
		}
	}
}
