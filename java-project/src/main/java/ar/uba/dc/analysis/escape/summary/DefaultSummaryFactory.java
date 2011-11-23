package ar.uba.dc.analysis.escape.summary;

import java.util.Iterator;

import soot.RefLikeType;
import soot.SootMethod;
import ar.uba.dc.analysis.common.SummaryFactory;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Cache;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;

public class DefaultSummaryFactory implements SummaryFactory<EscapeSummary> {

	protected int sensitivity;
	
	/**
	 * Conservative constructor for unanalysable calls.
	 * 
	 * <p>
	 * Note: this gives a valid summary for all native methods, including
	 * Thread.start().
	 * 
	 * @param withEffect
	 *            add a mutated abstract field for the global node to account
	 *            for side-effects in the environment (I/O, etc.).
	 */
	@SuppressWarnings("unchecked")
	public EscapeSummary conservativeGraph(SootMethod method, boolean withEffect) {
		EscapeSummary g = new EscapeSummary(method);
		Node glob = GlobalNode.node;
		g.add(glob);

		// parameters & this escape globally
		Iterator it = method.getParameterTypes().iterator();
		int i = 0;
		while (it.hasNext()) {
			if (it.next() instanceof RefLikeType) {
				Node n = Cache.cacheNode(new ParamNode(i));
				g.addGlobEscape(n);
				g.add(n);
			}
			i++;
		}

		// return value escapes globally
		if (method.getReturnType() instanceof RefLikeType)
			g.addReturned(glob);

		// add a side-effect on the environment
		// added by [AM]
		if (withEffect)
			g.addMutated(glob, "outside-world");
		
		return g;
	}

	/**
	 * Special constructor for methods returning a fresh object. (or if returns void or primitive).
	 */
	public EscapeSummary freshGraph(SootMethod method) {
		EscapeSummary g = new EscapeSummary(method);
		if (method.getReturnType() instanceof RefLikeType) {
			Node n = new MethodNode(method, sensitivity);
			g.addReturned(n);
			g.add(n);
		}
		
		return g;
	}

	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}
}
