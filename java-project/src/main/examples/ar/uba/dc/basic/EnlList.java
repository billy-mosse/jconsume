package ar.uba.dc.basic;

import ar.uba.dc.basic.IList;

public class EnlList implements IList{

	NumeroCell head = null;

	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public Numero head() {
		return head.data;
	}
	
	/**
	 * @temporal: 0
	 * @residual: 1
	 */
	public void add(Numero numero) {
		head = new NumeroCell(numero, head);
	}

	/**p
	 * @temporal: 0
	 * @residual: 1
	 */
	public IIterator iterator() {
		return new EnlListItr(head);
	}
	
}


