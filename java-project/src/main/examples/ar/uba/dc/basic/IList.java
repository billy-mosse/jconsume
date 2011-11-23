package ar.uba.dc.basic;

public interface IList {
	Numero head(); //obtener el primer elemento

	void add(Numero numero); //Agregar un elemento
	
	IIterator iterator(); //Iterar la lista
}
