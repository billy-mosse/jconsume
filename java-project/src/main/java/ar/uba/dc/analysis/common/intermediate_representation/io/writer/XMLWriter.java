package ar.uba.dc.analysis.common.intermediate_representation.io.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.escape.summary.io.xstream.XStreamFactory;
import ar.uba.dc.util.location.MethodLocationStrategy;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import com.thoughtworks.xstream.XStream;

public class XMLWriter implements SummaryWriter<IntermediateRepresentationMethod>{

	private static Log log = LogFactory.getLog(XMLWriter.class);
	
	protected XStream xstream;
	
	protected MethodLocationStrategy locationStrategy;
	protected String mainClass;
	
	public XMLWriter() {
		this.xstream = XStreamFactory.getXStream();
	}
	

	@Override
	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	
	public void write(IntermediateRepresentationMethod ir_method) {
		
		String location = locationStrategy.getXMLLocation(ir_method, this.mainClass);
		log.debug("Location for summary: [" + location + "]");
		
		File srcFile = new File(location);
		
		if (!srcFile.getParentFile().exists()) {
			srcFile.getParentFile().mkdirs();
		}
				
		try {
			xstream.toXML(ir_method, new FileWriter(srcFile, false));
		} catch (IOException e) {
			log.error("Error al imprimir el summary para el metodo [" + ir_method.getName() + "] a xml: " + e.getMessage(), e);
		}
	}

	public MethodLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}

	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}
}
