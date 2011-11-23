package ar.uba.dc.basic;

import ar.uba.dc.basic.IIterator;

public class EnlListItr implements IIterator{

	NumeroCell cell;

	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public EnlListItr(NumeroCell head) {
		cell = head;
	}

	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public boolean hasNext() {
		boolean hasNext = (cell != null);
		return hasNext;
	}

	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public Numero next() {
		Numero result = cell.data;
		cell = cell.next;
		return result;
	}
	
}

