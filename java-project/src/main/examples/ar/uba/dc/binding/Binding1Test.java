package ar.uba.dc.binding;

public class Binding1Test {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		for(int i=1;i<=count;i++)
		{
			String[] argsTest=new String[1];
			argsTest[0]=Integer.toString(i);
			Binding1.main(argsTest);
		}
	}
}
