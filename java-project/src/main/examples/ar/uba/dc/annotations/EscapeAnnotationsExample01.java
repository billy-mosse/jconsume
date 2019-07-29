package ar.uba.dc.annotations;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import fj.data.Enumerator;

public class EscapeAnnotationsExample01 {

	public static void main(String[] args)
	{
		
	}
	
	//Nos gustaria saber que este metodo es puro
	/*
	 * Una manera es meter los unanalizable methods en esos archivos xml medio feos
	 * que directamente dicen que es puro.
	 * Pero es mejor tener un degrade, y poder procesar metodos que no son puros
	 * pero tampoco "modifican todo"
	 */
	
	/*
	 * Los metodos no analizables son iterator, hasNext, y next.
	 * Conservativamente, el analisisi diria que iterator() puede escribir en, por ejemplo, todas las variables estaticas.
	 * Tambien podria modificar el parametro e.
	 * Lo mismo para hasNext(), y next().
	 * 
	 */
	//Este ejemplo reproduce el del paper de iwako
	
	//Ver https://stackoverflow.com/questions/2442337/common-ancestor-to-java-array-and-list
	@EscapeAnnotation(methods={
			//ejemplo. Seguir idea del paper, para cada callee relevante.
			//y usar un booleano para pisar el summary con uno armado por mi.
	@CalleeEscapeAnnotation(
		    escapes = false,
		    globalAccess = false,
		    methodSignature = "",
		    returnType = "",
		    writeConfined = false)})
	List<Integer> copy(Iterable<Integer> src)
	{
		List<Integer> l = new ArrayList<Integer>();
		Iterator<Integer> iter = src.iterator();
		while(iter.hasNext())
		{
			l.add(iter.next());
		}
		return l;
	}
}
