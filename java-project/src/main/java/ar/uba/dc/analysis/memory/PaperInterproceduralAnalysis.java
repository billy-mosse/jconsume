package ar.uba.dc.analysis.memory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.io.reader.XMLReader;
import ar.uba.dc.analysis.escape.InterproceduralAnalysis;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedMemorySummary;
import ar.uba.dc.util.location.MethodLocationStrategy;
import soot.SootMethod;







public class PaperInterproceduralAnalysis {
	
	
	/*public PaperInterproceduralAnalysis(MethodLocationStrategy locationStrategy)
	{
		this.locationStrategy = locationStrategy;
	}*/
	
	private static Log log = LogFactory.getLog(PaperInterproceduralAnalysis.class);

	
	protected MethodLocationStrategy locationStrategy;
	
	protected XMLReader reader;
	protected String mainClass;
	
	public void doAnalysis(String mainClass)
	{
		this.mainClass = mainClass;
		Set<IntermediateRepresentationMethod> irMethods = getIrMethods();
	}
	
	
	public Set<IntermediateRepresentationMethod> getIrMethods()
	{
		Set<IntermediateRepresentationMethod> methods = new LinkedHashSet<IntermediateRepresentationMethod>();
		String loc = getLocationStrategy().getBasePath() + mainClass + "/";
		
		File folder = new File(loc);
		for (final File fileEntry : folder.listFiles()) {
			log.debug("Retriving location for summary: [" + fileEntry.toString() + "]");			
			
			if (fileEntry.exists()) {
				try {
					IntermediateRepresentationMethod method = reader.read(new FileReader(fileEntry));
					methods.add(method);
				} catch (FileNotFoundException e) {
					log.error("Error al recuperar el summary de escape para el metodo [" + fileEntry.toString() + "] a xml: " + e.getMessage(), e);
				}
			}
			
		}
		
		
		
		return methods;
		
	}


	public MethodLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}


	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}


	public XMLReader getReader() {
		return reader;
	}


	public void setReader(XMLReader reader) {
		this.reader = reader;
	}
	
	
	
	
	
	/*public IntermediateRepresentationMethod get(SootMethod method) {
		String location = locationStrategy.getLocation(method);
		log.debug("Retriving location for summary: [" + location + "]");
		
		if (lastFilePathUsed == null || !lastFilePathUsed.equals(location)) {
			lastFilePathUsed = location; 
			currentSummary = null;
			File srcFile = new File(location);
			
			if (srcFile.exists()) {
				try {
					currentSummary = reader.read(new FileReader(srcFile));
				} catch (FileNotFoundException e) {
					log.error("Error al recuperar el summary de escape para el metodo [" + method.toString() + "] a xml: " + e.getMessage(), e);
				}
			}
		}	
		
		return currentSummary;
	}*/

}
