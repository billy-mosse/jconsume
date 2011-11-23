package ar.uba.dc.barvinok;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hamcrest.core.IsInstanceOf;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import ar.uba.dc.barvinok.calculators.AddAsCompareStrategy;
import ar.uba.dc.barvinok.calculators.CommandLineCalculator;
import ar.uba.dc.barvinok.calculators.LazyAsCompareStrategy;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;
import ar.uba.dc.barvinok.expression.operation.BindingException;
import ar.uba.dc.barvinok.expression.operation.impl.DefaultDomainUnifier;
import ar.uba.dc.barvinok.expression.operation.impl.DefaultKeyGenerator;
import ar.uba.dc.barvinok.expression.operation.impl.ExceptionalBindingValidator;
import ar.uba.dc.barvinok.expression.operation.impl.JEPExpressionMapper;
import ar.uba.dc.barvinok.expression.operation.impl.OptimisticBindingValidator;
import ar.uba.dc.barvinok.expression.operation.impl.PesimisticBindingValidator;
import ar.uba.dc.barvinok.expression.operation.impl.ToStringParserVisitor;
import ar.uba.dc.barvinok.syntax.IsccSyntax;
import ar.uba.dc.barvinok.syntax.OmegaSyntax;
import ar.uba.dc.util.ConsoleException;

@RunWith(Theories.class)
public class BarvinokCalculatorBindingProblemsTest {

	@DataPoints
	public static CommandLineCalculator[] calculators() {
		CommandLineBarvinokExecutor executor = new CommandLineBarvinokExecutor();
		executor.setBarvinokCmdPath("./bin/barvinok.sh");
		JEPExpressionMapper mapper = new JEPExpressionMapper();
		mapper.setKeyGenerator(new DefaultKeyGenerator());
		mapper.setToStringVisitor(new ToStringParserVisitor());
		
		BarvinokSyntax[] syntaxes = new BarvinokSyntax[] { new OmegaSyntax(), new IsccSyntax() };
		ComparePolynomialStrategy[] strategies = new ComparePolynomialStrategy[] { new LazyAsCompareStrategy(), new AddAsCompareStrategy() };
		List<CommandLineCalculator> calculators = new LinkedList<CommandLineCalculator>();
		
		for (BarvinokSyntax syntax : syntaxes) {
			for (ComparePolynomialStrategy strategy : strategies) {
				CommandLineCalculator calc = new CommandLineCalculator();
				calc.setExecutor(executor);
				calc.setMapper(mapper);
				calc.setPrefixForPolinomialParametersInRangedOperations("$t.");
				calc.setBarvinokSupportInfiniteOnSumOperator(false);
				calc.setDomainUnifier(new DefaultDomainUnifier());
				calc.setCompareStrategy(strategy);
				calc.setSyntax(syntax);
				calc.setBindingValidator(null);
				calculators.add(calc);
			}
		}
		
		return calculators.toArray(new CommandLineCalculator[] {});
	}
	
	@Theory
	public void throwExceptionWithExceptionalBindingValidator(CommandLineCalculator calc) {
		calc.setBindingValidator(new ExceptionalBindingValidator());
		
		DomainSet d = new DomainSet("0 <= i <= n and i == $t.n");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.addParameter("w");
		p.add(new QuasiPolynomial("n*n", "n >= 1"));
		
		// El binding se verifica en el sum
		try {
			calc.sumConsumtion(p, d);
			fail("Deberia haber dado una excepcion. Falto el binding para el parametro 'w'");
		} catch (BarvinokException e) {
			assertThat(e.getCause(), new IsInstanceOf(BindingException.class));
			Set<String> params = ((BindingException) e.getCause()).getParameters();
			assertThat(params.size(), is(equalTo(1)));
			assertThat(params, hasItem("$t.w"));
		}
		
		// El binding se verifica en el ub
		try {
			calc.upperBound(p, d);
			fail("Deberia haber dado una excepcion. Falto el binding para el parametro 'w'");
		} catch (BarvinokException e) {
			assertThat(e.getCause(), new IsInstanceOf(BindingException.class));
			Set<String> params = ((BindingException) e.getCause()).getParameters();
			assertThat(params.size(), is(equalTo(1)));
			assertThat(params, hasItem("$t.w"));
		}
		
		// El binding se verifica en el .
		try {
			p.getPieces().get(0).setPolynomial("max(n*n, 4)");
			calc.upperBound(p, d);
			fail("Deberia haber dado una excepcion. Falto el binding para el parametro 'w'");
		} catch (BarvinokException e) {
			assertThat(e.getCause(), new IsInstanceOf(BindingException.class));
			Set<String> params = ((BindingException) e.getCause()).getParameters();
			assertThat(params.size(), is(equalTo(1)));
			assertThat(params, hasItem("$t.w"));
		}
	}
	
