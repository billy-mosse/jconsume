package ar.uba.dc.visibility.p1;


class PrivateClass1
{
	private int n;
	public PrivateClass1()
	{
		this.n = 5;
	}
	public static void main(String[] args)
	{
		InstrumentedMethod.f();
	}
}