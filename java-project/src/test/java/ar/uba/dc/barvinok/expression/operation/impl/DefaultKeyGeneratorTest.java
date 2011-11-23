package ar.uba.dc.barvinok.expression.operation.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class DefaultKeyGeneratorTest {

	private DefaultKeyGenerator keyGenerator;
	
	@Before
	public void setUp() {
		keyGenerator = new DefaultKeyGenerator();
	}
	
	@Test
	public void startWithLetterA() {
		assertThat(keyGenerator.getNextKey(StringUtils.EMPTY), is(equalTo("a")));
	}
	
	@Test
	public void verifySecuence() {
		assertThat(keyGenerator.getNextKey("z"), is(equalTo("za")));
		assertThat(keyGenerator.getNextKey("zz"), is(equalTo("zza")));
		assertThat(keyGenerator.getNextKey("zzzzzzz"), is(equalTo("zzzzzzza")));
		
		for (String prefix : new String[] { "", "z", "zz", "zzzzz", })
		for (char i = 'a'; i < 'y'; i++) {
			assertThat(keyGenerator.getNextKey(prefix + Character.toString(i)), is(equalTo(prefix + Character.toString(++i))));
		}
	}
	
	@Test
	public void initialKeyIsEmptyIfMappingIsEmtpy() {
		assertThat(keyGenerator.getInitialKey(new HashMap<String, String>()), is(equalTo(StringUtils.EMPTY)));
	}
	
	@Test
	public void initialKeyTakesGreaterValueOfTheMapping() {
		Map<String, String> mapping = new HashMap<String, String>();
		
		mapping.put("a", "something");
		assertThat(keyGenerator.getInitialKey(mapping), is(equalTo("a")));
		
		mapping.put("z", "something_2");
		assertThat(keyGenerator.getInitialKey(mapping), is(equalTo("z")));
		
		mapping.put("zzzg", "something_3");
		assertThat(keyGenerator.getInitialKey(mapping), is(equalTo("zzzg")));
		
		mapping.put("zf", "something_4");
		assertThat(keyGenerator.getInitialKey(mapping), is(equalTo("zzzg")));
		
	}
	
	@Test
	public void initialKeyDoesNotChangeMapping() {
		Map<String, String> mapping = new HashMap<String, String>();
		
		mapping.put("a", "something");
		keyGenerator.getInitialKey(mapping);
		assertThat(mapping.size(), is(equalTo(1)));
		assertThat(mapping, hasEntry("a", "something"));
		
		mapping.put("z", "something_2");
		keyGenerator.getInitialKey(mapping);
		assertThat(mapping.size(), is(equalTo(2)));
		assertThat(mapping, hasEntry("a", "something"));
		assertThat(mapping, hasEntry("z", "something_2"));
		
		mapping.put("zzzg", "something_3");
		keyGenerator.getInitialKey(mapping);
		assertThat(mapping.size(), is(equalTo(3)));
		assertThat(mapping, hasEntry("a", "something"));
		assertThat(mapping, hasEntry("z", "something_2"));
		assertThat(mapping, hasEntry("zzzg", "something_3"));
		
		mapping.put("zf", "something_4");
		keyGenerator.getInitialKey(mapping);
		assertThat(mapping.size(), is(equalTo(4)));
		assertThat(mapping, hasEntry("a", "something"));
		assertThat(mapping, hasEntry("z", "something_2"));
		assertThat(mapping, hasEntry("zzzg", "something_3"));
		assertThat(mapping, hasEntry("zf", "something_4"));
	}
	/*	
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
		mapping.put("s", "something");
		DomainSet result = mapper.simplify(invariant, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getParameters().size(), is(equalTo(3)));
		assertThat(result.getParameters(), hasItem("t"));
		assertThat(result.getParameters(), hasItem("v"));
		assertThat(result.getParameters(), hasItem("x"));
		assertThat(result.getVariables().size(), is(equalTo(6)));
		assertThat(result.getVariables(), hasItem("u"));
		assertThat(result.getVariables(), hasItem("w"));
		assertThat(result.getVariables(), hasItem("y"));
		assertThat(result.getVariables(), hasItem("z"));
		assertThat(result.getVariables(), hasItem("za"));
		assertThat(result.getVariables(), hasItem("zb"));
		assertThat(mapping.size(), is(equalTo(10)));
		assertThat(mapping.get("s"), is(equalTo("something")));
		assertThat(mapping.get("t"), is(equalTo("$t.size")));
		assertThat(mapping.get("u"), is(equalTo("$t.values.size")));
		assertThat(mapping.get("v"), is(equalTo("args.size")));
		assertThat(mapping.get("w"), is(equalTo("in_dex")));
		assertThat(mapping.get("x"), is(equalTo("dex")));
		assertThat(mapping.get("y"), is(equalTo("size")));
		assertThat(mapping.get("z"), is(equalTo("e")));
		assertThat(mapping.get("za"), is(equalTo("iz")));
		assertThat(mapping.get("zb"), is(equalTo("i")));
	}
	
	@Test
	public void simplificationDontUseKeysThatExistInTheMapping() {
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
		mapping.put("zb", "something1");
		mapping.put("zs", "something2");
		DomainSet result = mapper.simplify(invariant, mapping);
		
		assertThat(result, is(notNullValue()));
		assertThat(result.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		assertThat(result.getParameters().size(), is(equalTo(3)));
		assertThat(result.getParameters(), hasItem("zt"));
		assertThat(result.getParameters(), hasItem("zv"));
		assertThat(result.getParameters(), hasItem("zx"));
		assertThat(result.getVariables().size(), is(equalTo(6)));
		assertThat(result.getVariables(), hasItem("zu"));
		assertThat(result.getVariables(), hasItem("zw"));
		assertThat(result.getVariables(), hasItem("zy"));
		assertThat(result.getVariables(), hasItem("zz"));
		assertThat(result.getVariables(), hasItem("zza"));
		assertThat(result.getVariables(), hasItem("zzb"));
		assertThat(mapping.size(), is(equalTo(11)));
		assertThat(mapping.get("zb"), is(equalTo("something1")));
		assertThat(mapping.get("zs"), is(equalTo("something2")));
		assertThat(mapping.get("zt"), is(equalTo("$t.size")));
		assertThat(mapping.get("zu"), is(equalTo("$t.values.size")));
		assertThat(mapping.get("zv"), is(equalTo("args.size")));
		assertThat(mapping.get("zw"), is(equalTo("in_dex")));
		assertThat(mapping.get("zx"), is(equalTo("dex")));
		assertThat(mapping.get("zy"), is(equalTo("size")));
		assertThat(mapping.get("zz"), is(equalTo("e")));
		assertThat(mapping.get("zza"), is(equalTo("iz")));
		assertThat(mapping.get("zzb"), is(equalTo("i")));
	}*/
	
}
