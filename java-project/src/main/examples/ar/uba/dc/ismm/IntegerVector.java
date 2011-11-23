package ar.uba.dc.ismm;

import java.util.Random;

public class IntegerVector {
	
	protected Integer[] values;
	
	public IntegerVector(int size) {
		this.values = new Integer[size];
	}
	
	public IntegerVector randomFactor() {
		IntegerVector res = new IntegerVector(this.values.length);
		Random random = new Random();
		for (int i=0;i<values.length;i++) {
			res.values[i] = new Integer(values[i] * random.nextInt());
		}
		return res;
	}
	

}
