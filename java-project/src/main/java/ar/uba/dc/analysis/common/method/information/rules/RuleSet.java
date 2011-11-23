package ar.uba.dc.analysis.common.method.information.rules;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.map.LRUMap;

import soot.SootMethod;

public class RuleSet {

	public static final String PURE_TYPE = "pure";
	public static final String ALTER_TYPE = "alter";
	public static final String IMPURE_TYPE = "impure";
	public static final String EXCLUDE_TYPE = "exclude";
	
	protected int cacheSize;
	protected LRUMap cache;
	
	protected List<Rule> rules = new ArrayList<Rule>();

	/**
	 * BugFix porque no se invoca al constructor por defecto con XStream
	 * 
	 * http://xstream.codehaus.org/faq.html
	 * 
	 * @return
	 */
	private Object readResolve() {
		cache = new LRUMap(cacheSize);
	    return this;
	}
	
	public int size() {
		return rules.size();
	}

	/**
	 * Un poco mas rapido porque filtra por tipo, pero no usa cache
	 */
	public Rule findWithType(final SootMethod method, final String type) {
		return (Rule) CollectionUtils.find(rules, new Predicate() {
			public boolean evaluate(Object object) {
				Rule rule = (Rule) object;
				return (type == null || rule.getType().equals(type)) && rule.match(method);
			}
		});
	}
	
	/**
	 * Un poco mas rapido porque filtra por tipo, pero no usa cache
	 */
	public Rule findWithoutType(final SootMethod method, final String type) {
		return (Rule) CollectionUtils.find(rules, new Predicate() {
			public boolean evaluate(Object object) {
				Rule rule = (Rule) object;
				return (type == null || !rule.getType().equals(type)) && rule.match(method);
			}
		});
	}
	
	public Rule find(final SootMethod method) {
		if (cache == null) {
			cache = new LRUMap(cacheSize);
		}
		
		Rule rule = (Rule) cache.get(method);
		
		if (rule == null) {
			rule = doFind(method);
			if (rule != null) {
				cache.put(method, rule);
			} else {
				cache.put(method, Rule.NULL_CONST);
			}
		}
		
		if (Rule.NULL_CONST.equals(rule)) {
			rule = null;
		}
		
		return rule;
	}	
	
	protected Rule doFind(final SootMethod method) {
		return (Rule) CollectionUtils.find(rules, new Predicate() {
			
			public boolean evaluate(Object object) {
				Rule rule = (Rule) object;
				return rule.match(method);
			}
		});
	}
}
