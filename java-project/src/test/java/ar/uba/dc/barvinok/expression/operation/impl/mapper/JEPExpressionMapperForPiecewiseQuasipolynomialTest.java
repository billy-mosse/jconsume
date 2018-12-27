package ar.uba.dc.barvinok.expression.operation.impl.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;
import ar.uba.dc.barvinok.expression.operation.impl.DefaultKeyGenerator;
import ar.uba.dc.barvinok.expression.operation.impl.JEPExpressionMapper;
@Ignore

public class JEPExpressionMapperForPiecewiseQuasipolynomialTest {

	private JEPExpressionMapper mapper;
	
	@Before
	public void setUp() {
		mapper = new JEPExpressionMapper();
		mapper.setKeyGenerator(new DefaultKeyGenerator());
	}
	
	@Test
	public void convertPolynomialToSimpleForm() {
		PiecewiseQuasipolynomial pwq = new PiecewiseQuasipolynomial();
		pwq.addParameter("args.length");
		pwq.addParameter("n");
		pwq.add(new QuasiPolynomial("4*i+5*(upToSomething.size^2)^2-5/2*cant", "(cant == 4 and i >= 25) || (upToSomething.size == n && i > 45) or (n==1)", setForVariables(new String[] {"i", "cant", "upToSomething.size"})));
		pwq.add(new QuasiPolynomial("4*i+5*w^2", "(w == 4 and args.length > i >= 25)", setForVariables(new String[] {"w", "i"})));
	
		Map<String, String> mapping = new HashMap<String, String>();
		PiecewiseQuasipolynomial result = mapper.simplify(pwq, mapping, StringUtils.EMPTY);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("a"));
		assertThat(result.getParameters(), hasItem("b"));
		assertThat(result.getPieces().size(), is(2));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo("4*d + 5*(e^2)^2 - (5/2)*c")));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("(c == 4 and d >= 25) or (e == b and d > 45) or b == 1")));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(3)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("c"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("d"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("e"));
		assertThat(result.getPieces().get(1).getPolynomial(), is(equalTo("4*d + 5*f^2")));
		assertThat(result.getPieces().get(1).getConstraints(), is(equalTo("f == 4 and a > d >= 25")));
		assertThat(result.getPieces().get(1).getVariables().size(), is(equalTo(2)));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("d"));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("f"));
		assertThat(mapping.size(), is(equalTo(6)));
		assertThat(mapping.get("a"), is(equalTo("args.length")));
		assertThat(mapping.get("b"), is(equalTo("n")));
		assertThat(mapping.get("c"), is(equalTo("cant")));
		assertThat(mapping.get("d"), is(equalTo("i")));
		assertThat(mapping.get("e"), is(equalTo("upToSomething.size")));
		assertThat(mapping.get("f"), is(equalTo("w")));
		
		
		mapping.clear();
		result = mapper.simplify(pwq, mapping, "$t.");
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("a"));
		assertThat(result.getParameters(), hasItem("b"));
		assertThat(result.getPieces().size(), is(2));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo("4*d + 5*(e^2)^2 - (5/2)*c")));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("(c == 4 and d >= 25) or (e == b and d > 45) or b == 1")));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(3)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("c"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("d"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("e"));
		assertThat(result.getPieces().get(1).getPolynomial(), is(equalTo("4*d + 5*f^2")));
		assertThat(result.getPieces().get(1).getConstraints(), is(equalTo("f == 4 and a > d >= 25")));
		assertThat(result.getPieces().get(1).getVariables().size(), is(equalTo(2)));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("d"));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("f"));
		assertThat(mapping.size(), is(equalTo(6)));
		assertThat(mapping.get("a"), is(equalTo("$t.args.length")));
		assertThat(mapping.get("b"), is(equalTo("$t.n")));
		assertThat(mapping.get("c"), is(equalTo("cant")));
		assertThat(mapping.get("d"), is(equalTo("i")));
		assertThat(mapping.get("e"), is(equalTo("upToSomething.size")));
		assertThat(mapping.get("f"), is(equalTo("w")));
	}

	@Test
	public void mappingIsDoneByNaturalOrderFirstParametersAndThenVariablesInPolynomialOrder() {
		PiecewiseQuasipolynomial pwq = new PiecewiseQuasipolynomial();
		pwq.addParameter("args.length");
		pwq.addParameter("n");
		pwq.addParameter("a");
		pwq.addParameter("Pablito");
		pwq.add(new QuasiPolynomial("4*i+5*(upToSomething.size^2)^2-5/2*cant", "(cant == 4 and i >= 25) || (upToSomething.size == n && i > 45) or (n==1)", setForVariables(new String[] {"i", "cant", "upToSomething.size"})));
		pwq.add(new QuasiPolynomial("4*i+5*w^2", "(w == 4 and args.length > i >= 25)", setForVariables(new String[] {"w", "i"})));
		pwq.add(new QuasiPolynomial("4*i+5*w^2", StringUtils.EMPTY, setForVariables(new String[] {"w", "i", "u"})));
	
		Map<String, String> mapping = new HashMap<String, String>();
		PiecewiseQuasipolynomial result = mapper.simplify(pwq, mapping, StringUtils.EMPTY);
		
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(4)));
		assertThat(result.getParameters(), hasItem("a"));
		assertThat(result.getParameters(), hasItem("b"));
		assertThat(result.getParameters(), hasItem("c"));
		assertThat(result.getParameters(), hasItem("d"));
		assertThat(result.getPieces().size(), is(3));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo("4*f + 5*(g^2)^2 - (5/2)*e")));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("(e == 4 and f >= 25) or (g == d and f > 45) or d == 1")));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(3)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("e"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("f"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("g"));
		assertThat(result.getPieces().get(1).getPolynomial(), is(equalTo("4*f + 5*h^2")));
		assertThat(result.getPieces().get(1).getConstraints(), is(equalTo("h == 4 and c > f >= 25")));
		assertThat(result.getPieces().get(1).getVariables().size(), is(equalTo(2)));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("f"));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("h"));
		assertThat(result.getPieces().get(2).getPolynomial(), is(equalTo("4*f + 5*h^2")));
		assertThat(result.getPieces().get(2).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(2).getVariables().size(), is(equalTo(3)));
		assertThat(result.getPieces().get(2).getVariables(), hasItem("f"));
		assertThat(result.getPieces().get(2).getVariables(), hasItem("h"));
		assertThat(result.getPieces().get(2).getVariables(), hasItem("i"));
		assertThat(mapping.size(), is(equalTo(9)));
		assertThat(mapping.get("a"), is(equalTo("Pablito")));
		assertThat(mapping.get("b"), is(equalTo("a")));
		assertThat(mapping.get("c"), is(equalTo("args.length")));
		assertThat(mapping.get("d"), is(equalTo("n")));
		assertThat(mapping.get("e"), is(equalTo("cant")));
		assertThat(mapping.get("f"), is(equalTo("i")));
		assertThat(mapping.get("g"), is(equalTo("upToSomething.size")));
		assertThat(mapping.get("h"), is(equalTo("w")));
		assertThat(mapping.get("i"), is(equalTo("u")));
	}
	
	@Test
	public void convertPolynomialToExpandedForm() {
		PiecewiseQuasipolynomial pwq = new PiecewiseQuasipolynomial();
		pwq.addParameter("args.length");
		pwq.addParameter("n");
		pwq.add(new QuasiPolynomial("4*i+5*(upToSomething.size^2)^2-5/2*cant", "(cant == 4 and i >= 25) || (upToSomething.size == n && i > 45) or (n==1)", setForVariables(new String[] {"i", "cant", "upToSomething.size"})));
		pwq.add(new QuasiPolynomial("4*i+5*w^2", "(w == 4 and args.length > i >= 25)", setForVariables(new String[] {"w", "i"})));
	
		Map<String, String> mapping = new HashMap<String, String>();
		PiecewiseQuasipolynomial result = mapper.simplify(pwq, mapping, StringUtils.EMPTY);
		result = mapper.expand(result, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("args.length"));
		assertThat(result.getParameters(), hasItem("n"));
		assertThat(result.getPieces().size(), is(2));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo("4*i + 5*(upToSomething.size^2)^2 - (5/2)*cant")));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("(cant == 4 and i >= 25) or (upToSomething.size == n and i > 45) or n == 1")));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(3)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("i"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("cant"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("upToSomething.size"));
		assertThat(result.getPieces().get(1).getPolynomial(), is(equalTo("4*i + 5*w^2")));
		assertThat(result.getPieces().get(1).getConstraints(), is(equalTo("w == 4 and args.length > i >= 25")));
		assertThat(result.getPieces().get(1).getVariables().size(), is(equalTo(2)));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("w"));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("i"));
		assertThat(mapping.size(), is(equalTo(6)));
		assertThat(mapping.get("a"), is(equalTo("args.length")));
		assertThat(mapping.get("b"), is(equalTo("n")));
		assertThat(mapping.get("c"), is(equalTo("cant")));
		assertThat(mapping.get("d"), is(equalTo("i")));
		assertThat(mapping.get("e"), is(equalTo("upToSomething.size")));
		assertThat(mapping.get("f"), is(equalTo("w")));
		
		mapping.clear();
		result = mapper.simplify(pwq, mapping, "$t.");
		result = mapper.expand(result, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("$t.args.length"));
		assertThat(result.getParameters(), hasItem("$t.n"));
		assertThat(result.getPieces().size(), is(2));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo("4*i + 5*(upToSomething.size^2)^2 - (5/2)*cant")));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("(cant == 4 and i >= 25) or (upToSomething.size == $t.n and i > 45) or $t.n == 1")));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(3)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("upToSomething.size"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("i"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("cant"));
		assertThat(result.getPieces().get(1).getPolynomial(), is(equalTo("4*i + 5*w^2")));
		assertThat(result.getPieces().get(1).getConstraints(), is(equalTo("w == 4 and $t.args.length > i >= 25")));
		assertThat(result.getPieces().get(1).getVariables().size(), is(equalTo(2)));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("w"));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("i"));
		assertThat(mapping.size(), is(equalTo(6)));
		assertThat(mapping.get("a"), is(equalTo("$t.args.length")));
		assertThat(mapping.get("b"), is(equalTo("$t.n")));
		assertThat(mapping.get("c"), is(equalTo("cant")));
		assertThat(mapping.get("d"), is(equalTo("i")));
		assertThat(mapping.get("e"), is(equalTo("upToSomething.size")));
		assertThat(mapping.get("f"), is(equalTo("w")));
		
		
		pwq = new PiecewiseQuasipolynomial();
		pwq.addParameter("args.length");
		pwq.addParameter("n");
		pwq.addParameter("a");
		pwq.addParameter("Pablito");
		pwq.add(new QuasiPolynomial("4*i+5*(upToSomething.size^2)^2-5/2*cant", "(cant == 4 and i >= 25) || (upToSomething.size == n && i > 45) or (n==1)", setForVariables(new String[] {"i", "cant", "upToSomething.size"})));
		pwq.add(new QuasiPolynomial("4*i+5*w^2", "(w == 4 and args.length > i >= 25)", setForVariables(new String[] {"w", "i"})));
		pwq.add(new QuasiPolynomial("4*i+5*w^2", StringUtils.EMPTY, setForVariables(new String[] {"w", "i", "u"})));
	
		mapping.clear();
		result = mapper.simplify(pwq, mapping, StringUtils.EMPTY);
		result = mapper.expand(result, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(4)));
		assertThat(result.getParameters(), hasItem("Pablito"));
		assertThat(result.getParameters(), hasItem("args.length"));
		assertThat(result.getParameters(), hasItem("n"));
		assertThat(result.getParameters(), hasItem("a"));
		assertThat(result.getPieces().size(), is(3));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo("4*i + 5*(upToSomething.size^2)^2 - (5/2)*cant")));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("(cant == 4 and i >= 25) or (upToSomething.size == n and i > 45) or n == 1")));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(3)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("cant"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("i"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("upToSomething.size"));
		assertThat(result.getPieces().get(1).getPolynomial(), is(equalTo("4*i + 5*w^2")));
		assertThat(result.getPieces().get(1).getConstraints(), is(equalTo("w == 4 and args.length > i >= 25")));
		assertThat(result.getPieces().get(1).getVariables().size(), is(equalTo(2)));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("w"));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("i"));
		assertThat(result.getPieces().get(2).getPolynomial(), is(equalTo("4*i + 5*w^2")));
		assertThat(result.getPieces().get(2).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(2).getVariables().size(), is(equalTo(3)));
		assertThat(result.getPieces().get(2).getVariables(), hasItem("w"));
		assertThat(result.getPieces().get(2).getVariables(), hasItem("i"));
		assertThat(result.getPieces().get(2).getVariables(), hasItem("u"));
		assertThat(mapping.size(), is(equalTo(9)));
		assertThat(mapping.get("a"), is(equalTo("Pablito")));
		assertThat(mapping.get("b"), is(equalTo("a")));
		assertThat(mapping.get("c"), is(equalTo("args.length")));
		assertThat(mapping.get("d"), is(equalTo("n")));
		assertThat(mapping.get("e"), is(equalTo("cant")));
		assertThat(mapping.get("f"), is(equalTo("i")));
		assertThat(mapping.get("g"), is(equalTo("upToSomething.size")));
		assertThat(mapping.get("h"), is(equalTo("w")));
		assertThat(mapping.get("i"), is(equalTo("u")));
	}
	
	@Test
	public void convertPolynomialToSimpleFormWithEmptyConstraints() {
		PiecewiseQuasipolynomial pwq = new PiecewiseQuasipolynomial();
		pwq.addParameter("args.length");
		pwq.addParameter("n");
		pwq.add(new QuasiPolynomial(StringUtils.EMPTY, StringUtils.EMPTY, setForVariables(new String[] {"i", "cant", "upToSomething.size"})));
		pwq.add(new QuasiPolynomial(StringUtils.EMPTY, StringUtils.EMPTY, setForVariables(new String[] {"w", "i"})));
	
		Map<String, String> mapping = new HashMap<String, String>();
		PiecewiseQuasipolynomial result = mapper.simplify(pwq, mapping, StringUtils.EMPTY);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("a"));
		assertThat(result.getParameters(), hasItem("b"));
		assertThat(result.getPieces().size(), is(2));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(3)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("c"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("d"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("e"));
		assertThat(result.getPieces().get(1).getPolynomial(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(1).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(1).getVariables().size(), is(equalTo(2)));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("d"));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("f"));
		assertThat(mapping.size(), is(equalTo(6)));
		assertThat(mapping.get("a"), is(equalTo("args.length")));
		assertThat(mapping.get("b"), is(equalTo("n")));
		assertThat(mapping.get("c"), is(equalTo("cant")));
		assertThat(mapping.get("d"), is(equalTo("i")));
		assertThat(mapping.get("e"), is(equalTo("upToSomething.size")));
		assertThat(mapping.get("f"), is(equalTo("w")));
	}
	
	@Test
	public void convertPolynomialToExpandedFormWithEmptyConstraints() {
		PiecewiseQuasipolynomial pwq = new PiecewiseQuasipolynomial();
		pwq.addParameter("args.length");
		pwq.addParameter("n");
		pwq.add(new QuasiPolynomial(StringUtils.EMPTY, StringUtils.EMPTY, setForVariables(new String[] {"i", "cant", "upToSomething.size"})));
		pwq.add(new QuasiPolynomial(StringUtils.EMPTY, StringUtils.EMPTY, setForVariables(new String[] {"w", "i"})));
	
		Map<String, String> mapping = new HashMap<String, String>();
		PiecewiseQuasipolynomial result = mapper.simplify(pwq, mapping, StringUtils.EMPTY);
		result = mapper.expand(result, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("args.length"));
		assertThat(result.getParameters(), hasItem("n"));
		assertThat(result.getPieces().size(), is(2));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(3)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("i"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("cant"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("upToSomething.size"));
		assertThat(result.getPieces().get(1).getPolynomial(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(1).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(1).getVariables().size(), is(equalTo(2)));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("w"));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("i"));
		assertThat(mapping.size(), is(equalTo(6)));
		assertThat(mapping.get("a"), is(equalTo("args.length")));
		assertThat(mapping.get("b"), is(equalTo("n")));
		assertThat(mapping.get("c"), is(equalTo("cant")));
		assertThat(mapping.get("d"), is(equalTo("i")));
		assertThat(mapping.get("e"), is(equalTo("upToSomething.size")));
		assertThat(mapping.get("f"), is(equalTo("w")));
	}
	
	@Test
	public void doNotGenerateKeysForVariablesAndParametersThatExistsInTheMapping() {
		PiecewiseQuasipolynomial pwq = new PiecewiseQuasipolynomial();
		pwq.addParameter("args.length");
		pwq.addParameter("n");
		pwq.add(new QuasiPolynomial(StringUtils.EMPTY, StringUtils.EMPTY, setForVariables(new String[] {"i", "cant", "upToSomething.size"})));
		pwq.add(new QuasiPolynomial(StringUtils.EMPTY, StringUtils.EMPTY, setForVariables(new String[] {"w", "i"})));
	
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("a", "pepe");
		mapping.put("x", "args.length");
		mapping.put("g", "i");
		
		PiecewiseQuasipolynomial result = mapper.simplify(pwq, mapping, StringUtils.EMPTY);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("x"));
		assertThat(result.getParameters(), hasItem("y"));
		assertThat(result.getPieces().size(), is(2));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(3)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("z"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("g"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("za"));
		assertThat(result.getPieces().get(1).getPolynomial(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(1).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(1).getVariables().size(), is(equalTo(2)));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("g"));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("zb"));
		assertThat(mapping.size(), is(equalTo(7)));
		assertThat(mapping.get("a"), is(equalTo("pepe")));
		assertThat(mapping.get("g"), is(equalTo("i")));
		assertThat(mapping.get("x"), is(equalTo("args.length")));
		assertThat(mapping.get("y"), is(equalTo("n")));
		assertThat(mapping.get("z"), is(equalTo("cant")));
		assertThat(mapping.get("za"), is(equalTo("upToSomething.size")));
		assertThat(mapping.get("zb"), is(equalTo("w")));
		
		pwq = new PiecewiseQuasipolynomial();
		pwq.addParameter("args.length");
		pwq.addParameter("n");
		pwq.add(new QuasiPolynomial(StringUtils.EMPTY, StringUtils.EMPTY, setForVariables(new String[] {"i", "cant", "upToSomething.size"})));
		pwq.add(new QuasiPolynomial(StringUtils.EMPTY, StringUtils.EMPTY, setForVariables(new String[] {"w", "i"})));
	
		mapping = new HashMap<String, String>();
		mapping.put("a", "pepe");
		mapping.put("x", "$t.args.length");
		mapping.put("g", "i");
		
		result = mapper.simplify(pwq, mapping, "$t.");
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("x"));
		assertThat(result.getParameters(), hasItem("y"));
		assertThat(result.getPieces().size(), is(2));
		assertThat(result.getPieces().get(0).getPolynomial(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(3)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("z"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("g"));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("za"));
		assertThat(result.getPieces().get(1).getPolynomial(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(1).getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getPieces().get(1).getVariables().size(), is(equalTo(2)));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("g"));
		assertThat(result.getPieces().get(1).getVariables(), hasItem("zb"));
		assertThat(mapping.size(), is(equalTo(7)));
		assertThat(mapping.get("a"), is(equalTo("pepe")));
		assertThat(mapping.get("g"), is(equalTo("i")));
		assertThat(mapping.get("x"), is(equalTo("$t.args.length")));
		assertThat(mapping.get("y"), is(equalTo("$t.n")));
		assertThat(mapping.get("z"), is(equalTo("cant")));
		assertThat(mapping.get("za"), is(equalTo("upToSomething.size")));
		assertThat(mapping.get("zb"), is(equalTo("w")));
	}

	@Test
	public void supportExistsSyntax() {
		PiecewiseQuasipolynomial pwq = new PiecewiseQuasipolynomial();		
		
		pwq.addParameter("list.size");
		pwq.add(new QuasiPolynomial("1/2 * i", "exists (e0 == [(i)/2], e1, e2 == [i/4]: 2e0 == i and i == list.size and list.size == 4)", setForVariables(new String[] {"i"})));
	
		Map<String, String> mapping = new HashMap<String, String>();
	
		PiecewiseQuasipolynomial result = mapper.simplify(pwq, mapping, StringUtils.EMPTY);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(1)));
		assertThat(result.getParameters(), hasItem("a"));
		assertThat(result.getPieces().size(), is(1));
		assertThat(result.getPieces().get(0).getPolynomial(), is("(1/2)*b"));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("exists(e0 == [b/2], e1, e2 == [b/4] : 2*e0 == b and b == a and a == 4)")));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(1)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("b"));
		assertThat(mapping.size(), is(equalTo(2)));
		assertThat(mapping.get("a"), is(equalTo("list.size")));
		assertThat(mapping.get("b"), is(equalTo("i")));
		
		
		pwq = new PiecewiseQuasipolynomial();		
		
		pwq.addParameter("n");
		pwq.add(new QuasiPolynomial("1/2 * i", "(exists e0 == [i/2], e1, e2 == [i/4] : 2*e0 == i and 4e2 == i and n == i and n == 4)", setForVariables(new String[] {"i"})));
	
		mapping = new HashMap<String, String>();
	
		result = mapper.simplify(pwq, mapping, StringUtils.EMPTY);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(1)));
		assertThat(result.getParameters(), hasItem("a"));
		assertThat(result.getPieces().size(), is(1));
		assertThat(result.getPieces().get(0).getPolynomial(), is("(1/2)*b"));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("exists(e0 == [b/2], e1, e2 == [b/4] : 2*e0 == b and 4*e2 == b and a == b and a == 4)")));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(1)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("b"));
		assertThat(mapping.size(), is(equalTo(2)));
		assertThat(mapping.get("a"), is(equalTo("n")));
		assertThat(mapping.get("b"), is(equalTo("i")));
		
		pwq = new PiecewiseQuasipolynomial();		
		
		pwq.addParameter("n");
		pwq.add(new QuasiPolynomial("2*n*i - n*n + 3*n - 1/2*i*i - 3/2*i-1", "(exists j : 0 <= i < 4*n-1 and 0 <= j < n and 2*n-1 <= i+j <= 4*n-2 and i <= 2*n-1 )", setForVariables(new String[] {"i"})));
	
		mapping = new HashMap<String, String>();
	
		result = mapper.simplify(pwq, mapping, StringUtils.EMPTY);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getParameters().size(), is(equalTo(1)));
		assertThat(result.getParameters(), hasItem("a"));
		assertThat(result.getPieces().size(), is(1));
		assertThat(result.getPieces().get(0).getPolynomial(), is("2*a*b - a*a + 3*a - (1/2)*b*b - (3/2)*b - 1"));
		assertThat(result.getPieces().get(0).getConstraints(), is(equalTo("exists(j : 0 <= b < 4*a - 1 and 0 <= j < a and 2*a - 1 <= b + j <= 4*a - 2 and b <= 2*a - 1)")));
		assertThat(result.getPieces().get(0).getVariables().size(), is(equalTo(1)));
		assertThat(result.getPieces().get(0).getVariables(), hasItem("b"));
		assertThat(mapping.size(), is(equalTo(2)));
		assertThat(mapping.get("a"), is(equalTo("n")));
		assertThat(mapping.get("b"), is(equalTo("i")));
	}
	
	private Set<String> setForVariables(String[] strings) {
		Set<String> vars = new TreeSet<String>();
		
		for (String s : strings) {
			vars.add(s);
		}
		
		return vars;
	}
}
