package ar.uba.dc.paper;



public class Program5
{
	
	
	public static void main(String[] args) {
		B b = keepOne();
		A2 a = new A2();
		B b2 = aliasIt(a);
	}


	public static B two(A2 a) {
		a.f = new B();
		return new B();
	}

	public static B aliasIt(A2 a) {
		a.f = new B();
		return a.f;
	}


	public static B keepOne() {
		return two(new A2());
	}

}