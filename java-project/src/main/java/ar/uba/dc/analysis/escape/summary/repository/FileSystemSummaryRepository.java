package ar.uba.dc.analysis.escape.summary.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import ar.uba.dc.analysis.common.SummaryReader;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.util.location.MethodLocationStrategy;

public class FileSystemSummaryRepository implements SummaryRepository<EscapeSummary> {

	private static Log log = LogFactory.getLog(FileSystemSummaryRepository.class);
	
	protected SummaryReader<EscapeSummary> reader;
	
	protected MethodLocationStrategy locationStrategy;
	
	protected String lastFilePathUsed = null;
	
	protected EscapeSummary currentSummary = null;
	
	public boolean contains(SootMethod method) {
		String location = locationStrategy.getLocation(method);
		log.debug("Check if exists location for summary: [" + location + "]");
		
		return new File(location).exists();
	}

	public EscapeSummary get(SootMethod method) {
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
	}

	public void setReader(SummaryReader<EscapeSummary> reader) {
		this.reader = reader;
	}

	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}
}
