package ar.uba.dc.analysis.common.method.information;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import soot.SootMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.method.information.rules.RuleSetReader;
import ar.uba.dc.analysis.common.method.information.rules.XMLRuleSetRepository;
import ar.uba.dc.soot.SootUtils;

//@Ignore
public class RuleBasedMethodInformationProviderTest {
	
	private RuleBasedMethodInformationProvider provider;

	@Before
	public void setUp() {
		XMLRuleSetRepository repository = new XMLRuleSetRepository();
		repository.setReader(new RuleSetReader());
		//repository.setRulesFile(Thread.currentThread().getContextClassLoader().getResource("unanalizable_rules.xml").getFile());
		
		repository.setRulesFile("unanalizable_rules.xml");
		provider = new RuleBasedMethodInformationProvider();
		provider.setRepository(repository);
	}

	@Test
	public void cacheWorksWithNull() {
		IntermediateRepresentationMethod method = new IntermediateRepresentationMethod("java.lang.Integer", "signum");
				
		boolean result = provider.isAnalyzable(method);
		
		assertThat(result, is(equalTo(true)));
		
		result = provider.isAnalyzable(method);
		
		assertThat(result, is(equalTo(true)));
	}
	
	@Test
	public void testMethodClasifications() {
		
		//We don't really need to create an ir_method
		
		IntermediateRepresentationMethod pureWithConsume = new IntermediateRepresentationMethod("java.lang.Integer", "valueOf");
		IntermediateRepresentationMethod pureWithoutConsume = new IntermediateRepresentationMethod("java.lang.StringBuilder", "toString");
		IntermediateRepresentationMethod alter = new IntermediateRepresentationMethod("java.lang.System", "arraycopy");
		IntermediateRepresentationMethod impure = new IntermediateRepresentationMethod("java.io.BufferedReader", "read");
		
		assertThat(provider.isAnalyzable(pureWithConsume), is(equalTo(false)));
		assertThat(provider.hasFreshGraph(pureWithConsume), is(equalTo(true)));
		assertThat(provider.hasConservativeGraph(pureWithConsume), is(equalTo(false)));
		assertThat(provider.hasNonConservativeGraph(pureWithConsume), is(equalTo(false)));
		
		assertThat(provider.isAnalyzable(pureWithoutConsume), is(equalTo(false)));
		assertThat(provider.hasFreshGraph(pureWithoutConsume), is(equalTo(true)));
		assertThat(provider.hasConservativeGraph(pureWithoutConsume), is(equalTo(false)));
		assertThat(provider.hasNonConservativeGraph(pureWithoutConsume), is(equalTo(false)));
		
		assertThat(provider.isAnalyzable(alter), is(equalTo(false)));
		assertThat(provider.hasFreshGraph(alter), is(equalTo(false)));
		assertThat(provider.hasConservativeGraph(alter), is(equalTo(true)));
		assertThat(provider.hasNonConservativeGraph(alter), is(equalTo(false)));
		
		assertThat(provider.isAnalyzable(impure), is(equalTo(false)));
		assertThat(provider.hasFreshGraph(impure), is(equalTo(false)));
		assertThat(provider.hasConservativeGraph(impure), is(equalTo(false)));
		assertThat(provider.hasNonConservativeGraph(impure), is(equalTo(true)));
	}
}
