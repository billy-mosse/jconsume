package ar.uba.dc.barvinok;

import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ar.uba.dc.util.ConsoleException;

@Ignore
public class BarvinokExpectationsTest {

	private CommandLineBarvinokExecutor executor;
	
	@Before
	public void setUp() {
		executor = new CommandLineBarvinokExecutor();
		executor.setBarvinokCmdPath("./bin/barvinok.sh");
	}
	
	@Test
	public void addOperatorExpectations() {
		// sintaxis de la calculadora version 0.30
		assertThat(executor.execCommand("{1} + {7};"), is(equalTo("{ 8 }")));
		assertThat(executor.execCommand("[n] -> {[]-> n: n >=1 } + [n] -> {1};"), is(equalTo("[n] -> { (1 + n) : n >= 1; 1 : n <= 0 }")));
		assertThat(executor.execCommand("[n] -> {[]-> n: n >=1 } + [n] -> {1: n <= 0};"), is(equalTo("[n] -> { n : n >= 1; 1 : n <= 0 }")));
		assertThat(executor.execCommand("[n] -> {[]-> n} + [n] -> {1: n <= 0};"), is(equalTo("[n] -> { (1 + n) : n <= 0; n : n >= 1 }")));
		assertThat(executor.execCommand("[n] -> {[]-> n: 0 <= n } + [n] -> {n^2: n <= 5};"), is(equalTo("[n] -> { (n + n^2) : n >= 0 and n <= 5; n : n >= 6; n^2 : n <= -1 }")));
		assertThat(executor.execCommand("[n,m] -> {[]-> n: n >=1 } + [n,m] -> {3*m + 5: m >= 0};"), is(equalTo("[n, m] -> { ((5 + n) + 3 * m) : n >= 1 and m >= 0; n : n >= 1 and m <= -1; (5 + 3 * m) : m >= 0 and n <= 0 }")));
		assertThat(executor.execCommand("[n,m] -> {[]-> n: n >=1 } + [n,m] -> {m: n <= 0};"), is(equalTo("[n, m] -> { n : n >= 1; m : n <= 0 }")));
		assertThat(executor.execCommand("[n,m] -> {[]-> n} + [n,m] -> {2*m^2 + 7: n <= 0};"), is(equalTo("[n, m] -> { ((7 + n) + 2 * m^2) : n <= 0; n : n >= 1 }")));
		assertThat(executor.execCommand("[n,m] -> {[]-> n: 0 <= n } + [n,m] -> {m^2: n <= 5 and m = 5};"), is(equalTo("[n, m] -> { (n + m^2) : m = 5 and n <= 5 and n >= 0; n : (n >= 0 and m <= 4) or (n >= 0 and m >= 6) or (m = 5 and n >= 6); m^2 : m = 5 and n <= -1 }")));
	}
	
	@Test
	public void addOperatorSupportInfinite() {
		assertThat(executor.execCommand("{1} + {infty};"), is(equalTo("{ infty }")));
		assertThat(executor.execCommand("[n] -> {[]-> infty: n >=1 } + [n] -> {1};"), is(equalTo("[n] -> { infty : n >= 1; 1 : n <= 0 }")));
		assertThat(executor.execCommand("[n] -> {[]-> infty: n >=1 } + [n] -> {1: n <= 0};"), is(equalTo("[n] -> { infty : n >= 1; 1 : n <= 0 }")));
		assertThat(executor.execCommand("[n] -> {[]-> infty} + [n] -> {1: n <= 0};"), is(equalTo("[n] -> { infty : n <= 0; infty : n >= 1 }")));
		assertThat(executor.execCommand("[n] -> {[]-> infty: 0 <= n } + [n] -> {n^2: n <= 5};"), is(equalTo("[n] -> { infty : n >= 0 and n <= 5; infty : n >= 6; n^2 : n <= -1 }")));
	}
	
