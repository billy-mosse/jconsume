package ar.uba.dc.analysis.common.method.information.rules;


public abstract class AbstractMatcher {
	
	private static final String STARTS_WITH = "startsWith";

	private static final String EQUALS = "equals";

	private static final String ENDS_WITH = "endsWith";

	protected String name;
	
	protected String type;
	
	protected boolean match(String toMatch) {
		boolean result = false;
		
		if (STARTS_WITH.equals(type)) {
			result = toMatch.startsWith(name);
		} else if (EQUALS.equals(type)) {
			result = toMatch.equals(name);
		} else if (ENDS_WITH.equals(type)) {
			result = toMatch.endsWith(name);
		}
		
		return result;
	}
}
