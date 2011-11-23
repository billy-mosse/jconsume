package ar.uba.dc.analysis.memory.impl.summary.io.writer;

import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

/**
 * Un {@link SummaryWriter} que no hace nada. Util para no tener que chequear si un writer es null
 * @author testis
 */
public class NullWriter implements SummaryWriter<MemorySummary> {

	public void write(MemorySummary summary) {
		// No hacemos nada
	}

}
