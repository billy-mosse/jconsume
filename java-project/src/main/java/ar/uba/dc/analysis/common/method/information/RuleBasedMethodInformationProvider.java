package ar.uba.dc.analysis.common.method.information;

import soot.ArrayType;
import soot.RefLikeType;
import soot.RefType;
import soot.SootClass;
import soot.SootMethod;
import soot.Type;
import soot.VoidType;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.MethodInformationProvider;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.method.information.rules.Rule;
import ar.uba.dc.analysis.common.method.information.rules.RuleSet;
import ar.uba.dc.analysis.common.method.information.rules.RuleSetRepository;
import ar.uba.dc.analysis.memory.impl.summary.repository.PaperDefaultSummaryRepository;

public class RuleBasedMethodInformationProvider implements MethodInformationProvider {
	
	protected RuleSetRepository repository;
	
	protected RuleSet rules;

	public Boolean hasConservativaGraph(SootMethod method) {
		return commonHasConservativaGraph(method.getDeclaringClass().getName());
	}

	public Boolean commonHasConservativaGraph(String methodClass) {
		if (rules == null) {
			init();
		}

		return isMethodOfType(methodClass, RuleSet.ALTER_TYPE);
	}	

	public Boolean hasFreshGraph(SootMethod method) {
		return commonHasFreshGraph(method.getDeclaringClass().getName());
	}
	
	
	public Boolean commonHasFreshGraph(String methodClass) {
		if (rules == null) {
			init();
		}
		
		return isMethodOfType(methodClass, RuleSet.PURE_TYPE);
	}
	

	public Boolean hasNonConservativaGraph(SootMethod method) {
		return commonHasNonConservativaGraph(method.getDeclaringClass().getName());
	}
	
	public Boolean commonHasNonConservativaGraph(String methodClass) {
		if (rules == null) {
			init();
		}
		
		return isMethodOfType(methodClass, RuleSet.IMPURE_TYPE);
	}
	
	

	public Boolean isAnalyzable(SootMethod method) {
		return commonIsAnalyzable(method.getDeclaringClass().getName());
	}
	
	public Boolean commonIsAnalyzable(String methodClass)
	{
		if (rules == null) {
			init();
		}

		return commonFindRule(methodClass) == null;
	}
	
	
	public Rule commonFindRule(String methodClass) {
		return rules.find(methodClass);
	}
	
	public Rule findRule(SootMethod method) {
		return commonFindRule(method.getDeclaringClass().getName());
	}
	
	public Rule IR_findRule(IntermediateRepresentationMethod ir_method) {
		return commonFindRule(ir_method.getDeclaringClass());
	}
	
	public Boolean isExcluded(SootMethod method) {
		return commonIsExcluded(method.getDeclaringClass().getName());
	}
	
	public Boolean commonIsExcluded(String methodClass) {
		if (rules == null) {
			init();
		}
		
		return rules.findWithType(methodClass, RuleSet.EXCLUDE_TYPE) != null 
				&& rules.findWithoutType(methodClass, RuleSet.EXCLUDE_TYPE) == null;
	}
	

	protected boolean isMethodOfType(String methodClass, String type) {
		Boolean ret = false;
		
		Rule rule = commonFindRule(methodClass);
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

	
	//TODO: hacerlo bien
	//en algun momento voy a volar lo que tenga que ver con SootMethods
	//pero por ahora no quiero que las cosas se rompan
	
	
	
	
	public SootMethod dummySootMethod = new SootMethod("dummy", Arrays.asList(new Type[] {ArrayType.v(RefType.v("java.lang.String"), 1)}),
		    VoidType.v(), Modifier.PUBLIC | Modifier.STATIC);
	
	@Override
	public Boolean isAnalyzable(IntermediateRepresentationMethod ir_method) {
		
		return commonIsAnalyzable(ir_method.getDeclaringClass());
	}
	
	private static Log log = LogFactory.getLog(RuleBasedMethodInformationProvider.class);


	@Override
	public Boolean hasFreshGraph(IntermediateRepresentationMethod ir_method) {
		return commonHasFreshGraph(ir_method.getDeclaringClass());
	}

	@Override
	public Boolean hasConservativaGraph(IntermediateRepresentationMethod ir_method) {
		

		return commonHasConservativaGraph(ir_method.getDeclaringClass());	}

	@Override
	public Boolean hasNonConservativaGraph(IntermediateRepresentationMethod ir_method) {

		return commonHasNonConservativaGraph(ir_method.getDeclaringClass());
	}

	@Override
	public Boolean isExcluded(IntermediateRepresentationMethod ir_method) {

		return commonIsExcluded(ir_method.getDeclaringClass());
	}
}
