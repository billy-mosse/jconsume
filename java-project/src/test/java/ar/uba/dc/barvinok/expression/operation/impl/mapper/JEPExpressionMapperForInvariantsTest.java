package ar.uba.dc.barvinok.expression.operation.impl.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.operation.impl.DefaultKeyGenerator;
import ar.uba.dc.barvinok.expression.operation.impl.JEPExpressionMapper;

@Ignore

public class JEPExpressionMapperForInvariantsTest {

	private JEPExpressionMapper mapper;
	
	@Before
	public void setUp() {
		mapper = new JEPExpressionMapper();
		mapper.setKeyGenerator(new DefaultKeyGenerator());
	}
	
	@Test
	public void convertInvariantsToSimpleForm() {
		DomainSet invariant = new DomainSet("(1 <= i < $t.size And 19 < in_dex && args.length < in_dex) or (in_dex <= 0 and ($t.size == 0 || i <= 0))");
		invariant.addParameter("$t.size");
		invariant.addParameter("args.length");
		invariant.addVariable("i");
		invariant.addVariable("in_dex");
		
		Map<String, String> mapping = new HashMap<String, String>();
		DomainSet result = mapper.simplify(invariant, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getConstraints(), is(equalTo("(1 <= c < a and 19 < d and b < d) or (d <= 0 and (a == 0 or c <= 0))")));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("a"));
		assertThat(result.getParameters(), hasItem("b"));
		assertThat(result.getVariables().size(), is(equalTo(2)));
		assertThat(result.getVariables(), hasItem("c"));
		assertThat(result.getVariables(), hasItem("d"));
		assertThat(mapping.size(), is(equalTo(4)));
		assertThat(mapping.get("a"), is(equalTo("$t.size")));
		assertThat(mapping.get("b"), is(equalTo("args.length")));
		assertThat(mapping.get("c"), is(equalTo("i")));
		assertThat(mapping.get("d"), is(equalTo("in_dex")));
		
	}
	
	@Test
	public void mappingIsDoneByNaturalOrderFirstParametersAndThenVariables() {
		DomainSet invariant = new DomainSet(StringUtils.EMPTY);
		invariant.addParameter("$t.size");
		invariant.addParameter("args.size");
		invariant.addParameter("dex");
		invariant.addVariable("e");
		invariant.addVariable("i");
		invariant.addVariable("iz");
		invariant.addVariable("size");
		invariant.addVariable("$t.values.size");
		invariant.addVariable("in_dex");
		
		Map<String, String> mapping = new HashMap<String, String>();
		DomainSet result = mapper.simplify(invariant, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getParameters().size(), is(equalTo(3)));
		assertThat(result.getParameters(), hasItem("a"));
		assertThat(result.getParameters(), hasItem("b"));
		assertThat(result.getParameters(), hasItem("c"));
		assertThat(result.getVariables().size(), is(equalTo(6)));
		assertThat(result.getVariables(), hasItem("d"));
		assertThat(result.getVariables(), hasItem("e"));
		assertThat(result.getVariables(), hasItem("f"));
		assertThat(result.getVariables(), hasItem("g"));
		assertThat(result.getVariables(), hasItem("h"));
		assertThat(result.getVariables(), hasItem("i"));
		assertThat(mapping.size(), is(equalTo(9)));
		assertThat(mapping.get("a"), is(equalTo("$t.size")));
		assertThat(mapping.get("b"), is(equalTo("args.size")));
		assertThat(mapping.get("c"), is(equalTo("dex")));
		assertThat(mapping.get("d"), is(equalTo("$t.values.size")));
		assertThat(mapping.get("e"), is(equalTo("e")));
		assertThat(mapping.get("f"), is(equalTo("i")));
		assertThat(mapping.get("g"), is(equalTo("in_dex")));
		assertThat(mapping.get("h"), is(equalTo("iz")));
		assertThat(mapping.get("i"), is(equalTo("size")));
	}
	
	@Test
	public void convertInvariantsToExpandedForm() {
		DomainSet invariant = new DomainSet("(1 <= i < $t.size And 19 < in_dex && args.length < in_dex) or (in_dex <= 0 and ($t.size == 0 || i <= 0)) || 1==1");
		invariant.addParameter("$t.size");
		invariant.addParameter("args.length");
		invariant.addVariable("i");
		invariant.addVariable("in_dex");
		
		Map<String, String> mapping = new HashMap<String, String>();
		DomainSet result = mapper.simplify(invariant, mapping);
		result = mapper.expand(result, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getConstraints(), is(equalTo("(1 <= i < $t.size and 19 < in_dex and args.length < in_dex) or (in_dex <= 0 and ($t.size == 0 or i <= 0)) or 1 == 1")));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("$t.size"));
		assertThat(result.getParameters(), hasItem("args.length"));
		assertThat(result.getVariables().size(), is(equalTo(2)));
		assertThat(result.getVariables(), hasItem("i"));
		assertThat(result.getVariables(), hasItem("in_dex"));		
		
		invariant = new DomainSet("$t.size == e and $t.size == i and ( iz < 9 || size == 5 or ( $t.values.size <= in_dex < size  ) )");
		invariant.addParameter("$t.size");
		invariant.addParameter("args.size");
		invariant.addParameter("dex");
		invariant.addVariable("e");
		invariant.addVariable("i");
		invariant.addVariable("iz");
		invariant.addVariable("size");
		invariant.addVariable("$t.values.size");
		invariant.addVariable("in_dex");
		
		mapping = new HashMap<String, String>();
		result = mapper.simplify(invariant, mapping);
		result = mapper.expand(result, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getConstraints(), is(equalTo("$t.size == e and $t.size == i and (iz < 9 or size == 5 or $t.values.size <= in_dex < size)")));
		assertThat(result.getParameters().size(), is(equalTo(3)));
		assertThat(result.getParameters(), hasItem("$t.size"));
		assertThat(result.getParameters(), hasItem("args.size"));
		assertThat(result.getParameters(), hasItem("dex"));
		assertThat(result.getVariables().size(), is(equalTo(6)));
		assertThat(result.getVariables(), hasItem("e"));
		assertThat(result.getVariables(), hasItem("i"));
		assertThat(result.getVariables(), hasItem("iz"));
		assertThat(result.getVariables(), hasItem("size"));
		assertThat(result.getVariables(), hasItem("$t.values.size"));
		assertThat(result.getVariables(), hasItem("in_dex"));
	}
	
	@Test
	public void convertInvariantsToSimpleFormWithEmptyConstraints() {
		DomainSet invariant = new DomainSet(StringUtils.EMPTY);
		invariant.addParameter("$t.size");
		invariant.addParameter("args.size");
		invariant.addVariable("$t.values.size");
		invariant.addVariable("in_dex");
		
		Map<String, String> mapping = new HashMap<String, String>();
		DomainSet result = mapper.simplify(invariant, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("a"));
		assertThat(result.getParameters(), hasItem("b"));
		assertThat(result.getVariables().size(), is(equalTo(2)));
		assertThat(result.getVariables(), hasItem("c"));
		assertThat(result.getVariables(), hasItem("d"));
		assertThat(mapping.size(), is(equalTo(4)));
		assertThat(mapping.get("a"), is(equalTo("$t.size")));
		assertThat(mapping.get("b"), is(equalTo("args.size")));
		assertThat(mapping.get("c"), is(equalTo("$t.values.size")));
		assertThat(mapping.get("d"), is(equalTo("in_dex")));
	}
	
	@Test
	public void convertInvariantsToExpandedFormWithEmptyConstraints() {
		DomainSet invariant = new DomainSet(StringUtils.EMPTY);
		invariant.addParameter("$t.size");
		invariant.addParameter("args.size");
		invariant.addVariable("$t.values.size");
		invariant.addVariable("in_dex");
		
		Map<String, String> mapping = new HashMap<String, String>();
		DomainSet result = mapper.simplify(invariant, mapping);
		result = mapper.expand(result, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("$t.size"));
		assertThat(result.getParameters(), hasItem("args.size"));
		assertThat(result.getVariables().size(), is(equalTo(2)));
		assertThat(result.getVariables(), hasItem("$t.values.size"));
		assertThat(result.getVariables(), hasItem("in_dex"));
		assertThat(mapping.size(), is(equalTo(4)));
		assertThat(mapping.get("a"), is(equalTo("$t.size")));
		assertThat(mapping.get("b"), is(equalTo("args.size")));
		assertThat(mapping.get("c"), is(equalTo("$t.values.size")));
		assertThat(mapping.get("d"), is(equalTo("in_dex")));
	}
	
	@Test
	public void doNotGenerateKeysForVariablesAndParametersThatExistsInTheMapping() {
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("b", "$t.size");
		mapping.put("w", "i");
		
		DomainSet invariant = new DomainSet("(1 <= i < $t.size And 19 < in_dex && args.length < in_dex) or (in_dex <= 0 and ($t.size == 0 || i <= 0))");
		invariant.addParameter("$t.size");
		invariant.addParameter("args.length");
		invariant.addVariable("i");
		invariant.addVariable("in_dex");
		
		DomainSet result = mapper.simplify(invariant, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getConstraints(), is(equalTo("(1 <= w < b and 19 < y and x < y) or (y <= 0 and (b == 0 or w <= 0))")));
		assertThat(result.getParameters().size(), is(equalTo(2)));
		assertThat(result.getParameters(), hasItem("b"));
		assertThat(result.getParameters(), hasItem("x"));
		assertThat(result.getVariables().size(), is(equalTo(2)));
		assertThat(result.getVariables(), hasItem("w"));
		assertThat(result.getVariables(), hasItem("y"));
		assertThat(mapping.size(), is(equalTo(4)));
		assertThat(mapping.get("b"), is(equalTo("$t.size")));
		assertThat(mapping.get("x"), is(equalTo("args.length")));
		assertThat(mapping.get("w"), is(equalTo("i")));
		assertThat(mapping.get("y"), is(equalTo("in_dex")));
	}
}
