package ar.uba.dc.analysis.common.method.information.rules;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import soot.SootMethod;

public class Rule {

	public static Object NULL_CONST = new Rule();
	
	private String name;
	
	private String type;
	
	private Resources resources;
	
	private List<PackageMatcher> matchers;

	@Override
	public String toString() {
		return StringUtils.defaultString(name) + " (" + StringUtils.defaultString(type) + ")";
	}

	public String getType() {
		return type;
	}

	public boolean match(final String methodClass, final String methodName) {
		return CollectionUtils.find(matchers, new Predicate() {
			
			public boolean evaluate(Object object) {
				PackageMatcher matcher = (PackageMatcher) object;
				

				
				return matcher.match(methodClass, methodName);
				
			}
		}) != null;
	}
	
	public Resources getResources() {
		return resources;
	}
}
