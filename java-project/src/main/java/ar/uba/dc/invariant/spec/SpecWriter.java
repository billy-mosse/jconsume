package ar.uba.dc.invariant.spec;

import java.io.Writer;

import ar.uba.dc.invariant.spec.bean.Specification;

/**
 * Clase que permite escribir una especificacion dado una especificacion y un destino donde escribir
 * 
 * @author testis
 */
public interface SpecWriter {

	public void write(final Specification spec, final Writer destination);
}
