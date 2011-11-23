package ar.uba.dc.basic.polymorphism;

public class BasicTest {
	static Object res;
	public static void main(String[] args) {

		ITransform t = null;
		
		if (args.length > 0) {
			t = new Transform1();
		} else {
			t = new Transform11();
		}
		
		res = t.trans();
		//t.transform();
		//t.t();
	}
	
}

interface ITransform {
	Object trans();
}

abstract class ATransform implements ITransform {
	
	public abstract Object transform();
}

class Transform1 extends ATransform {
	
	public Object transform() {
		return new Integer(1);
	}
	
	public Object trans() {
		return new Integer(1);
	}
	
	public Object t() {
		return new Integer(1);
	}
}

class Transform11 extends Transform1 {
	
	public Object transform() {
		return new Integer[] { new Integer(1) };
	}
	
	public Object trans() {
		return new Integer[] { new Integer(1) };
	}
	
	public Object t() {
		return new Integer[] { new Integer(1) };
	}
}