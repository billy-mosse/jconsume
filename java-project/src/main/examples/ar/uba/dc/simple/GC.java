package ar.uba.dc.simple;

public class GC {

	public static void main(String[] args) {
		Integer[] vector = new Integer[3];
		vector[0] = new Integer(1);
		
		lotOfGarbage(vector, 1);
		vector[2] = new Integer(2);
		System.out.println(vector);

	}
	
	public static void lotOfGarbage(Integer[] vector, int index) {
		//for(int i = 0; i< vector.length; i++) {
		//	Object o = new Object();
		//}
		vector[index] = new Integer(0);
	}
}
