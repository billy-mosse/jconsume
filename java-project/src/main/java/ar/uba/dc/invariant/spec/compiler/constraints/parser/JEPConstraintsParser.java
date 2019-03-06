package ar.uba.dc.invariant.spec.compiler.constraints.parser;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.NotImplementedException;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.Not;

import ar.uba.dc.invariant.spec.bean.SiteSpecification;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsParser;

public class JEPConstraintsParser implements ConstraintsParser {

	public static final String INCLUDE_INVARIANT_FUNCTION = "include";
	
	private JEP parser;
	
	private ConstraintsVisitor visitor; 
	
	public JEPConstraintsParser() {
		this.parser = new JEP();
		this.parser.setAllowUndeclared(true);
		this.parser.setImplicitMul(true);
		this.parser.addFunction(INCLUDE_INVARIANT_FUNCTION, new Not());
		
		this.visitor = new ConstraintsVisitor(INCLUDE_INVARIANT_FUNCTION);
	}
	
	public ConstraintsInfo parse(String constraints) {
		try {
			constraints = constraints.replaceAll("(?i) and ", " && ");
			constraints = constraints.replaceAll("(?i) or ", " || ");
			Node tree = parser.parse(constraints);
			ConstraintsInfo info = new ConstraintsInfo();
			
			tree.jjtAccept(visitor, info);
			
			return info;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void parse(SiteSpecification site, ConstraintsInfo info, Set<String> parameters, Set<DerivedVariable> new_parameters) {
		throw new NotImplementedException();
	}
}
