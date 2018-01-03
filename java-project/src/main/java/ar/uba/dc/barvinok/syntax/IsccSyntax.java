package ar.uba.dc.barvinok.syntax;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.barvinok.BarvinokSyntax;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;

public class IsccSyntax extends AbstractBarvinokSyntax {

	private static Log log = LogFactory.getLog(IsccSyntax.class);
	
	/**
	 * Esta sintaxis define que un polinomio tiene la siguiente forma
	 * 
	 * {quasypolinomio; quasypolinomio; ....; quasypolinomio }
	 * 
	 * Donde un quasipolinomio esta definido como
	 * 
	 * [[params] -> [variables]] -> polinomio : dominio
	 * 
	 * o puede no tener variables, en cuyo caso se escribe
	 * 
	 * [params] -> polinomio : dominio
	 * 
	 * o puede no tener ni parametros ni variables
	 * 
	 * polinomio : dominio
	 * 
	 * En todos los casos el dominio es opcional (no figura el :).
	 * 
	 */
	public PiecewiseQuasipolynomial parsePiecewiseQuasipolynomial(String value) {
		log.debug("Parsing result: " + value);
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		
		value = extractPartFromTuple(value.trim());
		
		// El resultado esta siempre encerrado entre {}"
		value = stripPart(value.trim());
		
		// Si esta vacio, devolvemos un 0
		if (StringUtils.isBlank(value)) {
			p.add(new QuasiPolynomial("0"));
			return p;
		}
		
		String[] pieces = value.split(";");
		
		// Asumimos que todas las partes tienen los mismos parametros (esto es porque antes si era asi).
		// Mantenemos compatibilidad con Omega. Ademas, en nuestro programa nunca se da el caso de que 
		// levantemos cosas con distintos parametros
		p.addAllParameters(getPiecewiseQuasipolynomialParameters(pieces[0]));
		
		for (String piece : pieces) {
			String constraints = StringUtils.EMPTY;
			String polynomial = StringUtils.EMPTY;
			Set<String> variables = new TreeSet<String>();
		
			if (StringUtils.isBlank(piece)) {
				polynomial = "0";	
			} else {
				int constraintsIndex = piece.indexOf(":");
				int declarationsEnd = piece.lastIndexOf("->");
				int polynomialStart = declarationsEnd + 2;
				int polynomialEnd = constraintsIndex;
				
				// Levantamos las variables
				// ------------------------
				if (declarationsEnd >= 0 && StringUtils.countMatches(piece, "->") > 1) {
					// La declaracion de variables y parametros esta encerrada en [].
					String declaration = stripPart(piece.substring(0, declarationsEnd).trim());
					// la declaracion es [params] -> [variables].
					String[] declarationParts = declaration.split("->");
					String vars = stripPart(declarationParts[1].trim());
					if (StringUtils.isNotBlank(vars)) {
						for (String var : vars.split(",")) {
							variables.add(var.trim());
						}
					}
				}
				
				// Levantamos el polinomio
				// -----------------------
				
				// Si no hay parametros, arranca desde el principio el polinomio 
				if (polynomialStart < 2) {
					polynomialStart = 0;
				}
				
				// Si no hay constraints, el polynomio llega hasta el final
				if (polynomialEnd < 0) {
					polynomialEnd = piece.length();
				}
			
				polynomial = piece.substring(polynomialStart, polynomialEnd).trim();
				if (polynomial.startsWith("(") && polynomial.endsWith(")")) {
					polynomial = stripPart(polynomial);
				}
			
				// Levantamos las constraints
				// --------------------------
				
				if (constraintsIndex >= 0) {
					constraints = piece.substring(constraintsIndex + 1).trim();
					constraints = constraints.replaceAll("([^<>])=", "$1==");
				}
			}
			
			if (BarvinokSyntax.NaN.equals(polynomial)) {
				polynomial = BarvinokSyntax.INFINITE;
			}
			
			// Convertimos [a/b] en (1/b*a)
			polynomial = polynomial.replaceAll("\\[([.[^\\[]]+)/([.[^\\[]]+)\\]", "((1/$2)*$1)"); //polynomial.replaceAll("\\[([a-zA-Z_0-9]+)/([a-zA-Z_0-9]+)\\]", "((1/$2)*$1");
			
			p.add(new QuasiPolynomial(polynomial, constraints, variables));
		}
			
		return p;
	}

