package ar.uba.dc.daikon;

public class Field4Test {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		for(int i=1;i<=count;i++)
		{
			String[] argsTest=new String[1];
			argsTest[0]=Integer.toString(i);
			Field4.main(argsTest);
		}
	}
}
