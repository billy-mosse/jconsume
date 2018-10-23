package ar.uba.dc.barvinok.expression.operation.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;
import ar.uba.dc.barvinok.expression.operation.ExpressionMapper;
import ar.uba.dc.barvinok.expression.operation.KeyGenerator;
import ar.uba.dc.barvinok.syntax.ExistsInfo;
import ar.uba.dc.barvinok.syntax.SyntaxUtils;


public class JEPExpressionMapper implements ExpressionMapper {

	private JEP parser;
	
	private KeyGenerator keyGenerator;
	
	private ToStringParserVisitor toStringVisitor;
	
	public JEPExpressionMapper() {
		super();
		parser = new JEP();
		parser.setAllowUndeclared(true);
		parser.setAllowAssignment(false);
		parser.setImplicitMul(false);
		parser.addFunction("max", new MaxMathCommand());
		parser.addFunction("floor", new FloorMathCommand());
		toStringVisitor = new ToStringParserVisitor();
		toStringVisitor.addAliasFor("&&", "and");
		toStringVisitor.addAliasFor("||", "or");
	}
	
	public DomainSet simplify(final DomainSet invariant, final Map<String, String> mapping) {
		DomainSet result = invariant;
	
		Map<String, String> inverseMapping = new HashMap<String, String>(mapping.size());
		for (Entry<String, String> entry : mapping.entrySet()) {
			inverseMapping.put(entry.getValue(), entry.getKey());
		}
		
		Set<String> params = new TreeSet<String>();
		String actualKey = keyGenerator.getInitialKey(mapping);
		for (String target : invariant.getParameters()) {
			if (!inverseMapping.containsKey(target)) {
				actualKey = keyGenerator.getNextKey(actualKey);
				params.add(actualKey);
				mapping.put(actualKey, target);
				inverseMapping.put(target, actualKey);
			} else {
				params.add(inverseMapping.get(target));
			}			
		}
		
		Set<String> variables = new TreeSet<String>();
		for (String target : invariant.getVariables()) {
			if (!inverseMapping.containsKey(target)) {
				actualKey = keyGenerator.getNextKey(actualKey);
				variables.add(actualKey);
				mapping.put(actualKey, target);
				inverseMapping.put(target, actualKey);
			} else {
				variables.add(inverseMapping.get(target));
			}
		}
		Set<String> inductives = new TreeSet<String>();
		for (String target : invariant.getInductives()) {
			if (!inverseMapping.containsKey(target)) {
				actualKey = keyGenerator.getNextKey(actualKey);
				inductives.add(actualKey);
				mapping.put(actualKey, target);
				inverseMapping.put(target, actualKey);
			} else {
				inductives.add(inverseMapping.get(target));
			}
		}
		
		try {
			String constraintsForResult = parseExpression(invariant.getConstraints(), inverseMapping); 

			result = new DomainSet(constraintsForResult);
			result.addAllParameters(params);
			result.addAllVariables(variables);
			result.addAllInductives(inductives);
			
			return result;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public DomainSet expand(final DomainSet invariant, final Map<String, String> mapping) {
		try {
			String constraintsForResult = parseExpression(invariant.getConstraints(), mapping);
			
			DomainSet result = new DomainSet(constraintsForResult);
			
			for (String var : invariant.getVariables()) {
				result.addVariable(mapping.get(var));
			}
			
			for (String param : invariant.getParameters()) {
				result.addParameter(mapping.get(param));
			}
			
			return result;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public PiecewiseQuasipolynomial simplify(PiecewiseQuasipolynomial pwq, Map<String, String> mapping, String prefixForParameters) {
		parser.initSymTab();
		PiecewiseQuasipolynomial result = new PiecewiseQuasipolynomial();
		
		Map<String, String> inverseMapping = new HashMap<String, String>(mapping.size());
		for (Entry<String, String> entry : mapping.entrySet()) {
			inverseMapping.put(entry.getValue(), entry.getKey());
		}
		
		String actualKey = keyGenerator.getInitialKey(mapping);
		for (String target : pwq.getParameters()) {
			String param = inverseMapping.get(prefixForParameters + target);
			if (param == null) {
				actualKey = keyGenerator.getNextKey(actualKey);
				param = actualKey;
				mapping.put(actualKey, prefixForParameters + target);
			} else {
				inverseMapping.remove(prefixForParameters + target);
			}
			
			inverseMapping.put(target, param);
			result.addParameter(inverseMapping.get(target));
		}
		
		for (QuasiPolynomial polynomial : pwq.getPieces()) {
			result.add(simplify(polynomial, mapping, inverseMapping));
		}
		
		return result;
	}

	protected QuasiPolynomial simplify(QuasiPolynomial quasiPolynomial, Map<String, String> mapping, Map<String, String> inverseMapping) {
		Set<String> variables = new TreeSet<String>();
		String actualKey = keyGenerator.getInitialKey(mapping);
		for (String target : quasiPolynomial.getVariables()) {
			String var = inverseMapping.get(target);
			if (var == null) {
				actualKey = keyGenerator.getNextKey(actualKey);
				var = actualKey;
				mapping.put(actualKey, target);
				inverseMapping.put(target, actualKey);
			}
			variables.add(var);
		}
		
		try {
			String polynomial = parsePolynomial(quasiPolynomial.getPolynomial(), inverseMapping);
			String constraints = parseConstraints(quasiPolynomial.getConstraints(), inverseMapping);
			
			QuasiPolynomial result = new QuasiPolynomial(polynomial, constraints, variables);
			return result;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String parsePolynomial(final String polynomial, final Map<String, String> mapping) throws ParseException {
		String pol = parseExpression(polynomial, mapping);
		// OJO!!! FIXME: Le saco los chorchetes al pol
		return pol.replaceAll("\\[([.[^\\[]]+)/([.[^\\[]]+)\\]", "($1/$2)");
	}
	
	/**
	 * Soportamos la existencia de la clausula exists en dos formas:
	 * 
	 * <code>( exists def : constraints )</code> o bien 
	 * <code>exists ( def  : constraints )</code>
	 * 
	 * En ambos casos <code>def</code> es la lista de variables declaradas (pueden ser asignaciones) y <code>constraints</code>
	 * son las restricciones definidas.
	 */
	protected String parseConstraints(String constraints, final Map<String, String> mapping) throws ParseException {
		constraints = constraints.trim();
		
		if (SyntaxUtils.containsExists(constraints)) {
			
			ExistsInfo existsInfo = SyntaxUtils.retriveExistsInformation(constraints); 
			
			// Procesamos las definiciones
			List<String> processedDefinitions = new LinkedList<String>();
			for (String def : existsInfo.getDefinitions().split(",")) {
				String finalDef = def.trim();
				
				// Si es una asignacion, la procesamos. 
				if (def.indexOf("==") >= 0) {
					String[] parts = def.split("==", 2);
					String variable = parts[0].trim();
					String assigment = parseExpression(parts[1].trim(), mapping);
					finalDef = variable + " == " + assigment.trim();
				}
				
				processedDefinitions.add(finalDef);
			}
			
			// Procesamos las constraints normalmente (necesitamos que no se transformen las variables provenientes del exists)
			String existsConstratins = parseExpression(existsInfo.getConstratins(), mapping);
			
			return SyntaxUtils.buildExists(StringUtils.join(processedDefinitions, ", "), existsConstratins);
		} else {
			return parseExpression(constraints, mapping);
		}
	}
	
	protected String parseExpression(final String target, final Map<String, String> mapping) throws ParseException {
		parser.initSymTab();
		
		String expr = target;
		expr = expr.replaceAll("(?i) and ", " && ");
		expr = expr.replaceAll("(?i) or ", " || ");
		// Diego: La saco por ahora 
		// expr = expr.replaceAll("(\\d+)([a-zA-Z_]\\w*)", "$1*$2"); // Cambiamos la multiplicacion implicita por explicita. Asumimos que solo se da el caso numero*variable.
		// expr = expr.replaceAll("(\\d+)([a-zA-Z_]\\w*)", "[$1/$2]"); // Convertimos la division en div entera 
			String constraintsForResult = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(expr)) {
			Node parsedConstratins = parser.parse(expr);
			ExpressionParsingResult parsingResult = (ExpressionParsingResult) parsedConstratins.jjtAccept(toStringVisitor, mapping);
			constraintsForResult = parsingResult.getString();
		}
		if(expr.indexOf("/")!=-1)
			System.out.print("");
		return constraintsForResult;
	}

	public PiecewiseQuasipolynomial expand(PiecewiseQuasipolynomial target, Map<String, String> mapping) {
		PiecewiseQuasipolynomial result = new PiecewiseQuasipolynomial();
		
		for (String param : target.getParameters()) {
			result.addParameter(mapping.get(param));
		}
		
		for (QuasiPolynomial qp : target.getPieces()) {
			result.add(expand(qp, mapping));
		}
		
		return result;
	}

	protected QuasiPolynomial expand(QuasiPolynomial quasiPolynomial, Map<String, String> mapping) {
		Set<String> variables = new TreeSet<String>();
		
		for (String variable : quasiPolynomial.getVariables()) {
			variables.add(mapping.get(variable));
		}
		
		try {
			String polynomial = parsePolynomial(quasiPolynomial.getPolynomial(), mapping);
			String constraints = parseConstraints(quasiPolynomial.getConstraints(), mapping);
			
			return new QuasiPolynomial(polynomial, constraints, variables);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ToStringParserVisitor getToStringVisitor() {
		return toStringVisitor;
	}

	public void setToStringVisitor(ToStringParserVisitor toStringVisitor) {
		this.toStringVisitor = toStringVisitor;
	}
	
	public KeyGenerator getKeyGenerator() {
		return keyGenerator;
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}
}
