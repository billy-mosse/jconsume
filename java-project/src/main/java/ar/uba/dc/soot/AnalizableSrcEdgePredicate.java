package ar.uba.dc.soot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.EdgePredicate;
import ar.uba.dc.analysis.common.MethodInformationProvider;

public class AnalizableSrcEdgePredicate implements EdgePredicate {

	private static Log log = LogFactory.getLog(AnalizableSrcEdgePredicate.class);
	
	protected MethodInformationProvider provider;
	
	public boolean want(Edge e) {
		boolean srcAnalyzable = provider.isAnalyzable(e.getSrc().method());
		boolean tgtExcluded = provider.isExcluded(e.getTgt().method());
		boolean srcExcluded = provider.isExcluded(e.getSrc().method());
			
		if (log.isDebugEnabled()) {
			log.debug("Want: " + e.getSrc().method() + " (" + srcAnalyzable + ", " + srcExcluded + ") " + " -> " + e.getTgt().method() + " (" + tgtExcluded + ")" + "?");
		}
		
		return srcAnalyzable && !tgtExcluded && !srcExcluded;
	}

	public MethodInformationProvider getProvider() {
		return provider;
	}

	public void setProvider(MethodInformationProvider provider) {
		this.provider = provider;
	}
}
