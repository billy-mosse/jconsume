package ar.uba.dc.barvinok.syntax;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import ar.uba.dc.barvinok.BarvinokSyntax;
import ar.uba.dc.barvinok.BarvinokUtils;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

public class OmegaSyntaxTest {

	private BarvinokSyntax syntax;
	
	@Before
	public void setUp() {
		syntax = new OmegaSyntax();
	}
	
	@Test
	public void parseConstants() {
		String[] constants = new String[] {
			"{1}", "{1 }", "{ 1}", "{ 1 }",
			"{[]->1}", "{[] ->1}", "{[] -> 1}", "{[] ->1 }", "{[] -> 1 }", "{[]-> 1}", "{[]->1 }", "{[]-> 1 }",
			"[]->{[]->1}", "[] ->{[]->1}", "[]-> {[]->1}", "[] -> { []->1}", "[] -> { [] -> 1 }" 
		};
		
		for (String constant : constants) {
			PiecewiseQuasipolynomial l = syntax.parsePiecewiseQuasipolynomial(constant);
			assertThat(l.toString(), is(equalTo("[] -> {[] -> 1}")));
			assertThat(syntax.toString(l), is(equalTo("[] -> {[] -> 1}")));
			assertThat(l.getParameters().size(), is(equalTo(0)));
			assertThat(l.getPieces().size(), is(equalTo(1)));
			assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
			assertThat(l.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
			assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("1")));
		}
		
		constants = new String[] {
			"[]->{[n]->1:n >= 0 and n <= 5}", "[] ->{[n]->1:n >= 0 and n <= 5}", "[]-> {[n]->1:n >= 0 and n <= 5}", "[] -> { [n]->1:n >= 0 and n <= 5}", "[] -> { [n] -> 1 : n >= 0 and n <= 5}" 
		};
		
