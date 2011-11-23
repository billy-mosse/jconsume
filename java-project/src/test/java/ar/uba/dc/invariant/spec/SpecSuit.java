package ar.uba.dc.invariant.spec;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ar.uba.dc.invariant.spec.compiler.FastSpecCompilerTest;
import ar.uba.dc.invariant.spec.compiler.FullReferencesSpecCompilerTest;
import ar.uba.dc.invariant.spec.compiler.SimpleReferenceSpecCompilerTest;
import ar.uba.dc.invariant.spec.compiler.constraints.JEPConstraintsParserTest;
import ar.uba.dc.invariant.spec.compiler.constraints.RegexpConstraintsParserTest;
import ar.uba.dc.invariant.spec.compiler.constraints.resolver.RegexpReferenceResolverTest;
import ar.uba.dc.invariant.spec.reader.XStreamSpecReaderTest;

@RunWith(Suite.class)
@SuiteClasses({	XStreamSpecReaderTest.class, 
				FastSpecCompilerTest.class,
				SimpleReferenceSpecCompilerTest.class,
				FullReferencesSpecCompilerTest.class,
				RegexpReferenceResolverTest.class,
				JEPConstraintsParserTest.class,
				RegexpConstraintsParserTest.class })
public class SpecSuit {

}
