package ar.uba.dc.daikon;

public class Spider extends Animal {

	
	public Spider()
	{
		eyes_blinked = 0;
	}
	
	@Override
	public void blink() {
		for(int i = 1; i <= 8; i++)
		{
			eyes_blinked +=1;
			InstrumentedMethod2.a10(i);
		}		
	}

	@Override
	public int get_eyes_blinked() {
		return eyes_blinked;
	}
	
	

}
