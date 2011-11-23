package ar.uba.dc.basic.polymorphism.detection;

public class BasicTest {

	public static void main(String[] args) {
		testShureImpl();
		testUnShureImpl();
		testCycle();
	}
	
	public static Object testShureImpl() {
		Transform t = getTransfrom();
		
		return t.transform();
	}
	
	public static Object testUnShureImpl() {
		Transform t = getTransfrom2(false);
		
		return t.transform();
	}
	
	public static Object[] testCycle() {
		Object[] arr = new Object[4];
		Transform t = new Transform2();
		for (int i : new int[] {1,2,3,4}) {
			arr[i] = t.transform();
			
			t = getTransfrom2(i > 2);
		}
		
		return arr;
	}
	
	public static Transform getTransfrom() {
		return new Transform();
	}
	
	public static Transform getTransfrom2(boolean b) {
		return b ? new Transform() : new Transform2();
	}
}

class Transform {
	
	public Object transform() {
		return new Integer(1);
	}
	
}

class Transform2 extends Transform {
	
	public Object transform() {
		return new Integer[] {new Integer(1), new Integer(2) };
	}
	
}
