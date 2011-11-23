package ar.uba.dc.analysis.common;

import java.io.Reader;

public interface SummaryReader<T> {

	public T read(Reader reader);
}