	@Test
	public void addOperatorNewSintaxExpectations() {
		// repetimos los ejemplos con la sintaxis de la calculadora 0.31 en adelante
		assertThat(executor.execCommand("{} + {7};"), is(equalTo("{ 7 }")));
		assertThat(executor.execCommand("{1} + {7};"), is(equalTo("{ 8 }")));
		assertThat(executor.execCommand("{[[n] -> []] -> n: n >=1 } + {[[n] -> []] -> 1};"), is(equalTo("{ [[n] -> []] -> (1 + n) : n >= 1; [[n] -> []] -> 1 : n <= 0 }")));
		assertThat(executor.execCommand("{[n] -> n: n >=1 } + {[n] -> 1};"), is(equalTo("{ [n] -> (1 + n) : n >= 1; [n] -> 1 : n <= 0 }")));
		assertThat(executor.execCommand("{[n] -> n: n >=1 } + {[n] -> 1: n <= 0};"), is(equalTo("{ [n] -> n : n >= 1; [n] -> 1 : n <= 0 }")));
		assertThat(executor.execCommand("{[n] -> n} + { [n] -> 1: n <= 0};"), is(equalTo("{ [n] -> (1 + n) : n <= 0; [n] -> n : n >= 1 }")));
		assertThat(executor.execCommand("{[n] -> n: 0 <= n } + {[n] -> n^2: n <= 5};"), is(equalTo("{ [n] -> (n + n^2) : n >= 0 and n <= 5; [n] -> n : n >= 6; [n] -> n^2 : n <= -1 }")));
		assertThat(executor.execCommand("{[n,m] -> n: n >=1 } + {[n,m] -> 3*m + 5: m >= 0};"), is(equalTo("{ [n, m] -> ((5 + n) + 3 * m) : n >= 1 and m >= 0; [n, m] -> n : n >= 1 and m <= -1; [n, m] -> (5 + 3 * m) : m >= 0 and n <= 0 }")));
		assertThat(executor.execCommand("{[n,m] -> n: n >=1 } + {[n,m] -> m: n <= 0};"), is(equalTo("{ [n, m] -> n : n >= 1; [n, m] -> m : n <= 0 }")));
		assertThat(executor.execCommand("{[n,m] -> n} + {[n,m] -> 2*m^2 + 7: n <= 0};"), is(equalTo("{ [n, m] -> ((7 + n) + 2 * m^2) : n <= 0; [n, m] -> n : n >= 1 }")));
		assertThat(executor.execCommand("{[n,m] -> n: 0 <= n } + {[n,m] -> m^2: n <= 5 and m = 5};"), is(equalTo("{ [n, m] -> (n + m^2) : m = 5 and n <= 5 and n >= 0; [n, m] -> n : (n >= 0 and m <= 4) or (n >= 0 and m >= 6) or (m = 5 and n >= 6); [n, m] -> m^2 : m = 5 and n <= -1 }")));
	}
	
	@Test
	public void addOperatorNewSintaxSupportInfinite() {
		assertThat(executor.execCommand("{1} + {infty};"), is(equalTo("{ infty }")));
		assertThat(executor.execCommand("{[n] -> infty: n >=1 } + {[n] -> 1};"), is(equalTo("{ [n] -> infty : n >= 1; [n] -> 1 : n <= 0 }")));
		assertThat(executor.execCommand("{[n] -> infty: n >=1 } + {[n] -> 1: n <= 0};"), is(equalTo("{ [n] -> infty : n >= 1; [n] -> 1 : n <= 0 }")));
		assertThat(executor.execCommand("{[n] -> infty} + {[n] -> 1: n <= 0};"), is(equalTo("{ [n] -> infty : n <= 0; [n] -> infty : n >= 1 }")));
		assertThat(executor.execCommand("{[n] -> infty: 0 <= n } + { [n] -> n^2: n <= 5};"), is(equalTo("{ [n] -> infty : n >= 0 and n <= 5; [n] -> infty : n >= 6; [n] -> n^2 : n <= -1 }")));
	}
	
	@Test
	public void cardOperatorExpectations() {
		assertThat(executor.execCommand("card [] -> {[i]: -3 <= i <= 4 };"), is(equalTo("{ 8 }")));
		assertThat(executor.execCommand("card [n] -> {[i]: 0 <= i <= n};"), is(equalTo("[n] -> { (1 + n) : n >= 0 }")));
		assertThat(executor.execCommand("card [n] -> {[i]: n <= i <= 25};"), is(equalTo("[n] -> { (26 - n) : n <= 25 }")));
		assertThat(executor.execCommand("card [n] -> {[i]: n <= i <= 25 and n >= 26};"), is(equalTo("[n] -> {  }")));
		assertThat(executor.execCommand("card [n,m] -> {[i]: n <= i <= m};"), is(equalTo("[n, m] -> { ((1 - n) + m) : m >= n }")));
		assertThat(executor.execCommand("card [n] -> {[i,j]: 0 <= i <= n and 0 <= j < i};"), is(equalTo("[n] -> { (1/2 * n + 1/2 * n^2) : n >= 1 }")));
	}
	
	@Test
	public void cardOperatorSupportInfinite() {
		assertThat(executor.execCommand("card [n] -> {[i]: 0 <= i};"), is(equalTo("[n] -> { infty }")));
		assertThat(executor.execCommand("card [n] -> {[i, j]: 0 <= i <= n};"), is(equalTo("[n] -> { infty : n >= 0 }")));
		assertThat(executor.execCommand("card [n, m] -> {[i, j]: 0 <= i <= n, j >= m};"), is(equalTo("[n, m] -> { infty : m <= n and n >= 0 }")));
	}
	
