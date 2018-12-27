package ar.uba.dc.barvinok;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import ar.uba.dc.barvinok.calculators.AddAsCompareStrategy;
import ar.uba.dc.barvinok.calculators.CommandLineCalculator;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;
import ar.uba.dc.barvinok.expression.operation.impl.DefaultDomainUnifier;
import ar.uba.dc.barvinok.expression.operation.impl.DefaultKeyGenerator;
import ar.uba.dc.barvinok.expression.operation.impl.ExceptionalBindingValidator;
import ar.uba.dc.barvinok.expression.operation.impl.JEPExpressionMapper;
import ar.uba.dc.barvinok.expression.operation.impl.ToStringParserVisitor;
import ar.uba.dc.barvinok.syntax.IsccSyntax;
import ar.uba.dc.barvinok.syntax.OmegaSyntax;

@Ignore
@RunWith(Theories.class)
public class BarvinokCalculatorWithAddAsMaxStrategyTest {

	@DataPoints
	public static CommandLineCalculator[] calculators() {
		CommandLineBarvinokExecutor executor = new CommandLineBarvinokExecutor();
		executor.setBarvinokCmdPath("./bin/barvinok.sh");
		JEPExpressionMapper mapper = new JEPExpressionMapper();
		mapper.setKeyGenerator(new DefaultKeyGenerator());
		mapper.setToStringVisitor(new ToStringParserVisitor());
		
		BarvinokSyntax[] syntaxes = new BarvinokSyntax[] { new OmegaSyntax(), new IsccSyntax() };
		List<CommandLineCalculator> calculators = new LinkedList<CommandLineCalculator>();
		
		for (BarvinokSyntax syntax : syntaxes) {
			CommandLineCalculator calc = new CommandLineCalculator();
			calc.setExecutor(executor);
			calc.setMapper(mapper);
			calc.setPrefixForPolinomialParametersInRangedOperations("$t.");
			calc.setBarvinokSupportInfiniteOnSumOperator(false);
			calc.setDomainUnifier(new DefaultDomainUnifier());
			calc.setCompareStrategy(new AddAsCompareStrategy());
			calc.setSyntax(syntax);
			calc.setBindingValidator(new ExceptionalBindingValidator());
			calculators.add(calc);
		}
		
		return calculators.toArray(new CommandLineCalculator[] {});
	}

