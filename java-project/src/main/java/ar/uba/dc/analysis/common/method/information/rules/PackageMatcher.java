package ar.uba.dc.analysis.common.method.information.rules;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import soot.SootMethod;

public class PackageMatcher extends AbstractMatcher {

	private List<MethodMatcher> matchers;

	/*public boolean match(final SootMethod method) {
		return super.match(method.getDeclaringClass().getName()) 
				&& CollectionUtils.find(matchers, new Predicate() {
					
					public boolean evaluate(Object object) {
						MethodMatcher matcher = (MethodMatcher) object;
						return matcher.match(method);
					}
				}) != null;
	}	*/
	
	
	public boolean match(final String methodClass, final String methodName) {
		return super.match(methodClass) 
				&& CollectionUtils.find(matchers, new Predicate() {
					
					public boolean evaluate(Object object) {
						MethodMatcher matcher = (MethodMatcher) object;
						return matcher.match(methodClass, methodName);
					}
				}) != null;
	}	
}
