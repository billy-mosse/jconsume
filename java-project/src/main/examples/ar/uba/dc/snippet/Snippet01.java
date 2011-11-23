package ar.uba.dc.snippet;

import java.util.Date;
import java.util.Random;

/**
 * @author martin
 *
 */
public class Snippet01 {

	
	public static void main(String[] args) {
		int size = Integer.valueOf(args[0]);
		Integer[] vector = randomVector(size);

		@SuppressWarnings("unused")
		Integer[] copy = copy(vector);
		
		@SuppressWarnings("unused")
		Integer[] deepCopy = deepCopy(vector); 
	}
	
	
	/**
	 * tmp(now.getTime()) = 1 
	 * @param size
	 * @return
	 */
	public static Integer[] randomVector(int size) {
		Date now = new Date(); 				
		long time = now.getTime();			
		Random random = new Random(time);	
		Integer[] vector = new Integer[size];
		for(int i = 0; i<size; i++) {
			Integer value = new Integer(random.nextInt());
			vector[i] = value;
		}
		return vector;
	}
	
	public static Integer[] copy(Integer[] vector) {
		Integer[] newVector = new Integer[vector.length];
		for (int i=0; i<vector.length;i++)
			newVector[i] = vector[i];
		
		return newVector;
	}	
	
	
	public static Integer[] deepCopy(Integer[] vector) {
		Integer[] newVector = new Integer[vector.length];
		for (int i=0; i<vector.length;i++)
			newVector[i] = new Integer(vector[i]);
		
		return newVector;
	}	
}
