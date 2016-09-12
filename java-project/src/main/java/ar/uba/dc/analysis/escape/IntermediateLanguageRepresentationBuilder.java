package ar.uba.dc.analysis.escape;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.MethodInformationProvider;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.code.BasicMethodBody;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.common.code.MethodDecorator;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationBodyBuilder;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethodBuilder;
//import ar.uba.dc.analysis.common.AbstractInterproceduralAnalysis.IntComparator;
import ar.uba.dc.analysis.escape.summary.repository.RAMSummaryRepository;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.invariant.InvariantProvider;
import ar.uba.dc.invariant.spec.SpecInvariantProvider;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;

//import ar.uba.dc.analysis.common.MethodInformationProvider;

/*import ar.uba.dc.analysis.common.MethodInformationProvider;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.escape.summary.repository.RAMSummaryRepository;*/

public class IntermediateLanguageRepresentationBuilder {
	
	
	private static Log log = LogFactory.getLog(IntermediateLanguageRepresentationBuilder.class);

	protected RAMSummaryRepository data;
	protected SummaryRepository<EscapeSummary> repository;	
	protected String outputFolder;
	protected MethodInformationProvider methodInformationProvider;
	protected Map<SootMethod, Integer> order; 
	protected MethodDecorator methodDecorator;
	protected InvariantProvider invariantProvider;
	private IntermediateRepresentationMethodBuilder irbuilder;
	private IntermediateRepresentationBodyBuilder irbody_builder;
	private String mainClass;

	private CallGraph callGraph;

	private LifeTimeOracle lifetimeOracle;
	
	
	
	
	public IntermediateLanguageRepresentationBuilder(RAMSummaryRepository data, Map<SootMethod, Integer> order, SummaryRepository<EscapeSummary> repository, 
			MethodInformationProvider methodInformationProvider, MethodDecorator methodDecorator, InvariantProvider invariantProvider, 
			String outputFolder, CallGraph callGraph, String mainClass, LifeTimeOracle lifetimeOracle)
	{
		this.data=data;
		this.order = order;
		this.repository = repository;
		this.outputFolder = outputFolder;
		this.methodInformationProvider = methodInformationProvider;
		this.methodDecorator = methodDecorator;
		this.invariantProvider = invariantProvider;
		this.callGraph = callGraph;
		this.mainClass = mainClass;
		this.lifetimeOracle = lifetimeOracle;
	}
	

	public Set<IntermediateRepresentationMethod> buildIntermediateLanguageRepresentation()
	{
		log.debug("Creando el IR...");
		
		//No hace falta este orden pero me gusta la idea de que este ordenado topologicamente por si un dia me sirve
		SortedSet<SootMethod> queue = new TreeSet<SootMethod>(getOrderComparator());
		queue.addAll(order.keySet());
		
		
		
		irbuilder = new IntermediateRepresentationMethodBuilder(invariantProvider, data, callGraph, lifetimeOracle);
		
		
		//El order era por si queria escribir el IR ya ordenado topologicamente asi no tenia que ordernarlo cuando lo levantaba
		//No lo estoy usando porque la idea es que uno pueda escribir un codigo en lenguaje intermedio, o hacerle cambios,
		//y que el programa pueda laburar con eso directamente
		
		//Podria escribirlo y si cuando lo levanto no hay order, ahi si ordeno topologicamente
		//TODO: hacerlo pero despues del analisis. No vale la pena calentarse por eso por ahora
		long order = 0;
		Set<IntermediateRepresentationMethod> ir_methods = new LinkedHashSet<IntermediateRepresentationMethod>();
		for (SootMethod method : queue) {
			log.debug("Generating IR of " + method.toString());
			order++;
			log.info(" |- processing " + method.toString());
			
			//Use una clase distinta a MethodBody porque quiero tener guardados los news/calls ordenados para el IR.
			//En un ppio pense que esto era necesario para obtener la info de invariantes, pero eso es mentira
			
			//El MethodBody guarda los news y calls por separado y prefiero no procesarlos por separado
			BasicMethodBody methodBody = methodDecorator.decorate_for_IR(method);
			
			
			IntermediateRepresentationMethod m = irbuilder.buildMethod(methodBody, order);
			ir_methods.add(m);
		}
		
		log.debug(ir_methods.toString());
		
		return ir_methods;
	}
	
	//TODO: esto esta copiado de AbstractInterProceduralAnalysis. Pensar como hacer para que este una vez
	protected Comparator<SootMethod> getOrderComparator() {
		return new IntComparator();
	}


	protected IntermediateRepresentationMethodBuilder getIrbuilder() {
		return irbuilder;
	}


	protected void setIrbuilder(IntermediateRepresentationMethodBuilder irbuilder) {
		this.irbuilder = irbuilder;
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
