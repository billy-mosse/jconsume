package ar.uba.dc.barvinok;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@Ignore
@RunWith(Suite.class)
@SuiteClasses({	BarvinokExpectationsTest.class,
				BarvinokUtilsTest.class,
				BarvinokCalculatorWithAddAsMaxStrategyTest.class, 
				BarvinokCalculatorWithLazyAsMaxStrategyTest.class,
				BarvinokCalculatorBindingProblemsTest.class
				})
public class BarvinokCalculatorSuit {

}
