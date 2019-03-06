package ar.uba.dc.analysis.escape;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.MethodInformationProvider;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.code.BasicMethodBody;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.common.code.MethodDecorator;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationBodyBuilder;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethodBody;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethodBuilder;
import ar.uba.dc.analysis.common.method.information.JsonBasedEscapeAnnotationsProvider;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.analysis.escape.graph.node.PaperStmtNode;
import ar.uba.dc.analysis.escape.summary.EscapeAnnotation;
//import ar.uba.dc.analysis.common.AbstractInterproceduralAnalysis.IntComparator;
import ar.uba.dc.analysis.escape.summary.repository.RAMSummaryRepository;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartitionBinding;
import ar.uba.dc.analysis.memory.impl.summary.RichPaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.SimplePaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.invariant.InvariantProvider;
import ar.uba.dc.invariant.spec.SpecInvariantProvider;
import ar.uba.dc.invariant.spec.compiler.constraints.parser.DerivedVariable;
import ar.uba.dc.util.collections.CircularStack;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;

//import ar.uba.dc.analysis.common.MethodInformationProvider;

/*import ar.uba.dc.analysis.common.MethodInformationProvider;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.escape.summary.repository.RAMSummaryRepository;*/

public class IntermediateLanguageRepresentationBuilder {
	
	
	private static Log log = LogFactory.getLog(IntermediateLanguageRepresentationBuilder.class);

	protected RAMSummaryRepository data;
	protected SummaryRepository<EscapeSummary, SootMethod> repository;	
	protected String outputFolder;
	protected MethodInformationProvider methodInformationProvider;
	protected Map<SootMethod, Integer> order; 
	protected MethodDecorator methodDecorator;
	protected InvariantProvider invariantProvider;
	protected IntermediateRepresentationMethodBuilder irbuilder;
	protected IntermediateRepresentationBodyBuilder irbody_builder;
	protected String mainClass;
	private JsonBasedEscapeAnnotationsProvider jsonBasedEscapeAnnotationsProvider;

	protected CallGraph callGraph;