		for (String constant : constants) {
			PiecewiseQuasipolynomial l = syntax.parsePiecewiseQuasipolynomial(constant);
			assertThat(l.toString(), is(equalTo("[] -> {[n] -> 1: n >= 0 and n <= 5}")));
			assertThat(syntax.toString(l), is(equalTo("[] -> {[n] -> 1: n >= 0 and n <= 5}")));
			assertThat(l.getParameters().size(), is(equalTo(0)));
			assertThat(l.getPieces().size(), is(equalTo(1)));
			assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(1)));
			assertThat(l.getPieces().get(0).getVariables(), hasItem("n"));
			assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("n >= 0 and n <= 5")));
			assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("1")));
		}
		
		constants = new String[] {
			"[n]->{[]->1:n >= 0 and n <= 5}", "[n] ->{[]->1:n >= 0 and n <= 5}", "[n]-> {[]->1:n >= 0 and n <= 5}", "[n] -> { []->1:n >= 0 and n <= 5}", "[n] -> { [] -> 1 : n >= 0 and n <= 5}" 
		};
		
		for (String constant : constants) {
			PiecewiseQuasipolynomial l = syntax.parsePiecewiseQuasipolynomial(constant);
			assertThat(l.toString(), is(equalTo("[n] -> {[] -> 1: n >= 0 and n <= 5}")));
			assertThat(syntax.toString(l), is(equalTo("[n] -> {[] -> 1: n >= 0 and n <= 5}")));
			assertThat(l.getParameters().size(), is(equalTo(1)));
			assertThat(l.getParameters(), hasItem("n"));
			assertThat(l.getPieces().size(), is(equalTo(1)));
			assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
			assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("n >= 0 and n <= 5")));
			assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("1")));
		}
		
		constants = new String[] {
			"{}", "{ }", "{   }" 
		};
		
		for (String constant : constants) {
			PiecewiseQuasipolynomial l = syntax.parsePiecewiseQuasipolynomial(constant);
			assertThat(l.toString(), is(equalTo("[] -> {[] -> 0}")));
			assertThat(syntax.toString(l), is(equalTo("[] -> {[] -> 0}")));
			assertThat(l.getParameters().size(), is(equalTo(0)));
			assertThat(l.getPieces().size(), is(equalTo(1)));
			assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
			assertThat(l.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
			assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("0")));
		}
		
		constants = new String[] {
			"[n] -> {}", "[n] -> { }", "[n] -> {   }" 
		};
		
		for (String constant : constants) {
			PiecewiseQuasipolynomial l = syntax.parsePiecewiseQuasipolynomial(constant);
			assertThat(l.toString(), is(equalTo("[n] -> {[] -> 0}")));
			assertThat(syntax.toString(l), is(equalTo("[n] -> {[] -> 0}")));
			assertThat(l.getParameters().size(), is(equalTo(1)));
			assertThat(l.getParameters(), hasItem("n"));
			assertThat(l.getPieces().size(), is(equalTo(1)));
			assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
			assertThat(l.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
			assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("0")));
		}
	}
	
	@Test
	public void parseInfinite() {
		PiecewiseQuasipolynomial l = syntax.parsePiecewiseQuasipolynomial("{ infty }");
		assertThat(l.toString(), is(equalTo("[] -> {[] -> infty}")));
		assertThat(syntax.toString(l), is(equalTo("[] -> {[] -> infty}")));
		assertThat(l.getParameters().size(), is(equalTo(0)));
		assertThat(l.getPieces().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("infty")));
		
		l = syntax.parsePiecewiseQuasipolynomial("[n] -> { infty : n >= 1 }");
		assertThat(l.toString(), is(equalTo("[n] -> {[] -> infty: n >= 1}")));
		assertThat(syntax.toString(l), is(equalTo("[n] -> {[] -> infty: n >= 1}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getPieces().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("infty")));
		
		l = syntax.parsePiecewiseQuasipolynomial("[n] -> { infty : n <= 0; infty : n >= 1 }");
		assertThat(l.toString(), is(equalTo("[n] -> {[] -> infty: n <= 0; [] -> infty: n >= 1}")));
		assertThat(syntax.toString(l), is(equalTo("[n] -> {[] -> infty: n <= 0; [] -> infty: n >= 1}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getPieces().size(), is(equalTo(2)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("n <= 0")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("infty")));
		assertThat(l.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(1).getConstraints(), is(equalTo("n >= 1")));
		assertThat(l.getPieces().get(1).getPolynomial(), is(equalTo("infty")));
		
		l = syntax.parsePiecewiseQuasipolynomial("[n] -> { infty : n >= 0 and n <= 5; infty : n >= 6 }");
		assertThat(l.toString(), is(equalTo("[n] -> {[] -> infty: n >= 0 and n <= 5; [] -> infty: n >= 6}")));
		assertThat(syntax.toString(l), is(equalTo("[n] -> {[] -> infty: n >= 0 and n <= 5; [] -> infty: n >= 6}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getPieces().size(), is(equalTo(2)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("n >= 0 and n <= 5")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("infty")));
		assertThat(l.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(1).getConstraints(), is(equalTo("n >= 6")));
		assertThat(l.getPieces().get(1).getPolynomial(), is(equalTo("infty")));
	}
	
	@Test
	public void NaNIsInterpretedAsInfinite() {
		PiecewiseQuasipolynomial l = syntax.parsePiecewiseQuasipolynomial("[k] -> { NaN }");
		assertThat(l.toString(), is(equalTo("[k] -> {[] -> infty}")));
		assertThat(syntax.toString(l), is(equalTo("[k] -> {[] -> infty}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("k"));
		assertThat(l.getPieces().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("infty")));
		
		l = syntax.parsePiecewiseQuasipolynomial("{ NaN }");
		assertThat(l.toString(), is(equalTo("[] -> {[] -> infty}")));
		assertThat(syntax.toString(l), is(equalTo("[] -> {[] -> infty}")));
		assertThat(l.getParameters().size(), is(equalTo(0)));
		assertThat(l.getPieces().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("infty")));
		
		l = syntax.parsePiecewiseQuasipolynomial("[n] -> { NaN : n >= 0 and n <= 5; NaN : n >= 6 }");
		assertThat(l.toString(), is(equalTo("[n] -> {[] -> infty: n >= 0 and n <= 5; [] -> infty: n >= 6}")));
		assertThat(syntax.toString(l), is(equalTo("[n] -> {[] -> infty: n >= 0 and n <= 5; [] -> infty: n >= 6}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getPieces().size(), is(equalTo(2)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("n >= 0 and n <= 5")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("infty")));
		assertThat(l.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(1).getConstraints(), is(equalTo("n >= 6")));
		assertThat(l.getPieces().get(1).getPolynomial(), is(equalTo("infty")));
	}
	
	@Test
	public void parsePolynomials() {
		PiecewiseQuasipolynomial l = syntax.parsePiecewiseQuasipolynomial("[n] -> { (1/2 * n + 1/2 * n^2) : n >= 0 }");
		assertThat(l.toString(), is(equalTo("[n] -> {[] -> 1/2 * n + 1/2 * n^2: n >= 0}")));
		assertThat(syntax.toString(l), is(equalTo("[n] -> {[] -> 1/2 * n + 1/2 * n^2: n >= 0}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getPieces().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("1/2 * n + 1/2 * n^2")));
		
		l = syntax.parsePiecewiseQuasipolynomial("[n] -> { (n + n^2) : n >= 0 and n <= 5; n : n >= 6; n^2 : n <= -1 }");
		assertThat(l.toString(), is(equalTo("[n] -> {[] -> n + n^2: n >= 0 and n <= 5; [] -> n: n >= 6; [] -> n^2: n <= -1}")));
		assertThat(syntax.toString(l), is(equalTo("[n] -> {[] -> n + n^2: n >= 0 and n <= 5; [] -> n: n >= 6; [] -> n^2: n <= -1}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getPieces().size(), is(equalTo(3)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("n >= 0 and n <= 5")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("n + n^2")));
		assertThat(l.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(1).getConstraints(), is(equalTo("n >= 6")));
		assertThat(l.getPieces().get(1).getPolynomial(), is(equalTo("n")));
		assertThat(l.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(2).getConstraints(), is(equalTo("n <= -1")));
		assertThat(l.getPieces().get(2).getPolynomial(), is(equalTo("n^2")));
		
		l = syntax.parsePiecewiseQuasipolynomial("[n, m] -> { ((5 + n) + 3 * m) : n >= 1 and m >= 0; n : n >= 1 and m <= -1; (5 + 3 * m) : m >= 0 and n <= 0 }");
		assertThat(l.toString(), is(equalTo("[m,n] -> {[] -> (5 + n) + 3 * m: n >= 1 and m >= 0; [] -> n: n >= 1 and m <= -1; [] -> 5 + 3 * m: m >= 0 and n <= 0}")));
		assertThat(syntax.toString(l), is(equalTo("[m,n] -> {[] -> (5 + n) + 3 * m: n >= 1 and m >= 0; [] -> n: n >= 1 and m <= -1; [] -> 5 + 3 * m: m >= 0 and n <= 0}")));
		assertThat(l.getParameters().size(), is(equalTo(2)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getParameters(), hasItem("m"));
		assertThat(l.getPieces().size(), is(equalTo(3)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("n >= 1 and m >= 0")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("(5 + n) + 3 * m")));
		assertThat(l.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(1).getConstraints(), is(equalTo("n >= 1 and m <= -1")));
		assertThat(l.getPieces().get(1).getPolynomial(), is(equalTo("n")));
		assertThat(l.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(2).getConstraints(), is(equalTo("m >= 0 and n <= 0")));
		assertThat(l.getPieces().get(2).getPolynomial(), is(equalTo("5 + 3 * m")));
		
		l = syntax.parsePiecewiseQuasipolynomial("[n, m] -> { (n + m^2) : m = 5 and n <= 5 and n >= 0; n : (n >= 0 and m <= 4) or (n >= 0 and m >= 6) or (m = 5 and n >= 6); m^2 : m = 5 and n <= -1 }");
		assertThat(l.toString(), is(equalTo("[m,n] -> {[] -> n + m^2: m == 5 and n <= 5 and n >= 0; [] -> n: (n >= 0 and m <= 4) or (n >= 0 and m >= 6) or (m == 5 and n >= 6); [] -> m^2: m == 5 and n <= -1}")));
		assertThat(syntax.toString(l), is(equalTo("[m,n] -> {[] -> n + m^2: m == 5 and n <= 5 and n >= 0; [] -> n: (n >= 0 and m <= 4) or (n >= 0 and m >= 6) or (m == 5 and n >= 6); [] -> m^2: m == 5 and n <= -1}")));
		assertThat(l.getParameters().size(), is(equalTo(2)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getParameters(), hasItem("m"));
		assertThat(l.getPieces().size(), is(equalTo(3)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("m == 5 and n <= 5 and n >= 0")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("n + m^2")));
		assertThat(l.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(1).getConstraints(), is(equalTo("(n >= 0 and m <= 4) or (n >= 0 and m >= 6) or (m == 5 and n >= 6)")));
		assertThat(l.getPieces().get(1).getPolynomial(), is(equalTo("n")));
		assertThat(l.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(2).getConstraints(), is(equalTo("m == 5 and n <= -1")));
		assertThat(l.getPieces().get(2).getPolynomial(), is(equalTo("m^2")));
	}
	
	@Test
	public void parseMaximum() {
		PiecewiseQuasipolynomial l = syntax.parsePiecewiseQuasipolynomial("[n] -> {  }");
		assertThat(l.toString(), is(equalTo("[n] -> {[] -> 0}")));
		assertThat(syntax.toString(l), is(equalTo("[n] -> {[] -> 0}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getPieces().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("0")));
		
		l = syntax.parsePiecewiseQuasipolynomial("[n] -> { max(1) : n >= 0 }");
		assertThat(l.toString(), is(equalTo("[n] -> {[] -> max(1): n >= 0}")));
		assertThat(syntax.toString(l), is(equalTo("[n] -> {[] -> max(1): n >= 0}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getPieces().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("max(1)")));
		
		l = syntax.parsePiecewiseQuasipolynomial("[n, m] -> { max(infty) : m >= 1 + n; max(n * m, m^2, n^2) : m <= n }");
		assertThat(l.toString(), is(equalTo("[m,n] -> {[] -> max(infty): m >= 1 + n; [] -> max(n * m, m^2, n^2): m <= n}")));
		assertThat(syntax.toString(l), is(equalTo("[m,n] -> {[] -> max(infty): m >= 1 + n; [] -> max(n * m, m^2, n^2): m <= n}")));
		assertThat(l.getParameters().size(), is(equalTo(2)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getParameters(), hasItem("m"));
		assertThat(l.getPieces().size(), is(equalTo(2)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("m >= 1 + n")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("max(infty)")));
		assertThat(l.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(1).getConstraints(), is(equalTo("m <= n")));
		assertThat(l.getPieces().get(1).getPolynomial(), is(equalTo("max(n * m, m^2, n^2)")));
		
		
		l = syntax.parsePiecewiseQuasipolynomial("([n] -> {  }, True)");
		assertThat(l.toString(), is(equalTo("[n] -> {[] -> 0}")));
		assertThat(syntax.toString(l), is(equalTo("[n] -> {[] -> 0}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getPieces().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("0")));
		
		l = syntax.parsePiecewiseQuasipolynomial("([n] -> { max(1) : n >= 0 }, True)");
		assertThat(l.toString(), is(equalTo("[n] -> {[] -> max(1): n >= 0}")));
		assertThat(syntax.toString(l), is(equalTo("[n] -> {[] -> max(1): n >= 0}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getPieces().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("max(1)")));
		
		l = syntax.parsePiecewiseQuasipolynomial("([n, m] -> { max(infty) : m >= 1 + n; max(n * m, m^2, n^2) : m <= n }, False)");
		assertThat(l.toString(), is(equalTo("[m,n] -> {[] -> max(infty): m >= 1 + n; [] -> max(n * m, m^2, n^2): m <= n}")));
		assertThat(syntax.toString(l), is(equalTo("[m,n] -> {[] -> max(infty): m >= 1 + n; [] -> max(n * m, m^2, n^2): m <= n}")));
		assertThat(l.getParameters().size(), is(equalTo(2)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getParameters(), hasItem("m"));
		assertThat(l.getPieces().size(), is(equalTo(2)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("m >= 1 + n")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("max(infty)")));
		assertThat(l.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(1).getConstraints(), is(equalTo("m <= n")));
		assertThat(l.getPieces().get(1).getPolynomial(), is(equalTo("max(n * m, m^2, n^2)")));		
	}

	@Test
	public void toStringDomainSet() {
		DomainSet d = new DomainSet(StringUtils.EMPTY);
		
		assertThat(syntax.toString(d), is(equalTo("{}")));
		
		d = new DomainSet(StringUtils.EMPTY);
		d.addParameter("a");
		
		assertThat(syntax.toString(d), is(equalTo("[a] -> {}")));
		
		d = new DomainSet("a = 20");
		d.addParameter("a");
		
		assertThat(syntax.toString(d), is(equalTo("[a] -> {a = 20}")));
		
		d = new DomainSet(StringUtils.EMPTY);
		d.addVariable("a");
		
		assertThat(syntax.toString(d), is(equalTo("{[a]: }")));
		
		d = new DomainSet("a = 20");
		d.addVariable("a");
		
		assertThat(syntax.toString(d), is(equalTo("{[a]: a = 20}")));

		d = new DomainSet(StringUtils.EMPTY);
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("j");
		
		assertThat(syntax.toString(d), is(equalTo("[n] -> {[i,j]: }")));
		
		d = new DomainSet("0 <= i <= n and 0 <= j < i");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("j");
		
		assertThat(syntax.toString(d), is(equalTo("[n] -> {[i,j]: 0 <= i <= n and 0 <= j < i}")));
		
		d = new DomainSet("0 <= i <= n and 0 <= j < i");
		d.addParameter("n");
		d.addParameter("w");
		d.addParameter("m");
		d.addVariable("i");
		d.addVariable("j");
		
		assertThat(syntax.toString(d), is(equalTo("[m,n,w] -> {[i,j]: 0 <= i <= n and 0 <= j < i}")));
	}
	
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
	
	@Test
	public void parsePolinomialsWithLists() {
		PiecewiseQuasipolynomial l = syntax.parsePiecewiseQuasipolynomial("[a] -> {[] -> (a - [(a)/2]) : a >= 1}");
		assertThat(l.toString(), is(equalTo("[a] -> {[] -> a - (1/2)*(a): a >= 1}")));
		assertThat(syntax.toString(l), is(equalTo("[a] -> {[] -> a - (1/2)*(a): a >= 1}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("a"));
		assertThat(l.getPieces().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("a >= 1")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("a - (1/2)*(a)")));
	}
	
	@Test
	public void stripPartOnlyIfSurroundAllTerm() {
		PiecewiseQuasipolynomial l = syntax.parsePiecewiseQuasipolynomial("[a, b] -> { [] -> (1 + a) * b : a >= 1 and b >= 2; [] -> b : b >= 2 and a <= 0; [] -> (1 + a) : a >= 1 and b <= 1; [] -> 1 : b <= 1 and a <= 0 }");
		assertThat(l.toString(), is(equalTo("[a,b] -> {[] -> (1 + a) * b: a >= 1 and b >= 2; [] -> b: b >= 2 and a <= 0; [] -> 1 + a: a >= 1 and b <= 1; [] -> 1: b <= 1 and a <= 0}")));
		assertThat(syntax.toString(l), is(equalTo("[a,b] -> {[] -> (1 + a) * b: a >= 1 and b >= 2; [] -> b: b >= 2 and a <= 0; [] -> 1 + a: a >= 1 and b <= 1; [] -> 1: b <= 1 and a <= 0}")));
		assertThat(l.getParameters().size(), is(equalTo(2)));
		assertThat(l.getParameters(), hasItem("a"));
		assertThat(l.getParameters(), hasItem("b"));
		assertThat(l.getPieces().size(), is(equalTo(4)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("a >= 1 and b >= 2")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("(1 + a) * b")));
		assertThat(l.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(1).getConstraints(), is(equalTo("b >= 2 and a <= 0")));
		assertThat(l.getPieces().get(1).getPolynomial(), is(equalTo("b")));
		assertThat(l.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(2).getConstraints(), is(equalTo("a >= 1 and b <= 1")));
		assertThat(l.getPieces().get(2).getPolynomial(), is(equalTo("1 + a")));
		assertThat(l.getPieces().get(3).getVariables().size(), is(equalTo(0)));
		assertThat(l.getPieces().get(3).getConstraints(), is(equalTo("b <= 1 and a <= 0")));
		assertThat(l.getPieces().get(3).getPolynomial(), is(equalTo("1")));
	}
	
	@Test
	public void existsConstraintDoesNotAddVariablesToPolynomial() {
		PiecewiseQuasipolynomial l = syntax.parsePiecewiseQuasipolynomial("[n] -> { [i] -> 2*n*i - n*n + 3*n - 1/2*i*i - 3/2*i-1: (exists j : 0 <= i < 4*n-1 and 0 <= j < n and 2*n-1 <= i+j <= 4*n-2 and i <= 2*n-1 ) }");
		assertThat(l.toString(), is(equalTo("[n] -> {[i] -> 2*n*i - n*n + 3*n - 1/2*i*i - 3/2*i-1: (exists j : 0 <= i < 4*n-1 and 0 <= j < n and 2*n-1 <= i+j <= 4*n-2 and i <= 2*n-1 )}")));
		assertThat(syntax.toString(l), is(equalTo("[n] -> {[i] -> 2*n*i - n*n + 3*n - 1/2*i*i - 3/2*i-1: (exists j : 0 <= i < 4*n-1 and 0 <= j < n and 2*n-1 <= i+j <= 4*n-2 and i <= 2*n-1 )}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getPieces().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables(), hasItem("i"));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("(exists j : 0 <= i < 4*n-1 and 0 <= j < n and 2*n-1 <= i+j <= 4*n-2 and i <= 2*n-1 )")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("2*n*i - n*n + 3*n - 1/2*i*i - 3/2*i-1")));
		
		l = syntax.parsePiecewiseQuasipolynomial("[n] -> { [i] -> 2*n*i - n*n + 3*n - 1/2*i*i - 3/2*i-1: exists (j : 0 <= i < 4*n-1 and 0 <= j < n and 2*n-1 <= i+j <= 4*n-2 and i <= 2*n-1 ) }");
		assertThat(l.toString(), is(equalTo("[n] -> {[i] -> 2*n*i - n*n + 3*n - 1/2*i*i - 3/2*i-1: exists (j : 0 <= i < 4*n-1 and 0 <= j < n and 2*n-1 <= i+j <= 4*n-2 and i <= 2*n-1 )}")));
		assertThat(syntax.toString(l), is(equalTo("[n] -> {[i] -> 2*n*i - n*n + 3*n - 1/2*i*i - 3/2*i-1: exists (j : 0 <= i < 4*n-1 and 0 <= j < n and 2*n-1 <= i+j <= 4*n-2 and i <= 2*n-1 )}")));
		assertThat(l.getParameters().size(), is(equalTo(1)));
		assertThat(l.getParameters(), hasItem("n"));
		assertThat(l.getPieces().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables().size(), is(equalTo(1)));
		assertThat(l.getPieces().get(0).getVariables(), hasItem("i"));
		assertThat(l.getPieces().get(0).getConstraints(), is(equalTo("exists (j : 0 <= i < 4*n-1 and 0 <= j < n and 2*n-1 <= i+j <= 4*n-2 and i <= 2*n-1 )")));
		assertThat(l.getPieces().get(0).getPolynomial(), is(equalTo("2*n*i - n*n + 3*n - 1/2*i*i - 3/2*i-1")));
	}
}
