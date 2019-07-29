package ar.uba.dc.annotations.omega;


public class MutatedParameterExampleTest {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		for(int i=1;i<=count;i++)
		{
			String[] argsTest=new String[1];
			argsTest[0]=Integer.toString(i);
			MutatedParameterExample.main(argsTest);
		}
	}
}