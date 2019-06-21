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
import ar.uba.dc.analysis.escape.graph.node.ArtificialNode;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.escape.graph.node.ThisNode;

public class EscapeAnnotationsSummaryFactory {
	
	private static int sensitivity = -1;
	private static Log log = LogFactory.getLog(EscapeAnnotationsSummaryFactory.class);
	
	
	private boolean isUnreachable(ParamNode p, EscapeAnnotation annotation)
	{
		return annotation.getUnreachables().contains(p.getIndex());
	}
	public EscapeSummary buildPTG(SootMethod method, EscapeAnnotation annotation)
	{
		//TODO: que pasa si this es tambien parametro?
		
		EscapeSummary summary = new EscapeSummary(method);
		summary.setArtificial(true);
		//boolean isOmega = var.contains("Fresh");
		
		
		
		//aca es donde deberiamos poner por donde escapan los objetos.
		Node n = null;
		if(!annotation.getType().equals("nonFresh")){				
			if (method.getReturnType() instanceof RefLikeType) {				
				//el tercer
				//es un omega node
				n = new MethodNode(method, sensitivity, true);
				summary.addReturned(n);
				summary.add(n);
			}
		}
		
		
	
		//PARTE DE ANOTACION WRITE	

		List<ParamNode> writableParameterNodes = new ArrayList<ParamNode>();
		
		
		//this node es el 0
		ThisNode t = new ThisNode();
		if(!method.isStatic())
		{
			if(annotation.thisIsWritable())
				writableParameterNodes.add(t);
			
			if(annotation.getType().equals("fresh"))
				summary.relate(n, "?", t, true);
	
				summary.add(t);
		}
		
		for(int i = 0; i < method.getParameterCount(); i++)
		{
			//todos deberian ser omega nodes porque no tenemos control sobre ellos?
			//igual no afecta si no les llegan edges.
			ParamNode p = new ParamNode(i, true);
			
			if(i==0)
			{
				ArtificialNode s = new ArtificialNode(i, method.getName());
				summary.add(s);
				summary.relate(p, "?", s, true);
			}
			
			
			if(annotation.isWritableParameter(i))
				writableParameterNodes.add(p);
			
			//que onda los mutated?
			
			//si el nodo es parametro automaticamente lo agrega a paramNodes
			summary.add(p);
			
			//no se si hace falta crear los local edges
			if(annotation.getType().equals("fresh"))
			{
				//si es fresh el nodo, que es un inside node,
				//puede acceder a todos los parametros.
				//esto hace que una variable muerta de fuera del metodo pueda ser atrapada por el nodo que se retorna
				if(n != null && !isUnreachable(p, annotation))
					summary.relate(n, "?", p, true);
				
				
			}
			else
			{
				if(annotation.getType().equals("nonFresh"))
				{
					//el return puede ser cualquier parametro
					//TODO: chequear tipos?
					if(method.getReturnType() instanceof RefLikeType)
						summary.addReturned(p);
				}
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
				ParamNode pp = (ParamNode) p;
				if(!isUnreachable(pp, annotation))
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
		
		summary.setEscape(annotation.getEscape());
		summary.setMaxLive(annotation.getMaxLive());
		
		if(annotation.getClass().equals(DefaultEscapeAnnotation.class))
		{
			summary.setMethodRequirements(((DefaultEscapeAnnotation)annotation).getMethodRequirements());
		}
		
		return summary;		
	}
}
