package ar.uba.dc.util.comparator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Ignore;
import org.junit.Test;
@Ignore

public class InclusionComparatorTest {
	
	@Test
	public void twoDisjoinedStringsAreEqual() {
		assertThat(new InclusionComparator().compare("string", "java"), is(equalTo(0)));
	}
	
	@Test
	public void substringIsGreaterThanStringExceptIfTheyAreEquals() {
		assertThat(new InclusionComparator().compare("i", "string"), is(greaterThan(0)));
		assertThat(new InclusionComparator().compare("ri", "string"), is(greaterThan(0)));
		assertThat(new InclusionComparator().compare("rin", "string"), is(greaterThan(0)));
		assertThat(new InclusionComparator().compare("rin", "string"), is(greaterThan(0)));
		assertThat(new InclusionComparator().compare("tring", "string"), is(greaterThan(0)));
		assertThat(new InclusionComparator().compare("strin", "string"), is(greaterThan(0)));
		assertThat(new InclusionComparator().compare("string", "string"), is(equalTo(0)));
	}
	
	@Test
	public void stringIsLowerThanSubstringExceptIfTheyAreEquals() {
		assertThat(new InclusionComparator().compare("string", "i"), is(lessThan(0)));
		assertThat(new InclusionComparator().compare("string", "ri"), is(lessThan(0)));
		assertThat(new InclusionComparator().compare("string", "rin"), is(lessThan(0)));
		assertThat(new InclusionComparator().compare("string", "rin"), is(lessThan(0)));
		assertThat(new InclusionComparator().compare("string", "tring"), is(lessThan(0)));
		assertThat(new InclusionComparator().compare("string", "strin"), is(lessThan(0)));
		assertThat(new InclusionComparator().compare("string", "string"), is(equalTo(0)));
	}
	
	@Test
	public void comparisonIsCaseSensitive() {
		assertThat(new InclusionComparator().compare("stRing", "jAvA"), is(equalTo(0)));

		assertThat(new InclusionComparator().compare("I", "string"), is(equalTo(0)));
		assertThat(new InclusionComparator().compare("ri", "string"), is(greaterThan(0)));
		assertThat(new InclusionComparator().compare("rin", "string"), is(greaterThan(0)));
		assertThat(new InclusionComparator().compare("riN", "string"), is(equalTo(0)));
		assertThat(new InclusionComparator().compare("tring", "string"), is(greaterThan(0)));
		assertThat(new InclusionComparator().compare("strin", "stRing"), is(equalTo(0)));
		assertThat(new InclusionComparator().compare("string", "string"), is(equalTo(0)));

		assertThat(new InclusionComparator().compare("string", "i"), is(lessThan(0)));
		assertThat(new InclusionComparator().compare("string", "ri"), is(lessThan(0)));
		assertThat(new InclusionComparator().compare("stRing", "rin"), is(equalTo(0)));
		assertThat(new InclusionComparator().compare("string", "rIn"), is(equalTo(0)));
		assertThat(new InclusionComparator().compare("String", "tring"), is(lessThan(0)));
		assertThat(new InclusionComparator().compare("StRing", "tring"), is(equalTo(0)));
		assertThat(new InclusionComparator().compare("strinG", "strin"), is(lessThan(0)));
		assertThat(new InclusionComparator().compare("strinG", "string"), is(equalTo(0)));
	}
}
