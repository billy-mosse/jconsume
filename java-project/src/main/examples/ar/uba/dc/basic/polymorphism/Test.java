package ar.uba.dc.basic.polymorphism;

public class Test {

	static Object o;
	
	public static void main(String[] args) {
		M3 m3 = new M3();
		M1 m1 = new M1();
		
		if (args.length > 0) {
			m3 = new M3_();
			m1 = new M1_();
		}
		
		o = m3.m(m1);
	}
	
}

class M3 {
	
	public Object m(M1 m1) {
		return new Integer(1);
	}
	
}

class M3_ extends M3 {
	
	private M2 m2;
	
	public Object m(M1 m1) {
		Object result = m1.m();
		if (m2 != null) {
			result = m2.m(m1);
		}
		
		return result;
	}
	
}

class M2 {
	
	public Object m(M1 m1) {
		Object result = new Integer(1);
		
		if (m1 != null) {
			result = m1.m();
		}
		
		return result;
	}
	
}

class M1 {
	
	public Object m() {
		return new Integer[] {new Integer(1), new Integer(2) };
	}
	
}

class M1_ extends M1 {
	
	public Object m() {
		return new Integer(4);
	}
	
}