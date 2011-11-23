package ar.uba.dc.basic.array;

@SuppressWarnings({"unused"})
public class EjemploArray01 {

	/**
	 * ???
	 * @temporal 0
	 * @residual 0
	 */
	public void main(String[] args) {
		int size = 5;
		testCrearArrayNoEspecificado(size);
		testCrearArrayEspecificado(size);
		testCrearArrayEnForNoEspecificado(size);
		testCrearArrayEnForEspecificado(size);
	}

	
	/**
	 * Metodo q crea un arreglo de n posiciones pero no se especifica, es 1 solo new 
	 * 
	 * @temporal 1
	 * @residual 0
	 */
	public void testCrearArrayNoEspecificado(int size) {
		int[] array = new int[size]; //tempLocal = 1
	}
	
	/**
	 * Metodo q crea un arreglo de n posiciones pero se especifica, son size news 
	 * 
	 * @temporal size : size
	 * @residual 0
	 */
	public void testCrearArrayEspecificado(int size) {
		int[] array = new int[size]; //tempLocal = size : size >=1
	}

	/**
	 * Metodo q crea un arreglo de size arreglos, de 1<=i<=size posiciones, pero no esta especificado el largo del arreglo 
	 * 
	 * @temporal size : size >=1
	 * @residual 0
	 */
	public void testCrearArrayEnForNoEspecificado(int size) {
		for (int i = 1; i <= size; i++) { 
			int[] array = new int[i]; //tempLocal = size : size >=1	 
		}
	}
	
	/**
	 * Metodo q crea un arreglo de size arreglos, de 1<=i<=size posiciones, pero no esta especificado el largo del arreglo 
	 * 
	 * @temporal (1/2) * size + (1/2) * size^2 : size >=2
	 * @residual 0
	 */
	public void testCrearArrayEnForEspecificado(int size) {
		for (int i = 1; i <= size; i++) { 
			int[] array = new int[i]; //tempLocal = (size * (size + 1))/2	
		}
	}
}
