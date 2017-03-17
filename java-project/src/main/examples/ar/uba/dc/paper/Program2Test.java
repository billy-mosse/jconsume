package ar.uba.dc.paper;

public class Program2Test {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		for(int i=0;i<=count;i++)
		{
			String[] argsTest=new String[1];
			argsTest[0]=Integer.toString(i);
			Program2.main(argsTest);
		}
	}
}
