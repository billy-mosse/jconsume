package ar.uba.dc.basic;

/**
 * Ejemplo para probar como se comporta el tipo Integer
 *
 */
@SuppressWarnings({"unused"})
public class EjemploInteger01 {

	/**
	 * El temporal deberia ser 8, por el bug...
	 * 
	 * @temporal 9 (
	 * @residual 0
	 */
	public void main(String[] args) {
		Integer a = newIntReturn(); //tempCall = 1
		newIntNoReturn(); //maxCall = 1
		newIntNoReturnConSuma(); //maxCall = 2
		newIntNoReturnConSumas(); //maxCall = 4
		Integer b = newIntsConSumaYReturn(); //maxCall = 4, tempCall = 1
		Integer c = newIntsConSumaYNoReturn(); //maxCall = 4, tempCall = 1
		Integer d = newIntsConSumas(); //maxCall = 4, tempCall = 2 (Deberia ser 1) 
		newIntsConSumasYNoReturn(); //maxCall = 4
	}
	
	/**
	 * Sirve para mostrar que el Integer escapa bien cuando se lo devuelve
	 * @temporal 0
	 * @residual 1
	 */
	public Integer newIntReturn() {
		Integer a = new Integer(1); //residual = 1
		return a; 
	}
	
	/**
	 * Sirve para mostrar que el Integer no escapa 
	 * @temporal 1
	 * @residual 0
	 */
	public void newIntNoReturn() {
		Integer a = new Integer(1); //tempLocal = 1
	}
	
	/**
	 * Sirve para mostrar que el Integer no escapa y el valueof da 1 de temporal 
	 * @temporal 2
	 * @residual 0
	 */
	public void newIntNoReturnConSuma() {
		Integer a = new Integer(1); //tempLocal = 1
	 	a++; //tempLocal = 1 (del value of)
	}
	
	/**
	 * Sirve para mostrar que el Integer no escapa, y el valueOf da 1 de temporal y suma al mismo resumen 
	 * @temporal 4
	 * @residual 0
	 */
	public void newIntNoReturnConSumas() {
		Integer a = new Integer(1); //tempLocal = 1
		a++; //tempLocal = 1
		a++; //tempLocal = 1
		a++; //tempLocal = 1
	}
	
	/**
	 * Sirve para mostrar que el valueOf sobre algo q escapa y existiendo algo q no escapa, no hace q este escape 
	 * @temporal 2
	 * @residual 1
	 */
	public Integer newIntsConSumaYReturn() {
		Integer a = new Integer(1); //residual = 1
		Integer b = new Integer(2); //tempLocal = 1
		a++; //tempLocal = 1 (El value of)
		return a;
	}
	
	/**
	 * Sirve para mostrar que el valueof sobre algo q no escapa no hace q este escape 
	 * @temporal 2
	 * @residual 1
	 */
	public Integer newIntsConSumaYNoReturn() {
		Integer a = new Integer(1); //residual = 1
		Integer b = new Integer(2); //tempLocal = 1
		a++; //tempLocal = 1 (El value of)
		return b;
	}
	
	/**
	 * Sirve para mostrar que el valueOf sobre algo q escapa y algo q no escapa hace tambien q este escape 
	 * BUG (deberia dar @temporal 3   @residual 1)
	 *  
	 * @temporal 2
	 * @residual 2
	 */
	public Integer newIntsConSumas() {
		Integer a = new Integer(1); //residual = 1
		Integer b = new Integer(2); //tempLocal = 1
		a++; //tempLocal = 1 (el value of)
		b++; //tempLocal = 1 (el value of) (Este es el q colapsa mal y hace q b sea residual)
		return a;
	}
	
	/**
	 * Sirve para mostrar que si no hay un Integer q escapa, ningun value of escapa. (Caso opuesto al bug) 
	 * @temporal 4
	 * @residual 0
	 */
	public void newIntsConSumasYNoReturn() {
		Integer a = new Integer(1); //tempLocal = 1
		Integer b = new Integer(2); //tempLocal = 1
		a++; //tempLocal = 1
		b++; //tempLocal = 1
	}

}