	@Test
	public void cardOperatorNewSintaxExpectations() {
		assertThat(executor.execCommand("card {[] -> [i]: -3 <= i <= 4 };"), is(equalTo("{ 8 }")));
		assertThat(executor.execCommand("card {[n] -> [i]: 0 <= i <= n};"), is(equalTo("{ [n] -> (1 + n) : n >= 0 }")));
		assertThat(executor.execCommand("card {[n] -> [i]: n <= i <= 25};"), is(equalTo("{ [n] -> (26 - n) : n <= 25 }")));
		assertThat(executor.execCommand("card {[n] -> [i]: n <= i <= 25 and n >= 26};"), is(equalTo("{  }")));
		assertThat(executor.execCommand("card {[n,m] -> [i]: n <= i <= m};"), is(equalTo("{ [n, m] -> ((1 - n) + m) : m >= n }")));
		assertThat(executor.execCommand("card {[n] -> [i,j]: 0 <= i <= n and 0 <= j < i};"), is(equalTo("{ [n] -> (1/2 * n + 1/2 * n^2) : n >= 1 }")));
	}
	
	@Test
	public void cardOperatorNewSintaxSupportInfinite() {
		assertThat(executor.execCommand("card {[n] -> [i]: 0 <= i};"), is(equalTo("{ [n] -> infty }")));
		assertThat(executor.execCommand("card {[n] -> [i, j]: 0 <= i <= n};"), is(equalTo("{ [n] -> infty : n >= 0 }")));
		assertThat(executor.execCommand("card {[n, m] -> [i, j]: 0 <= i <= n, j >= m};"), is(equalTo("{ [n, m] -> infty : m <= n and n >= 0 }")));
	}
	
	@Test
	public void sumOperatorExpectations() {
		// Necesitamos sum de parametrico
		assertThat(executor.execCommand("sum [n] -> {[i]-> i: 0 <= i <= n};"), is(equalTo("[n] -> { (1/2 * n + 1/2 * n^2) : n >= 0 }")));
		assertThat(executor.execCommand("sum [k] -> {[i,tn]-> tn: 0 <= i <= k and tn=i};"), is(equalTo("[k] -> { (1/2 * k + 1/2 * k^2) : k >= 0 }")));
		assertThat(executor.execCommand("sum [n] -> {[i]-> i: 0 <= i <= n and n < 0};"), is(equalTo("[n] -> {  }")));
		assertThat(executor.execCommand("sum [k] -> {[i,tn]-> tn: 0 <= i <= k and tn=1};"), is(equalTo("[k] -> { (1 + k) : k >= 0 }")));
		assertThat(executor.execCommand("sum [k] -> {[i,tn]-> tn: 0 <= i <= k and tn=5};"), is(equalTo("[k] -> { (5 + 5 * k) : k >= 0 }")));
		assertThat(executor.execCommand("sum [k] -> {[i,tn]-> tn: 0 <= i <= k and tn=i+10};"), is(equalTo("[k] -> { (10 + 21/2 * k + 1/2 * k^2) : k >= 0 }")));
		assertThat(executor.execCommand("sum [a,b] -> {[] -> 1: b > 0};"), is(equalTo("[a, b] -> { 1 : b >= 1 }")));
		assertThat(executor.execCommand("sum [] -> {[a,b] -> 1: b > 0};"), is(equalTo("{ NaN }")));
		
		// Necesitamos sum de constante
		assertThat(executor.execCommand("sum [k] -> {[i,tn]-> tn: 0 <= i <= k and tn=i+10 and k = 3};"), is(equalTo("[k] -> { 46 : k = 3 }")));
	}
	
	
	@Test
	public void sumOperatorNotSupportInfinite() {
		try {
			executor.execCommand("sum [n] -> {[i] -> infty: 0 <= i <= n};");
			fail("deberia dar error");
		} catch (ConsoleException e) {
			assertThat(e.getMessage(), containsString("!isl_upoly_is_infty(up)"));
		}
		
		try {
			executor.execCommand("sum [n] -> {[i] -> infty: 0 <= i <= n and n = 0; [i] -> i: 0 <= i <= n and n > 0};");
			fail("deberia dar error");
		} catch (ConsoleException e) {
			assertThat(e.getMessage(), containsString("!isl_upoly_is_infty(up)"));
		}
	}
	
	@Test
	public void sumOperatorDontReturnInfinite() {
		assertThat(executor.execCommand("sum [k] -> {[i,tn]-> tn: 0 <= i and tn=1; [w]-> w: 0 <= w};"), is(equalTo("[k] -> { NaN }")));
		assertThat(executor.execCommand("sum [k] -> {[i,tn]-> tn: 0 <= i and tn=1; [w]-> w*w: 0 <= w};"), is(equalTo("[k] -> { NaN }")));
		assertThat(executor.execCommand("sum [k] -> {[i,tn]-> tn: 0 <= i and tn=1};"), is(equalTo("[k] -> { NaN }")));
		assertThat(executor.execCommand("sum [k] -> {[i]-> i: 0 <= i};"), is(equalTo("[k] -> { NaN }")));
	}
	
