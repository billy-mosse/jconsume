package ar.uba.dc.analysis.memory.impl.summary.io.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.io.xstream.XStreamFactory;
import ar.uba.dc.util.location.MethodLocationStrategy;

import com.thoughtworks.xstream.XStream;

public class XMLWriter implements SummaryWriter<EscapeBasedMemorySummary> {

	private static Log log = LogFactory.getLog(XMLWriter.class);
	
	protected XStream xstream;
	
	protected MethodLocationStrategy locationStrategy;
	
	public XMLWriter() {
		this.xstream = XStreamFactory.getXStream();
	}
	
	public void write(EscapeBasedMemorySummary summary) {
		String location = locationStrategy.getLocation(summary.getTarget());
		log.debug("Location for summary: [" + location + "]");
		
		File srcFile = new File(location);
		
		if (!srcFile.getParentFile().exists()) {
			srcFile.getParentFile().mkdirs();
		}
		
		try {
			xstream.toXML(summary, new FileWriter(srcFile, false));
		} catch (IOException e) {
			log.error("Error al imprimir el summary para el metodo [" + summary.getTarget().getSubSignature() + "] a xml: " + e.getMessage(), e);
		}
	}

	public MethodLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}

	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}
}