	protected Set<String> getPiecewiseQuasipolynomialParameters(String quasiPolynomial) {
		Set<String> ret = new TreeSet<String>();
		
		String params = StringUtils.EMPTY;
		// Hay variables y parametros?
		int definitionEnd = quasiPolynomial.lastIndexOf("->");
		
		if (definitionEnd >= 0) {
			String definitionPart = stripPart(quasiPolynomial.substring(0, definitionEnd).trim());
			if (StringUtils.countMatches(definitionPart, "->") >= 1) {
				params = definitionPart.split("->")[0];
				params = stripPart(params.trim());
			} else {
				params = definitionPart;
			}
		}
		
		if (StringUtils.isNotBlank(params)) {
			for (String param : params.split(",")) {
				ret.add(param.trim());
			}
		}
		
		return ret;
	}

	public String toString(DomainSet value) {
		Set<String> varsToElim = value.variablesToExclude(); //variables - inductivas
		Set<String> varsToInclude = value.getVariables();
		varsToInclude.removeAll(varsToElim);
		String params = StringUtils.join(value.getParameters(), ',');
		String vars = StringUtils.join(varsToInclude, ',');
		
		String ret = "{ ";
		
		if (StringUtils.isNotEmpty(params) || StringUtils.isNotEmpty(vars)) {
			ret += "[" + params + "] -> [" + vars + "]";
		}
		
		
		if(!varsToElim.isEmpty())  {
			String stringElim = StringUtils.join(varsToElim, ',');
			ret += ": exists "+stringElim+" ";
		}
		
		if (StringUtils.isNotEmpty(value.getConstraints())) {
			ret += " : " + value.getConstraints();
		}
		
		ret += " }";
		
		return ret;
	}

	
	
	@Override
	public String toString(PiecewiseQuasipolynomial value, boolean includeVariables) {
		//String params = StringUtils.join(value.getParameters(), ',');
		String ret = "{ ";
		String polynomials = StringUtils.EMPTY; 
		
		for (QuasiPolynomial q : value.getPieces()) {
			polynomials += "; " + toString(q, value.getParameters(), includeVariables);
		}
		
		if (StringUtils.isNotEmpty(polynomials)) {
			polynomials = polynomials.substring(2);
		}
		
		ret += polynomials + " }";
		
		return ret;
	}

	public String toString(PiecewiseQuasipolynomial value) {
		return toString(value, true);
	}

	protected String toString(QuasiPolynomial value, Set<String> parameterList, boolean includeVariables) {
		
		String params = StringUtils.join(parameterList, ',');
		//BILLY DEBUG: estoy borrando b sin querer
		Set<String> varsToElim = value.variablesToExclude();
		
		//no quiero eliminar existencialmente los parametros
		varsToElim.removeAll(parameterList);
		
		Set<String> varsToInclude = value.getVariables();
		varsToInclude.removeAll(varsToElim);
		String vars = StringUtils.join(varsToInclude, ',');
		
		String ret = StringUtils.EMPTY;
		
		if (StringUtils.isNotBlank(params) || StringUtils.isNotBlank(vars)) {
			if (includeVariables) {
				ret += "[[" + params + "] -> [" + vars + "]] -> ";
			} else {
				ret += "[" + params + "] -> ";
			}
		}
		
		ret += value.getPolynomial();
		
		if(!varsToElim.isEmpty())  {
			String stringElim = StringUtils.join(varsToElim, ',');
			ret += ": exists "+stringElim+" ";
		}
		
		if (StringUtils.isNotBlank(value.getConstraints())) {
			ret += " : " + value.getConstraints();
		}
		
		return ret;
	}
}