	@Test
	public void sumOperatorNewSintaxExpectations() {
		// Necesitamos sum de parametrico
		assertThat(executor.execCommand("sum {[[n] -> [i]] -> i: 0 <= i <= n};"), is(equalTo("{ [n] -> (1/2 * n + 1/2 * n^2) : n >= 0 }")));
		assertThat(executor.execCommand("sum {[[k] -> [i,tn]] -> tn: 0 <= i <= k and tn=i};"), is(equalTo("{ [k] -> (1/2 * k + 1/2 * k^2) : k >= 0 }")));
		assertThat(executor.execCommand("sum {[[n] -> [i]]-> i: 0 <= i <= n and n < 0};"), is(equalTo("{  }")));
		assertThat(executor.execCommand("sum {[[k] -> [i,tn]] -> tn: 0 <= i <= k and tn=1};"), is(equalTo("{ [k] -> (1 + k) : k >= 0 }")));
		assertThat(executor.execCommand("sum {[[k] -> [i,tn]]-> tn: 0 <= i <= k and tn=5};"), is(equalTo("{ [k] -> (5 + 5 * k) : k >= 0 }")));
		assertThat(executor.execCommand("sum {[[k] -> [i,tn]]-> tn: 0 <= i <= k and tn=i+10};"), is(equalTo("{ [k] -> (10 + 21/2 * k + 1/2 * k^2) : k >= 0 }")));
		assertThat(executor.execCommand("sum {[[a,b] -> []] -> 1: b > 0};"), is(equalTo("{ [a, b] -> 1 : b >= 1 }")));
		assertThat(executor.execCommand("sum {[a,b] -> 1: b > 0};"), is(equalTo("{ NaN }")));
		
		// Necesitamos sum de constante
		assertThat(executor.execCommand("sum {[[k] -> [i,tn]]-> tn: 0 <= i <= k and tn=i+10 and k = 3};"), is(equalTo("{ [k] -> 46 : k = 3 }")));
	}
	
	
	@Test
	public void sumOperatorNewSintaxNotSupportInfinite() {
		try {
			executor.execCommand("sum {[[n] -> [i]] -> infty: 0 <= i <= n};");
			fail("deberia dar error");
		} catch (ConsoleException e) {
			assertThat(e.getMessage(), containsString("!isl_upoly_is_infty(up)"));
		}
		
		try {
			executor.execCommand("sum {[[n] -> [i]] -> infty: 0 <= i <= n and n = 0; [[n] -> [i]] -> i: 0 <= i <= n and n > 0};");
			fail("deberia dar error");
		} catch (ConsoleException e) {
			assertThat(e.getMessage(), containsString("!isl_upoly_is_infty(up)"));
		}
	}
	
	@Test
	public void sumOperatorNewSintaxisDontReturnInfinite() {
		assertThat(executor.execCommand("sum {[[k] -> [i,tn]]-> tn: 0 <= i and tn=1; [[k] -> [w]]-> w: 0 <= w};"), is(equalTo("{ [k] -> NaN }")));
		assertThat(executor.execCommand("sum {[[k] -> [i,tn]]-> tn: 0 <= i and tn=1; [[k] -> [w]]-> w*w: 0 <= w};"), is(equalTo("{ [k] -> NaN }")));
		assertThat(executor.execCommand("sum {[[k] -> [i,tn]]-> tn: 0 <= i and tn=1};"), is(equalTo("{ [k] -> NaN }")));
		assertThat(executor.execCommand("sum {[[k] -> [i]]-> i: 0 <= i};"), is(equalTo("{ [k] -> NaN }")));
	}
	
