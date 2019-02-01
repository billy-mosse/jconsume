package ar.uba.dc.analysis.escape.summary;

import soot.RefLikeType;
import soot.SootMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;
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

		for(int i = 0; i < method.getParameterCount(); i++)
		{
			boolean isOmega= annotation.isWritableParameter(i);	
			ParamNode p = new ParamNode(i, isOmega);
			
			//que onda los mutated?
			
			//si el nodo es parametro automaticamente lo agrega a paramNodes
			summary.add(p);

			//no se si hace falta crear los local edges
		}

		
		return summary;
		
	}
}