	protected LifeTimeOracle lifetimeOracle;
	

	
	public IntermediateLanguageRepresentationBuilder(RAMSummaryRepository data, Map<SootMethod, Integer> order, SummaryRepository<EscapeSummary, SootMethod> repository, 
			MethodInformationProvider methodInformationProvider, MethodDecorator methodDecorator, InvariantProvider invariantProvider, 
			String outputFolder, CallGraph callGraph, String mainClass, LifeTimeOracle lifetimeOracle, JsonBasedEscapeAnnotationsProvider jsonBasedEscapeAnnotationsProvider)
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
		this.jsonBasedEscapeAnnotationsProvider = jsonBasedEscapeAnnotationsProvider;
	}
	

	
	
	public Set<IntermediateRepresentationMethod> buildIntermediateLanguageRepresentation()
	{
		log.debug("Creando el IR...");
		
		//No hace falta este orden pero me gusta la idea de que este ordenado topologicamente por si un dia me sirve
		
		//ha llegado el dia en que me sirve
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
			
			EscapeSummary summary = data.get(method);
			IntermediateRepresentationMethod m;
			if(summary.isArtificial)
			{
				//esto me parece que esta mal.
				//deberiamos usar el resultado del escape analysis.
				//creo. De ahi sacamos los nodos que escapan, justamente.
				//Lo de crear NEWS esta bien, pero los nodos no los deberia crear yo sino traerlos del resultado del escape analysis.
				
				
				//el comentario de arriba...es fruta.
				
				//el consumo anotado deberia ser parametrico y deberia crear un solo nodo con un invariante dummy.
				
				m = new IntermediateRepresentationMethod(method, order);
				Map<Node, Integer> numbers = new HashMap<Node, Integer>();
				m.setNodesInfo(summary, numbers);
				m.setDeclaringClass(method.getDeclaringClass().toString());
				m.setName(method.getName());
				
				
				//no se si esto esta bien
				m.setNewRelevantParameters(new HashSet<DerivedVariable>());
				m.setDeclaration(method.getDeclaration());
				
				//TODO crear metodo que reciba un intermediate representation method (& subsignature)
				//EscapeAnnotation annotation = jsonBasedEscapeAnnotationsProvider.get(m.getDeclaration());
				
				m.setRelevantParameters(this.invariantProvider.getRelevantParameters(method));
				
				//SootMethod sm = new SootMethod();
				//sm.set...
				//m.setRelevantParameters(invariantProvider.getRelevantParameters(m));
				
				
				//m.setRelevantParameters(relevant_parameters)
				
				//esto esta mal
				//me parece que deberia ser independiente de si es fresh o no.
			
				Set<PaperPointsToHeapPartition> escapeNodes = new HashSet<PaperPointsToHeapPartition>();
				Set<PaperPointsToHeapPartition> nodes = new HashSet<PaperPointsToHeapPartition>();
				int i = 0;
				
				IntermediateRepresentationMethodBody body = new IntermediateRepresentationMethodBody();

				//I add a NEW associated to a hp for every escaping object 
				
				if(m.getEscapeNodes().size() > 0)
				{					
					PaperPointsToHeapPartition hp_method = null;
					Iterator<PaperPointsToHeapPartition> it = m.getEscapeNodes().iterator();
					
					while(it.hasNext())
					{
						PaperPointsToHeapPartition hp_escape = it.next();

						//HACK: el HP es el primero (method node si el metodo no analizable fue anotado como fresh,
						//o el primer parametro, si no.
						hp_method = hp_escape;
						hp_method.setNumber(i);
						i +=1;
						break;
						
					}
					if(hp_method != null)
					{
						//creo escape NEWs y los asigno a un solo nodo que escapa.
						escapeNodes.add(hp_method);
						for(i = 0; i < summary.getEscape(); i++)
						{												
							
							
							Invocation invocation = new Invocation();
							Line line = new Line();
							
							line.setInvariant(new DomainSet());
							
							line.setName("new");
							line.setIrName("new");
							
							line.setIrClass(""); //I dont care
							line.magicalStmtName = "";
							invocation.setIsReturnRefLikeType(false);
							invocation.setClass_called("");
							invocation.setCalled_implementation_signature("");
							invocation.setParameters(new HashSet());
							invocation.setHpBindings(new HashSet());
							invocation.setNameCalled("");
							
							List<Invocation> invocations = new ArrayList<Invocation>();
							
							
							///////por donde escapa el objeto creado por el NEW? Por ejemplo, si hay varios parametros....
							//creo que a partir de por donde escapa el return value....deberia....hacer que apunte a eso?
							//por ejemplo, si escapa a traves de M_0, ese es el SH...pero que pasa si escapa a traves de un parametro?
							//ademas un parametro es un outside node y el paper dice que los subheap descriptors son todos inside nodes. mhm.
							invocation.setHeapPartition(hp_method);
							
							invocations.add(invocation);
							line.setInvocations(invocations);
						
							
							body.addLine(line);
						}
						m.setEscapeNodes(escapeNodes);	
						nodes.addAll(escapeNodes);
					}	
				}
				if(m.getNodes().size() > 0)
				{				
					//me parece que deberia crear un inside node para simular cuantos objetos escapan
					//y por donde.
					PaperNode n = new PaperStmtNode(0,  true, new CircularStack<String>());
					PaperPointsToHeapPartition non_escaping = new RichPaperPointsToHeapPartition(n, m.getName());
	
				
					//I add a NEW associated to a hp for every other object
					for(int j = i+1; j <= summary.getMaxLive(); j++)
					{
						//PaperPointsToHeapPartition hp = new RichPaperPointsToHeapPartition(j);
						
						non_escaping.setNumber(1);
						Invocation invocation = new Invocation();
						Line line = new Line();
						line.setInvariant(new DomainSet());
						
						line.setName("new");
						line.setIrName("new");
						line.setIrClass(""); //I dont care
						line.magicalStmtName = "";
						invocation.setIsReturnRefLikeType(false);
						List<Invocation> invocations = new ArrayList<Invocation>();
						invocation.setHeapPartition(non_escaping);
						invocation.setClass_called("");
						invocation.setCalled_implementation_signature("");
						invocation.setParameters(new HashSet());
						invocation.setHpBindings(new HashSet());
						invocation.setNameCalled("");
						
						
						
						invocations.add(invocation);

						line.setInvocations(invocations);
	
						body.addLine(line);
						nodes.add(non_escaping);
						
					}
				}
				m.setBody(body);				
			}
			else
			{
				//Use una clase distinta a MethodBody porque quiero tener guardados los news/calls ordenados para el IR.
				//En un ppio pense que esto era necesario para obtener la info de invariantes, pero eso es mentira
				
				//El MethodBody guarda los news y calls por separado y prefiero no procesarlos por separado
				BasicMethodBody methodBody = methodDecorator.decorate_for_IR(method);
				
				System.out.println(method.toString());
				
				//al final no necesito queue
				m = irbuilder.buildMethod(methodBody, order, ir_methods, queue);
				
				//convertRichPaperPointsToHeapPartitionsToSimplePaperPointsToHeapPartition(m);
			}
			ir_methods.add(m);

		}
		
		log.debug(ir_methods.toString());
		
		return ir_methods;
	}
	
	
	//Al final no lo uso
	private void convertRichPaperPointsToHeapPartitionsToSimplePaperPointsToHeapPartition(
			IntermediateRepresentationMethod m) {
		log.debug("bienvenido");
		try
		{	
			Set<PaperPointsToHeapPartition> nodes = new LinkedHashSet<PaperPointsToHeapPartition>();
			for(PaperPointsToHeapPartition hp : m.getNodes())
			{
				if(hp!=null)
					nodes.add(new SimplePaperPointsToHeapPartition(hp.getNumber()));
			}
			m.setNodes(nodes);
			
			Set<PaperPointsToHeapPartition> escapeNodes = new LinkedHashSet<PaperPointsToHeapPartition>();

			for(PaperPointsToHeapPartition hp : m.getEscapeNodes())
			{

				if(hp!=null)
					escapeNodes.add(new SimplePaperPointsToHeapPartition(hp.getNumber()));
			}
			
			for(Line l : m.getBody().getLines())
			{
				for(Invocation i: l.getInvocations())
				{
					PaperPointsToHeapPartition hp = i.getHeapPartition();

					if(hp!=null)
						i.setHeapPartition(new SimplePaperPointsToHeapPartition(hp.getNumber()));
					
					Set<PaperPointsToHeapPartitionBinding> hpBindings = new HashSet<PaperPointsToHeapPartitionBinding>();
					for(PaperPointsToHeapPartitionBinding hpBinding : i.getHpBindings())
					{
						PaperPointsToHeapPartition calleeHp = hpBinding.getCallee_hp();

						if(calleeHp!=null)
							calleeHp = new SimplePaperPointsToHeapPartition(calleeHp.getNumber());
						
						PaperPointsToHeapPartition callerHp = hpBinding.getCaller_hp();

						if(callerHp!=null)
							callerHp = new SimplePaperPointsToHeapPartition(callerHp.getNumber());
						
						hpBindings.add(new PaperPointsToHeapPartitionBinding(calleeHp, callerHp));
					}
					
					i.setHpBindings(hpBindings);
				}
			}
		
		}
		catch(Exception e)
		{
			log.debug("hola");
		}
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
