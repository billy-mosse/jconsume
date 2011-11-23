package ar.uba.dc.basic;

/**
 * Clase q crea una cell con un numero y un numero siguiente
 * 
 * @temporal: 0
 * @residual: 0
 */
public class NumeroCell {
	Numero data;
	NumeroCell next;

	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public NumeroCell(Numero d, NumeroCell n) {
		data = d;
		next = n;
	}
}

