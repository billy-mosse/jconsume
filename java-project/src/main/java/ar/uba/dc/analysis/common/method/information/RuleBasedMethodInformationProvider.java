package ar.uba.dc.analysis.common.method.information;

import soot.SootMethod;
import ar.uba.dc.analysis.common.MethodInformationProvider;
import ar.uba.dc.analysis.common.method.information.rules.Rule;
import ar.uba.dc.analysis.common.method.information.rules.RuleSet;
import ar.uba.dc.analysis.common.method.information.rules.RuleSetRepository;

public class RuleBasedMethodInformationProvider implements MethodInformationProvider {
	
	protected RuleSetRepository repository;
	
	protected RuleSet rules;

	public Boolean hasConservativaGraph(SootMethod method) {
		if (rules == null) {
			init();
		}

		return isMethodOfType(method, RuleSet.ALTER_TYPE);
	}

	public Boolean hasFreshGraph(SootMethod method) {
		if (rules == null) {
			init();
		}
		
		return isMethodOfType(method, RuleSet.PURE_TYPE);
	}

	public Boolean hasNonConservativaGraph(SootMethod method) {
		if (rules == null) {
			init();
		}
		
		return isMethodOfType(method, RuleSet.IMPURE_TYPE);
	}

	public Boolean isAnalyzable(SootMethod method) {
		if (rules == null) {
			init();
		}

		return findRule(method) == null;
	}
	
	public Rule findRule(SootMethod method) {
		return rules.find(method);
	}
	
	public Boolean isExcluded(SootMethod method) {
		if (rules == null) {
			init();
		}
		
		return rules.findWithType(method, RuleSet.EXCLUDE_TYPE) != null 
				&& rules.findWithoutType(method, RuleSet.EXCLUDE_TYPE) == null;
	}

	protected boolean isMethodOfType(SootMethod method, String type) {
		Boolean ret = false;
		
		Rule rule = findRule(method);
		if (rule != null) {
			ret = type.equals(rule.getType());
		}
		
		return ret;
	}

	protected void init() {
		rules = repository.get();
	}

	public void setRepository(RuleSetRepository repository) {
		this.repository = repository;
	}
}