	@Theory
	public void returnInfiniteWithPesimisticBindingValidator(CommandLineCalculator calc) {
		calc.setBindingValidator(new PesimisticBindingValidator());
		
		DomainSet d = new DomainSet("0 <= i <= n and i == $t.n");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.addParameter("w");
		p.add(new QuasiPolynomial("n*n", "n >= 1"));
		
		// El binding se verifica en el sum
		PiecewiseQuasipolynomial result = calc.sumConsumtion(p, d);
		assertThat(result.getParameters().size(), is(equalTo(1)));
		assertThat(result.getParameters(), hasItem("n"));
		assertThat(result.getPieces().size(), is(equalTo(1)));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo(BarvinokSyntax.INFINITE)));
		
		// El binding se verifica en el ub
		result = calc.upperBound(p, d);
		assertThat(result.getParameters().size(), is(equalTo(1)));
		assertThat(result.getParameters(), hasItem("n"));
		assertThat(result.getPieces().size(), is(equalTo(1)));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo("n^2")));
		
		if (calc.getSyntax() instanceof IsccSyntax && calc.getCompareStrategy() instanceof LazyAsCompareStrategy) {
			// El binding se verifica en el .
			p.getPieces().get(0).setPolynomial("max(n*n, 4)");
			result = calc.upperBound(p, d);
			assertThat(result.getParameters().size(), is(equalTo(1)));
			assertThat(result.getParameters(), hasItem("n"));
			assertThat(result.getPieces().size(), is(equalTo(1)));
			assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(0)));
			assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
			assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo("max(n^2, 4)")));
		}
	}
	
	@Theory
	public void returnCorrectValueWithOptimisticBindingValidatorIfParametersIsNotPartOfPolynomial(CommandLineCalculator calc) {
		calc.setBindingValidator(new OptimisticBindingValidator());
		
		DomainSet d = new DomainSet("0 <= i <= n and i == $t.n");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.addParameter("w");
		p.add(new QuasiPolynomial("n", "n >= 1"));
		
		// El binding se verifica en el sum
		PiecewiseQuasipolynomial result = calc.sumConsumtion(p, d);
		assertThat(result.getParameters().size(), is(equalTo(1)));
		assertThat(result.getParameters(), hasItem("n"));
		assertThat(result.getPieces().size(), is(equalTo(1)));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo("(1/2)*n + (1/2)*n^2")));
		
		// El binding se verifica en el ub
		result = calc.upperBound(p, d);
		assertThat(result.getParameters().size(), is(equalTo(1)));
		assertThat(result.getParameters(), hasItem("n"));
		assertThat(result.getPieces().size(), is(equalTo(1)));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo("n")));
		
		if (calc.getSyntax() instanceof IsccSyntax && calc.getCompareStrategy() instanceof LazyAsCompareStrategy) {
			// El binding se verifica en el .
			p.getPieces().get(0).setPolynomial("max(n*n, 4)");
			result = calc.upperBound(p, d);
			assertThat(result.getParameters().size(), is(equalTo(1)));
			assertThat(result.getParameters(), hasItem("n"));
			assertThat(result.getPieces().size(), is(equalTo(1)));
			assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(0)));
			assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("n >= 1")));
			assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo("max(n^2, 4)")));
		}
	}
	
	@Theory
	public void throwExceptionWithOptimisticBindingValidatorIfParametersIsPartOfPolynomial(CommandLineCalculator calc) {
		calc.setBindingValidator(new OptimisticBindingValidator());
		
		DomainSet d = new DomainSet("0 <= i <= n and i == $t.n");
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.n");
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("n");
		p.addParameter("w");
		p.add(new QuasiPolynomial("w", "n >= 1"));
		
		// El binding se verifica en el sum
		try {
			calc.sumConsumtion(p, d);
			fail("Deberia haber dado error porque faltaba una variable");
		} catch (ConsoleException e) {
			assertThat(e.getMessage(), containsString("unknown identifier"));
		}
		
		// El binding se verifica en el ub
		try  {
			calc.upperBound(p, d);
			fail("Deberia haber dado error porque faltaba una variable");
		} catch (ConsoleException e) {
			assertThat(e.getMessage(), containsString("unknown identifier"));
		}
				
		if (calc.getSyntax() instanceof IsccSyntax && calc.getCompareStrategy() instanceof LazyAsCompareStrategy) {
			// El binding se verifica en el .
			p.getPieces().get(0).setPolynomial("max(w*w, 4)");
			try {
				calc.upperBound(p, d);
				fail("Deberia haber dado error porque faltaba una variable");
			} catch (ConsoleException e) {
				assertThat(e.getMessage(), containsString("unknown identifier"));
			}
		}
	}
}
