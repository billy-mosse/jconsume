package ar.uba.dc.invariant.spec;

import java.io.Reader;

import ar.uba.dc.invariant.spec.bean.Specification;

/**
 * Clase que permite recuperar una especificacion dado un reader
 * 
 * @author testis
 */
public interface SpecReader {

	public Specification read(final Reader reader);
}
