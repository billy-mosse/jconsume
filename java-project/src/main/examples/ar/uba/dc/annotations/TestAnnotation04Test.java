package ar.uba.dc.annotations;

public class TestAnnotation04Test {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		
		String[] argsTest=new String[2];
		
		for(int i = 0; i < Integer.parseInt(args[0]); i++)
		{
			argsTest[0] = Integer.toString(i);
			for(int j = 0; j < Integer.parseInt(args[0]); j++)
			{
				argsTest[1] = Integer.toString(j);
				TestAnnotation04.main(argsTest);
			}
		}
	}
}
