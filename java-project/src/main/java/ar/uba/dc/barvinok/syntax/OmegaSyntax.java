package ar.uba.dc.barvinok.syntax;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;

public class OmegaSyntax extends AbstractBarvinokSyntax {
	
	private static Log log = LogFactory.getLog(OmegaSyntax.class);
	
	public PiecewiseQuasipolynomial parsePiecewiseQuasipolynomial(String value) {
		log.debug("Parsing result: " + value);
		
		value = extractPartFromTuple(value);
		
		PiecewiseQuasipolynomial ret = new PiecewiseQuasipolynomial();
		int indexParameters = value.indexOf("->");
		int indexPieces = value.indexOf("{");
	
		// Parametros
		String params = StringUtils.EMPTY; 
		// Polinomios
		String pieces = StringUtils.EMPTY; 
		
		if (indexParameters >= 0 && indexParameters < indexPieces) { // Tengo parametros?
			params = stripPart(value.substring(0, indexParameters).trim());
			pieces = stripPart(value.substring(indexParameters + 2).trim());
		} else {
			pieces = stripPart(value.substring(indexPieces).trim());
		}
		
		if (StringUtils.isNotBlank(params)) {
			for (String p : params.split(",")) {
				if (StringUtils.isNotBlank(p)) {
					ret.addParameter(p.trim());
				}
			}
		}
		
		for (String piece : pieces.split(";")) {
			ret.add(parseQuasipolynomial(piece));
		}
		
		return ret;
	}

	protected QuasiPolynomial parseQuasipolynomial(String piece) {
		Set<String> variables = new TreeSet<String>();
		String[] aux = piece.split(":", 2);
		String vars = StringUtils.EMPTY;
		String constraints = StringUtils.EMPTY; 
		String quasy = aux[0].trim();
		
		if (aux.length >= 2) {
			constraints = aux[1].trim();
		}
		
		constraints = constraints.replaceAll("([^<>])=", "$1==");
		
		if (quasy.indexOf("->") >= 0) {
			String[] aux2 = quasy.split("->");
			vars = stripPart(aux2[0].trim());
			quasy = aux2[1].trim();
		}
		
		if (StringUtils.isNotBlank(vars)) {
			for (String v : vars.split(",")) {
				if (StringUtils.isNotBlank(v)) {
					variables.add(v.trim());
				}
			}
		}
		
		if (quasy.startsWith("(") && quasy.endsWith(")")) {
			quasy = stripPart(quasy);
		}
		
		quasy = quasy.trim();
		if (StringUtils.isBlank(quasy)) {
			quasy = "0";
		}
		
		if (NaN.equals(quasy)){
			quasy = INFINITE;
		}
		
		quasy = quasy.replaceAll("\\[(.+)/(.+)\\]", "(1/$2)*$1");
		
		return new QuasiPolynomial(quasy, constraints, variables);
	}

	public String toString(DomainSet value) {
		String ret = StringUtils.EMPTY;
		String params = StringUtils.join(value.getParameters(), ",");
		String vars = StringUtils.join(value.getVariables(), ",");
		
		if (params.length() > 0) {
			ret += "[" + params + "] -> ";
		}
		
		ret += "{";
		
		if (vars.length() > 0) {
			ret += "[" + vars + "]: ";
		} 
		
		ret += value.getConstraints() + "}";
	
		return ret;
	}
	
	@Override
	public String toString(PiecewiseQuasipolynomial value, boolean includeVariables) {
		String params = StringUtils.join(value.getParameters(), ',');
		String polynomials = StringUtils.EMPTY;
		
		for (QuasiPolynomial q : value.getPieces()) {
			polynomials += "; " + toString(q, includeVariables);
		}
		
		if (StringUtils.isNotEmpty(polynomials)) {
			polynomials = polynomials.substring(2);
		}
		
		return "[" + params + "] -> {" + polynomials + "}";
	}

	public String toString(PiecewiseQuasipolynomial value) {
		return toString(value, true);
	}

	protected String toString(QuasiPolynomial value, boolean includeVariables) {
		String vars = StringUtils.join(value.getVariables(), ',');
		String varsValue = StringUtils.EMPTY;
		
		if (includeVariables) {
			varsValue = "[" + vars + "] -> ";
		}
		
		String ret = varsValue + value.getPolynomial();
		
		if (!StringUtils.isEmpty(value.getConstraints())) {
			ret += ": " + value.getConstraints();
		}
		
		return ret;
	}
}
