package ar.uba.dc.util;

import java.util.Iterator;


@SuppressWarnings("unchecked")
public class ListItr implements Iterator {

	Cell cell;

	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public ListItr(Cell head) {
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
	public Object next() {
		Object result = cell.data;
		cell = cell.next;
		return result;
	}
	
	public void remove() { }
}
