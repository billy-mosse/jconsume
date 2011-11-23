package ar.uba.dc.basic.polymorphism;

/**
 * Este ejemplo pretende ver si se me solapan nodos en el escape que siguen
 * el mismo camino de calls pero que son de implementaciones distintas. 
 * 
 * @author testis
 */
public class Colors {

	static Object[] o = new Object[2];
	
	public static void main(String[] args) {
		Super m = new A();
		
		if (args.length > 0) {
			m = new B();
			o[0] = m;
		}
		
		o[0] = m.t();
		o[1] = new Object();
	}
	
}

interface M {
	Object t();
}

class Super {
	Object t() {
		return new Object();
	}
	
	Integer s() {
		return new Integer(1);
	}
}

class A extends Super {

	@Override
	public Object t() {
		return super.s();
	}
	
}

class B extends Super {

	@Override
	public Object t() {
		return super.s();
	}
	
}