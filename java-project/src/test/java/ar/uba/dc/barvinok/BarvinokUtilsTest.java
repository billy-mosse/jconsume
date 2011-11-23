package ar.uba.dc.barvinok;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.syntax.OmegaSyntax;

public class BarvinokUtilsTest {

	private BarvinokSyntax syntax = new OmegaSyntax();
	
	@Test
	public void constantsHasNoParameters() {
		PiecewiseQuasipolynomial pqw = syntax.parsePiecewiseQuasipolynomial("[] -> {[] -> (14)}");
		assertThat(BarvinokUtils.isConstant(pqw), is(equalTo(true)));
		assertThat(BarvinokUtils.isPolynomial(pqw), is(equalTo(false)));
		assertThat(pqw.getParameters().isEmpty(), is(equalTo(true)));
		assertThat(pqw.getPieces().size(), is(equalTo(1)));
		assertThat(pqw.getPieces().get(0).getVariables().isEmpty(), is(equalTo(true)));
		assertThat(pqw.getPieces().get(0).getPolynomial(), is(equalTo("14")));
		assertThat(pqw.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
	}
	
	@Test
	public void hasFold() {
		PiecewiseQuasipolynomial pqw = syntax.parsePiecewiseQuasipolynomial("[] -> {[] -> max(14, 3)}");
		assertThat(BarvinokUtils.hasFoldPiece(pqw), is(equalTo(true)));
		
		pqw = syntax.parsePiecewiseQuasipolynomial("[] -> {[] -> max (14, 3)}");
		assertThat(BarvinokUtils.hasFoldPiece(pqw), is(equalTo(false)));
		
		pqw = syntax.parsePiecewiseQuasipolynomial("[] -> {[] -> max  (14, 3)}");
		assertThat(BarvinokUtils.hasFoldPiece(pqw), is(equalTo(false)));
		
		pqw = syntax.parsePiecewiseQuasipolynomial("[] -> {[] -> 1; [] -> max(14, 3)}");
		assertThat(BarvinokUtils.hasFoldPiece(pqw), is(equalTo(true)));
	}
}
