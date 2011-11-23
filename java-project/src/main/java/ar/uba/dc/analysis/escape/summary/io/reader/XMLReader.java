package ar.uba.dc.analysis.escape.summary.io.reader;

import java.io.Reader;

import ar.uba.dc.analysis.common.SummaryReader;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.summary.io.xstream.XStreamFactory;
import ar.uba.dc.soot.xstream.StatementIdConverter;

import com.thoughtworks.xstream.XStream;

public class XMLReader implements SummaryReader<EscapeSummary> {

	protected XStream xstream;
	
	public XMLReader() {
		xstream = XStreamFactory.getXStream();
	}
	
	public XMLReader(StatementIdConverter converter) {
		xstream = XStreamFactory.getXStream(converter);
	}
	
	public EscapeSummary read(Reader reader) {
		return (EscapeSummary) xstream.fromXML(reader);
	}
}
