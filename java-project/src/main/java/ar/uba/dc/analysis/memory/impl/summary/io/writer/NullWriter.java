package ar.uba.dc.analysis.memory.impl.summary.io.writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.memory.impl.report.html.HtmlReportWriter;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

/**
 * Un {@link SummaryWriter} que no hace nada. Util para no tener que chequear si un writer es null
 * @author testis
 */
public class NullWriter implements SummaryWriter<MemorySummary> {
	private static Log log = LogFactory.getLog(NullWriter.class);


	public void write(MemorySummary summary) {
		log.debug("No estamos haciendo nada");

		// No hacemos nada
	}

	@Override
	public void setMainClass(String mainClass) {
		throw new java.lang.UnsupportedOperationException();
		
	}

}
