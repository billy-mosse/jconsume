package ar.uba.dc.analysis.common.method.information.rules;

import java.io.Reader;

import com.thoughtworks.xstream.XStream;

public class RuleSetReader {

	protected XStream xstream;
	
	public RuleSetReader() { 
		xstream = new XStream();
		
		xstream.alias("rules", RuleSet.class);
		xstream.useAttributeFor(RuleSet.class, "cacheSize");
		xstream.aliasAttribute("cache-size", "cacheSize");
		
		xstream.alias("rule", Rule.class);
		xstream.useAttributeFor(Rule.class, "name");
		xstream.useAttributeFor(Rule.class, "type");
		
		xstream.alias("resources", Resources.class);
		xstream.useAttributeFor(Resources.class, "syntax");
		
		xstream.alias("package", PackageMatcher.class);
		xstream.useAttributeFor(AbstractMatcher.class, "name");
		xstream.useAttributeFor(AbstractMatcher.class, "type");
		
		xstream.alias("method", MethodMatcher.class);
		xstream.useAttributeFor(AbstractMatcher.class, "name");
		xstream.useAttributeFor(AbstractMatcher.class, "type");
		
		xstream.addImplicitCollection(RuleSet.class, "rules");
		xstream.addImplicitCollection(Rule.class, "matchers");
		xstream.addImplicitCollection(PackageMatcher.class, "matchers");
	}
	
	public RuleSet read(Reader reader) {
		return (RuleSet) xstream.fromXML(reader);
	}
}
