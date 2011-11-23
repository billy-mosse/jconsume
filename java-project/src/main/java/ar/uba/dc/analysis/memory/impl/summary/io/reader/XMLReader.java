package ar.uba.dc.analysis.memory.impl.summary.io.reader;

import java.io.Reader;

import ar.uba.dc.analysis.common.SummaryReader;

import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.io.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class XMLReader implements SummaryReader<EscapeBasedMemorySummary> {

	protected XStream xstream;
	
	public XMLReader() {
		xstream = XStreamFactory.getXStream();
	}
	
	public EscapeBasedMemorySummary read(Reader reader) {
		return (EscapeBasedMemorySummary) xstream.fromXML(reader);
	}
	
}
