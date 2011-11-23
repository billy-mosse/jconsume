package ar.uba.dc.analysis;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({	ar.uba.dc.analysis.escape.IntraproceduralTest.class,
				ar.uba.dc.analysis.memory.IntraproceduralTest.class
				})
public class IntraproceduralSuit {

}
