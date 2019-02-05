package ar.uba.dc.analysis.escape.summary;

import java.util.ArrayList;
import java.util.List;

import soot.RefLikeType;
import soot.SootMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;

public class EscapeAnnotationsSummaryFactory {
	
	private static int sensitivity = -1;
	
	public EscapeSummary buildPTG(SootMethod method, EscapeAnnotation annotation)
	{
		EscapeSummary summary = new EscapeSummary(method);
		
		//boolean isOmega = var.contains("Fresh");
		
		
		
		//PARTE DE ANOTACION FRESH
		if(annotation.isFresh())
		{
			//hace falta este IF? Supongo que es solo para chequear
			if (method.getReturnType() instanceof RefLikeType) {				
				//el tercer
				Node n = new MethodNode(method, sensitivity, true);
				summary.addReturned(n);
				summary.add(n);
			}
			else
			{
				throw new RuntimeException("A fresh method should have RefLikeType return");
			}			
		}
		else
		{
			Node n = new MethodNode(method, sensitivity, true);
			summary.addReturned(n);
			summary.add(n);
		}
		
	
		//PARTE DE ANOTACION WRITE	

		List<ParamNode> writableParameterNodes = new ArrayList<ParamNode>();
		for(int i = 0; i < method.getParameterCount(); i++)
		{
			ParamNode p = new ParamNode(i, true);
			
			if(annotation.isWritableParameter(i))
				writableParameterNodes.add(p);
			
			
			//que onda los mutated?
			
			//si el nodo es parametro automaticamente lo agrega a paramNodes
			summary.add(p);
			
			//no se si hace falta crear los local edges
			
			
			
				
			
		}

		//si es WRITE entonces puede ser escrito transitivamente
		//esto se modela con un edge "?" a todos los nodos alcanzables desde afuera
		for(ParamNode wp :  writableParameterNodes)
		{
			for(Node p : summary.getParameterNodes())
			{

				//Edge e = new Edge(wp, "?", p, true);
				
				summary.relate(wp, "?", p, true);
				//summary.getEdgesOutOf(wp).add(e);
				
			}
			for(Node r : summary.getReturnedNodes())
			{
				//Edge e = new Edge(wp, "?", r, true);
				summary.relate(wp,"?",r, true);
			}
			
		}		
		return summary;		
	}
	
	
	
}
