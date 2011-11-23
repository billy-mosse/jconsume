package ar.uba.dc.basic;

/**
 * Clase que implementa un iterador de un arreglo 
 */
public class ArrayListItr implements IIterator{
	int posActual;
	Numero[] iter;

	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public ArrayListItr(Numero[] list) {
		posActual = 0;
		iter = list;
	}

	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public boolean hasNext() {
		boolean hasNext = (posActual < iter.length);
		return hasNext;
	}

	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public Numero next() {
		Numero numero = iter[posActual];
		posActual++;
		return numero;
	}
}
