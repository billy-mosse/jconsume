package ar.uba.dc.barvinok.expression.operation.impl.binding;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;
import ar.uba.dc.barvinok.expression.operation.BindingException;
import ar.uba.dc.barvinok.expression.operation.BindingValidator;
import ar.uba.dc.barvinok.expression.operation.impl.ExceptionalBindingValidator;

public class ExceptionalBindingValidatorTest {

	private BindingValidator bindingValidator;
	
	@Before
	public void setUp() {
		bindingValidator = new ExceptionalBindingValidator();
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
	}
	
	@Test
	public void throwExceptionIfSomeParameterHasNotReferenceInDomain() {
		DomainSet d = new DomainSet(StringUtils.EMPTY);
		d.addParameter("n");
		d.addVariable("i");
		d.addVariable("$t.k");
		
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addParameter("$t.j");
		p.addParameter("$t.k");
		p.addParameter("$t.q");
		p.add(new QuasiPolynomial("$t.k + 1"));
		
		try {
			bindingValidator.validate(p, d);
			fail("Tendria que tirar error porque falto bindear el paramtro $t.j");
		} catch (BindingException e) {
			assertThat(e.getParameters().size(), is(equalTo(2)));
			assertThat(e.getParameters(), hasItem("$t.j"));
			assertThat(e.getParameters(), hasItem("$t.q"));
		}
	}
}