	@Test
	public void ubOperatorExpectations() {
		// Debe soportar ub de 0
		assertThat(executor.execCommand("ub [] -> {[] -> 0};"), is(equalTo("({  }, True)")));
		assertThat(executor.execCommand("ub [n] -> {[i] -> 0: 0 <= i < n};"), is(equalTo("([n] -> {  }, True)")));
		
		// Necesitamos ub que de parametrico
		assertThat(executor.execCommand("ub [n] -> {[i] -> 0: 0 <= i <= n};"), is(equalTo("([n] -> {  }, True)")));
		assertThat(executor.execCommand("ub [n] -> {[i] -> 1: 0 <= i <= n};"), is(equalTo("([n] -> { max(1) : n >= 0 }, True)")));
		assertThat(executor.execCommand("ub [n] -> {[i] -> i*i: -3 <= i <= n};"), is(equalTo("([n] -> { max(9, n^2) : n >= -3 }, True)")));
		assertThat(executor.execCommand("ub [n,m] -> {[i] -> i*i: m <= i <= n};"), is(equalTo("([n, m] -> { max(n * m, m^2, n^2) : m <= n }, False)")));
		assertThat(executor.execCommand("ub [a,b] -> {[] -> 1: b > 0};"), is(equalTo("([a, b] -> { max(1) : b >= 1 }, True)")));
		assertThat(executor.execCommand("ub [] -> {[a, b] -> 1: b > 0};"), is(equalTo("({ max(1) }, True)")));

		// Necesitamos ub que de constante
		assertThat(executor.execCommand("ub [n] -> {[i,tn] -> tn*tn: -3 <= i <= n and tn=5};"), is(equalTo("([n] -> { max(25) : n >= -3 }, True)")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void ubOperatorSupportInfinite() {
		assertThat(executor.execCommand("ub [n] -> {[i] -> infty: 0 <= i <= n};"), is(equalTo("([n] -> { max(infty) : n >= 0 }, True)")));
		assertThat(executor.execCommand("ub [n] -> {[i] -> infty: 0 <= i <= n and n = 0; [i] -> i: 0 <= i <= n and n > 0};"), is(equalTo("([n] -> { max(infty) : n = 0; max(n) : n >= 1 }, True)")));
		assertThat(executor.execCommand("ub [n,m] -> {[i] -> i*i: m <= i <= n; [] -> infty: n < m};"),  is(anyOf(equalTo("([n, m] -> { max(n * m, m^2, n^2) : m <= n; max(infty) : m >= 1 + n }, False)"), equalTo("([n, m] -> { max(infty) : m >= 1 + n; max(n * m, m^2, n^2) : m <= n }, False)"))));
	}

	@Test
	public void ubOperatorReturnInfinite() {
		assertThat(executor.execCommand("ub [n] -> {[i] -> 1: 0 <= i};"), is(equalTo("([n] -> { max(1) : n >= 0; max(1) : n <= -1 }, True)")));
		assertThat(executor.execCommand("ub [n] -> {[i] -> 1: i <= n};"), is(equalTo("([n] -> { max(1) : n >= 0; max(1) : n <= -1 }, True)")));
		assertThat(executor.execCommand("ub [n] -> {[i] -> i: 0 <= i};"), is(equalTo("([n] -> { max(infty) : n >= 0; max(infty) : n <= -1 }, True)")));
		assertThat(executor.execCommand("ub [n] -> {[i] -> i: i <= n};"), is(equalTo("([n] -> { max(n) : n >= 0; max(n) : n <= -2; max(-1) : n = -1 }, True)")));
	}
	
	@Test
	public void ubOperatorNewSintaxExpectations() {
		// Debe soportar ub de 0
		assertThat(executor.execCommand("ub {0};"), is(equalTo("({  }, True)")));
		assertThat(executor.execCommand("ub {[[n] -> [i]] -> 0: 0 <= i < n};"), is(equalTo("({  }, True)")));
		
		// Necesitamos ub que de parametrico
		assertThat(executor.execCommand("ub {[[n] -> [i]] -> 0: 0 <= i <= n};"), is(equalTo("({  }, True)")));
		assertThat(executor.execCommand("ub {[[n] -> [i]] -> 1: 0 <= i <= n};"), is(equalTo("({ [n] -> max(1) : n >= 0 }, True)")));
		assertThat(executor.execCommand("ub {[[n] -> [i]] -> i*i: -3 <= i <= n};"), is(equalTo("({ [n] -> max(9, n^2) : n >= -3 }, True)")));
		assertThat(executor.execCommand("ub {[[n,m] -> [i]] -> i*i: m <= i <= n};"), is(equalTo("({ [n, m] -> max(n * m, m^2, n^2) : m <= n }, False)")));
		assertThat(executor.execCommand("ub {[[a,b] -> []] -> 1: b > 0};"), is(equalTo("({ [a, b] -> max(1) : b >= 1 }, True)")));
		assertThat(executor.execCommand("ub {[a, b] -> 1: b > 0};"), is(equalTo("({ max(1) }, True)")));
		
		// Necesitamos ub que de constante
		assertThat(executor.execCommand("ub {[[n] -> [i,tn]] -> tn*tn: -3 <= i <= n and tn=5};"), is(equalTo("({ [n] -> max(25) : n >= -3 }, True)")));
	}

	@Test
	public void ubOperatorNewSintaxSupportInfinite() {
		assertThat(executor.execCommand("ub {[[n] -> [i]] -> infty: 0 <= i <= n};"), is(equalTo("({ [n] -> max(infty) : n >= 0 }, True)")));
		assertThat(executor.execCommand("ub {[[n] -> [i]] -> infty: 0 <= i <= n and n = 0; [[n] -> [i]] -> i: 0 <= i <= n and n > 0};"), is(equalTo("({ [n] -> max(infty) : n = 0; [n] -> max(n) : n >= 1 }, True)")));
		assertThat(executor.execCommand("ub {[[n,m] -> [i]] -> i*i: m <= i <= n; [[n,m] -> []] -> infty: n < m};"), is(equalTo("({ [n, m] -> max(infty) : m >= 1 + n; [n, m] -> max(n * m, m^2, n^2) : m <= n }, False)")));
	}

	@Test
	public void ubOperatorNewSintaxReturnInfinite() {
		assertThat(executor.execCommand("ub {[[n] -> [i]] -> 1: 0 <= i};"), is(equalTo("({ [n] -> max(1) : n >= 0; [n] -> max(1) : n <= -1 }, True)")));
		assertThat(executor.execCommand("ub {[[n] -> [i]] -> 1: i <= n};"), is(equalTo("({ [n] -> max(1) : n >= 0; [n] -> max(1) : n <= -1 }, True)")));
		assertThat(executor.execCommand("ub {[[n] -> [i]] -> i: 0 <= i};"), is(equalTo("({ [n] -> max(infty) : n >= 0; [n] -> max(infty) : n <= -1 }, True)")));
		assertThat(executor.execCommand("ub {[[n] -> [i]] -> i: i <= n};"), is(equalTo("({ [n] -> max(n) : n >= 0; [n] -> max(n) : n <= -2; [n] -> max(-1) : n = -1 }, True)")));
	}
	
	@Test
	public void addOperatorExpectationsWithMaximum() {
		// Necesitamos poder sumar polinomios con folds. El problema es que convierte todo en "max"
		assertThat(executor.execCommand("[m] -> { max((-1 + 2 * m)) : m >= 1 } + [m] -> {  m : m >= 1 };"), is(equalTo("[m] -> { max((-1 + 3 * m)) : m >= 1 }")));
		assertThat(executor.execCommand("[m] -> { max((-1 + 2 * m), 3) : m >= 1 } + [m] -> { m };"), is(equalTo("[m] -> { max((-1 + 3 * m), (3 + m)) : m >= 1; max(m) : m <= 0 }")));
		try {
			executor.execCommand("[m] -> { max((-1 + 2 * m), 3) : m >= 1 } + [m] -> { max((-1 + 2 * m), 3) : m >= 1 };");
			assertThat(executor.execCommand("[m] -> { max((-1 + 2 * m), 3) : m >= 1 } + [m] -> { max((-1 + 2 * m), 3) : m >= 1 };"), is(equalTo("[m] -> { max((-1 + 2 * m), 3) : m >= 1 }[m] -> { max((-1 + 2 * m), 3) : m >= 1 }")));
			//fail("No se pueden sumar varios maximos, eso no tiene sentido");
		} catch (ConsoleException e) {
			assertThat(e.getMessage(), containsString("\"op\" failed"));
		}
		assertThat(executor.execCommand("[m] -> { m : m >= 1 } + [m] -> { max((-1 + 2 * m)) : m >= 1 };"), is(equalTo("[m] -> { max((-1 + 3 * m)) : m >= 1 }")));
		assertThat(executor.execCommand("[m] -> { max((-1 + 2 * m), 3) : m >= 1 } + [m] -> { m*m };"), is(equalTo("[m] -> { max((-1 + 2 * m + m^2), (3 + m^2)) : m >= 1; max(m^2) : m <= 0 }")));
		assertThat(executor.execCommand("[m] -> { max((-1 + 2 * m + m^2), (3 + m^2)) : m >= 1; max(m^2) : m <= 0 } + [m] -> { m*m };"), is(equalTo("[m] -> { max((-1 + 2 * m + 2 * m^2), (3 + 2 * m^2)) : m >= 1; max(2 * m^2) : m <= 0 }")));
	}
	
	@Test
	public void addOperatorNewSintaxExpectationsWithMaximum() {
		// Necesitamos poder sumar polinomios con folds. El problema es que convierte todo en "max"
		assertThat(executor.execCommand("{ [m] ->  max((-1 + 2 * m)) : m >= 1 } + { [m] ->  m : m >= 1 };"), is(equalTo("{ [m] -> max((-1 + 3 * m)) : m >= 1 }")));
		assertThat(executor.execCommand("{ [m] -> max((-1 + 2 * m), 3) : m >= 1 } + { [m] -> m };"), is(equalTo("{ [m] -> max((-1 + 3 * m), (3 + m)) : m >= 1; [m] -> max(m) : m <= 0 }")));
		try {
			assertThat(executor.execCommand("{ [m] -> max((-1 + 2 * m), 3) : m >= 1 } + { [m] -> max((-1 + 2 * m), 3) : m >= 1 };"), is(equalTo("{ [m] -> max((-1 + 2 * m), 3) : m >= 1 }{ [m] -> max((-1 + 2 * m), 3) : m >= 1 }")));
			//fail("No se pueden sumar varios maximos, eso no tiene sentido");
		} catch (ConsoleException e) {
			assertThat(e.getMessage(), containsString("\"op\" failed"));
		}
		assertThat(executor.execCommand("{ [m] -> m : m >= 1 } + { [m] -> max((-1 + 2 * m)) : m >= 1 };"), is(equalTo("{ [m] -> max((-1 + 3 * m)) : m >= 1 }")));
		assertThat(executor.execCommand("{ [m] -> max((-1 + 2 * m), 3) : m >= 1 } + { [m] ->  m*m };"), is(equalTo("{ [m] -> max((-1 + 2 * m + m^2), (3 + m^2)) : m >= 1; [m] -> max(m^2) : m <= 0 }")));
		assertThat(executor.execCommand("{ [m] -> max((-1 + 2 * m + m^2), (3 + m^2)) : m >= 1; [m] -> max(m^2) : m <= 0 } + { [m] -> m*m };"), is(equalTo("{ [m] -> max((-1 + 2 * m + 2 * m^2), (3 + 2 * m^2)) : m >= 1; [m] -> max(2 * m^2) : m <= 0 }")));
	}
	
	@Test
	public void dotOperatorExpectations() {
		// Necesitamos comparar maximos
		assertThat(executor.execCommand("[m] -> { max((-1 + 2 * m), 3) : m >= 1 } . [m] -> { max(-2 + 2 * m, 71*m, 9) : m >= 0 };"), is(equalTo("[m] -> { max(71 * m, 9) : m >= 1; max((-2 + 2 * m), 71 * m, 9) : m = 0 }")));
		assertThat(executor.execCommand("[m] -> { max((-1 + 2 * m), 3) } . [m] -> { max(-2 + 2 * m, 71*m, 9) };"), is(equalTo("[m] -> { max((-1 + 2 * m), 71 * m, 9) }")));
		assertThat(executor.execCommand("[n, m] -> { max(infty) : m >= 1 + n; max(n * m, m^2, n^2) : m <= n } . [n, m] -> { max((-2 + 2 * m), 3, 71*m) : m >= 1 };"), is(equalTo("[n, m] -> { max(infty) : m >= 1 + n and m >= 1; max(infty) : m >= 1 + n and m <= 0; max(n * m, m^2, n^2, 3, 71 * m) : m <= n and m >= 1; max(n * m, m^2, n^2) : m <= n and m <= 0 }")));
		
		// No se soporta la sintaxis vieja
		try {
			executor.execCommand("[m] -> { [c,w] : -3 <= c <= m and w = c} . [k, j] ->  { max((3 * k + k^2), 3) : k >= 1 };");
		} catch (ConsoleException e) {
			assertThat(e.getMessage(), containsString("expecting other token" + System.getProperty("line.separator") + "got '.'"));
		}
	}
	
	@Test
	public void dotOperatorNewSintaxExpectations() {
		// Necesitamos comparar maximos
		assertThat(executor.execCommand("{ [m] -> max((-1 + 2 * m), 3) : m >= 1 } . { [m] -> max(-2 + 2 * m, 71*m, 9) : m >= 0 };"), is(equalTo("{ [m] -> max(71 * m, 9) : m >= 1; [m] -> max((-2 + 2 * m), 71 * m, 9) : m = 0 }")));
		assertThat(executor.execCommand("{ [m] -> max((-1 + 2 * m), 3) } . { [m] -> max(-2 + 2 * m, 71*m, 9) };"), is(equalTo("{ [m] -> max((-1 + 2 * m), 71 * m, 9) }")));
		assertThat(executor.execCommand("{ [n, m] -> max(infty) : m >= 1 + n; [n, m] -> max(n * m, m^2, n^2) : m <= n } . { [n, m] -> max((-2 + 2 * m), 3, 71*m) : m >= 1 };"), is(equalTo("{ [n, m] -> max(infty) : m >= 1 + n and m >= 1; [n, m] -> max(infty) : m >= 1 + n and m <= 0; [n, m] -> max(n * m, m^2, n^2, 3, 71 * m) : m <= n and m >= 1; [n, m] -> max(n * m, m^2, n^2) : m <= n and m <= 0 }")));
		
		
		// Si tengo mas variables que parametros me da vacio
		assertThat(executor.execCommand("{ [m] -> [c,w] : -3 <= c <= m and w = c} . { [k] -> max((3 * k + k^2), 3) : k >= 1 };"), is(equalTo("({  }, True)")));

		// Hace el binding 1 a 1 de las variables
		assertThat(executor.execCommand("{ [m] -> [c,w] : -3 <= c <= m and w = c} . { [k, j] -> max((3 * k + k^2), 3) : k >= 1 };"), is(equalTo("({ [m] -> max((3 * m + m^2)) : m >= 1 }, True)")));
		
		// Devuelve infinito si corresponde
		assertThat(executor.execCommand("{[n] -> [i, m]: 0 <= i and i = m } . { [i, m] -> max(-2 + 2 * m, 71*m, 9) : m >= 4 };"), is(equalTo("({ [n] -> max(infty) : n >= 0; [n] -> max(infty) : n <= -1 }, True)")));

		// Justo para el ejemplo da lo mismo rotar las variables...
		assertThat(executor.execCommand("{[n] -> [i, m]: 0 <= i and i = m } . { [m, i] -> max(-2 + 2 * m, 71*m, 9) : m >= 4 };"), is(equalTo("({ [n] -> max(infty) : n >= 0; [n] -> max(infty) : n <= -1 }, True)")));
 
		// Elimina candidatos y partes si corresponde
		assertThat(executor.execCommand("{[k, j] -> [n, m]: k = n and j = m and k > j and j > 0} . {[n, m] -> max(infty) : m >= 1 + n; [n, m] -> max(n * m, m^2, n^2) : m <= n };"), is(equalTo("({ [k, j] -> max(k * j, k^2) : j <= -1 + k and j >= 1 }, True)")));

		// Puede generar nuevos candidatos si lo necesita
		assertThat(executor.execCommand("{[k, l] -> [n, m, i, j]: l <= i <= k and l <= j < i and i = n and j = m and l >= 0} . {[n, m, i, j] -> max(infty) : m >= 1 + n; [n, m, i, j] -> max(n * m, m^2, n^2) : m <= n };"), is(equalTo("({ [k, l] -> max(((-1/2 * k + 1/2 * k^2) + 1/2 * k * l), (k + k * l), k^2, (1 + 2 * l + l^2)) : l >= 0 and l <= -1 + k }, False)")));

		// Realmente resuelve candidatos si puede
		assertThat(executor.execCommand("{[k, l] -> [n, m, i, j]: 0 <= i <= k and 0 <= j < i and i = n and j = m} . {[n, m, i, j] -> max(infty) : m >= 1 + n; [n, m, i, j] -> max(n * m, m^2, n^2) : m <= n };"), is(equalTo("({ [k, l] -> max(k^2) : k >= 1 }, True)")));
	}
	
	@Test
	public void polynomialsWithSets() {
		assertThat(executor.execCommand("card [n] -> { [i] : 0 <= 2*i < n };"), is(equalTo("[n] -> { (n - [(n)/2]) : n >= 1 }")));
		assertThat(executor.execCommand("card { [n] -> [i] : 0 <= 2*i < n };"), is(equalTo("{ [n] -> (n - [(n)/2]) : n >= 1 }")));
	}
	
	@Test 
	public void existsConstraintExpectations() {
		// Esperamos una unica declaracion de exists que englobe a todas las constratins
		assertThat(executor.execCommand("sum [n] -> { [i] -> i : n = 4*i };"), is(equalTo("[n] -> { 1/4 * n : exists (e0 = [(n)/4]: 4e0 = n) }")));
		
		// Puede usarse el exists y hacer cte algun parametro
		assertThat(executor.execCommand("sum [i]-> { [n] -> 1/2 * n : (exists e0 = [n/2], e1, e2 = [n/4]: 2e0 = n and 4e2 = n and n = i and i = 4)};"), is(equalTo("[i] -> { 2 : i = 4 }")));
		assertThat(executor.execCommand("sum [i]-> { [n] -> 1/2 * n : (exists e0 = [n/2], e1, e2 = [n/4]: 2e0 = n and 5e2 = n and n = i and i = 4)};"), is(equalTo("[i] -> {  }")));
		
	}
	
	@Test 
	public void existsConstraintWithNewSintaxExpectations() {
		// Esperamos una unica declaracion de exists que englobe a todas las constratins
		assertThat(executor.execCommand("sum { [[n] -> [i]] -> i : n = 4*i };"), is(equalTo("{ [n] -> 1/4 * n : exists (e0 = [(n)/4]: 4e0 = n) }")));
		
		// Puede usarse el exists y hacer cte algun parametro
		assertThat(executor.execCommand("sum { [[i]->[n]] -> 1/2 * n : (exists e0 = [n/2], e1, e2 = [n/4]: 2e0 = n and 4e2 = n and n = i and i = 4)};"), is(equalTo("{ [i] -> 2 : i = 4 }")));
		assertThat(executor.execCommand("sum { [[i]->[n]] -> 1/2 * n : (exists e0 = [n/2], e1, e2 = [n/4]: 2e0 = n and 5e2 = n and n = i and i = 4)};"), is(equalTo("{  }")));
		
	}
}
