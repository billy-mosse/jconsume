package ar.uba.dc.annotations.demo;

public class Demo07Test {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		
		String[] argsTest=new String[3];
		
		for(int i = 0; i < Integer.parseInt(args[0]); i++)
		{
			argsTest[0] = Integer.toString(i);
			for(int j = 0; j < Integer.parseInt(args[0]); j++)
			{
				argsTest[1] = Integer.toString(j);
				argsTest[2] = Integer.toString(i*j);
				Demo07.main(argsTest);
			}
		}
	}
}