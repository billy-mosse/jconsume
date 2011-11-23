package ar.uba.dc.analysis.escape.summary.simplification;

import java.util.Set;

import soot.SootClass;
import soot.SootMethod;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.Box;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

/**
 * Agrupa teniendo en cuenta la implementacion
 * 
 * @author testis
 */
public class GroupByImplementationCriteria implements GroupByCriteria {

	@Override
	public SootMethod getBelongsTo(Box<EscapeSummary> box, Set<Node> targets) {
		// Como agrupo por contexto e implementacion, todos los nodos deben pertenecer al mismo metodo (sino seria otra implementacion)
		return targets.iterator().next().belongsTo();
	}

	@Override
	public GroupId getGroup(Edge edge) {
		Stmt call = null;
		SootClass implementation = null;
		
		if (edge.getTarget().getContext() != null && !edge.getTarget().getContext().empty()) {
			call = edge.getTarget().getContext().peek().getId();
			implementation = getImplementation(edge.getTarget());
		}
		
		return new ImplementationGroupId(edge.getSource(), edge.getField(), edge.isInside(), edge.getTarget().isInside(), edge.getTarget().getContext(), call, implementation);
	}
	
	protected SootClass getImplementation(Node node) {
		CircularStack<StatementId> ctx = node.getContext();
		SootClass impl = null;
		
		// El stack tiene como primer elemento al metodo invocado por el metodo al cual pertenece el summary 
		// que estamos procesando. Si el nodo procede de otro call (el metodo invocado invoco a otro call), la implementacion
		// podemos obtenerla de dicho call. Si no hay mas calls, eso quiere decir que la impl la obtenemos de la clase a la que
		// pertenece el nodo (la que creo el nodo).
		if (ctx.size() > 1) {
			ctx.pop();
			impl = ctx.peek().getMethodOfId().getDeclaringClass();
		} else {
			impl = node.belongsTo().getDeclaringClass();
		}
		
		return impl;
	}
}
