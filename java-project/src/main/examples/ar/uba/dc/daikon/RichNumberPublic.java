package ar.uba.dc.daikon;

public class RichNumberPublic {
	public int number;
	
	public RichNumberPublic(int num)
	{
		//InstrumentedMethod2.a2(this, num);
		this.number = num;
	}

	public RichNumberPublic() {
		// TODO Auto-generated constructor stub
	}

	public void f() {
		this.number = this.number +1;
	}

}
