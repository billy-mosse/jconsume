package ar.uba.dc.basic;

import ar.uba.dc.basic.Numero;

@SuppressWarnings({"unused"})
public class EjemploInfinito01 {
	/**
	 * 
	 * @temporal infty
	 * @residual 0
	 */
	public void main(String[] args) {
		int n = 5;
		Numero[] arrNumero;
		testNumerosHastaSinSentido(n);
		testNumerosHastaSinSentido2(n);
		arrNumero = testNumerosHastaConResidual(n);
		testNumerosHastaConCallYNews(n);
		testNumerosHastaSinResidual(n);
		//cambiarNumeros(arr1[0], arr1[1]);
	}
	
	
	/**
	 * Metodo q hace call sobre un metodo con temporal infinito. La idea es probar el ub de la calculadora  
	 * 
	 * @temporal infty
	 * @residual 0
	 */
	public void testNumerosHastaSinSentido(int n) {
		Numero[] arrNumero;
		arrNumero = numerosHastaSinSentido(n); //maxCall = infty, tempCall = 1
	}
	
	/**
	 * Metodo q hace call sobre algo con temporal, y luego sobre algo infinito y prueba q el max funcione bien  
	 * 
	 * @temporal infty +1 : n>=1 ; 1 : infty<=0
	 * @residual 0
	 */
	public void testNumerosHastaSinSentido2(int n) {
		Numero[] arrNumero, arrNumero2;
		arrNumero2 = numerosHastaSinSentido2(n); //maxCall = n +1 : n>=1 ; 1 : n==0, tempCall = 1
		arrNumero = numerosHastaSinSentido(n); //maxCall = infty, tempCall = 1 
		
	}
	
	/**
	 * Metodo q hace prueba q pasa cuando tengo bien el residual y infinito el temporal  
	 * 
	 * ArrayCountSize
	 * @temporal infty : n>=1 ; infty : n<=0
	 * @residual 2*n : n>=1
	 * 
	 * ArrayCountOne
	 * @temporal infty : n>=1 ; infty : n<=0
	 * @residual n +1 : n>=1 ; 1 : n<=0
	 */
	public Numero[] testNumerosHastaConResidual(int n) {
		Numero[] arrNumero;
		arrNumero = numerosHastaSinSentido(n); //maxCall = infty

		return numerosHasta(n); //residual = n +1 : n>=1 ; 1 : n<=0  (2*n : n>=1 ArrayCountSize)   
		
	}
	
	/**
	 * Metodo q hace la prueba de devolvar temporal infinito sumando un polinomio en tempCall con infinito en MaxCall  
	 * 
	 * @temporal infty : n>=1 ; infty : n<=0
	 * @residual 0
	 */
	public void testNumerosHastaSinResidual(int n) {
		Numero[] arrNumero, arrNumero2;
		arrNumero = numerosHastaSinSentido(n); //maxCall = infty
		arrNumero2 = numerosHasta(n); //tempCall = n +1 : n>=1 ; 1 : n<=0  (2*n : n>=1 ArrayCountSize)
	}
	
	/**
	 * Metodo q hace la prueba de llamar a cosas q tienen residual infinito, pero aca son temporales  
	 * 
	 * @temporal infty : n>=1 ; infty : n<=0
	 * @residual 0
	 */
	public void testNumerosHastaConCallYNews(int n) {
		Numero[] arrNumero, arrNumero2;
		arrNumero = testNumerosHastaConCall(n); //tempCall = infty : n>=1 ; infty : n<=0
		arrNumero2 = testNumerosHastaConNew(n); //tempCall = infty : n>=1 ; infty : n<=0
	}
	
	/**
	 * Metodo q hace la prueba de devolver un infinito y prueba el metodo que crea ese infinito como residual con calls  
	 * 
	 * @temporal 0
	 * @residual infty
	 */
	public Numero[] testNumerosHastaConCall(int n) {
		return numerosHastaConCall(n); //residual = infty
	}
	
