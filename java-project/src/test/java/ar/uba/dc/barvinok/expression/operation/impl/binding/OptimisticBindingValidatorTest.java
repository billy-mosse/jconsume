package ar.uba.dc.barvinok.expression.operation.impl.binding;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;
import ar.uba.dc.barvinok.expression.operation.BindingException;
import ar.uba.dc.barvinok.expression.operation.BindingValidator;
import ar.uba.dc.barvinok.expression.operation.impl.OptimisticBindingValidator;
@Ignore

public class OptimisticBindingValidatorTest {

private BindingValidator bindingValidator;
	
	@Before
	public void setUp() {
		bindingValidator = new OptimisticBindingValidator();
	}
	
	@Test
	public void dontThrowExceptionIfAllParametersHaveReferenceInDomain() throws BindingException {
		DomainSet d = new DomainSet(StringUtils.EMPTY);
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.k");
		d.addVariable("$t.j");
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("$t.k");
		p.addParameter("$t.j");
		p.add(new QuasiPolynomial("$t.k + 1"));
		
		bindingValidator.validate(p, d);
		assertThat(d.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(d.getParameters().size(), is(equalTo(1)));
		assertThat(d.getParameters(), hasItem("n"));
		assertThat(d.getVariables().size(), is(equalTo(3)));
		assertThat(d.getVariables(), hasItem("i"));
		assertThat(d.getVariables(), hasItem("$t.k"));
		assertThat(d.getVariables(), hasItem("$t.j"));
		
		assertThat(p.getParameters().size(), is(equalTo(2)));
		assertThat(p.getParameters(), hasItem("$t.k"));
		assertThat(p.getParameters(), hasItem("$t.j"));
		assertThat(p.getPieces().size(), is(equalTo(1)));
		assertThat(p.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(p.getPieces().get(0).getPolynomial(), is(equalTo("$t.k + 1")));
		assertThat(p.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
	}
	
	@Test
	public void throwExceptionIfSomeParameterHasNotReferenceInDomain() throws BindingException {
		DomainSet d = new DomainSet(StringUtils.EMPTY);
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.k");
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("$t.j");
		p.addParameter("$t.k");
		p.addParameter("$t.q");
		p.add(new QuasiPolynomial("$t.k + 1"));
		
		bindingValidator.validate(p, d);
		assertThat(d.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(d.getParameters().size(), is(equalTo(1)));
		assertThat(d.getParameters(), hasItem("n"));
		assertThat(d.getVariables().size(), is(equalTo(2)));
		assertThat(d.getVariables(), hasItem("i"));
		assertThat(d.getVariables(), hasItem("$t.k"));
		
		assertThat(p.getParameters().size(), is(equalTo(1)));
		assertThat(p.getParameters(), hasItem("$t.k"));
		assertThat(p.getPieces().size(), is(equalTo(1)));
		assertThat(p.getPieces().get(0).getVariables().size(), is(equalTo(0)));
		assertThat(p.getPieces().get(0).getPolynomial(), is(equalTo("$t.k + 1")));
		assertThat(p.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
	}
}
