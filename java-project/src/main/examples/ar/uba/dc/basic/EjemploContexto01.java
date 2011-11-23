package ar.uba.dc.basic;

import ar.uba.dc.basic.Numero;

@SuppressWarnings({"unused"})
public class EjemploContexto01 {
	
	/**
	 * 
	 * Deberia Dar
	 * @temporal 7
	 * @residual 0
	 * 
	 * Esta dando
	 * @temporal 12
	 * @residual 0
	 */
	public void main(String[] args) {
		Numero numero1;
		numero1 = testContexto1(); //maxCall = 1, tempCall = 1
		numero1 = testContexto2(); //maxCall = 1, tempCall = 1
		numero1 = testContexto3(); //maxCall = 1, tempCall = 1
		numero1 = testContexto4(); //maxCall = 1, tempCall = 1
		numero1 = testContexto5(); //maxCall = 1, tempCall = 1
		numero1 = testContexto6(); //maxCall = 1, tempCall = 1
	}

	
	/**
	 * Metodo q prueba los problemas de contexto cuando se llama al mismo metodo q devueve algo q se usa como residual y tambien como temporal 
	 * 
	 * Deberia dar
	 * @temporal 1
	 * @residual 1
	 * 
	 * Esta dando
	 * @temporal 0
	 * @residual 2
	 */
	public Numero testContexto1() {
		Numero numero1 = crearNumero(3); //residual = 1
		Numero numero2 = crearNumero(4); //tempCall = 1
		return numero1;
	}
	
	/**
	 * Metodo q prueba q si hago lo mismo q el metodo pero sin llamarlo, no hay temas de contexto 
	 * 
	 * @temporal 1
	 * @residual 1
	 */
	public Numero testContexto2() {
		Numero numero1 = crearNumero(3); //residual = 1
		Numero numero2 = new Numero(4); //tempCall = 1
		return numero1;
	}
	
	/**
	 * Metodo q prueba problemas de contexto cuando A->B  y B  y devuelvo lo de A->B  
	 * 
	 * Deberia dar
	 * @temporal 1
	 * @residual 1
	 * 
	 * Esta dando
	 * @temporal 0
	 * @residual 2
	 */
	public Numero testContexto3() {
		Numero numero1 = crearNumero2(3); //residual = 1
		Numero numero2 = crearNumero(4); //tempCall = 1
		return numero1;
	}
	
	/**
	 * Metodo q prueba problemas de contexto cuando A->B  y B  y B  
	 * 
	 * Deberia dar
	 * @temporal 1
	 * @residual 1
	 * 
	 * Esta dando
	 * @temporal 0
	 * @residual 2
	 */
	public Numero testContexto4() {
		Numero numero1 = crearNumero2(3); //tempCall = 1
		Numero numero2 = crearNumero(4); //residual = 1
		return numero2;
	}
	
	/**
	 * Metodo q prueba problemas de contexto cuando A->B->C  y D->C  y devuelvo lo de A->B->C  
	 * 
	 * Deberia dar
	 * @temporal 1
	 * @residual 1
	 * 
	 * Esta dando
	 * @temporal 0
	 * @residual 2
	 */
	public Numero testContexto5() {
		Numero numero1 = crearNumero3(3); //tempCall = 1
		Numero numero2 = crearNumero4(4); //residual = 1
		return numero1;
	}
	
	/**
	 * Metodo q prueba problemas de contexto cuando A->B->C  y D->C  y devuelvo lo de D->C  
	 * 
	 * Deberia dar
	 * @temporal 1
	 * @residual 1
	 * 
	 * Esta dando
	 * @temporal 0
	 * @residual 2
	 */
	public Numero testContexto6() {
		Numero numero1 = crearNumero3(3); //tempCall = 1
		Numero numero2 = crearNumero4(4); //residual = 1
		return numero2;
	}
	
	/**
	 * Metodo q crea un numero, la idea es q tenga residual 
	 * 
	 * @temporal 0
	 * @residual 1 
	 */
	public Numero crearNumero(int num) {
		Numero numero = new Numero(num); //residual = 1
		return numero;
	}
	
	/**
	 * Metodo q crea un numero, llamando a crearNumero, es para probar el problema de contexto en varias llamadas 
	 * 
	 * @temporal 0
	 * @residual 1 
	 */
	public Numero crearNumero2(int num) {
		return crearNumero(num); //residual = 1
	}
	
	/**
	 * Metodo q crea un numero, llamando a crearNumero2, es para probar el problema de contexto en varias llamadas 
	 * 
	 * @temporal 0
	 * @residual 1 
	 */
	public Numero crearNumero3(int num) {
		return crearNumero2(num); //residual = 1
	}
	
	/**
	 * Metodo q crea un numero, llamando a crearNumero2, es para probar el problema de contexto en varias llamadas 
	 * 
	 * @temporal 0
	 * @residual 1 
	 */
	public Numero crearNumero4(int num) {
		return crearNumero(num); //residual = 1
	}
}
