package ar.uba.dc.visibility.p2;

class PrivateClass2
{
	private int n;
	public PrivateClass2()
	{
		this.n = 5;
	}

	public static void main(String[] args)
	{
		InstrumentedMethod.f();
	}
}