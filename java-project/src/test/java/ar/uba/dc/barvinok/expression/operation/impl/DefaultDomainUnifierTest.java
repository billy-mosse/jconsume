package ar.uba.dc.barvinok.expression.operation.impl;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import ar.uba.dc.barvinok.expression.operation.DomainUnifier;

public class DefaultDomainUnifierTest {

	private DomainUnifier unifier;
	
	@Before
	public void setUp() {
		unifier = new DefaultDomainUnifier();
	}
	
	@Test
	public void unifyWithEmtpyArguments() {
		assertThat(unifier.unify(StringUtils.EMPTY, StringUtils.EMPTY), is(equalTo(StringUtils.EMPTY)));
		assertThat(unifier.unify(StringUtils.EMPTY, "($t.m > 0 And i < 0) Or i >= 0"), is(equalTo("($t.m > 0 and i < 0) or i >= 0")));
		assertThat(unifier.unify("($t.m > 0 && i < 0) || i >= 0", StringUtils.EMPTY), is(equalTo("($t.m > 0 and i < 0) or i >= 0")));
	}
	
	@Test
	public void unifyOnlyConjunctions() {
		assertThat(unifier.unify("0 <= i <= $t.m", "k >= 1"), is(equalTo("0 <= i <= $t.m and k >= 1")));
		assertThat(unifier.unify("0 <= i <= $t.m and $t.m == k", "k >= 1 and $t.m >= 0"), is(equalTo("0 <= i <= $t.m and $t.m == k and k >= 1 and $t.m >= 0")));
		assertThat(unifier.unify("0 <= i <= $t.m and $t.m == k", StringUtils.EMPTY), is(equalTo("0 <= i <= $t.m and $t.m == k")));
		assertThat(unifier.unify(StringUtils.EMPTY, "k >= 1 and $t.m >= 0"), is(equalTo("k >= 1 and $t.m >= 0")));
	}

	@Test
	public void unifyOnlyDisjunctions() {
		assertThat(unifier.unify("k >= 0 or $t.m >= 0", "0 <= i <= $t.k or i >= 0"), is(equalTo("(k >= 0 and 0 <= i <= $t.k) or (k >= 0 and i >= 0) or ($t.m >= 0 and 0 <= i <= $t.k) or ($t.m >= 0 and i >= 0)")));
		assertThat(unifier.unify("k >= 0 or $t.m >= 0", StringUtils.EMPTY), is(equalTo("k >= 0 or $t.m >= 0")));
		assertThat(unifier.unify(StringUtils.EMPTY, "0 <= i <= $t.k or i >= 0"), is(equalTo("0 <= i <= $t.k or i >= 0")));
	}
	
	@Test
	public void unifyMixOfConjunctionAndDisjunctions() {
		assertThat(unifier.unify("k >= 0 or $t.m >= 0", "0 <= i <= $t.k and i >= 0"), is(equalTo("(k >= 0 and 0 <= i <= $t.k and i >= 0) or ($t.m >= 0 and 0 <= i <= $t.k and i >= 0)")));
		assertThat(unifier.unify("0 <= i <= $t.k and i >= 0", "k >= 0 or $t.m >= 0"), is(equalTo("(0 <= i <= $t.k and i >= 0 and k >= 0) or (0 <= i <= $t.k and i >= 0 and $t.m >= 0)")));
		assertThat(unifier.unify("(0 <= i <= $t.k and i >= 0) or (0 <= w < 5 and quasy == 38) or a >= 0", "(i >= 0 and k <= 6) or $t.b == 5 or (k == 25 and $t.m == 36)"), is(equalTo("(0 <= i <= $t.k and i >= 0 and i >= 0 and k <= 6) or (0 <= i <= $t.k and i >= 0 and $t.b == 5) or (0 <= i <= $t.k and i >= 0 and k == 25 and $t.m == 36) or (0 <= w < 5 and quasy == 38 and i >= 0 and k <= 6) or (0 <= w < 5 and quasy == 38 and $t.b == 5) or (0 <= w < 5 and quasy == 38 and k == 25 and $t.m == 36) or (a >= 0 and i >= 0 and k <= 6) or (a >= 0 and $t.b == 5) or (a >= 0 and k == 25 and $t.m == 36)")));
	}
	
	/**
	 * Cuando unificamos algo con exists, asumimos que solo un dominio tiene exists. En ese caso
	 * el exists de ese dominio queda como el exists del dominio unificado.
	 */
	@Test
	public void unifyExists() {
		assertThat(unifier.unify(StringUtils.EMPTY, "exists (j: ($t.m > 0 And i < 0) Or i >= 0)"), is(equalTo("exists(j : ($t.m > 0 and i < 0) or i >= 0)")));
		assertThat(unifier.unify("exists j: ($t.m > 0 && i < 0) || i >= 0", StringUtils.EMPTY), is(equalTo("exists(j : ($t.m > 0 and i < 0) or i >= 0)")));
		
		assertThat(unifier.unify("0 <= i <= $t.m", "exists(k: k >= 1)"), is(equalTo("exists(k : 0 <= i <= $t.m and k >= 1)")));
		assertThat(unifier.unify("exists(e0 == 3 : 0 <= i <= $t.m and $t.m == k and k == e0)", "k >= 1 and $t.m >= 0"), is(equalTo("exists(e0 == 3 : 0 <= i <= $t.m and $t.m == k and k == e0 and k >= 1 and $t.m >= 0)")));
		
		assertThat(unifier.unify("exists(j: k >= 0 or $t.m >= 0)", "0 <= i <= $t.k and i >= 0"), is(equalTo("exists(j : (k >= 0 and 0 <= i <= $t.k and i >= 0) or ($t.m >= 0 and 0 <= i <= $t.k and i >= 0))")));
		assertThat(unifier.unify("0 <= i <= $t.k and i >= 0", "exists(q: k >= 0 or $t.m >= 0)"), is(equalTo("exists(q : (0 <= i <= $t.k and i >= 0 and k >= 0) or (0 <= i <= $t.k and i >= 0 and $t.m >= 0))")));
	}
}