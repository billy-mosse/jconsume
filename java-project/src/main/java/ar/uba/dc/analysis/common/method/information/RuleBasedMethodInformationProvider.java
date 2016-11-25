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
import ar.uba.dc.soot.SootUtils;

public class RuleBasedMethodInformationProvider implements MethodInformationProvider {
	
	protected RuleSetRepository repository;
	
	protected RuleSet rules;

	public Boolean hasConservativaGraph(SootMethod method) {
		return commonHasConservativaGraph(method.getDeclaringClass().toString(), method.getName());
	}

	public Boolean commonHasConservativaGraph(String methodClass, String methodName) {
		if (rules == null) {
			init();
		}

		return isMethodOfType(methodClass, methodName, RuleSet.ALTER_TYPE);
	}	

	public Boolean hasFreshGraph(SootMethod method) {
		return commonHasFreshGraph(method.getDeclaringClass().toString(), method.getName());
	}
	
	
	public Boolean commonHasFreshGraph(String methodClass, String methodName) {
		if (rules == null) {
			init();
		}
		
		return isMethodOfType(methodClass, methodName, RuleSet.PURE_TYPE);
	}
	

	public Boolean hasNonConservativaGraph(SootMethod method) {
		return commonHasNonConservativaGraph(method.getDeclaringClass().toString(), method.getName());
	}
	
	public Boolean commonHasNonConservativaGraph(String methodClass, String methodName) {
		if (rules == null) {
			init();
		}
		
		return isMethodOfType(methodClass, methodName, RuleSet.IMPURE_TYPE);
	}
	
	

	public Boolean isAnalyzable(SootMethod method) {
		return commonIsAnalyzable(method.getDeclaringClass().toString(), method.getName());
	}
	
	public Boolean commonIsAnalyzable(String methodClass, String methodName)
	{
		if (rules == null) {
			init();
		}

		return commonFindRule(methodClass, methodName) == null;
	}
	
	
	public Rule commonFindRule(String methodClass, String methodName) {
		return rules.find(methodClass, methodName);
	}
	
	public Rule findRule(SootMethod method) {
		return commonFindRule(method.getDeclaringClass().toString(), method.getName());
	}
	
	public Rule IR_findRule(IntermediateRepresentationMethod ir_method) {
		return commonFindRule(ir_method.getDeclaringClass().toString(), ir_method.getName());
	}
	
	public Boolean isExcluded(SootMethod method) {
		return commonIsExcluded(method.getDeclaringClass().toString(), method.getName());
	}
	
	public Boolean commonIsExcluded(String methodClass, String methodName) {
		if (rules == null) {
			init();
		}
		
		return rules.findWithType(methodClass, methodName, RuleSet.EXCLUDE_TYPE) != null 
				&& rules.findWithoutType(methodClass, methodName, RuleSet.EXCLUDE_TYPE) == null;
	}
	

	protected boolean isMethodOfType(String methodClass, String methodName, String type) {
		Boolean ret = false;
		
		Rule rule = commonFindRule(methodClass, methodName);
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
		
		return commonIsAnalyzable(ir_method.getDeclaringClass(), ir_method.getName());
	}
	
	private static Log log = LogFactory.getLog(RuleBasedMethodInformationProvider.class);


	@Override
	public Boolean hasFreshGraph(IntermediateRepresentationMethod ir_method) {
		return commonHasFreshGraph(ir_method.getDeclaringClass(), ir_method.getName());
	}

	@Override
	public Boolean hasConservativaGraph(IntermediateRepresentationMethod ir_method) {
		

		return commonHasConservativaGraph(ir_method.getDeclaringClass(), ir_method.getName());	}

	@Override
	public Boolean hasNonConservativeGraph(IntermediateRepresentationMethod ir_method) {

		return commonHasNonConservativaGraph(ir_method.getDeclaringClass(), ir_method.getName());
	}

	@Override
	public Boolean isExcluded(IntermediateRepresentationMethod ir_method) {

		return commonIsExcluded(ir_method.getDeclaringClass(), ir_method.getName());
	}
}
