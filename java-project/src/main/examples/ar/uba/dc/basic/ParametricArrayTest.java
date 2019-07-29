package ar.uba.dc.basic;

/**
 * Clase Que implementa una lista sobre arreglos
 */
public class ParametricArrayTest {
	public static void main(String[] args) {
		for(int i = 0; i < 10; i++)
		{
			ParametricArray.main(new String[]{Integer.toString(i)});
		}
	}
}
