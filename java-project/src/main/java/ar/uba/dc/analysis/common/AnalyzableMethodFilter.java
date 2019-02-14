package ar.uba.dc.analysis.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import ar.uba.dc.analysis.common.method.information.JsonBasedEscapeAnnotationsProvider;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.soot.SootMethodFilter;

/**
 * Filtro de metodos basado en verificar si un metodo es analizable o no
 */
public class AnalyzableMethodFilter implements SootMethodFilter {

	private static Log log = LogFactory.getLog(AnalyzableMethodFilter.class);
	
	private SummaryRepository<EscapeSummary, SootMethod> escapeRepository;
	
	private SummaryRepository<MemorySummary, SootMethod> memoryRepository;
	
	private MethodInformationProvider methodInformationProvider;
	
	private JsonBasedEscapeAnnotationsProvider jsonBasedEscapeAnnotationsProviderFilter;
	
	private boolean includeKnownMethod;
	
	public AnalyzableMethodFilter() {
		super();
	}
	
	public void build(String mainClass)
	{
		this.jsonBasedEscapeAnnotationsProviderFilter.fetchAnnotations(mainClass);
	}
	
	public AnalyzableMethodFilter(MethodInformationProvider provider) {
		this.methodInformationProvider = provider;
	}

	public boolean want(SootMethod method, String mainClass) {
		boolean isAnalyzable = methodInformationProvider.isAnalyzable(method);
		boolean hasSummary = alreadyHasSummary(method); 
		
		//al final no los filtramos porque queremos que este en la lista para cuando escribamos el lenguaje intermedio
		boolean hasAnnotation = alreadyHasAnnotation(method, mainClass);
		
		if (log.isDebugEnabled()) {
			log.debug("Want [" + method + "]? isAnalizable [" + isAnalyzable + "] - includeKnown [" + includeKnownMethod  + "] - hasSummary [" + hasSummary + "]");
		}
		
		return isAnalyzable && (includeKnownMethod || !hasSummary);// && !hasAnnotation;
	}

	private boolean alreadyHasAnnotation(SootMethod method, String mainClass) {
		return jsonBasedEscapeAnnotationsProviderFilter.hasAnnotationFor(method.getDeclaringClass().toString() + "." + method.getName());
	}

	private boolean alreadyHasSummary(SootMethod method) {
		boolean existEscapeSummary = escapeRepository.contains(method);
		boolean existMemorySummary = memoryRepository.contains(method);
		
		if (log.isDebugEnabled()) {
			log.debug("Searching summary for method [" + method + "]: exists escape? [" + existEscapeSummary + "] - exists memory? [" + existMemorySummary + "]");
		}
		
		return existEscapeSummary && existMemorySummary;
	}

	public void setMethodInformationProvider(MethodInformationProvider methodInformationProvider) {
		this.methodInformationProvider = methodInformationProvider;
	}

	public void setEscapeRepository(SummaryRepository<EscapeSummary, SootMethod> escapeRepository) {
		this.escapeRepository = escapeRepository;
	}

	public void setMemoryRepository(SummaryRepository<MemorySummary, SootMethod>memoryRepository) {
		this.memoryRepository = memoryRepository;
	}

	public void setIncludeKnownMethod(boolean includeKnownMethod) {
		this.includeKnownMethod = includeKnownMethod;
	}	

	public JsonBasedEscapeAnnotationsProvider getJsonBasedEscapeAnnotationsProviderFilter() {
		return jsonBasedEscapeAnnotationsProviderFilter;
	}

	public void setJsonBasedEscapeAnnotationsProviderFilter(
			JsonBasedEscapeAnnotationsProvider jsonBasedEscapeAnnotationsProviderFilter) {
		this.jsonBasedEscapeAnnotationsProviderFilter = jsonBasedEscapeAnnotationsProviderFilter;
	}


}