	/**
	 * Metodo q hace la prueba de devolver un infinito y prueba el metodo que crea ese infinito como residual con news  
	 * 
	 * @temporal 0
	 * @residual infty
	 */
	public Numero[] testNumerosHastaConNew(int n) {
		return numerosHastaConNew(n); //residual = infty
	}
	
	
	/**
	 * Metodo q mete n numeros en una array haciendo News  y esta bien hecho  
	 * 
	 * ArrayCountSize
	 * @temporal 0
	 * @residual 2*n : n>=1
	 * 
	 * ArrayCountOne
	 * @temporal 0
	 * @residual n +1 : n>=1 ; 1 : n<=0  
	 */
	public Numero[] numerosHasta(int n) {
		Numero[] arrNumero = new Numero[n]; //residual = 1 (n ArrayCountSize)
		for (int j = 0; j < n; j++) {
			Numero numero = new Numero(j); //residual = n si n >=1 
			arrNumero[j] = numero;
		}
		return arrNumero;
	}
	
	/**
	 * Metodo q mete n numeros en una array haciendo News (card). La idea es bindear mal, ver que de infinito y propagarlo para arriba  
	 * 
	 * @temporal 0
	 * @residual infty : n>=1 ; infty : n<=0
	 */
	public Numero[] numerosHastaConNew(int n) {
		Numero[] arrNumero = new Numero[n]; //residual = 1 (n ArrayCountSize)
		for (int j = 0; j < n; j++) {
			Numero numero = new Numero(j); //residual = n si n >=1 
			arrNumero[j] = numero;
		}
		return arrNumero;
	}
	
	/**
	 * Metodo q mete n numeros en una array haciendo Call (sum al residual). La idea es bindear mal, ver que de infinito y propagarlo para arriba 
	 * 
	 * @temporal 0
	 * @residual infty : n>=1 ; infty : n<=0 
	 */
	public Numero[] numerosHastaConCall(int n) {
		Numero[] arrNumero = new Numero[n]; //residual = 1 (n ArrayCountSize)
		for (int j = 0; j < n; j++) {
			arrNumero[j] = crearNumero(j); //residual = n si n >=1
		}
		return arrNumero;
	}
	
	/**
	 * Metodo q mete n numeros en una array haciendo Call (sum al temporal). La idea es bindear mal, ver que de infinito y propagarlo para arriba 
	 * 
	 * ArrayCountSize
	 * @temporal infty : n>=1 ; infty : n<=0
	 * @residual n : n>=1
	 * 
	 * ArrayCountOne
	 * @temporal infinito
	 * @residual 1    
	 */
	public Numero[] numerosHastaSinSentido(int n) {
		Numero[] arrNumero = new Numero[n]; //tempLocal = 1 (n ArrayCountSize)
		Numero[] arrNumero2 = new Numero[n]; //residual = 1 (n ArrayCountSize)
		for (int j = 0; j < n; j++) {
			arrNumero[j] = crearNumero(j); //maxCall = 0, tempCall = n si n >=1
		}
		return arrNumero2;
	}
	
	/**
	 * Metodo q mete n numeros en una array haciendo Call (sum al temporal), esta bien bindeado pero no lo devuelve 
	 * 
	 * ArrayCountSize
	 * @temporal 2*n : n>=1
	 * @residual n  : n>=1
	 * 
	 * ArrayCountOne
	 * @temporal n +1 : n>=1 ; 1 : n<=0
	 * @residual 1  
	 */
	public Numero[] numerosHastaSinSentido2(int n) {
		Numero[] arrNumero = new Numero[n]; //tempLocal = 1 (n ArrayCountSize)
		Numero[] arrNumero2 = new Numero[n]; //residual = 1 (n ArrayCountSize)
		for (int j = 0; j < n; j++) {
			arrNumero[j] = crearNumero(j); //maxCall = 0, tempCall = n si n >=1
		}
		return arrNumero2;
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
	 * Metodo q cambia el valor de 2 numeros, la idea es q tenga algo temporal 
	 * 
	 * @temporal 1
	 * @residual 0 
	 */
	public void cambiarNumeros(Numero n, Numero m) {
		Numero t = new Numero(n.numero); //tempLocal = 1
		n.numero = m.numero;
		m.numero = t.numero;
	}
	
}


