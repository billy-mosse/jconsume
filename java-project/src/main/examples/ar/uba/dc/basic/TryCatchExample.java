package ar.uba.dc.basic;


@SuppressWarnings("unused")
public class TryCatchExample {

	public static void main(String[] args) {
		test(args[0]);
		test2(args[1]);
	}
	
	public static void test(String a) {
		try {
			Integer b = new Integer(1);
			if (a.equals("")) {
				throw new RuntimeException("El parametro 1 estaba vacio");
			} else {
				System.out.println("El parametro 1 es " + a);
			}
		} catch (Throwable e) {
			System.out.println("CAPTURADA 1");
		}
	}
	
	public static void test2(String a) {
		try {
			test3(a);
		} catch (Throwable e) {
			System.out.println("CAPTURADA 2");
		}
	}
	
	public static void test3(String a) {
		Integer b = new Integer(1);
		
		if (a.equals("")) {
			throw new RuntimeException("El parametro 2 estaba vacio");
		} else {
			System.out.println("El parametro 2 es " + a);
		}
		Integer c = new Integer(1);
		Integer d = new Integer(1);
	}
	
}
