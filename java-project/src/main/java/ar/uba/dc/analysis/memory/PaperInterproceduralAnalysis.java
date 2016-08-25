package ar.uba.dc.analysis.memory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.SummaryReader;
//import ar.uba.dc.analysis.common.AbstractInterproceduralAnalysis.IntComparator;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.io.reader.JsonReader;
import ar.uba.dc.analysis.common.intermediate_representation.io.reader.XMLReader;
import ar.uba.dc.analysis.escape.InterproceduralAnalysis;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedMemorySummary;
import ar.uba.dc.soot.SootMethodFilter;
import ar.uba.dc.util.location.MethodLocationStrategy;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.toolkits.graph.Orderer;
import soot.toolkits.graph.PseudoTopologicalOrderer;







public class PaperInterproceduralAnalysis {
	
	protected MethodLocationStrategy locationStrategy;

	
	private static Log log = LogFactory.getLog(PaperInterproceduralAnalysis.class);

	
	
	protected SummaryReader<IntermediateRepresentationMethod> jsonReader;
	protected String mainClass;
	
	public SummaryReader<IntermediateRepresentationMethod> getJsonReader() {
		return jsonReader;
	}

	public void setJsonReader(SummaryReader<IntermediateRepresentationMethod> jsonReader) {
		this.jsonReader = jsonReader;
	}

	public void doAnalysis(String mainClass)
	{
		this.mainClass = mainClass;
		Set<IntermediateRepresentationMethod> irMethods = getIrMethods();
		//irMethods = irMethods;
	}
	
	public void run(CallGraph cg, SootMethodFilter filter, String mainClass) {
		
		doAnalysis(mainClass);
		
	}
	
	public Set<IntermediateRepresentationMethod> getIrMethods()
	{
		Set<IntermediateRepresentationMethod> methods = new LinkedHashSet<IntermediateRepresentationMethod>();
		String loc = getLocationStrategy().getBasePath() + "json/" + mainClass + "/";
		
		File folder = new File(loc);
		for (final File fileEntry : folder.listFiles()) {
			log.debug("Retriving location for summary: [" + fileEntry.toString() + "]");			
			
			if (fileEntry.exists()) {
				try {
					IntermediateRepresentationMethod method = jsonReader.read(new FileReader(fileEntry));
					methods.add(method);
				} catch (FileNotFoundException e) {
					log.error("Error al recuperar el summary de escape para el metodo [" + fileEntry.toString() + "] a xml: " + e.getMessage(), e);
				}
			}
			
		}
		
		
		
		return methods;
		
	}


	public SummaryReader<IntermediateRepresentationMethod> getReader() {
		return jsonReader;
	}


	public void setReader(SummaryReader<IntermediateRepresentationMethod>  jsonReader) {
		this.jsonReader = jsonReader;
	}


	protected void init() {
		// TODO Auto-generated method stub
		
	}


	protected void doAnalysis() {
		// TODO Auto-generated method stub
		
	}
	
	public MethodLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}


	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
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
	
	
	protected Map<SootMethod, Integer> order;
	
	/*protected void buildOrder() {
		this.order = new HashMap<SootMethod, Integer>();
		
		//BILLY linear ordering of its vertices such that for every directed edge uv from vertex u to vertex v, v comes before u in the ordering
		Orderer<SootMethod> o = new PseudoTopologicalOrderer<SootMethod>();
		Integer i = 0;
		for (SootMethod m : o.newList(directedCallGraph, true)) {
			this.order.put(m, new Integer(i));
			i++;
		}
	}*/
	
	protected Comparator<SootMethod> getOrderComparator() {
		return new IntComparator();
	}
	
	// queue class
	class IntComparator implements Comparator<SootMethod> {
		public int compare(SootMethod o1, SootMethod o2) {
			Integer v1 = order.get(o1);
			Integer v2 = order.get(o2);
			if(v1!=null && v2!=null)	return v1.intValue() - v2.intValue();
			else if(v1==null && v2!=null) return -v2.intValue();
			else if(v1!=null) return v1.intValue();
			else return 0;
		}
	};
	
	

}
