package ar.uba.dc.ismm;


public class Snippet03 {

	/** 
	 * Cha - Lazy
     * @temporal 140   
	 * @residual 0
	 * 
	 * Cha - Add
     * @temporal 150   
	 * @residual 0
	 */
	public static void main(String[] args) {
		Integer[] v = new Integer[10];
		Integer[] v2 = new Integer[20];
		
		for (int i = 0; i < 20; i++){
			if (i < 10) {
				v[i] = i;
			}
			v2[i] = i;
		}
		
		duplicate(v, v2);
		duplicate(v2, v);
	}
	
	/** 
	 * Lazy
     * @temporal max(3*v1.length + 2*v2.length,2*v1.length + 3*v2.length)  : v1.length >=1 && v2.length >=1
	 * @residual 0
	 * 
	 * 	Add
     * @temporal 3*v1.length + 3*v2.length : v1.length >=1 && v2.length >=1
	 * @residual 0
	 */
	public static void duplicate(Integer[] v1, Integer[] v2) {
		multiyply(v1, 2); //tempCall = 2*v1.length, maxCall = v1.length
		multiyply(v2, 2); //tempCall = 2*v2.length, maxCall = v2.length + v1.length
	}
	
	/**
     * @temporal v.length : v.length >= 1
	  * @residual 2*v.length  : v.length >= 1
	  */
	public static Integer[] multiyply(Integer[] v, int f) {
		Integer[] res = new Integer[v.length]; //residual = v.length
		for (int i=0;i<v.length;i++) {
			int value = v[i].intValue();
			Integer factor = new Integer(f); //tempLocal = v.length
			res[i] = new Integer(value * factor); //residual = v.length  
		}
		return res;
	}
	
}
