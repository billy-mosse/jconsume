package ar.uba.dc.invariant.simple;

import java.io.Reader;
import java.util.Map;

import ar.uba.dc.barvinok.expression.DomainSet;


/**
 * Clase que permite recuperar una especificacion dado un reader
 * 
 * @author testis
 */
public interface SimpleReader {

	public Map<String, DomainSet> read(final Reader reader);
}