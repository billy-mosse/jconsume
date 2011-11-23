package ar.uba.dc.invariant.spec.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una especificacion de invariantes. No se mas que un conjunto de especificaciones
 * para clases
 * @author testis
 */
public class Specification implements Serializable {

	private static final long serialVersionUID = 1L;

	protected List<ClassSpecification> classes;
	
	public Specification() {
		super();
		classes = new ArrayList<ClassSpecification>();
	}
	
	/**
	 * BugFix porque no se invoca al constructor por defecto con XStream
	 * 
	 * http://xstream.codehaus.org/faq.html
	 * 
	 * @return
	 */
	private Object readResolve() {
		if (classes == null) {
			classes = new ArrayList<ClassSpecification>();
		}
	    return this;
	}

	
	/**
	 * Obtiene la especificacion de invariantes para un clase
	 * @param className - full class name (con el package) de la clase para la que desea recuperar el invariante
	 * @return ClassSpecification o null (en caso de no existir especificacion de para la clase)
	 */
	public ClassSpecification get(final String className) {
		for (ClassSpecification classSpecification : classes) {
			if (classSpecification.getClassName().equals(className)) {
				return classSpecification;
			}
		}
		
		return null;
	}

	public List<ClassSpecification> getClasses() {
		return new ArrayList<ClassSpecification>(classes);
	}

	public void add(ClassSpecification classSpect) {
		classes.add(classSpect);
	}
}
