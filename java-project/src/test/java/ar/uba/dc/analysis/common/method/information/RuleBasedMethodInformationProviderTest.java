package ar.uba.dc.analysis.common.method.information;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

import soot.SootMethod;
import ar.uba.dc.analysis.common.method.information.rules.RuleSetReader;
import ar.uba.dc.analysis.common.method.information.rules.XMLRuleSetRepository;
import ar.uba.dc.soot.SootUtils;

//TODO signum es un metodo existente?
public class RuleBasedMethodInformationProviderTest {
	
	private RuleBasedMethodInformationProvider provider;

	@Before
	public void setUp() {
		XMLRuleSetRepository repository = new XMLRuleSetRepository();
		repository.setReader(new RuleSetReader());
		repository.setRulesFile(Thread.currentThread().getContextClassLoader().getResource("unanalizable_rules.xml").getFile());
		provider = new RuleBasedMethodInformationProvider();
		provider.setRepository(repository);
	}

	@Test
	public void cacheWorksWithNull() {
		SootMethod method = SootUtils.getMethod("java.lang.Integer", "int signum(int)");
				
		boolean result = provider.isAnalyzable(method);
		
		assertThat(result, is(equalTo(true)));
		
		result = provider.isAnalyzable(method);
		
		assertThat(result, is(equalTo(true)));
	}
	
	@Test
	public void testMethodClasifications() {
		SootMethod prueWithConsume = SootUtils.getMethod("java.lang.Integer", "java.lang.Integer valueOf(java.lang.String)");
		SootMethod prueWithoutConsume = SootUtils.getMethod("java.lang.StringBuilder", "java.lang.String toString()");
		SootMethod alter = SootUtils.getMethod("java.lang.System", "void arraycopy(java.lang.Object,int,java.lang.Object,int,int)");
		SootMethod impure = SootUtils.getMethod("java.io.BufferedReader", "int read()");
		
		assertThat(provider.isAnalyzable(prueWithConsume), is(equalTo(false)));
		assertThat(provider.hasFreshGraph(prueWithConsume), is(equalTo(true)));
		assertThat(provider.hasConservativaGraph(prueWithConsume), is(equalTo(false)));
		assertThat(provider.hasNonConservativaGraph(prueWithConsume), is(equalTo(false)));
		
		assertThat(provider.isAnalyzable(prueWithoutConsume), is(equalTo(false)));
		assertThat(provider.hasFreshGraph(prueWithoutConsume), is(equalTo(true)));
		assertThat(provider.hasConservativaGraph(prueWithoutConsume), is(equalTo(false)));
		assertThat(provider.hasNonConservativaGraph(prueWithoutConsume), is(equalTo(false)));
		
		assertThat(provider.isAnalyzable(alter), is(equalTo(false)));
		assertThat(provider.hasFreshGraph(alter), is(equalTo(false)));
		assertThat(provider.hasConservativaGraph(alter), is(equalTo(true)));
		assertThat(provider.hasNonConservativaGraph(alter), is(equalTo(false)));
		
		assertThat(provider.isAnalyzable(impure), is(equalTo(false)));
		assertThat(provider.hasFreshGraph(impure), is(equalTo(false)));
		assertThat(provider.hasConservativaGraph(impure), is(equalTo(false)));
		assertThat(provider.hasNonConservativaGraph(impure), is(equalTo(true)));
	}
}
