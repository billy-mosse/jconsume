package ar.uba.dc.daikon;

public class RichNumberPublic {
	public int number;
	
	public RichNumberPublic(int num)
	{
		this.number = num + 1;
	}

	public RichNumberPublic() {
		// TODO Auto-generated constructor stub
	}

	public void f() {
		this.number = this.number +1;
	}

}
