package ar.uba.dc.invariant.spec.compiler.constraints;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Ignore;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import ar.uba.dc.invariant.spec.compiler.constraints.parser.JEPConstraintsParser;
@Ignore

public class JEPConstraintsParserTest {

	@Test
	public void parseWithoutInclude() throws ParseException {
		ConstraintsParser parser = new JEPConstraintsParser();
		ConstraintsInfo info = parser.parse("($t.vector.length == args && args > 0) Or ($t.vector.length <= args AnD args < -5) || (j == 9)");
		
		assertThat(info, is(notNullValue()));
		assertThat(info.getReferences(), is(notNullValue()));
		assertThat(info.getReferences().size(), is(equalTo(0)));
		
		assertThat(info.getVariables().size(), is(equalTo(3)));
		assertThat(info.getVariables(), hasItem("$t.vector.length"));
		assertThat(info.getVariables(), hasItem("args"));
		assertThat(info.getVariables(), hasItem("j"));
	}
	
	@Test
	public void includeCouldHaveStringOrVariableAsArgument() throws ParseException {
		ConstraintsParser parser = new JEPConstraintsParser();
		ConstraintsInfo info = parser.parse("include(\"loop_invariant\") || ($t.vector.length == args && args > 0) || include(loop_invariant) || ($t.vector.length <= args && args < -5) || (j == 9) || include(loop)");
		
		assertThat(info, is(notNullValue()));
		assertThat(info.getReferences(), is(notNullValue()));
		assertThat(info.getReferences().size(), is(equalTo(2)));
		assertThat(info.getReferences(), hasItem("loop_invariant"));
		assertThat(info.getReferences(), hasItem("loop"));
		
		assertThat(info.getVariables().size(), is(equalTo(3)));
		assertThat(info.getVariables(), hasItem("$t.vector.length"));
		assertThat(info.getVariables(), hasItem("args"));
		assertThat(info.getVariables(), hasItem("j"));
	}
	
	@Test
	public void variableOfIncludeIsDoesNotFigureInVariablesUnlessItIsReferedOutsideTheInclude() throws ParseException {
		JEPConstraintsParser parser = new JEPConstraintsParser();
		ConstraintsInfo info = parser.parse("include(\"loop_invariant\") || loop == 7 || ($t.vector.length == args && args > 0) || include(loop_invariant) || ($t.vector.length <= args && args < -5) || (j == 9) || include(loop)");
		
		assertThat(info, is(notNullValue()));
		assertThat(info.getReferences(), is(notNullValue()));
		assertThat(info.getReferences().size(), is(equalTo(2)));
		assertThat(info.getReferences(), hasItem("loop_invariant"));
		assertThat(info.getReferences(), hasItem("loop"));
		
		assertThat(info.getVariables().size(), is(equalTo(4)));
		assertThat(info.getVariables(), hasItem("$t.vector.length"));
		assertThat(info.getVariables(), hasItem("args"));
		assertThat(info.getVariables(), hasItem("j"));
		assertThat(info.getVariables(), hasItem("loop"));
	}
	
	@Test
	public void dontGenerateVariableWithEmptyName() {
		JEPConstraintsParser parser = new JEPConstraintsParser();
		ConstraintsInfo info = parser.parse("include(\"loop_invariant\") and 1==1");
		
		assertThat(info, is(notNullValue()));
		assertThat(info.getReferences(), is(notNullValue()));
		assertThat(info.getReferences().size(), is(equalTo(1)));
		assertThat(info.getReferences(), hasItem("loop_invariant"));
		
		assertThat(info.getVariables().size(), is(equalTo(0)));
	}
}
