package ar.uba.dc.invariant.spec.compiler.constraints.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nfunk.jep.JEP;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.Not;

import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsParser;
import ar.uba.dc.invariant.spec.compiler.constraints.ReferenceResolver;

public class RegexpConstraintsParser implements ConstraintsParser {

	public static final String INCLUDE_INVARIANT_FUNCTION = "_at";
	
	private JEP parser;
	
	private ReferenceResolver resolver;
	
	public RegexpConstraintsParser() {
		this.parser = new JEP();
		this.parser.setAllowUndeclared(true);
		//this.parser.setImplicitMul(true);
		this.parser.addFunction(INCLUDE_INVARIANT_FUNCTION, new Not());
	}
	
	public ConstraintsInfo parse(String constraints) {
		ConstraintsInfo info = new ConstraintsInfo();
		
		String constraintToParse = new String(constraints);
		Pattern pattern = Pattern.compile("\\B@(\\S+)\\b");
		Matcher match = pattern.matcher(constraints);
		
		while (match.find()) {
			String ref = match.group(1);
			info.addReference(ref);
			constraintToParse = resolver.resolve(constraintToParse, ref, INCLUDE_INVARIANT_FUNCTION + "(\"" + ref + "\")");
		}
		
		try {
			constraintToParse = constraintToParse.replaceAll("(?i) and ", " && ");
			constraintToParse = constraintToParse.replaceAll("(?i) or ", " || ");
			parser.initSymTab();
			parser.parse(constraintToParse);
			
			for (Object var : parser.getSymbolTable().keySet()) {
				info.addVariable(var.toString());
			}
		} catch (ParseException e) {
			throw new RuntimeException("Problemas al parsear la constraint [" + constraintToParse + "]: " + e.getMessage(), e);
		}
				
		return info;
	}

	public void setResolver(ReferenceResolver resolver) {
		this.resolver = resolver;
	}
}
