package ar.uba.dc.daikon;

import ar.uba.dc.util.ListItrE;

/**
 * Tiene polimorfismo
 * @author billy
 *
 */
public class Ins7 {
	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		//InstrumentedMethod..a4(n,args);
		mainParameters(n);		
	}
	
	public static void mainParameters(int n)
	{
		Animal a;
		
		
		if(n%2==0)
		{
			a = new Dog();
		}
		else
		{
			a = new Spider();
		}
		
		a.blink(); //Spiders blink 8 eyes, dog 2.
		
		for(int i = 1 ; i < a.get_eyes_blinked(); i++)
		{
			//InstrumentedMethod..a11(i,a.eyes_blinked);
			System.out.println("El animal cerro y abrio el ojo " + i);
		}
		
		
	}
}