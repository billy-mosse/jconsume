package ar.uba.dc.analysis.escape.summary;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.RefLikeType;
import soot.SootMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;

public class EscapeAnnotationsSummaryFactory {
	
	private static int sensitivity = -1;
	private static Log log = LogFactory.getLog(EscapeAnnotationsSummaryFactory.class);
	
	public EscapeSummary buildPTG(SootMethod method, EscapeAnnotation annotation)
	{
		//TODO: que pasa si this es tambien parametro?
		
		EscapeSummary summary = new EscapeSummary(method);
		summary.setArtificial(true);
		//boolean isOmega = var.contains("Fresh");
		
		Node n = null;
		if(annotation.isFresh()){				
			if (method.getReturnType() instanceof RefLikeType) {				
				//el tercer
				n = new MethodNode(method, sensitivity, true);
				summary.addReturned(n);
				summary.add(n);
			}
		}
	
		//PARTE DE ANOTACION WRITE	

		List<ParamNode> writableParameterNodes = new ArrayList<ParamNode>();
		for(int i = 0; i < method.getParameterCount(); i++)
		{
			//todos deberian ser omega nodes porque no tenemos control sobre ellos?
			//igual no afecta si no les llegan edges.
			ParamNode p = new ParamNode(i, true);
			
			if(annotation.isWritableParameter(i))
				writableParameterNodes.add(p);
			
			
			//que onda los mutated?
			
			//si el nodo es parametro automaticamente lo agrega a paramNodes
			summary.add(p);
			
			//no se si hace falta crear los local edges
			if(annotation.isFresh())
			{
				if(n != null)
					summary.relate(n, "?", p, true);
			}
			else
			{
				if(method.getReturnType() instanceof RefLikeType)
					summary.addReturned(p);
			}
		}

		//si es WRITE entonces puede ser escrito transitivamente
		//esto se modela con un edge "?" a todos los nodos alcanzables desde afuera
		for(ParamNode wp :  writableParameterNodes)
		{
			//any field can mutate
			//TODO: how does this affect the rest of the analysis?
			summary.addMutated(wp, "?");
			
			for(Node p : summary.getParameterNodes())
			{
				//Edge e = new Edge(wp, "?", p, true);
				
				summary.relate(wp, "?", p, true);
				//summary.getEdgesOutOf(wp).add(e);
				
			}
			
			//hay uno solo en realidad
			for(Node r : summary.getReturnedNodes())
			{
				//Edge e = new Edge(wp, "?", r, true);
				summary.relate(wp,"?",r, true);					
			}			
		}
			
		return summary;		
	}
}