	@Theory
	public void cardOperator(CommandLineCalculator calc) {
		// Me devuelve constantes
		DomainSet domain = new DomainSet("-3 <= i <= 4");
		domain.addVariable("i");
		
		PiecewiseQuasipolynomial m = calc.countExecutions(domain);
		
		assertThat(m.getParameters().size(), is(equalTo(0)));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("8")));

		// Devuelve varias aprtes si aplica
		domain = new DomainSet("0 <= i <= n and i <= j <= m");
		domain.addParameter("m");
		domain.addParameter("n");
		domain.addVariable("i");
		domain.addVariable("j");
		
		m = calc.countExecutions(domain);
		
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("m"));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n <= m && n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("1 + m + (1/2 + m)*n - (1/2)*n^2")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("m >= 0 && n >= 1 + m")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("1 + (3/2)*m + (1/2)*m^2")));
		
		domain = new DomainSet("0 <= 2*i < arreglo.size and arreglo.size == size");
		domain.addParameter("size");
		domain.addVariable("arreglo.size");
		domain.addVariable("i");
		
		m = calc.countExecutions(domain);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("size"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("size >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("size - (1/2)*size")));
	}
	
	@Theory
	public void cardOperatorWithEmptyDomains(CommandLineCalculator calc) {
		// Me devuelve 1 con un dominio vacio
		PiecewiseQuasipolynomial m = calc.countExecutions(new DomainSet(StringUtils.EMPTY));
		
		assertThat(m.getParameters().size(), is(equalTo(0)));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("1")));
	}
	
	@Theory
	public void cardOperatorAndInfinite(CommandLineCalculator calc) {
		// Me devuelve infinito si no esta bien definido el intervalo
		DomainSet domain = new DomainSet("0 <= i <= n and m <= n and j >= m");
		domain.addParameter("m");
		domain.addParameter("n");
		domain.addVariable("i");
		domain.addVariable("j");
		
		PiecewiseQuasipolynomial m = calc.countExecutions(domain);
		
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("m"));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= m && n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("infty")));	
	}
	
	@Theory
	public void cardOperatorAndConstants(CommandLineCalculator calc) {
		for (Integer constant : new Integer[] { 0, 1, 2, 6, 7, 29, 71 }) {
			DomainSet domain = new DomainSet(constant.toString());
					
			PiecewiseQuasipolynomial m = calc.countExecutions(domain);
			
			assertThat(m.getParameters().size(), is(equalTo(0)));
			assertThat(m.getPieces().size(), is(equalTo(1)));
			assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
			assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
			assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo(constant.toString())));	
		}
	}
	
	@Theory
	public void sumOperator(CommandLineCalculator calc) {
		DomainSet d = new DomainSet("0 <= i < n");
		d.addParameter("n");
		d.addVariable("i");
		
		// Probar el caso de sumar con un polinimio 0
		PiecewiseQuasipolynomial m = calc.sumConsumtion(BarvinokFactory.polynomial(new TreeSet<String>(), new QuasiPolynomial("0")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("0")));

		
		// Probar el caso de sumar con un polinomio cte
		d = new DomainSet("0 <= i < n");
		d.addParameter("n");
		d.addVariable("i");
		
		m = calc.sumConsumtion(BarvinokFactory.polynomial(new TreeSet<String>(), new QuasiPolynomial("1")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("n")));
		
		
		d = new DomainSet("-3 <= i < n and $t.k == i");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.k");
		
		Set<String> params = new TreeSet<String>();
		params.add("k");
		
		m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial("20", "k >= 0")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("20*n")));
		
		
		// Probar el caso de sumar con un polinomio simple
		d = new DomainSet("0 <= i <= n and $t.n == i");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		params = new TreeSet<String>();
		params.add("n");
		
		m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial("n")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("(1/2)*n + (1/2)*n^2")));
		
		
		// Probar el caso de sumar con un polinomio raro
		d = new DomainSet("0 <= $t.j <= n and $t.i == m and m >= 0");
		d.addParameter("n");
		d.addParameter("m");
		d.addVariable("$t.i");
		d.addVariable("$t.j");
		
		params = new TreeSet<String>();
		params.add("i");
		params.add("j");
		
		m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial("2*i + j")), d);
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getParameters(), hasItem("m"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("m >= 0 && n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("2*m + (1/2 + 2*m)*n + (1/2)*n^2")));
		
		
		d = new DomainSet("0 <= i <= n and $t.k == i");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.k");
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("k");
		
		p.add(new QuasiPolynomial("k", "0 <= k <= 5"));
		p.add(new QuasiPolynomial("k*k", "5 < k"));
		
		m = calc.sumConsumtion(p, d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 6")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("-40 + (1/6)*n + (1/2)*n^2 + (1/3)*n^3")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n >= 0 && n <= 5")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("(1/2)*n + (1/2)*n^2")));
	}
	
	@Theory
	public void sumOperatorWithEmptyDomains(CommandLineCalculator calc) {
		// Me devuelve a mi mismo si le paso un dominio vacio
		PiecewiseQuasipolynomial m = calc.sumConsumtion(BarvinokFactory.polynomial(new TreeSet<String>(), new QuasiPolynomial("0")), new DomainSet(StringUtils.EMPTY));
		assertThat(m.getParameters().size(), is(equalTo(0)));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("0")));			
		
		Set<String> params = new TreeSet<String>();
		params.add("m");
		params.add("k");
		m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial("m*k + 10", "m > 0 and k >= m")), new DomainSet(StringUtils.EMPTY));
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("m"));
		assertThat(m.getParameters(), hasItem("k"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("m > 0 and k >= m")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("m*k + 10")));
	}
	
	@Theory
	public void sumOperatorAndInfinite(CommandLineCalculator calc) {
		// Probar el caso en que de infinito
		DomainSet d = new DomainSet("0 <= i and $t.n == i");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		Set<String> params = new TreeSet<String>();
		params.add("n");
		
		PiecewiseQuasipolynomial m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial("n*n")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		
		// No importa si tenemos varios pedazos... me devuelve 1 solo infinito
		d = new DomainSet("0 <= i and $t.k == i");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.k");
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("k");
		
		p.add(new QuasiPolynomial("k", "0 <= k <= 5"));
		p.add(new QuasiPolynomial("k*k", "5 < k"));
		
		m = calc.sumConsumtion(p, d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		
		// Probar el caso de pasarle infinito y ver que resuelve bien
		d = new DomainSet("0 <= i and $t.n == i");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		params = new TreeSet<String>();
		params.add("n");
		
		m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial(BarvinokSyntax.INFINITE)), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		
		// No importa si tenemos varios pedazos... me devuelve 1 solo infinito
		d = new DomainSet("0 <= i and $t.k == i");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.k");
		
		p = new PiecewiseQuasipolynomial();
		p.addParameter("k");
		
		p.add(new QuasiPolynomial("k", "0 <= k <= 5"));
		p.add(new QuasiPolynomial(BarvinokSyntax.INFINITE, "5 < k"));
		
		m = calc.sumConsumtion(p, d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		
		d = new DomainSet("0 <= i and $t.k == i");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.k");
		
		p = new PiecewiseQuasipolynomial();
		p.addParameter("k");
		
		p.add(new QuasiPolynomial(BarvinokSyntax.INFINITE, "0 <= k <= 5"));
		p.add(new QuasiPolynomial("k*k", "5 < k"));
		
		m = calc.sumConsumtion(p, d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
	}
	
	@Theory
	public void sumOperatorCanOperateWithFoldPolynomial(CommandLineCalculator calc) {
		Set<String> params = new TreeSet<String>();
		params.add("m");
		params.add("k");
		// Aunque no tengamos dominio, debe tirar error
		PiecewiseQuasipolynomial m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial("max(m*k + 10, 3)", "m > 0 and k >= m")), new DomainSet(StringUtils.EMPTY));
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("m"));
		assertThat(m.getParameters(), hasItem("k"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("m > 0 and k >= m")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("max(m*k + 10, 3)")));
		
		
		DomainSet d = new DomainSet("0 <= $t.m <= n and $t.k == m and m >= 0");
		d.addParameter("n");
		d.addParameter("m");
		d.addVariable("$t.m");
		d.addVariable("$t.k");
				
		m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial("max(m*k + 10, 3)", "m > 0 and k >= m")), d);
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("m"));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n <= m && n >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("(13 + (1/2)*m)*n + (1/2)*m*n^2")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("m >= 1 && n >= 1 + m")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("13*m + (1/2)*m^2 + (1/2)*m^3")));
		
		// Tira error aun cuando hay varias partes. Alcanza con que haya 1 que sea max
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addAllParameters(params);
		p.add(new QuasiPolynomial("3", "m <= 0 and k >= m"));
		p.add(new QuasiPolynomial("max(m*k + 10, 3)", "m > 0 and k >= m"));
		
		m = calc.sumConsumtion(p, d);
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("m"));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(3)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("m >= 0 && n >= 1 && n <= m")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("3 + (13 + (1/2)*m)*n + (1/2)*m*n^2")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("m >= 1 && n >= 0 && n >= 1 + m")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("3 + 13*m + (1/2)*m^2 + (1/2)*m^3")));
		assertThat(m.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(2).getConstraints(), is(equalTo("(m == 0 && n >= 1) || (n == 0 && m >= 0)")));
		assertThat(m.getPieces().get(2).getPolynomial(), is(equalTo("3")));
		
		d = new DomainSet("1 <= i <= p and $t.n == i");
		d.addParameter("p");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		params = new TreeSet<String>();
		params.add("n");
		m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial("max(n*n, n+4)", "n > 0")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("p"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("p >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("(14/3)*p + p^2 + (1/3)*p^3")));
		
		d = new DomainSet("4 <= i <= p and $t.n == i");
		d.addParameter("p");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		params = new TreeSet<String>();
		params.add("n");
		m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial("max(n*n, n+4)", "n > 0")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("p"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("p >= 4")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("-32 + (14/3)*p + p^2 + (1/3)*p^3")));
		
		d = new DomainSet("-3 <= i <= p and $t.n == i");
		d.addParameter("p");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.add(new QuasiPolynomial("max(n*n, n+4)", "n > 0"));
		p.add(new QuasiPolynomial("max(n*n + 10*n, 40*n)", "n <= 0"));
		m = calc.sumConsumtion(p, d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("p"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("p >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("-286 + (14/3)*p + p^2 + (1/3)*p^3")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("p >= -3 && p <= 0")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("-286 + (151/6)*p + (51/2)*p^2 + (1/3)*p^3")));
		
		d = new DomainSet("-3 <= i <= p and $t.n == i");
		d.addParameter("p");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.add(new QuasiPolynomial("max(n*n, n+4)", "n > 0"));
		p.add(new QuasiPolynomial("4*n", "n <= 0"));
		m = calc.sumConsumtion(p, d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("p"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("p >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("-24 + (14/3)*p + p^2 + (1/3)*p^3")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("p >= -3 && p <= 0")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("-24 + 2*p + 2*p^2")));
		
		d = new DomainSet("-3 <= i <= p and $t.n == i and $t.k == 4");
		d.addParameter("p");
		d.addVariable("i");
		d.addVariable("$t.n");
		d.addVariable("$t.k");
		
		p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.addParameter("k");
		p.add(new QuasiPolynomial("max(n*n, n+k)", "n > 0"));
		p.add(new QuasiPolynomial("k*n", "n <= 0"));
		m = calc.sumConsumtion(p, d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("p"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("p >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("-24 + (14/3)*p + p^2 + (1/3)*p^3")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("p >= -3 && p <= 0")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("-24 + 2*p + 2*p^2")));
	}
	
	@Theory
	public void sumOperatorCanOperateWithExists(CommandLineCalculator calc) {
		DomainSet d = new DomainSet("n == 4*$t.i");
		d.addParameter("n");
		d.addVariable("$t.i");
		
		Set<String> params = new TreeSet<String>();
		params.add("i");

		// Esperamos una unica declaracion de exists que englobe a todas las constratins
		PiecewiseQuasipolynomial m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial("i")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("exists(e0 == [n/4] : 4*e0 == n)")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("(1/4)*n")));
		
		// Puede usarse el exists y hacer cte algun parametro
		
		d = new DomainSet("n == $t.i and $t.i == 4");
		d.addParameter("n");
		d.addVariable("$t.i");
		
		params = new TreeSet<String>();
		params.add("i");

		// Esperamos una unica declaracion de exists que englobe a todas las constratins
		m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial("1/2 * i", "(exists e0 == [i/2], e1, e2 == [i/4]: 2*e0 == n and 4*e2 == n)")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n == 4")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("2")));
		
		m = calc.sumConsumtion(BarvinokFactory.polynomial(params, new QuasiPolynomial("1/2 * i", "(exists e0 == [i/2], e1, e2 == [i/4]: 2*e0 == n and 5*e2 == n)")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("0")));
	}
	
	@Theory
	public void upperBoundOperator(CommandLineCalculator calc) {
		// Probar con un polinomio con valor 0
		DomainSet d = new DomainSet("0 <= i < n");
		d.addParameter("n");
		d.addVariable("i");
		 
		PiecewiseQuasipolynomial m = calc.upperBound(BarvinokFactory.constant(0L), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("0")));

		
		// Probar con un polinomio con valor cte
		d = new DomainSet("0 <= i < n");
		d.addParameter("n");
		d.addVariable("i");
		
		m = calc.upperBound(BarvinokFactory.polynomial(new TreeSet<String>(), new QuasiPolynomial("1")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("1")));
		
		
		d = new DomainSet("-3 <= i < n and $t.k == i");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.k");
		
		Set<String> params = new TreeSet<String>();
		params.add("k");
		
		m = calc.upperBound(BarvinokFactory.polynomial(params, new QuasiPolynomial("20", "k >= 0")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("20")));
		
		
		// Probar un ejemplo que devuelva un solo candidato
		d = new DomainSet("0 <= i <= n and $t.n == i");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		params = new TreeSet<String>();
		params.add("n");
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.add(new QuasiPolynomial("1", "n == 0"));
		p.add(new QuasiPolynomial("n", "n > 0"));
		
		m = calc.upperBound(p, d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("n")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n == 0")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("1")));
		
		
		// Probar un ejemplo que devuelva varios candidatos. Deberia devolver la suma si son todos aunque sean constantes
		d = new DomainSet("m <= i <= n and $t.n == i");
		d.addParameter("m");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		params = new TreeSet<String>();
		params.add("n");
		
		p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.add(new QuasiPolynomial("n*n", "n >= 0"));
		p.add(new QuasiPolynomial("n", "n < 0"));
		
		m = calc.upperBound(p, d);
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getParameters(), hasItem("m"));
		assertThat(m.getPieces().size(), is(equalTo(4)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("m <= -1 && n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("n^2")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("m == 0 && n >= 0")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("n^2")));
		assertThat(m.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(2).getConstraints(), is(equalTo("n >= m && m >= 1")));
		assertThat(m.getPieces().get(2).getPolynomial(), is(equalTo("m^2 + m*n + n^2")));
		assertThat(m.getPieces().get(3).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(3).getConstraints(), is(equalTo("n >= m && n <= -1")));
		assertThat(m.getPieces().get(3).getPolynomial(), is(equalTo("n")));
	}
	
	@Theory
	public void upperBoundOperatorWithEmptyDomains(CommandLineCalculator calc) {
		// Me devuelve a mi mismo si le paso un dominio vacio
		PiecewiseQuasipolynomial m = calc.upperBound(BarvinokFactory.polynomial(new TreeSet<String>(), new QuasiPolynomial("0")), new DomainSet(StringUtils.EMPTY));
		assertThat(m.getParameters().size(), is(equalTo(0)));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("0")));			
		
		Set<String> params = new TreeSet<String>();
		params.add("m");
		params.add("k");
		m = calc.upperBound(BarvinokFactory.polynomial(params, new QuasiPolynomial("m*k + 10", "m > 0 and k >= m")), new DomainSet(StringUtils.EMPTY));
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("m"));
		assertThat(m.getParameters(), hasItem("k"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("m > 0 and k >= m")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("m*k + 10")));
	}
	
	@Theory
	public void upperBoundOperatorAndInfinite(CommandLineCalculator calc) {
		// Probar el caso en que de infinito
		DomainSet d = new DomainSet("0 <= i and i == $t.n");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		Set<String> params = new TreeSet<String>();
		params.add("n");
		
		PiecewiseQuasipolynomial m = calc.upperBound(BarvinokFactory.polynomial(params, new QuasiPolynomial("i")), d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n <= -1")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE))); 
		
		
		// Probar el caso de pasarle infinito y ver que resuelve bien
		d = new DomainSet("0 <= i <= n and i == $t.n");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.add(new QuasiPolynomial(BarvinokSyntax.INFINITE, "n > 0"));
		p.add(new QuasiPolynomial("n", "n == 0"));
		
		m = calc.upperBound(p, d);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n == 0")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("0")));
	}
	
	@Theory
	public void addOperator(CommandLineCalculator calc) {
		// Probar sumar constantes
		Set<String> params = new TreeSet<String>();
		params.add("n");
		PiecewiseQuasipolynomial m = calc.add(BarvinokFactory.constant(15L), BarvinokFactory.constant(20L), BarvinokFactory.polynomial(params, new QuasiPolynomial("7", "n >= 0")));
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("42")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n <= -1")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("35")));

		
		// debe ser posible sumar dos polinomios con el mismo domino
		params = new TreeSet<String>();
		params.add("n");
		
		QuasiPolynomial q = new QuasiPolynomial("n*n", "0 <= n");

		m = calc.add(BarvinokFactory.polynomial(params, q), BarvinokFactory.polynomial(params, q));
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("2*n^2")));
		

		// debe ser posible sumar una constante a un polinomio
		params = new TreeSet<String>();
		params.add("n");
		
		m = calc.add(BarvinokFactory.polynomial(params, new QuasiPolynomial("n", "n >= 0")), BarvinokFactory.constant(7L));
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("7 + n")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n <= -1")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("7")));
		
		
		// debe ser posible sumar dos polinomios con dominios distintos
		params = new TreeSet<String>();
		params.add("n");
		params.add("m");
		
		m = calc.add(BarvinokFactory.polynomial(params, new QuasiPolynomial("m + 1", "m >= 0")), BarvinokFactory.polynomial(params, new QuasiPolynomial("n + 2", "n >= 0")));
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getParameters(), hasItem("m"));
		assertThat(m.getPieces().size(), is(equalTo(3)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("m >= 0 && n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("3 + m + n")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("m >= 0 && n <= -1")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("1 + m")));
		assertThat(m.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(2).getConstraints(), is(equalTo("n >= 0 && m <= -1")));
		assertThat(m.getPieces().get(2).getPolynomial(), is(equalTo("2 + n")));
	}
	
	@Theory
	public void addOperatorAndInfinite(CommandLineCalculator calc) {
		// Probar sumar algo con infinito en rangos partidos y ver que resuelve bien
		Set<String> params = new TreeSet<String>();
		params.add("n");
		params.add("m");
		
		PiecewiseQuasipolynomial m = calc.add(BarvinokFactory.polynomial(params, new QuasiPolynomial(BarvinokSyntax.INFINITE, "m >= 0")), BarvinokFactory.polynomial(params, new QuasiPolynomial("n + 2", "n >= 0")));
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getParameters(), hasItem("m"));
		assertThat(m.getPieces().size(), is(equalTo(3)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("m >= 0 && n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("m >= 0 && n <= -1")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		assertThat(m.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(2).getConstraints(), is(equalTo("n >= 0 && m <= -1")));
		assertThat(m.getPieces().get(2).getPolynomial(), is(equalTo("2 + n")));
	}
	
	@Theory
	public void addOperatorCanOperateWithTwoMaximum(CommandLineCalculator calc) {
		// Probar sumar algo con infinito en rangos partidos y ver que resuelve bien
		Set<String> params = new TreeSet<String>();
		params.add("m");

		PiecewiseQuasipolynomial m = calc.add(BarvinokFactory.polynomial(params, new QuasiPolynomial("max((-1 + 2 * m), 3)", "m >= 1")), 
											  BarvinokFactory.polynomial(params, new QuasiPolynomial("0", StringUtils.EMPTY)),
											  BarvinokFactory.polynomial(params, new QuasiPolynomial("max((-1 + 2 * m), 3)", "m >= 1")));
		
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("m"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("m >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("max(-2 + 4*m, 2 + 2*m, 6)")));
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("$t.n");
		p.add(new QuasiPolynomial("max($t.n*$t.n, $t.n+4)", "$t.n > 0"));
		p.add(new QuasiPolynomial("4", "$t.n <= 0"));
		
		PiecewiseQuasipolynomial q = new PiecewiseQuasipolynomial();
		q.addAllParameters(p.getParameters());
		q.add(new QuasiPolynomial("max(3, $t.n)", "$t.n >= 5"));
		q.add(new QuasiPolynomial("max(3, $t.n, $t.n*$t.n)", "-2 <= $t.n <= 4"));
		
		m = calc.add(p, 
					 BarvinokFactory.polynomial(p.getParameters(), new QuasiPolynomial("max(4, $t.n)", "$t.n >= 3")),
					 q,
					 BarvinokFactory.constant(8L));
		
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("$t.n"));
		assertThat(m.getPieces().size(), is(equalTo(5)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("$t.n >= 5")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("8 + 2*$t.n + $t.n^2")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("$t.n >= 3 && $t.n <= 4")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("12 + 2*$t.n^2")));
		assertThat(m.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(2).getConstraints(), is(equalTo("$t.n >= 1 && $t.n <= 2")));
		assertThat(m.getPieces().get(2).getPolynomial(), is(equalTo("max(15 + $t.n, 8 + 2*$t.n^2, 12 + $t.n + $t.n^2)")));
		assertThat(m.getPieces().get(3).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(3).getConstraints(), is(equalTo("$t.n >= -2 && $t.n <= 0")));
		assertThat(m.getPieces().get(3).getPolynomial(), is(equalTo("max(15, 12 + $t.n^2)")));
		assertThat(m.getPieces().get(4).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(4).getConstraints(), is(equalTo("$t.n <= -3")));
		assertThat(m.getPieces().get(4).getPolynomial(), is(equalTo("12")));
		
		p = new PiecewiseQuasipolynomial();
		p.addParameter("$t.n");
		p.addParameter("$t.k");
		p.add(new QuasiPolynomial("max($t.n*$t.n, $t.n+$t.k)", "$t.n > 0 and $t.k == 4"));
		p.add(new QuasiPolynomial("$t.k", "$t.n <= 0"));
		
		q = new PiecewiseQuasipolynomial();
		q.addAllParameters(p.getParameters());
		q.add(new QuasiPolynomial("max(3, $t.n)", "$t.n >= 5"));
		q.add(new QuasiPolynomial("max(3, $t.n, $t.n*$t.n)", "-2 <= $t.n <= 4"));
		
		m = calc.add(p, 
					 BarvinokFactory.polynomial(p.getParameters(), new QuasiPolynomial("max($t.k, $t.n)", "$t.n >= 3 and $t.k == 4")),
					 q,
					 BarvinokFactory.constant(8L),
					 BarvinokFactory.constant(4L));
		
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("$t.n"));
		assertThat(m.getParameters(), hasItem("$t.k"));
		assertThat(m.getPieces().size(), is(equalTo(7)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("$t.k == 4 && $t.n >= 5")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("12 + 2*$t.n + $t.n^2")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("$t.k == 4 && $t.n >= 3 && $t.n <= 4")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("12 + $t.k + 2*$t.n^2")));
		assertThat(m.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(2).getConstraints(), is(equalTo("$t.k == 4 && $t.n >= 1 && $t.n <= 2")));
		assertThat(m.getPieces().get(2).getPolynomial(), is(equalTo("max(15 + $t.k + $t.n, 12 + 2*$t.n^2, 12 + $t.k + $t.n + $t.n^2)")));
		assertThat(m.getPieces().get(3).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(3).getConstraints(), is(equalTo("$t.n >= -2 && $t.n <= 0")));
		assertThat(m.getPieces().get(3).getPolynomial(), is(equalTo("max(15 + $t.k, 12 + $t.k + $t.n^2)")));
		assertThat(m.getPieces().get(4).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(4).getConstraints(), is(equalTo("$t.n <= -3")));
		assertThat(m.getPieces().get(4).getPolynomial(), is(equalTo("12 + $t.k")));
		assertThat(m.getPieces().get(5).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(5).getConstraints(), is(equalTo("($t.n >= 5 && $t.k <= 3) || ($t.n >= 5 && $t.k >= 5)")));
		assertThat(m.getPieces().get(5).getPolynomial(), is(equalTo("12 + $t.n")));
		assertThat(m.getPieces().get(6).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(6).getConstraints(), is(equalTo("($t.n >= 1 && $t.n <= 4 && $t.k <= 3) || ($t.n >= 1 && $t.n <= 4 && $t.k >= 5)")));
		assertThat(m.getPieces().get(6).getPolynomial(), is(equalTo("max(15, 12 + $t.n^2)"))); 
	}
	
	@Theory
	public void maxOperator(CommandLineCalculator calc) {
		// Con ambos constantes, devuelve el maximo entre ellos
		PiecewiseQuasipolynomial m = calc.max(BarvinokFactory.constant(14L), BarvinokFactory.constant(25L));
		assertThat(m.getParameters().size(), is(equalTo(0)));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("25")));
		
		m = calc.max(BarvinokFactory.constant(25L), BarvinokFactory.constant(14L));
		assertThat(m.getParameters().size(), is(equalTo(0)));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("25")));
		
		// Si ya hay intervalos para alguno, devolvemos la suma
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.add(new QuasiPolynomial("25", "n >= 0"));
		
		m = calc.max(p, BarvinokFactory.constant(14L));
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("39")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n <= -1")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("14")));
		
		m = calc.max(BarvinokFactory.constant(14L), p);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("39")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n <= -1")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("14")));
		
		// El maximo entre un polinomio y una constante es la suma
		p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.add(new QuasiPolynomial("n + 14", "n >= 0"));
		
		m = calc.max(p, BarvinokFactory.constant(14L));
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("28 + n")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n <= -1")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("14")));
		
		m = calc.max(BarvinokFactory.constant(14L), p);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(2)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("28 + n")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n <= -1")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("14")));
		
		
		// El maximo entre dos polinomios es la suma
		p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.add(new QuasiPolynomial("n + 14", "n >= 0"));
		p.add(new QuasiPolynomial("n*n", "n < 0"));
		
		PiecewiseQuasipolynomial q = new PiecewiseQuasipolynomial();
		q.addParameter("n");
		q.add(new QuasiPolynomial("n", "n >= -3"));
		
		m = calc.max(p, q);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(3)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("14 + 2*n")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n <= -1 && n >= -3")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("n + n^2")));
		assertThat(m.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(2).getConstraints(), is(equalTo("n <= -4")));
		assertThat(m.getPieces().get(2).getPolynomial(), is(equalTo("n^2")));
		
		m = calc.max(q, p);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(3)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("14 + 2*n")));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n >= -3 && n <= -1")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo("n + n^2")));
		assertThat(m.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(2).getConstraints(), is(equalTo("n <= -4")));
		assertThat(m.getPieces().get(2).getPolynomial(), is(equalTo("n^2")));
		
		
		// El maximo entre algo e inifinito es la suma (por ende infinito)
		p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.add(new QuasiPolynomial("n + 14", "n >= 0"));
		p.add(new QuasiPolynomial("n*n", "n < 0"));
		
		q = new PiecewiseQuasipolynomial();
		q.addParameter("n");
		q.add(new QuasiPolynomial(BarvinokSyntax.INFINITE, "n >= -3"));
		
		m = calc.max(p, q);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(3)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n <= -1 && n >= -3")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		assertThat(m.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(2).getConstraints(), is(equalTo("n <= -4")));
		assertThat(m.getPieces().get(2).getPolynomial(), is(equalTo("n^2")));
		
		m = calc.max(q, p);
		assertThat(m.getParameters().size(), is(equalTo(1)));
		assertThat(m.getParameters(), hasItem("n"));
		assertThat(m.getPieces().size(), is(equalTo(3)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("n >= 0")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		assertThat(m.getPieces().get(1).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(1).getConstraints(), is(equalTo("n >= -3 && n <= -1")));
		assertThat(m.getPieces().get(1).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		assertThat(m.getPieces().get(2).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(2).getConstraints(), is(equalTo("n <= -4")));
		assertThat(m.getPieces().get(2).getPolynomial(), is(equalTo("n^2")));
	}
	
	@Theory
	public void bugWhenPolynomialHasNoVariables(CommandLineCalculator calc) {
		DomainSet varDomain = new DomainSet("b > 0");
		varDomain.addVariable("b");
		varDomain.addVariable("a");
		
		DomainSet paramDomain = new DomainSet("b > 0");
		paramDomain.addParameter("b");
		paramDomain.addParameter("a");
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.add(new QuasiPolynomial("1"));
		
		
		PiecewiseQuasipolynomial m = calc.sumConsumtion(p, paramDomain);
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("a"));
		assertThat(m.getParameters(), hasItem("b"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("b >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("1")));
		
		m = calc.sumConsumtion(p, varDomain);
		assertThat(m.getParameters().size(), is(equalTo(0)));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		
		m = calc.upperBound(p, paramDomain);
		assertThat(m.getParameters().size(), is(equalTo(2)));
		assertThat(m.getParameters(), hasItem("a"));
		assertThat(m.getParameters(), hasItem("b"));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo("b >= 1")));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("1")));
		
		m = calc.upperBound(p, varDomain);
		assertThat(m.getParameters().size(), is(equalTo(0)));
		assertThat(m.getPieces().size(), is(equalTo(1)));
		assertThat(m.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(m.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(m.getPieces().get(0).getPolynomial(), is(equalTo("1")));
	}
	
	@Theory
	public void testBugOnMaxOperator(CommandLineCalculator calc) {
		QuasiPolynomial p = new QuasiPolynomial("1 + (1/2)*args__length + (1/2)*args__length^2");
		
		PiecewiseQuasipolynomial pqw = new PiecewiseQuasipolynomial();
		pqw.addParameter("args__length");
		pqw.add(p);
		
		calc.add(BarvinokFactory.constant(14L), pqw);
		calc.add(pqw, BarvinokFactory.constant(14L));
	}
}
