package ar.uba.dc.daikon;

public class Dog extends Animal {

	public Dog()
	{
		eyes_blinked = 0;
	}
	@Override
	public void blink() {
		for(int i = 1; i <= 2; i++)
		{
			eyes_blinked+=1;
			//InstrumentedMethod..a10(i);
		}		
	}
	
	@Override
	public int get_eyes_blinked() {
		return eyes_blinked;
	}

}
