package ar.uba.dc.daikon;

public class RichNumber{
	private int number;

	
	public boolean isEven()
	{
		return this.getNumber() % 2 == 0;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}
}
