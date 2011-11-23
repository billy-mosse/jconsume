package ar.uba.dc.basic;

import ar.uba.dc.basic.IList;
/**
 * Clase Que implementa una lista sobre arreglos
 */
public class ArrayList implements IList{
	Numero[] list = null;

	/**
	 * Metodo q devuelve el primero de la lista, o null
	 * 
	 * @temporal: 0
	 * @residual: 0
	 */
	public Numero head() {
		if (list.length == 0) {
			return null;	
		}
		else {
			return list[0];
		}
	}
	
	/**
	 * Metodo q agrega un elemento a la lista
	 * 
	 * @temporal: 0
	 * @residual: list.size + 1
	 */
	public void add(Numero numero) {
		Numero[] newList = new Numero[list.length + 1]; //residual = 1
		//Creo una nueva lista con una pos mas, copio la vieja y el nuevo elemento y seteo la lista original con la nueva
		
		for (int i = 0; i < list.length ; i++) {
			//Numero num = new Numero(list[i].numero);
			//newList[i] =  num;
			newList[i] =  list[i]; 
			
		}
		newList[list.length] = numero;
		list = newList;
	}

	/**
	 * Implementacion del iterador usando el ArrayListItr
	 * 
	 * @temporal: 0
	 * @residual: 1
	 */
	public IIterator iterator() {
		return new ArrayListItr(list);
	}
}
