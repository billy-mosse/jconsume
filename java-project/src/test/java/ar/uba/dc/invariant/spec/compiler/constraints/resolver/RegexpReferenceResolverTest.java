package ar.uba.dc.invariant.spec.compiler.constraints.resolver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class RegexpReferenceResolverTest {

	private RegexpReferenceResolver resolver = null;
	
	@Before
	public void setUp() {
		resolver = new RegexpReferenceResolver();
	}

	@Test
	public void dontFailOnEmptyTarget() {
		assertThat(resolver.resolve(StringUtils.EMPTY, "new_id", "i > 0"), is(equalTo(StringUtils.EMPTY)));
	}
	
	@Test
	public void dontFailWithEmptyReplacement() {
		assertThat(resolver.resolve("@loop and i > 0", "loop", StringUtils.EMPTY), is(equalTo(" and i > 0")));
		assertThat(resolver.resolve("i > 0 and @loop", "loop", StringUtils.EMPTY), is(equalTo("i > 0 and ")));
		assertThat(resolver.resolve("i > 0 and @loop and i < 25", "loop", StringUtils.EMPTY), is(equalTo("i > 0 and  and i < 25")));
	}
	
	@Test
	public void replaceIdentifierInAnyPlace() {
		assertThat(resolver.resolve("@new_id", "new_id", "i > 0"), is(equalTo("i > 0")));
		assertThat(resolver.resolve(" @new_id", "new_id", "i > 0"), is(equalTo(" i > 0")));
		assertThat(resolver.resolve("@new_id ", "new_id", "i > 0"), is(equalTo("i > 0 ")));
		assertThat(resolver.resolve(" @new_id ", "new_id", "i > 0"), is(equalTo(" i > 0 ")));
		assertThat(resolver.resolve("@new_id", "new_id", "i > 0 and 1 <= $t.k <= m"), is(equalTo("i > 0 and 1 <= $t.k <= m")));	  
	}
	
	@Test
	public void replaceAllOcurrences() {
		assertThat(resolver.resolve("(@new_id and i <= 0) or ($t.m == 0 and @new_id)", "new_id", "i > 0"), is(equalTo("(i > 0 and i <= 0) or ($t.m == 0 and i > 0)")));
	}
	
	@Test
	public void dontReplaceOtherThings() {
		assertThat(resolver.resolve("@new_id and i > 0 and k <= $t.m", "an_invariant", "e == 0"), is(equalTo("@new_id and i > 0 and k <= $t.m")));
	}
}
