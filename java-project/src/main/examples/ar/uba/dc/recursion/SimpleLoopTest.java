package ar.uba.dc.recursion;

public class SimpleLoopTest {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);		   
		for(int i=0;i<=count;i++)
		{
				String[] argsTest=new String[1];
				argsTest[0]=Integer.toString(i);
				SimpleLoop.main(argsTest);
			}
		}
	}

