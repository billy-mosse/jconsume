package ar.uba.dc.util;

public class Cell {
   
	Object data;
	Cell next;

	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public Cell(Object d, Cell n) {
		data = d;
		next = n;
	}
   
}
