package ar.uba.dc.invariant.spec.compiler.constraints;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import ar.uba.dc.invariant.spec.compiler.constraints.parser.RegexpConstraintsParser;
import ar.uba.dc.invariant.spec.compiler.constraints.resolver.RegexpReferenceResolver;

public class RegexpConstraintsParserTest {

	@Test
	public void regexpTest() {
		Pattern pattern = Pattern.compile("\\B@(\\S+)\\b");		
		
		assertThat(finds(pattern.matcher("@loop_invariant")), equalTo(1));
		assertThat(finds(pattern.matcher("loop_@invariant")), equalTo(0));
		assertThat(finds(pattern.matcher("@loop_@invariant")), equalTo(1));
		assertThat(finds(pattern.matcher(" @loop_@invariant")), equalTo(1));
		assertThat(finds(pattern.matcher("@loop_@invariant ")), equalTo(1));
		assertThat(finds(pattern.matcher(" @loop_@invariant ")), equalTo(1));
		assertThat(finds(pattern.matcher(" @loop_@invariant @Loop")), equalTo(2));
		assertThat(finds(pattern.matcher("@Loop.invariant @loop_@invariant @Loop")), equalTo(3));
		assertThat(finds(pattern.matcher("@Lo123op.invariant ||   && @(Looop)  @loop_@invariant && @Loop")), equalTo(4));
	}
	
	private Integer finds(Matcher match) {
		Integer i = 0;
		while (match.find()) {
			System.out.println(match.group());
			i++;
		}
		System.out.println("");
		
		return i;
	}
	
	@Test
	public void parseWithoutInclude() throws ParseException {
		ConstraintsParser parser = new RegexpConstraintsParser();
		ConstraintsInfo info = parser.parse("($t.vector.length == args && args > 0) || ($t.vector.length <= args && args < -5) || (j == 9)");
		
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
		RegexpConstraintsParser parser = new RegexpConstraintsParser();
		parser.setResolver(new RegexpReferenceResolver());
		ConstraintsInfo info = parser.parse("@loop_invariant || ($t.vector.length == args && args > 0) || @loop_invariant || @this.is.an.invariant.id || ($t.vector.length <= args && args < -5) || (j == 9) || @loop");
		
		assertThat(info, is(notNullValue()));
		assertThat(info.getReferences(), is(notNullValue()));
		assertThat(info.getReferences().size(), is(equalTo(3)));
		assertThat(info.getReferences(), hasItem("loop_invariant"));
		assertThat(info.getReferences(), hasItem("loop"));
		assertThat(info.getReferences(), hasItem("this.is.an.invariant.id"));
		
		assertThat(info.getVariables().size(), is(equalTo(3)));
		assertThat(info.getVariables(), hasItem("$t.vector.length"));
		assertThat(info.getVariables(), hasItem("args"));
		assertThat(info.getVariables(), hasItem("j"));
	}
	
	@Test
	public void variableOfIncludeIsDoesNotFigureInVariablesUnlessItIsReferedOutsideTheInclude() throws ParseException {
		RegexpConstraintsParser parser = new RegexpConstraintsParser();
		parser.setResolver(new RegexpReferenceResolver());
		ConstraintsInfo info = parser.parse("@loop_invariant oR loop == 7 || ($t.vector.length == args aNd args > 0) || @loop_invariant || ($t.vector.length <= args && args < -5) || (j == 9) || @loop");
		
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
}
