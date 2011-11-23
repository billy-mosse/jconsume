package ar.uba.dc.barvinok;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;

public class BarvinokUtils {
	
	public static Boolean isConstant(PiecewiseQuasipolynomial piecewiseQuasiPolynomial) {
		return toConstant(piecewiseQuasiPolynomial) != null;
	}
	
	public static Long toConstant(PiecewiseQuasipolynomial piecewiseQuasiPolynomial) {
		Long constant = null;
		
		if (piecewiseQuasiPolynomial.getPieces().size() == 1) {
			try {
				constant = Long.parseLong(piecewiseQuasiPolynomial.getPieces().get(0).getPolynomial(), 10);
			} catch (NumberFormatException e) {}
		}
		
		return constant;
	}
	
	public static Long toConstant(DomainSet invariant) {
		Long constant = null; 
		
		try {
			constant = Long.parseLong(invariant.getConstraints(), 10);
		} catch (NumberFormatException e) {}
		
		return constant;
	}

	public static Boolean isPolynomial(PiecewiseQuasipolynomial piecewiseQuasiPolynomial) {
		return !isConstant(piecewiseQuasiPolynomial);
	}
	
	public static Boolean hasInfinitePiece(PiecewiseQuasipolynomial piecewiseQuasiPolynomial) {
		for (QuasiPolynomial q : piecewiseQuasiPolynomial.getPieces()) {
			if (BarvinokSyntax.INFINITE.equals(q.getPolynomial().trim())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isFold(QuasiPolynomial q) {
		return q.getPolynomial().contains(BarvinokSyntax.MAX_CANDIDATES + "(");
	}
	
	public static Boolean hasFoldPiece(PiecewiseQuasipolynomial piecewiseQuasiPolynomial) {
		for (QuasiPolynomial q : piecewiseQuasiPolynomial.getPieces()) {
			if (isFold(q)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static Boolean hasFoldPiece(PiecewiseQuasipolynomial... piecewiseQuasiPolynomials) {
		for (PiecewiseQuasipolynomial p : piecewiseQuasiPolynomials) {
			if (hasFoldPiece(p)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void surroundWithMax(PiecewiseQuasipolynomial polynomial) {
		String candidatesPrefix = BarvinokSyntax.MAX_CANDIDATES + "(";
		for (QuasiPolynomial quasipolynomial : polynomial.getPieces()) {
			if (!quasipolynomial.getPolynomial().startsWith(candidatesPrefix)) {
				quasipolynomial.setPolynomial(candidatesPrefix + quasipolynomial.getPolynomial() + ")");
			}
		}
	}
	
	public static void cleanMaxIfPossible(PiecewiseQuasipolynomial polynomial) {
		String candidatesPrefix = BarvinokSyntax.MAX_CANDIDATES + "(";
		for (QuasiPolynomial quasipolynomial : polynomial.getPieces()) {

			if (quasipolynomial.getPolynomial().startsWith(candidatesPrefix)) {
				String candidates = quasipolynomial.getPolynomial().substring(candidatesPrefix.length(), quasipolynomial.getPolynomial().lastIndexOf(")"));
				candidates = candidates.trim();
				
				List<String> newCandidates = new LinkedList<String>();
				for (String candidate : candidates.split(",")) {
					candidate = candidate.trim();
					if (candidate.startsWith("(") && candidate.endsWith(")")) {
						candidate = candidate.substring(1, candidate.length() - 1).trim();
					}
					newCandidates.add(candidate);
				}
				
				String finalCandidates = StringUtils.join(newCandidates, ", ");
				
				if (newCandidates.size() > 1) {
					finalCandidates = candidatesPrefix + finalCandidates + ")";	
				}
				
				quasipolynomial.setPolynomial(finalCandidates);
			}
		}
	}
}
