package ar.uba.dc.analysis.common.intermediate_representation.io.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.escape.summary.io.xstream.XStreamFactory;
import ar.uba.dc.util.location.MethodLocationStrategy;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import com.thoughtworks.xstream.XStream;

public class HumanReadableWriter implements SummaryWriter<IntermediateRepresentationMethod>{

	private static Log log = LogFactory.getLog(XMLWriter.class);
	
	protected XStream xstream;
	
	protected MethodLocationStrategy locationStrategy;
	
	public HumanReadableWriter() {
		this.xstream = XStreamFactory.getXStream();
	}
	
	public void write(IntermediateRepresentationMethod ir_method) {
		
		log.debug("estoy escribiendo el metodo " + ir_method.toString());
		
		String location = locationStrategy.getHumanReadableLocation(ir_method);
		log.debug("Location for summary: [" + location + "]");
		
		File srcFile = new File(location);
		
		if (!srcFile.getParentFile().exists()) {
			srcFile.getParentFile().mkdirs();
		}
				
		try {
			//xstream.toXML(ir_method, new FileWriter(srcFile, false));
			//PrintWriter writer = new PrintWriter(srcFile, "UTF-8");
			//writer.println(ir_method.toHumanReadableString());
			//writer.close();
			
            BufferedWriter bwr = new BufferedWriter(new FileWriter(srcFile));
            bwr.write(ir_method.toHumanReadableString());
            bwr.flush();
            bwr.close();

		} catch (IOException e) {
			log.error("Error al imprimir el summary para el metodo [" + ir_method.getTarget().getSubSignature() + "] a xml: " + e.getMessage(), e);
		}
	}

	public MethodLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}

	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}
}