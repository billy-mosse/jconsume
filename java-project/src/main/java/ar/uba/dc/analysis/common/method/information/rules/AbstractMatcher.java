package ar.uba.dc.analysis.common.method.information.rules;


public abstract class AbstractMatcher {
	
	private static final String STARTS_WITH = "startsWith";

	private static final String EQUALS = "equals";

	private static final String ENDS_WITH = "endsWith";

	protected String name;
	
	protected String type;
	
	protected boolean match(String method) {
		boolean result = false;
		
		if (STARTS_WITH.equals(type)) {
			result = method.startsWith(name);
		} else if (EQUALS.equals(type)) {
			result = method.equals(name);
		} else if (ENDS_WITH.equals(type)) {
			result = method.endsWith(name);
		}
		
		return result;
	}
}
