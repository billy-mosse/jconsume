package ar.uba.dc.analysis.escape.summary.io.writer;

import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.escape.EscapeSummary;

/**
 * Un {@link EscapeSummaryWriter} que no hace nada. Util para no tener que chequear si un writer es null
 * @author testis
 */
public class NullWriter implements SummaryWriter<EscapeSummary> {

	public void write(EscapeSummary summary) {
		// No hacemos nada
	}

	@Override
	public void setMainClass(String mainClass) {
		throw new java.lang.UnsupportedOperationException();
		
	}

}
