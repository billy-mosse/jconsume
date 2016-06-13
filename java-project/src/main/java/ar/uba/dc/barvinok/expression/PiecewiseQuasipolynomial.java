package ar.uba.dc.barvinok.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

public class PiecewiseQuasipolynomial {

	protected Set<String> parameters = new TreeSet<String>(); // AKA, free variables
	protected List<QuasiPolynomial> pieces = new ArrayList<QuasiPolynomial>();

	
	@Override
	public int hashCode() {
		return parameters.hashCode() + pieces.hashCode(); 
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PiecewiseQuasipolynomial)) return false;
		PiecewiseQuasipolynomial p = (PiecewiseQuasipolynomial) o;
		return parameters.equals(p.parameters) && pieces.equals(p.pieces);
	}
	
	public String asString() {
		String ret = StringUtils.EMPTY;
		
		for (QuasiPolynomial polynomial : pieces) {
			ret += "; " + polynomial.asString();
		}
		
		if (!StringUtils.isEmpty(ret)) {
			ret = ret.substring(2);
		}
		
		return ret;
	}
	
	public String toString() {
		String params = StringUtils.join(parameters, ',');
		String polynomials = StringUtils.join(pieces, "; ");
		
		return "[" + params + "] -> {" + polynomials + "}";
	}
	
	public PiecewiseQuasipolynomial clone() {
		PiecewiseQuasipolynomial clone = new PiecewiseQuasipolynomial();
		clone.parameters = new TreeSet<String>(parameters);
		clone.pieces = new ArrayList<QuasiPolynomial>();
		
		for (QuasiPolynomial polynomial : pieces) {
			clone.pieces.add(polynomial.clone());
		}

		return clone;
	}	
	
	
	public void addParameter(String param) {
		parameters.add(param);
	}
	
	public void removeParameter(String param) {
		parameters.remove(param);
	}
	
	public void addAllParameters(Set<String> parameters) {
		this.parameters.addAll(parameters);
	}
	
	public void removeAllParameters(Set<String> parameters) {
		this.parameters.removeAll(parameters);
	}

	public void add(QuasiPolynomial polynomial) {
		pieces.add(polynomial);
	}

	public List<QuasiPolynomial> getPieces() {
		return pieces;
	}
	
	public Set<String> getParameters() {
		return new TreeSet<String>(parameters);
	}

	public void clearPieces() {
		pieces.clear();
	}
}
